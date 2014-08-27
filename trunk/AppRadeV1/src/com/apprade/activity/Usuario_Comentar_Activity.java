/**
 * 
 */
package com.apprade.activity;


import com.apprade.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Julio
 *
 */
public class Usuario_Comentar_Activity extends Activity {

	private Button btnCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup);
		
		btnCancel = (Button)findViewById(R.id.btn_cancel_comen);
	    btnCancel.setOnClickListener((android.view.View.OnClickListener) cancel_button_click_listener);
	    
	    Intent i = getIntent();
		final String saludo  = i.getStringExtra("AAA");
		Log.e("ass", saludo);
		Toast.makeText(this, saludo , Toast.LENGTH_LONG).show();
		
	}
	
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
		finish();	
		}
	
	};

}
