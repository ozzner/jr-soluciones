package wasitec;

import com.example.appcalculator1.R;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
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
			
			Intent i = new Intent(this, Guide_Activity.class);
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
			View rootView = inflater.inflate(R.layout.dashboard, container,
					false);
			return rootView;
		}
	
	}
	
	
	public void btn1_onClick (View v) {
		
		Toast.makeText(this, R.string.Ope_1, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Protein_Activity.class);
		startActivity(i);
		
	}
	
	public void btn2_onClick (View v) {
		
		Toast.makeText(this, R.string.Ope_2, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Moisture_Activity.class);
		startActivity(i);
		
	}
	
	public void btn3_onClick (View v) {
		
		Toast.makeText(this, R.string.Ope_3, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Fat_Activity.class);
		startActivity(i);
		
	}
	
	public void btn4_onClick (View v) {

		Toast.makeText(this, R.string.Ope_4, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Fiber_Activity.class);
		startActivity(i);
	}
	
	public void btn5_onClick (View v) {

		Toast.makeText(this, R.string.Ope_5, Toast.LENGTH_SHORT).show();
		Intent i = new Intent (this, Ash_Activity.class);
		startActivity(i);
	}



}