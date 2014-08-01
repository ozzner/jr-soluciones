package com.apprade.activity;


import java.util.Calendar;

import com.apprade.R;
import com.apprade.activity.Usuario_Login_Activity.TaskHttpMethodAsync;
import com.apprade.dao.DAO_Usuario;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
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
	private EditText etFecha;
	private RadioGroup rgSexo;
	private TextView txFecha;
	private ImageButton ib;
	private int day;
	private int month;
	private int year;
	private ProgressDialog proDialog;
	private String sFecha="2006-05-18",sNombre,sEmail,sPassword,sPassword2,sSexo;
	private  ActionBar actionBar;
	private DAO_Usuario dao;
	
	
	public Usuario_Registro_Activity() {
		super();
		dao = new DAO_Usuario();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiy_usuario_registro);
				
		btnSend = (Button)findViewById(R.id.btn_enviar);
		etNombres = (EditText)findViewById(R.id.et_nombres);
		etPassword = (EditText)findViewById(R.id.et_password_reg);
		etConfPassword = (EditText)findViewById(R.id.et_pass_confirmar);
		etCorreo = (EditText)findViewById(R.id.et_correo);
		ib = (ImageButton) findViewById(R.id.imb_date);
		rgSexo = (RadioGroup)findViewById(R.id.rg_sexo);
//		txFecha= (TextView) findViewById(R.id.txtFecha);
		etFecha = (EditText)findViewById(R.id.edt_fecha);
		
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
			public void EnviarRegistro() {
				
				 RadioButton selectRadio = (RadioButton) findViewById(rgSexo
			             .getCheckedRadioButtonId());
				 

				  sNombre = etNombres.getText().toString();
				  sEmail = etCorreo.getText().toString();
				  sPassword = etPassword.getText().toString();
				  sPassword2 = etConfPassword.getText().toString();				 
				  String sexo = selectRadio.getText().toString();

				 boolean esError=false;
					
				 	if (sexo == "Masculino") 
						sSexo = "M";
					 else
						 sSexo = "F";
				 
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
					
					if(sNombre.compareTo("")==0){
						etNombres.setError("Debes ingresar un nombre");
			    		esError=true;
			    	}
					
					if(sPassword.equals(sPassword2)){
						exeHttpAsync();
					}
					else{
						Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
						etPassword.setText("");
						etConfPassword.setText("");
					}
										
					if(esError)
						return;
								 
				
	}
			
			 protected Dialog onCreateDialog(int id) {
				  return new DatePickerDialog(this, datePickerListener, year, month, day);
				 }
		    
			 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
				  public void onDateSet(DatePicker view, int selectedYear,
				    int selectedMonth, int selectedDay) {
						sFecha = (selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
//				  		Toast.makeText(getApplicationContext(),(sFecha), Toast.LENGTH_LONG).show();
						etFecha.setText(sFecha);
					  return;
					  			  }
				   };	 
				 

			private void exeHttpAsync(){
				TaskHttpMethodAsync task =  new TaskHttpMethodAsync();
				task.execute();
			}
			
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
						Toast.makeText(getApplicationContext(), "Mensaje: "+dao.oJsonStatus.getMessage(), Toast.LENGTH_LONG).show();
						Intent i = new Intent (getApplicationContext(),App_GPSMapa_Activity.class);
						startActivity(i);
					}else{
						Toast.makeText(getApplicationContext()," error: "+dao.oJsonStatus.getMessage()+" Info: "+dao.oJsonStatus.getInfo(),Toast.LENGTH_LONG).show();
					}
				}
								
			}		
			
			protected void llamarMapa() {
				
				Intent i = new Intent(this, App_GPSMapa_Activity.class);
				startActivity(i);
				finish();				
			}		 

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
		       Toast.makeText(this, "Accion  LOGIN", Toast.LENGTH_SHORT)
		           .show();
			   actionBar.setSubtitle("Inicio sesion");
		       break;

		     case R.id.reg_about_action:
		       Toast.makeText(this, "Accion ABOUT", Toast.LENGTH_SHORT)
		           .show();
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
