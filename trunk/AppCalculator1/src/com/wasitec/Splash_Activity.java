package com.wasitec;

import java.util.Timer;
import java.util.TimerTask;



import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;



public class Splash_Activity extends Activity {

	private static final int TIEMPO_DEL_SPLASH = 1500;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
 
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
 
                Intent mainIntent = new Intent().setClass(
                		Splash_Activity.this, Principal_Activity.class);
                startActivity(mainIntent);
                
                finish();
        		overridePendingTransition(R.anim.anim_in_splash,
						R.anim.anim_out_splash);
            }
        };
 
        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, TIEMPO_DEL_SPLASH);
				
	}

}
