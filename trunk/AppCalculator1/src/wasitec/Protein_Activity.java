package wasitec;


import java.text.DecimalFormat;

import com.example.appcalculator1.R;

import android.R.bool;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Protein_Activity extends ActionBarActivity {
	
	EditText val1, val2, val3, val4;
	TextView rest; 
	Double valor1, valor2, valor3, valor4;
	Double result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_protein);
		
		val1 = (EditText)findViewById(R.id.edt_val1);
		val2 = (EditText)findViewById(R.id.edt_val2);
		val3 = (EditText)findViewById(R.id.edt_val3);
		val4 = (EditText)findViewById(R.id.edt_val4);
		rest = (TextView) findViewById(R.id.txtResult);
		
		android.app.ActionBar actionBar = getActionBar();
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
			
			Intent i = new Intent(this, Protein_Guide_Activity.class);
			startActivity(i);
		}
		

		return super.onOptionsItemSelected(item);
	}
	
	
		
		public void btnResult_onClick (View v){


		
			String values1 = val1.getText().toString();
			String values2 = val2.getText().toString();
			String values3 = val3.getText().toString();
			String values4 = val4.getText().toString();
			
			
			
			
			if (values1.equals("") || values2.equals("") || values3.equals("") || values4.equals("")){
				

				
				Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
			
				
			}
			
			else {
				
				
				Double valor1 = Double.parseDouble(val1.getText().toString());
				Double valor2 = Double.parseDouble(val2.getText().toString());
				Double valor3 = Double.parseDouble(val3.getText().toString());
				Double valor4 = Double.parseDouble(val4.getText().toString());
				
				
			if (valor3 >= valor2){
				
				Toast.makeText(this, "El Vol. de la muestra de debe ser mayor al Vol. Blanco", Toast.LENGTH_SHORT).show();
				val2.setText("");
				val3.setText("");
				
			}

			
			else if ((valor4 < 0.09) || (valor4 > 0.2)){
				
				Toast.makeText(this, "La Normal no debe ser menor a 0.09 ni mayor a 0.2", Toast.LENGTH_SHORT).show();
				val4.setText("");
				rest.setText("");
			}
			
			
			else {
				
//			result = ((((valor2 - valor3) * valor4 * 14.01)/(valor1 * 10)) * 100) * 6.25;
//			DecimalFormat df = new DecimalFormat("0.000"); 
//			
//			String resultado = df.format(result);
//			rest.setText(resultado);
			
				Intent i = new Intent (this, Resultado_Activity.class);
				startActivity(i);
			
			}
			
		}
			
		}
		
	

}
