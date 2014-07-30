package com.wasitec.app_1;

import android.support.v7.app.ActionBarActivity;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class Principal_Activity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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
			
			Intent i = new Intent(this, Guia_Activity.class);
			startActivity(i);
			
		}
		

		return super.onOptionsItemSelected(item);
	}

	
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	
	}
	
	public void btnOpe1_onClick (View v) {
		
		Toast.makeText(this, R.string.Ope_1, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Proteina_Activity.class);
		startActivity(i);
		
	}
	
	public void btnOpe2_onClick (View v) {
		
		Toast.makeText(this, R.string.Ope_2, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Humedad_Activity.class);
		startActivity(i);
		
	}
	
	public void btnOpe3_onClick (View v) {
		
		Toast.makeText(this, R.string.Ope_3, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Grasa_Activity.class);
		startActivity(i);
		
	}
	
	public void btnOpe4_onClick (View v) {

		Toast.makeText(this, R.string.Ope_4, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Fibra_Activity.class);
		startActivity(i);
	}
	
	public void btnOpe5_onClick (View v) {

		Toast.makeText(this, R.string.Ope_5, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Ceniza_Activity.class);
		startActivity(i);
	}



}