package com.apprade.activity;


import java.util.Calendar;

import com.apprade.R;
import com.apprade.activity.Usuario_Login_Activity.TaskHttpMethodAsync;
import com.apprade.dao.DAO_Usuario;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Usuario_Registro_Activity extends Activity {
	private Button btnSend;
	private EditText etNombres;
	private EditText etCorreo;
	private EditText etPassword;
	private EditText etConfPassword;
	private RadioGroup rgSexo;
	private TextView txFecha;
	private ImageButton ib;
	private int day;
	private int month;
	private int year;
	private ProgressDialog proDialog;
	private String sFecha="2006-05-18",sNombre,sEmail,sPassword,sPassword2,sSexo;
	private ActionBar actionBar;
	private DAO_Usuario dao;
	private Helper_SubRoutines oRoutine;
	private static final String TAG_VACIO = "";
	
	public Usuario_Registro_Activity() {
		super();
		dao = new DAO_Usuario();
		oRoutine = new Helper_SubRoutines();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_intro_3);
				
		btnSend = (Button)findViewById(R.id.btn_enviar);
		etNombres = (EditText)findViewById(R.id.et_nombres);
		etPassword = (EditText)findViewById(R.id.et_password_reg);
		etConfPassword = (EditText)findViewById(R.id.et_pass_confirmar);
		etCorreo = (EditText)findViewById(R.id.et_correo);
		ib = (ImageButton) findViewById(R.id.imb_date);
		rgSexo = (RadioGroup)findViewById(R.id.rg_sexo);
//		txFecha= (TextView) findViewById(R.id.txtFecha);
	
		
		 ib.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  showDialog(0);
			}
		 });
	
		 btnSend.setOnClickListener( new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			
			EnviarRegistro();
//			exeHttpAsync();
				
		}
	 });
	
}
	
	
	private boolean validarRegistro(){

		 boolean esError=false;
		 RadioButton selectRadio = (RadioButton) findViewById(rgSexo
	             .getCheckedRadioButtonId());
		  String sexo = selectRadio.getText().toString();
		  
		  sSexo = sexo;
		  
		  if(sNombre.compareTo("")==0){
				etNombres.setError("Debes ingresar tu nombre");
	    		esError=true;
	    	}
		  
			if(sEmail.compareTo("")==0){
				etCorreo.setError("Debes ingresar un Correo");
	    		esError=true;
	    	}
			
			if(sPassword.compareTo("")==0){
				etPassword.setError("Debes ingresar un Password");
	    		esError=true;
	    	}
			
			if(sPassword2.compareTo("")==0){
				etConfPassword.setError("Debes confirmar tu Password");
	    		esError=true;
	    	}
			
			if (sFecha.equals("2006-05-18")) {
				oRoutine.showToast(getApplicationContext(), "Ingrese fecha nacimiento");
				esError =  true;
			}else{
				
				if (sFecha.equals(oRoutine.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_DATE_MM))) {
					esError=true;
					oRoutine.showToast(getApplicationContext(), "Ingrese fecha correcta");
				}
			}
			
			return esError;
	}
	
	
			public void EnviarRegistro() {

				  sNombre = etNombres.getText().toString();
				  sEmail = etCorreo.getText().toString();
				  sPassword = etPassword.getText().toString();
				  sPassword2 = etConfPassword.getText().toString();				 
				 
				  if (!validarRegistro())
					  if(sPassword.equals(sPassword2))
							new TaskHttpMethodAsync().execute();
					  else{
							Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
							etPassword.setText(TAG_VACIO);
							etConfPassword.setText(TAG_VACIO);
						}
	}
			
			 protected Dialog onCreateDialog(int id) {
				 
				 
				 // Use the current date as the default date in the picker
			        final Calendar c = Calendar.getInstance();
			        int year = c.get(Calendar.YEAR);
			        int month = c.get(Calendar.MONTH);
			        int day = c.get(Calendar.DAY_OF_MONTH);

			        // Create a new instance of DatePickerDialog and return it
				 
				  return new DatePickerDialog(this, datePickerListener, year, month, day);
				 }
		    
			 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
				  public void onDateSet(DatePicker view, int selectedYear,
				    int selectedMonth, int selectedDay) {
						sFecha = (selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
			  		Toast.makeText(getApplicationContext(),(sFecha), Toast.LENGTH_SHORT).show();
					  return;
				}
				   };	 
				 

		
		    class TaskHttpMethodAsync extends AsyncTask<String, Void,Boolean>{

		    @Override
		    protected Boolean doInBackground(String... params) {
					boolean bRequest = false;
										
					if (dao.registarUsuario(sEmail, sSexo, sNombre, sPassword, sFecha)) 
						bRequest = true;

					return bRequest;
				}
		    

		    protected void onPreExecute() {
		    	
		    	showDialogo();
		    	
		    	proDialog.setOnCancelListener(new OnCancelListener() {
		    	
		        @Override
			    public void onCancel(DialogInterface dialog) {
			    	TaskHttpMethodAsync.this.cancel(true);  }
			    });
			    proDialog.setProgress(0);
		    }
		    
		    
		    @Override
			protected void onPostExecute(Boolean result) {		
					super.onPostExecute(result);
					proDialog.dismiss();
					
					if (result) {
						
						Helper_SharedPreferences oShaPre =  new Helper_SharedPreferences();
						oShaPre.storeLogin(sNombre, sEmail, dao.oUsuario.getUsuarioID(),1,getApplicationContext());
						
						Toast.makeText(getApplicationContext(),"Hola "+ sNombre+", ya puedes iniciar sesión con tus datos, presiona Login " , Toast.LENGTH_LONG).show();
						Intent i = new Intent (getApplicationContext(),Usuario_Login_Activity.class);
						i.putExtra("correo", sEmail);
						i.putExtra("password", sPassword);
						startActivity(i);
						finish();
					}else{
						Toast.makeText(getApplicationContext(),dao.oJsonStatus.getMessage()+"\nInfo: "+dao.oJsonStatus.getInfo(),Toast.LENGTH_LONG).show();
					}
					
					
				}
								
			}		
			
