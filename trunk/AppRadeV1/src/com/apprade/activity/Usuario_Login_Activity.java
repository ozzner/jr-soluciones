package com.apprade.activity;

import com.apprade.R;






import com.apprade.dao.DAO_Usuario;
import com.apprade.entity.Entity_Ranking;
import com.apprade.entity.Entity_Usuario;
import com.apprade.helper.Helper_JSONStatus;
import com.apprade.helper.Helper_SharedPreferences;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


@SuppressLint("NewApi")
public class Usuario_Login_Activity extends Activity {
	
	private EditText password,email;	
	private Button btnLogin;
	private DAO_Usuario dao;
	public Entity_Ranking rank;
	private String sEmail="",sPassword="";
    private ActionBar actionBar;
	private ProgressDialog proDialogo;
	
	
	public Usuario_Login_Activity() {
		super();
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
		
//		ActionBar Bar = getActionBar();
//		Bar.setIcon(R.drawable.check_user);
		
		
		
		email.setText(getIntent().getStringExtra("correo"));
		password.setText(getIntent().getStringExtra("password"));
		
		
		btnLogin.setOnClickListener( new OnClickListener() {			
			
			@Override
			public void onClick(View v) {
				
				if (validarCampos()) 
					exeHttpAsync();
					
					 
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

	private void exeHttpAsync(){
		TaskHttpMethodAsync task =  new TaskHttpMethodAsync();
		task.execute();
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
				oShared.getAlldataStore(getApplicationContext());
				
				oShared.storeLogin(dao.oUsuario.getNombre(),dao.oUsuario.getEmail() ,dao.oUsuario.getUsuarioID(),1, getApplicationContext());
				
				
				String sUser = dao.oUsuario.getNombre();
				Toast.makeText(getApplicationContext(),"Bienvenid@_"+sUser, Toast.LENGTH_SHORT).show();

			}else{
				Toast.makeText(getApplicationContext(),dao.oJsonStatus.getMessage()+" Info: "+dao.oJsonStatus.getInfo(),Toast.LENGTH_LONG).show();
			}
		}
    
    @Override
    protected void onCancelled() {
    Toast.makeText(getApplicationContext(), "Acción cancelada!",
    Toast.LENGTH_SHORT).show();
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
	    	 if (validarCampos()) 
					exeHttpAsync();
	       break;
	       
	     case R.id.log_about_action:
		   actionBar.setSubtitle("Acerca de");
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
	
	public void tvRegistrar_onClick (View v){
		
		Intent i = new Intent (this, Usuario_Registro_Activity.class);
		startActivity(i);
		finish();
	}
	
	
//	setContentView(R.layout.activity_usuario_login);		
//	email = (EditText) findViewById(R.id.txtEmail);		
//	SharedPreferences prefe=getSharedPreferences("datos",Context.MODE_PRIVATE);
//	email.setText(prefe.getString("mail",""))
//	
//	SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
//    Editor editor=preferencias.edit();
//    editor.putString("mail", email.getText().toString());
//    editor.commit();
//    
//    Intent i = new Intent(this, App_GPSMapa_Activity.class);
//    startActivity(i);
//    finish();
//	
	
	
}

