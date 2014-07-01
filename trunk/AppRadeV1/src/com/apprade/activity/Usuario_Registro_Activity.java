package com.apprade.activity;


import java.util.Calendar;

import com.apprade.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
	private EditText etRepitepass;
	private RadioGroup rgSexo;
	 private ImageButton ib;
	 private Calendar cal;
	 private int day;
	 private int month;
	 private int year;
	
	
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
		etApellidos = (EditText)findViewById(R.id.et_paterno);
		etCorreo = (EditText)findViewById(R.id.et_correo);
		ib = (ImageButton) findViewById(R.id.imb_date);
		rgSexo = (RadioGroup)findViewById(R.id.rg_sexo);
		
		 ib.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  showDialog(0);
			}
		});
		
		btnSend.setOnClickListener( new OnClickListener() {			
			@Override
			public void onClick(View v) {
//				
//				  cal = Calendar.getInstance();
//				  day = cal.get(Calendar.DAY_OF_MONTH);
//				  month = cal.get(Calendar.MONTH);
//				  year = cal.get(Calendar.YEAR);
												
				String sexo = chkRadiobuttons();				
				Toast.makeText(getApplicationContext(),"Prueba: "+sexo, Toast.LENGTH_SHORT).show();			
					
						
			}
		});
	}
		 
	 protected Dialog onCreateDialog(int id) {
		  return new DatePickerDialog(this, datePickerListener, year, month, day);
		 }
	 
	 private String chkRadiobuttons(){
		 String sSexo = null;
			int idSeleccionado = rgSexo.getCheckedRadioButtonId();
			if (idSeleccionado!=-1) {
				RadioButton rbMas = (RadioButton)findViewById(R.id.rb_masculino);
				if (rbMas.isChecked()) 
				 sSexo = "M";
				else
				sSexo = "F";				
			}
			else
				sSexo = "-1";
			
			return sSexo;
	 }
	 
	 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		  public void onDateSet(DatePicker view, int selectedYear,
		    int selectedMonth, int selectedDay) {
			  String sFecha = (selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
			  Toast.makeText(getApplicationContext(),(sFecha), Toast.LENGTH_LONG).show();
		  }
		 };	
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