//			protected void llamarMapa() {
//				
//				Intent i = new Intent(this, App_GPSMapa_Activity.class);
//				startActivity(i);
//				finish();				
//			}		 

		 @Override
		   public boolean onCreateOptionsMenu(Menu menu) {
		     MenuInflater inflater = getMenuInflater();
		     inflater.inflate(R.menu.registro_menu, menu);
		     
		     return true;
		   } 
		   
		 
		 
		public void showDialogo() {
			proDialog = new ProgressDialog(Usuario_Registro_Activity.this);
			proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			proDialog.setMessage("Enviando...");
			proDialog.show();
		}

		@Override
		   public boolean onOptionsItemSelected(MenuItem item) {
			  
			  actionBar = getActionBar();

			  switch (item.getItemId()) {
		
			     case R.id.reg_login_action:
					Intent login = new Intent(getApplicationContext(), Usuario_Login_Activity.class);
					startActivity(login);
					finish();
			       break;
	
			     case R.id.reg_about_action:
				   actionBar.setSubtitle("About app");
			       break;      
			       
			     default:
			       break;
		     }

		     return true;
		   } 	 
		 
		 
}

// check network connection
//	    public boolean isConnected(){
//	    	
//	        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE); 
//
//
//	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//	            if (networkInfo != null && networkInfo.isConnected()) 
//	                return true;
//	            else
//	                return false;   
//	    }
		

//	
//    public void btnRegistrar_onClick (View v) {
//    	
//    	Intent i = new Intent(this, Usuario_Login_Activity.class);
//    	startActivity(i);
//    	finish();
//        
//        
//    } 
//
//}



// 
//try {
//
//Intent i = new Intent (this, App_GPSMapa_Activity.class);
//i.putExtra("NOMBRE", nom);
//i.putExtra("CORREO", correo);
//i.putExtra("PASSWORD", pass);
//
//Toast.makeText(this, saludo+nom, Toast.LENGTH_LONG).show();
//startActivity(i);
//finish();
//
//
//} catch (Exception e) {
//
//}
//
//
//}
//
//else {
//
//Toast.makeText(this, "Faltan completar 1 o más campos", Toast.LENGTH_LONG).show();
//}
