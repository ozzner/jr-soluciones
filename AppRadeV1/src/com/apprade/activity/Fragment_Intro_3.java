package com.apprade.activity;

import com.apprade.R;
import com.apprade.activity.Usuario_Registro_Activity.TaskHttpMethodAsync;
import com.apprade.dao.DAO_Usuario;
import com.apprade.helper.Helper_SharedPreferences;

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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Fragment_Intro_3 extends Fragment{
	private static final int DATE_DIALOG_ID = 1;
	private Button btnSend;
	private EditText etNombres;
	private EditText etCorreo;
	private EditText etPassword;
	private EditText etConfPassword;
	private RadioGroup rgSexo;
	private RadioButton selectRadio;
	private TextView txFecha;
	private ImageButton ib;
	private int day;
	private int month;
	private int year;
	private ProgressDialog proDialog;
	private String sFecha = "2006-05-18", sNombre, sEmail, sPassword,
			sPassword2, sSexo;
	private ActionBar actionBar;
	private DAO_Usuario dao;

	public Fragment_Intro_3() {
		dao = new DAO_Usuario();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_intro_3, container, false);

		

		btnSend = (Button) v.findViewById(R.id.btn_enviar);
		etNombres = (EditText) v.findViewById(R.id.et_nombres);
		etPassword = (EditText) v.findViewById(R.id.et_password_reg);
		etConfPassword = (EditText) v.findViewById(R.id.et_pass_confirmar);
		etCorreo = (EditText) v.findViewById(R.id.et_correo);
		ib = (ImageButton) v.findViewById(R.id.imb_date);
		rgSexo = (RadioGroup) v.findViewById(R.id.rg_sexo);
		selectRadio = (RadioButton) v.findViewById(rgSexo.getCheckedRadioButtonId());
		

		
		ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				EnviarRegistro();

			}
		});

		return v;
	}

	
	
	
	public void EnviarRegistro() {

		sNombre = etNombres.getText().toString();
		sEmail = etCorreo.getText().toString();
		sPassword = etPassword.getText().toString();
		sPassword2 = etConfPassword.getText().toString();
		String sexo = selectRadio.getText().toString();

		boolean esError = false;

		if (sexo == "Masculino")
			sSexo = "M";
		else
			sSexo = "F";

		if (sEmail.compareTo("") == 0) {
			etCorreo.setError("Debes ingresar un Correo");
			esError = true;
		}

		if (sPassword.compareTo("") == 0) {
			etPassword.setError("Debes ingresar un Password");
			esError = true;
		}

		if (sPassword2.compareTo("") == 0) {
			etConfPassword.setError("Debes confirmar tu Password");
			esError = true;
		}

		if (sNombre.compareTo("") == 0) {
			etNombres.setError("Debes ingresar un nombre");
			esError = true;
		}

		if (sPassword.equals(sPassword2)) {
			new TaskHttpMethodAsync().execute();
		} else {
			Toast.makeText(getActivity() ,
					"Las contraseñas no coincien", Toast.LENGTH_LONG).show();
			etPassword.setText("");
			etConfPassword.setText("");
		}

		if (esError)
			return;

	}

	protected Dialog onCreateDialog(int id) {
		
		return new DatePickerDialog(getActivity(), datePickerListener, year, month, day);
		
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			sFecha = (selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
			Toast.makeText(getActivity(), (sFecha), Toast.LENGTH_LONG)
					.show();
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
					oShaPre.storeLogin(sNombre, sEmail, dao.oUsuario.getUsuarioID(),1,getActivity());
					
					Toast.makeText(getActivity(),"Hola "+ sNombre+", ya puedes iniciar sesión con tus datos, presiona Login " , Toast.LENGTH_LONG).show();
					Intent i = new Intent (getActivity(),Usuario_Login_Activity.class);
					i.putExtra("correo", sEmail);
					i.putExtra("password", sPassword);
					startActivity(i);
					getActivity().finish();
				}else{
					Toast.makeText(getActivity(),dao.oJsonStatus.getMessage()+"\nInfo: "+dao.oJsonStatus.getInfo(),Toast.LENGTH_LONG).show();
				}
				
				
			}
							
		}	
	
	
	public void showDialogo() {
		proDialog = new ProgressDialog(getActivity());
		proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialog.setMessage("Enviando...");
		proDialog.show();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}//End class
