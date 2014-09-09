package com.wasitec;

import java.util.Locale;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Principal_Activity extends Activity {
	Button abc;
	Button bt2;
	Button bt3;
	Button bt4;
	Button bt5;
	public static final String TAG_RESULT = "resultado";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/umbrage.ttf");

		abc = (Button) findViewById(R.id.btnProtenia);
		bt2 = (Button) findViewById(R.id.btnHumendad);
		bt3 = (Button) findViewById(R.id.btnGrasa);
		bt4 = (Button) findViewById(R.id.btnFibra);
		bt5 = (Button) findViewById(R.id.btn5);

		abc.setTypeface(tf);
		bt2.setTypeface(tf);
		bt3.setTypeface(tf);
		bt4.setTypeface(tf);
		bt5.setTypeface(tf);

		cambiarIconoPorIdioma();
		
		
	}

	protected void cambiarIconoPorIdioma() {
		String sLenguaje = Locale.getDefault().getDisplayLanguage();

		switch (sLenguaje) {

		case "English":

			bt2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.m, 0, 0);
			bt3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.f, 0, 0);
			bt5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.a, 0, 0);
			break;

		case "español":

			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.action_settings) {

			Intent i = new Intent(this, Guide_Activity.class);
			startActivity(i);

		}

		return super.onOptionsItemSelected(item);
	}



	public void btn1_onClick(View v) {

		Toast.makeText(this, R.string.Ope_1, Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, Protein_Activity.class);
		startActivity(i);

	}

	public void btn2_onClick(View v) {

		Toast.makeText(this, R.string.Ope_2, Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, Moisture_Activity.class);
		startActivity(i);

	}

	public void btn3_onClick(View v) {

		Toast.makeText(this, R.string.Ope_3, Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, Fat_Activity.class);
		startActivity(i);

	}

	public void btn4_onClick(View v) {

		Toast.makeText(this, R.string.Ope_4, Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, Fiber_Activity.class);
		startActivity(i);
	}

	public void btn5_onClick(View v) {

		Toast.makeText(this, R.string.Ope_5, Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, Ash_Activity.class);
		startActivity(i);
	}

}