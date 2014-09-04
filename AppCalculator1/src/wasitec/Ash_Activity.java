package wasitec;

import java.text.DecimalFormat;

import com.example.appcalculator1.R;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
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
			
		val1 = (EditText)findViewById(R.id.edt_val1);
		val2 = (EditText)findViewById(R.id.edt_val2);
		rest = (TextView) findViewById(R.id.txtResult);
		
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
