package com.apprade.activity;


import java.util.Calendar;

import com.apprade.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Usuario_Registro_Activity extends Activity{
	
	private TextView mDateDisplay;
	private Button mPickDate;
	private int mYear;
	private int mMonth;
	private int mDay;
	static final int DATE_DIALOG_ID = 0;

	private RadioGroup radioSexGroup;
	private RadioButton radioSexButton;
	private Button btnDisplay;
	
	private EditText et1;



//	INICIA EL CÓDIGO DEL DATEPICKER
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);

		addListenerOnButton();
					
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		
		
//		OBTIENE LA FECHA ACTUAL Y LA MUESTRA EN EL DATEPICKER
//		
//		final Calendar c = Calendar.getInstance();
//		mYear = c.get(Calendar.YEAR);
//		mMonth = c.get(Calendar.MONTH);
//		mDay = c.get(Calendar.DAY_OF_MONTH);
//		
		
//		updateDisplay();
	}

// 		MUESTRA LA FECHA ACTUAL Y LA MUESTRA EN EL TEXTVIEW
	
		private void updateDisplay() {
		mDateDisplay.setText(new StringBuilder()
	
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
		}

		@Override
		protected Dialog onCreateDialog(int id) {
			
			switch (id) {
			case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

		// check network connection
//	    public boolean isConnected(){
//	    	
//	        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE); 
//
//
//	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//	            if (networkInfo != null && networkInfo.isConnected()) 
//	                return true;
//	            else
//	                return false;   
//	    }
		
//	INICIA EL CÓDIGO DEL RADIO BUTTON
		
	public void addListenerOnButton() {



		btnDisplay.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				int selectedId = radioSexGroup.getCheckedRadioButtonId();
				radioSexButton = (RadioButton) findViewById(selectedId);

				Toast.makeText(Usuario_Registro_Activity.this, radioSexButton.getText(),
						Toast.LENGTH_SHORT).show();

				
			}

		});

	}
	
    public void btnRegistrar_onClick (View v) {
    	
    	Intent i = new Intent(this, Usuario_Login_Activity.class);
    	startActivity(i);
    	finish();
        
        
    } 

}
