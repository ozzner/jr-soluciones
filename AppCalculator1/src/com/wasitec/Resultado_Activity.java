package com.wasitec;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Resultado_Activity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resultado);
		
		Intent i = getIntent();
		String sNombre = i.getStringExtra(Principal_Activity.TAG_RESULT);	
		
		TextView tvShowResult = (TextView)findViewById(R.id.tv_result);
		tvShowResult.setText(sNombre);
	}

}
