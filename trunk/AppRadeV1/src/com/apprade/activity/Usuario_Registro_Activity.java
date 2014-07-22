package com.apprade.activity;


import java.util.Calendar;

import com.apprade.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
	private EditText etApellidos;
	private EditText etCorreo;
	private EditText etPassword;
	private EditText etConfPassword;
	private EditText etRepitepass;
	private RadioGroup rgSexo;
	private TextView txFecha;
	private ImageButton ib;
	private Calendar cal;
	private int day;
	private int month;
	private int year;
	private String sFecha;
	private String saludo,sSexo;
	private  ActionBar actionBar;
	
	
	
	
	public Usuario_Registro_Activity() {
		super();
		
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
		txFecha= (TextView) findViewById(R.id.txtFecha);
	
		
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
		}
	 });
	
}
	
			public void EnviarRegistro() {

				
				 RadioButton selectRadio = (RadioButton) findViewById(rgSexo
			             .getCheckedRadioButtonId());
				 

				 String sNombre = etNombres.getText().toString();
				 String sEmail = etCorreo.getText().toString();
				 String sPassword = etPassword.getText().toString();
				 String sPassword2 = etConfPassword.getText().toString();
				 
				 String sexo = selectRadio.getText().toString();
					
				 boolean esError=false;
					
					if(sEmail.compareTo("")==0){
						etCorreo.setError("Debes ingresar un Correo");
			    		esError=true;
			    	}
					
					if(sPassword.compareTo("")==0){
						etPassword.setError("Debes ingresar un Password");
			    		esError=true;
			    	}
					
					if(sNombre.compareTo("")==0){
						etNombres.setError("Debes ingresar un nombre");
			    		esError=true;
			    	}
					
					if(sPassword2.compareTo("")==0){
						etConfPassword.setError("Debes confirmar tu Password");
			    		esError=true;
			    	}
					
					if(esError)
						return;
				
				 
				 
				 
				 
				 
				 
//				 
//				 
//				 
//				 
//					 					 
//					 try {
//						 
//						 Intent i = new Intent (this, App_GPSMapa_Activity.class);
//						 i.putExtra("NOMBRE", nom);
//						 i.putExtra("CORREO", correo);
//						 i.putExtra("PASSWORD", pass);
//						 
//						 Toast.makeText(this, saludo+nom, Toast.LENGTH_LONG).show();
//						 startActivity(i);
//						 finish();
//						 
//						
//					} catch (Exception e) {
//						
//					}
//					 
//					 
//				 }
//				 
//				 else {
//					 
//					 Toast.makeText(this, "Faltan completar 1 o más campos", Toast.LENGTH_LONG).show();
//				 }
			
	
	}
		 
	 protected Dialog onCreateDialog(int id) {
		  return new DatePickerDialog(this, datePickerListener, year, month, day);
		 }
    
	 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		  public void onDateSet(DatePicker view, int selectedYear,
		    int selectedMonth, int selectedDay) {
			  sFecha = (selectedDay + " - " + (selectedMonth + 1) + " - " + selectedYear);
//			  Toast.makeText(getApplicationContext(),(sFecha), Toast.LENGTH_LONG).show();
			  txFecha.setText(sFecha);
			  return;
			  
			  			  
		  }
		  
		 };	
		 

		 @Override
		   public boolean onCreateOptionsMenu(Menu menu) {
		     MenuInflater inflater = getMenuInflater();
		     inflater.inflate(R.menu.registro_menu, menu);
		     
		     return true;
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
