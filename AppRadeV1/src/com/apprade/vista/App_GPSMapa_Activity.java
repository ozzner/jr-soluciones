package com.apprade.vista;

import com.apprade.R;
import com.apprade.modelo.HANDLER_GPS_Tracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;



public class App_GPSMapa_Activity extends FragmentActivity {
	
	 private GoogleMap map;
	 
	 HANDLER_GPS_Tracker gps;
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
			
			gps = new HANDLER_GPS_Tracker(App_GPSMapa_Activity.this);
	        
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
	    		
	    		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Aqu� estoy :)"));
	    			
	    		latitude = latitude+0.002;
	    		
	    	}

//		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Aqu� estoy :)"));
//		map.addMarker(new MarkerOptions().position(new LatLng(latitude+0.005, longitude)).title("Aqu� no estoy :)"));
//		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude+0.005)).title("Aqu� tampoco :)"));
//		map.addMarker(new MarkerOptions().position(new LatLng(latitude+0.009, longitude)).title("Pelaooooooo"));
//		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude+0.009)).title("Goooooollll!!!!"));

	    
	}
	}