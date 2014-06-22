package com.apprade.vista;

import com.apprade.R;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class Login_Activity extends Activity {
private EditText email;
	
	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		email = (EditText) findViewById(R.id.txtEmail);
		
		SharedPreferences prefe=getSharedPreferences("datos",Context.MODE_PRIVATE);
		email.setText(prefe.getString("mail",""));
	}
	

	public void btnIngresar_onClick (View v) {
		
		SharedPreferences preferencias=getSharedPreferences("datos",Context.MODE_PRIVATE);
        Editor editor=preferencias.edit();
        editor.putString("mail", email.getText().toString());
        editor.commit();
        
        Intent i = new Intent(this, GPS_Mapa_Activity.class);
        startActivity(i);
        finish();
        
        

	}
	
	
}
