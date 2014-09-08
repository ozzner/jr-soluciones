package com.wasitec;

import java.text.DecimalFormat;








import android.app.ActionBar;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Ash_Activity extends ActionBarActivity {
	
	EditText val1, val2;
	TextView rest; 
	Double valor1, valor2, valor3, valor4;
	Double result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ash);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/playtime.ttf");
		
		val1 = (EditText)findViewById(R.id.edt_val1);
		val2 = (EditText)findViewById(R.id.edt_val2);
		TextView tv1 = (TextView)findViewById(R.id.txtCeniza1);
		TextView tv2 = (TextView)findViewById(R.id.txtCeniza2);
		
	
		Button btna = (Button)findViewById(R.id.btnResult);
		TextView tvOp1 = (TextView)findViewById(R.id.txtOpe5);
	
		tv1.setTypeface(tf);
		tv2.setTypeface(tf);
		
		btna.setTypeface(tf);
		val1.setTypeface(tf);
		val2.setTypeface(tf);

		tvOp1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/umbrage.ttf"));
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
	}
		
		public void btnResult_onClick (View v){

			
			String values1 = val1.getText().toString();
			String values2 = val2.getText().toString();

			
			if (values1.equals("") || values2.equals("")){
				

				
				Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
			
				
			}
			
			else {
				
				
				Double valor1 = Double.parseDouble(val1.getText().toString());
				Double valor2 = Double.parseDouble(val2.getText().toString());

				
				
			if (valor2 >= valor1){
				
				Toast.makeText(this, "Final Weight cannot be greater or equal than Initial Weight", Toast.LENGTH_SHORT).show();
				val1.setText("");
				val2.setText("");
		
				
			}
			
		
			else {
				
			result = (1 - (valor2/valor1)) * 100;
			DecimalFormat df = new DecimalFormat("0.000"); 
			
			String resultado = df.format(result);
			rest.setText(resultado);
			
			}
			
		}
			
		}
		
	

}
