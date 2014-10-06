package com.sigetdriver.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import com.sigetdriver.R;
import com.sigetdriver.ServicioWorkingSet;

public class SplashActivity extends Activity {
	
	private static int SPLASH_TIME_OUT = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		setTitle("SIGET DRIVER");
		
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(i);
				finish();
			}
		}, SPLASH_TIME_OUT);

	}

}
