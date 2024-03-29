package com.wasitec;

import java.text.DecimalFormat;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Fiber_Activity extends ActionBarActivity {
	
	EditText val1, val2, val3;
	TextView rest; 
	Double valor1, valor2, valor3, valor4;
	Double result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fiber);
			
		val1 = (EditText)findViewById(R.id.edt_val1);
		val2 = (EditText)findViewById(R.id.edt_val2);
		val3 = (EditText)findViewById(R.id.edt_val0);
		Button btna = (Button)findViewById(R.id.btnResult);
		Typeface tf =  Typeface.createFromAsset(getAssets(), "fonts/playtime.ttf");

		val1.setTypeface(tf);
		val2.setTypeface(tf);
		val3.setTypeface(tf);
		TextView tvOp1 = (TextView)findViewById(R.id.txtOpe4);
		btna.setTypeface(tf);
		
		tvOp1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/umbrage.ttf"));
		
		ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_ope, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
	
		if (id == R.id.definition) {
			
			Intent i = new Intent(this, Fiber_Guide_Activity.class);
			startActivity(i);
		}else{
			Intent main = new Intent(getApplicationContext(),Principal_Activity.class);
			startActivity(main);
			finish();
		}
		
		

		return super.onOptionsItemSelected(item);
	}
		
		public void btnResult_onClick (View v){


		
			String values1 = val1.getText().toString();
			String values2 = val2.getText().toString();
			String values3 = val2.getText().toString();

			
			
			
			
			if (values1.equals("") || values2.equals("") || values3.equals("") ){
				

				
				Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
			
				
			}
			
			else {
				
				
				Double valor1 = Double.parseDouble(val1.getText().toString());
				Double valor2 = Double.parseDouble(val2.getText().toString());
				Double valor3 = Double.parseDouble(val3.getText().toString());

				
				
			if (valor2 >= valor1){
				
				Toast.makeText(this, "Final Weight cannot be greater or equal than Initial Weight", Toast.LENGTH_SHORT).show();
				val1.setText("");
				val2.setText("");
		
				
			}
			
		
			else {
				
			result = ((valor2 - valor3)/valor1) * 100;
			DecimalFormat df = new DecimalFormat("0.000"); 
			
			String resultado = df.format(result);			
			
			Intent intents = new Intent(getApplicationContext(), Resultado_Activity.class);
			intents.putExtra(Principal_Activity.TAG_RESULT, resultado);
			startActivity(intents);
			
			}
			
		}
			
		}
		
	

}
