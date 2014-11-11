package com.apprade.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.dao.DAO_Usuario;
import com.apprade.entity.Entity_Ranking;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;


@SuppressLint("NewApi")
public class Usuario_Login_Activity extends Activity implements ValidationListener {
	
	private Validator validator;
	@TextRule(order = 2, minLength = 3, message = "Ingrese min 3 caracteres.")
	@Required(order = 1, message = "Este campo es requerido.")
	private EditText password,email;	
	private Button btnLogin;
	private DAO_Usuario dao;
	public Entity_Ranking rank;
	private String sEmail="",sPassword="";
    private ActionBar actionBar;
	private ProgressDialog proDialogo;
	private Helper_SubRoutines oRoutine;
	private static final String TAG_VACIO = "";
	
	public Usuario_Login_Activity() {
		super();
		oRoutine = new Helper_SubRoutines();
		dao= new DAO_Usuario();
		rank = new Entity_Ranking();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_login);
	
		email = (EditText)findViewById(R.id.txtEmail);
		password= (EditText)findViewById(R.id.txtPassword);
		btnLogin = (Button)findViewById(R.id.btnLogin);

		email.setText(getIntent().getStringExtra("correo"));
		password.setText(getIntent().getStringExtra("password"));
		
		validator = new Validator(this);
		validator.setValidationListener(this);
		
		btnLogin.setOnClickListener( new OnClickListener() {			
			
			@Override
			public void onClick(View v) {
				
				validator.validate();
			}
		});			
	}
		
	

	protected boolean validarCampos(){
		boolean esError=false;
		
		sEmail = email.getText().toString();
		sPassword = password.getText().toString();
		
		if(sEmail.compareTo("")==0){
			email.setError("Debes ingresar un Correo");
			
    	}else if(sPassword.compareTo("")==0){
			password.setError("Debes ingresar un Password");
    		
    	}else{
    		esError=true;
    	}
		
		return esError;
		
	}
	
	protected void showDialogo(){
		proDialogo = new ProgressDialog(Usuario_Login_Activity.this);
		proDialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialogo.setMessage("Conectando...");
		proDialogo.show();
	}
	
	
	protected void llamarMapa() {
		
		Intent mapa = new Intent(getApplicationContext(), App_GPSMapa_Activity.class);
		Log.e("oUserID - login", dao.oUsuario.getUsuarioID()+"");
		mapa.putExtra("user_id",dao.oUsuario.getUsuarioID());
		mapa.putExtra("user",dao.oUsuario.getNombre());
		startActivity(mapa);
		finish();
			
	}

	
    class TaskHttpMethodAsync extends AsyncTask<String, Void,Boolean>{

    @Override
    protected Boolean doInBackground(String... params) {
			boolean bRequest = false;
	
			sEmail = email.getText().toString();
			sPassword = password.getText().toString();
			
			if (dao.loginUsuario(sEmail, sPassword)) 
				bRequest = true;
	
			return bRequest;
		}

    @Override
    protected void onPreExecute() {
    	
    	showDialogo();
    	
    	proDialogo.setOnCancelListener(new OnCancelListener() {
    	
        @Override
	    public void onCancel(DialogInterface dialog) {
	    	TaskHttpMethodAsync.this.cancel(true);  }
	    });
	    proDialogo.setProgress(0);
	   // pDialog.show();
    }
    
    @Override
	protected void onPostExecute(Boolean result) {		
			super.onPostExecute(result);
			proDialogo.dismiss();
			
			if (result) {
				llamarMapa();
				Helper_SharedPreferences oShared = new Helper_SharedPreferences();
				oShared.getAllLoginDataStored(getApplicationContext());
				
				oShared.storeLogin(dao.oUsuario.getNombre(),dao.oUsuario.getEmail() ,dao.oUsuario.getUsuarioID(),1, getApplicationContext());

			}else{
				Toast.makeText(getApplicationContext(),dao.oJsonStatus.getMessage()+". "+dao.oJsonStatus.getInfo()+".",Toast.LENGTH_SHORT).show();
				password.setText(TAG_VACIO);
				email.setText(TAG_VACIO);
			}
		}
    
    @Override
    protected void onCancelled() {
    }
			
}//End ClassAsync

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.login_menu, menu);
	     
	     return true;
	   } 
	   
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		  
		  actionBar = getActionBar();
	     switch (item.getItemId()) {
	
	     case R.id.log_login_action:
	    	 actionBar.setSubtitle("Login");
	    	 validator.validate();
	       break;
	       
	     case R.id.log_about_action:
	    	 
		   actionBar.setSubtitle("Acerca de");
		   LoadInfo();
		   
	       break;
	       
	     case R.id.log_registro_action:
	    	 
		       Intent i = new Intent(getApplicationContext(),Usuario_Registro_Activity.class);
		       startActivity(i);
			   finish();
		       break;
	       
	     default:
	       break;
	     }

	     return true;
	   } 
	
	
	private void LoadInfo() {
		final View v;
		AlertDialog.Builder adInfo = new AlertDialog.Builder(Usuario_Login_Activity.this);
		
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
	
	public void tvRegistrar_onClick (View v){
		
		Intent i = new Intent (this, Usuario_Registro_Activity.class);
		startActivity(i);
		finish();
	}

	@Override
	public void onValidationSucceeded() {
		
		if (oRoutine.isOnline(getApplicationContext())) 
			new TaskHttpMethodAsync().execute();	
		else
			Toast.makeText(getApplicationContext(), "Necesita tener conexión a Internet.", Toast.LENGTH_SHORT).show();
			
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
	
}

