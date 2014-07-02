package com.apprade.activity;

import com.apprade.R;






import com.apprade.dao.DAO_Usuario;
import com.apprade.entity.Entity_Ranking;
import com.apprade.entity.Entity_Usuario;
import com.apprade.helper.Helper_JSONStatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Usuario_Login_Activity extends Activity {
	
	private EditText password,email;	
	private Button btnLogin;
	private DAO_Usuario dao;
	private Entity_Ranking rank;
	private Entity_Usuario user;
	private String sEmail="",sPassword="";
	private String nombre;
    
	
	public Usuario_Login_Activity() {
		super();
		dao= new DAO_Usuario();
		rank = new Entity_Ranking();
		user = new Entity_Usuario() ;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_login);
		
		
//		Intent intent = getIntent();
//        nombre = intent.getStringExtra("NOMBRE");
//        
//        Toast.makeText(this, nombre, Toast.LENGTH_LONG).show();
		
		email = (EditText)findViewById(R.id.txtEmail);
		password= (EditText)findViewById(R.id.txtPassword);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		
		btnLogin.setOnClickListener( new OnClickListener() {			
			
			@Override
			public void onClick(View v) {
					exeHttpAsync();
			}
		});			
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
		protected void onPostExecute(Boolean result) {		
			super.onPostExecute(result);
			if (result) {
				Toast.makeText(getApplicationContext()," ranking_: ", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(getApplicationContext()," error: "+dao.oJsonStatus.getMessage()+" Info: "+dao.oJsonStatus.getInfo(),Toast.LENGTH_LONG).show();
			}
		}
						
		
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

