package contenedor.apprade.Vista;


import contenedor.apprade.Controlador.GPSTracker;
import edu.pe.apprade.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AppGPSActivity  extends Activity {
	TextView tvLat, tvLon;
	Button btnShowLocation;
	GPSTracker gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		
		tvLat = (TextView) findViewById(R.id.txtLat);
		tvLon = (TextView) findViewById(R.id.txtLon);

	        
		 btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
	        
	    
	        btnShowLocation.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {		
					
			        gps = new GPSTracker(AppGPSActivity.this);

			        if(gps.canGetLocation()){
			        	
			        	double latitude = gps.getLatitude();
			        	double longitude = gps.getLongitude();
			        	
			        	
			        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + 
			        								latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
			        	
			        	tvLat.setText(String.valueOf(latitude));
			        	tvLon.setText(String.valueOf(longitude));
			        	
			        }else{
			        	
			        	gps.showSettingsAlert();
			        }
					
				}
			});
	    }
	    
	}