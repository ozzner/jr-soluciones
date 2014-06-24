package com.apprade.activity;

import com.apprade.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class App_Splash_Activity extends Activity {

	private static int TIEMPO_DEL_SPLASH = 2000;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent i = new Intent(App_Splash_Activity.this,
						Usuario_Registro_Activity.class);
				startActivity(i);

				finish();
				overridePendingTransition(R.anim.anim_in_splash,
						R.anim.anim_out_splash);
			}
		}, TIEMPO_DEL_SPLASH);
	}

}
