package com.apprade.activity;

import com.apprade.R;
import com.apprade.helper.Helper_GPS_Tracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class App_GPSMapa_Activity extends FragmentActivity {
	
	 private GoogleMap map;
	 
	 Helper_GPS_Tracker gps;
	 private double latitude;
	 private double longitude;
	 private String nombre;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_gps_mapa);
	               
	        Intent intent = getIntent();
	        nombre = intent.getStringExtra("NOMBRE");
       
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
			gps = new Helper_GPS_Tracker(App_GPSMapa_Activity.this);

			if(gps.canGetLocation()){
	        	
	        	  latitude = gps.getLatitude();
	         	  longitude = gps.getLongitude();
	        	
	        	
//	        	Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + 
//	        								latitude + "\nLong: " + longitude + "\n" + nombre, Toast.LENGTH_LONG).show();	
			
	        	CameraUpdate camera1 = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15f);
	        	map.animateCamera(camera1);
			
	        	setUpMap();
	        }else{
	        	
	        	gps.showSettingsAlert();
	        }
	        
	    }
	    
	    public void btnLogin_onClick (View v){
	    	
	    	Intent intent = new Intent(this, Usuario_Login_Activity.class);
	    	
	    	startActivity(intent);
	    	
	    }
	    
	    private void setUpMap() {
	    	
	    	int i;
	    	for (i=0; i<5; i++){
	    		
	    		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Aquí estoy :)"));
	    			
	    		latitude = latitude+0.002;
	    		
	    	}

//		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Aquí estoy :)"));

	}
	}