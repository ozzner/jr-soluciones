package contenedor.apprade.Vista;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import contenedor.apprade.Controlador.GPSTracker;
import edu.pe.apprade.R;


public class GPSMapaActivity extends FragmentActivity {
	
	 private GoogleMap map;
	 
	 GPSTracker gps;
	 private double latitude;
	 private double longitude;
 	
 	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_gps_mapa);
	        setUpMapIfNeeded();
	            	        
	    }
	  
	    @Override
	    protected void onResume() {
	        super.onResume();
	        setUpMapIfNeeded();
	    }
	    
	    private void setUpMapIfNeeded() {
	    	
	        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			map.setMyLocationEnabled(true);
			
			gps = new GPSTracker(GPSMapaActivity.this);
	        
	        if(gps.canGetLocation()){
	        	
	        	  latitude = gps.getLatitude();
	         	  longitude = gps.getLongitude();
	        	
	        	
	        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + 
	        								latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();	
			
	        	CameraUpdate camera1 = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15f);
	        	map.animateCamera(camera1);
			
	        	setUpMap();
	        }else{
	        	
	        	gps.showSettingsAlert();
	        }
	        
	    }
	    
	    private void setUpMap() {
	    	
	    	int i;
	    	for (i=0; i<5; i++){
	    		
	    		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Aquí estoy :)"));
	    			
	    		latitude = latitude+0.002;
	    		
	    	}

//		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Aquí estoy :)"));
//		map.addMarker(new MarkerOptions().position(new LatLng(latitude+0.005, longitude)).title("Aquí no estoy :)"));
//		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude+0.005)).title("Aquí tampoco :)"));
//		map.addMarker(new MarkerOptions().position(new LatLng(latitude+0.009, longitude)).title("Pelaooooooo"));
//		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude+0.009)).title("Goooooollll!!!!"));

	    
	}
	}