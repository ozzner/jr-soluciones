package com.apprade.activity;


import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.Toast;

import com.apprade.R;
import com.apprade.dao.DAO_Usuario;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

public class Usuario_Registro_Activity extends Activity implements ValidationListener {
	
	private Validator validator ;
	private Button btnSend;
	private EditText etNombres;
	@Required(order = 1, message = "Este campo es requerido.")
	@TextRule(order = 2, minLength = 3,trim = true)
	private EditText etCorreo;
	@Password(order = 3, message = "Debe ingresar un password")
	@TextRule(order = 4, minLength = 3, message = "Ingrese min 3 caracteres.",trim = true)
	private EditText etPassword;
	@ConfirmPassword(order = 5, message = "Las contraseñas no son iguales")
	private EditText etConfPassword;
	private RadioGroup rgSexo;
	private ImageButton ib;
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
		
		validator = new Validator(this);
		validator.setValidationListener(this);
		
		
		 ib.setOnClickListener( new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				  showDialog(0);
			}
		 });
	
		 btnSend.setOnClickListener( new OnClickListener() {
			
		@Override
		public void onClick(View v) {
			validator.validate();
		}
	 });
	
}
	
	
	private boolean validarRegistro(){

		 boolean esError=false;
		 RadioButton selectRadio = (RadioButton) findViewById(rgSexo
	             .getCheckedRadioButtonId());
		  String sexo = selectRadio.getText().toString();
		  sSexo = sexo;
		  
			if (sFecha.equals("2006-05-18")) {
				oRoutine.showToast(getApplicationContext(), "Ingrese fecha nacimiento");
				esError =  true;
			}else{
				if (sFecha.equals(oRoutine
						.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_DATE_MM))) {
					esError=true;
					oRoutine.showToast(getApplicationContext(), "Ingrese fecha correcta");
				}
			}
			
			return esError;
	}
	
			 protected Dialog onCreateDialog(int id) {
				 
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
						sFecha = oRoutine.customDateConverter(sFecha, "yyyy-MM-dd", "yyyy-MM-dd");
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
				   LoadInfo();
			       break;      
			       
			     default:
			       break;
		     }

		     return true;
		   }

		@Override
		public void onValidationSucceeded() {
			
			 if (!validarRegistro()){
				 sNombre = etNombres.getText().toString().trim();
				 sEmail = etCorreo.getText().toString().trim();
				 sPassword = etConfPassword.getText().toString().trim();
				 
				 new TaskHttpMethodAsync().execute();
			 }
					
		}
		

		@Override
		public void onValidationFailed(View failedView, Rule<?> failedRule) {
			String message = failedRule.getFailureMessage();

	        if (failedView instanceof EditText) {
	            failedView.requestFocus();
	            ((EditText) failedView).setError(message);
	        } else {
	            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	        }
		} 	 
		 
		
		
		
		private void LoadInfo() {
			final View v;
			
			AlertDialog.Builder adInfo = new AlertDialog.Builder(Usuario_Registro_Activity.this);
			
			LayoutInflater layInfo = this.getLayoutInflater();
			v = layInfo.inflate(R.layout.dialog_custom_about, null);
			adInfo.setView(v);
			
			adInfo.setNeutralButton("Okay",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
			
			adInfo.show();
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
