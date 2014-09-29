package com.sigetdriver.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dsbmobile.dsbframework.util.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sigetdriver.R;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.controller.PuntoController;
import com.sigetdriver.controller.ServicioController;
import com.sigetdriver.controller.ZonaController;
import com.sigetdriver.entities.PuntoBean;

public class EditarDestinoActivity extends Activity implements OnMapClickListener {
	
	private GoogleMap googleMap;
    private GPSTracker gps;
    private LatLng coordenada = null;
    private EditText edtZona;
    private EditText edtDireccion;
    private Button btnGrabar;
    private PuntoBean punto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!ServicioController.getInstance().verificarLogin()) {
        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(i);
		}
		
		setContentView(R.layout.activity_insertar_destino);
		
		setTitle("Unidad móvil [" + ServicioWorkingSet.codigoUnidad + "] - " + ServicioWorkingSet.nombreConductor);
		
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		// Recuperamos el id
		final int id = getIntent().getExtras().getInt("id");
		punto = ServicioWorkingSet.servicio.getPuntos().get(id);
		
		edtZona = (EditText) findViewById(R.id.edtZona);
		edtDireccion = (EditText) findViewById(R.id.edtDireccion);
		
		// Populamos
		edtZona.setText(punto.getZona());
		edtDireccion.setText(punto.getDireccion());
		if (!punto.getLatitudPunto().equals("0.0") &&
				!punto.getLongitudPunto().equals("0.0")) {
			coordenada = new LatLng(
					Double.parseDouble(punto.getLatitudPunto()), 
					Double.parseDouble(punto.getLongitudPunto()));
		}
		
		btnGrabar = (Button) findViewById(R.id.btnGrabar);
		btnGrabar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ServicioWorkingSet.servicio.getPuntos().get(id).setDireccion(edtDireccion.getText().toString());
				ServicioWorkingSet.servicio.getPuntos().get(id).setZona(edtZona.getText().toString());
				if (coordenada != null) {
					ServicioWorkingSet.servicio.getPuntos().get(id).setZona(
							ZonaController.getInstance().ubicarZona(
									coordenada.latitude, coordenada.longitude).getNombre());
					ServicioWorkingSet.servicio.getPuntos().get(id).setLatitudPunto(String.valueOf(coordenada.latitude));
					ServicioWorkingSet.servicio.getPuntos().get(id).setLongitudPunto(String.valueOf(coordenada.longitude));
				}	
				PuntoController.getInstance().actualizarPuntosPorServicio(ServicioWorkingSet.servicio);
				finish();
			}
		});
		
		try {
            // Loading map
            initilizeMap();
            pintarMapa();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    
    private void pintarMapa() {
    	
    	gps = new GPSTracker(EditarDestinoActivity.this);			
		// check if GPS enabled     
        if(gps.canGetLocation()){
        	
        	CameraPosition cameraPosition;
            if (coordenada != null) {
            	cameraPosition = new CameraPosition.Builder().target(
                        coordenada).zoom(16).build();
            	googleMap.addMarker(new MarkerOptions().position(coordenada).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
            } else {
            	cameraPosition = new CameraPosition.Builder().target(
                        new LatLng(gps.getLatitude(), gps.getLongitude())).zoom(16).build();
            }
     
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        
        }else{
            gps.showSettingsAlert();
        }
        
        googleMap.setOnMapClickListener(this);
    	
    }

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		coordenada = arg0;
		googleMap.clear();
		googleMap.addMarker(new MarkerOptions().position(arg0).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
//		Toast.makeText(getApplicationContext(), "Lat: "+arg0.latitude+"\nLon:"+arg0.longitude, Toast.LENGTH_SHORT).show();
	}

}
