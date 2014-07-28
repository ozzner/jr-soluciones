package com.apprade.activity;

import com.apprade.R;
import com.apprade.helper.Helper_GPS_Tracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

public class App_GPSMapa_Activity extends FragmentActivity {
	
	 private GoogleMap map;
	 
	 Helper_GPS_Tracker gps;
	 private double latitude;
	 private double longitude;
	 private String nombre;
	 private ActionBar actionBar;
	 private PopupWindow popWin;
	 
	 
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
	    
//	    public void btnLogin_onClick (View v){
//	    	
//	    	Intent intent = new Intent(this, Usuario_Login_Activity.class);
//	    	
//	    	startActivity(intent);
//	    	
//	    }
	    
	    private void setUpMap() {
	    	
	    	int i;
	    	for (i=0; i<5; i++){
	    		
	    		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Has hecho click!"));
	    			
	    		latitude = latitude+0.002;
	    		
	    	}

//		map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Aquí estoy :)"));

	}
	    
	    
	 /*
	  * POPUP CONFIGURATIONS 
	  * */   
	    
	    private void initiatePopupWindow() {
	    	try {
	    	// We need to get the instance of the LayoutInflater
	    	LayoutInflater inflater = (LayoutInflater) App_GPSMapa_Activity.this
	    	.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	View layout = inflater.inflate(R.layout.popup_comentario,
	    	(ViewGroup) findViewById(R.id.popup_element));
	    	popWin = new PopupWindow(layout, 300, 370, true);
	    	popWin.showAtLocation(layout, Gravity.CENTER, 0, 0);

//	    	btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
//	    	btnClosePopup.setOnClickListener(cancel_button_click_listener);

	    	} catch (Exception e) {
	    	e.printStackTrace();
	    	}
	    	}

  

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		     MenuInflater inflater = getMenuInflater();
		     inflater.inflate(R.menu.mapa_menu, menu);
		     
		     return true;
		   } 
		   
		@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
			  
			  actionBar = getActionBar();
		     switch (item.getItemId()) {
		
		     case R.id.action_map_comentar:
		       Toast.makeText(this, "Acción comentar", Toast.LENGTH_SHORT)
		           .show();
			   actionBar.setSubtitle("Comentando...");
			   initiatePopupWindow();
		       break;

		       
		     default:
		       break;
		     }

		     return true;
		   } 
	    
	    
	    
	    
	    
	}