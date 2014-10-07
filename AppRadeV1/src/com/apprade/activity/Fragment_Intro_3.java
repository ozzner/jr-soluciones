package com.apprade.activity;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.adapter.Adapter_Dialog_Fragment;
import com.apprade.dao.DAO_Usuario;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;

public class Fragment_Intro_3 extends Fragment {

	private static final CharSequence TAG_VACIO = "";
	private Helper_SubRoutines oRoutine;
	Adapter_Dialog_Fragment oDialFrag;
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
		oRoutine = new Helper_SubRoutines();
		oDialFrag =  new Adapter_Dialog_Fragment();
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
		selectRadio = (RadioButton) v.findViewById(rgSexo
				.getCheckedRadioButtonId());

		ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			    oDialFrag = new Adapter_Dialog_Fragment();
				oDialFrag.show(getChildFragmentManager(), "MyDataPiker");

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

	private boolean validarRegistro() {

		boolean esError = false;

		String sexo = selectRadio.getText().toString();

		sSexo = sexo;
Log.e("FECHA-MM", oRoutine
		.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_DATE_MM));
		if (sNombre.compareTo("") == 0) {
			etNombres.setError("Debes ingresar tu nombre");
			esError = true;
		}

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

		if (sFecha==null) {
			oRoutine.showToast(getActivity(), "Ingrese fecha nacimiento");
			esError = true;
		} else {

			if (sFecha.equals(oRoutine
					.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_DATE_MM))) {
				esError = true;
				oRoutine.showToast(getActivity(), "Ingrese fecha correcta");
			}
		}

		return esError;
	}

	public void EnviarRegistro() {

		sNombre = etNombres.getText().toString();
		sEmail = etCorreo.getText().toString();
		sPassword = etPassword.getText().toString();
		sPassword2 = etConfPassword.getText().toString();
		
	    oDialFrag = new Adapter_Dialog_Fragment();
		sFecha = oDialFrag.getsFecha();

		if (!validarRegistro())
			if (sPassword.equals(sPassword2))
				new TaskHttpMethodAsync().execute();
			else {
				Toast.makeText(getActivity(), "Las contrase�as no coinciden",
						Toast.LENGTH_LONG).show();
				etPassword.setText(TAG_VACIO);
				etConfPassword.setText(TAG_VACIO);
			}

	}

	class TaskHttpMethodAsync extends AsyncTask<String, Void, Boolean> {

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
					TaskHttpMethodAsync.this.cancel(true);
				}
			});
			proDialog.setProgress(0);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialog.dismiss();

			if (result) {

				Helper_SharedPreferences oShaPre = new Helper_SharedPreferences();
				oShaPre.storeLogin(sNombre, sEmail,
						dao.oUsuario.getUsuarioID(), 1, getActivity());

				Toast.makeText(
						getActivity(),
						"Hola "
								+ sNombre
								+ ", ya puedes iniciar sesi�n con tus datos, presiona Login ",
						Toast.LENGTH_LONG).show();
				Intent i = new Intent(getActivity(),
						Usuario_Login_Activity.class);
				i.putExtra("correo", sEmail);
				i.putExtra("password", sPassword);
				startActivity(i);
				getActivity().finish();
			} else {
				Toast.makeText(
						getActivity(),
						dao.oJsonStatus.getMessage() + "\nInfo: "
								+ dao.oJsonStatus.getInfo(), Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	public void showDialogo() {
		proDialog = new ProgressDialog(getActivity());
		proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialog.setMessage("Enviando...");
		proDialog.show();
	}

}// End class