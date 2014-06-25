package com.apprade.activity;

import com.apprade.R;






import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class Usuario_Login_Activity extends Activity {
	
	private EditText email;
	private EditText password;	
	private Button btnLogin;	


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		email = (EditText)findViewById(R.id.txtEmail);
		password= (EditText)findViewById(R.id.txtPassword);
		
		btnLogin.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String sEmail,sPassword;
				
				
				sEmail = email.getText().toString();
				sPassword = password.getText().toString();
				
				
			}
		});
		
	
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

