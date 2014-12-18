package pe.nullpoint.easymaestro.view;

import pe.nullpoint.easymaestro.R;
import pe.nullpoint.easymaestro.utils.Utils_HelperGPSTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class Activity_mapa extends ActionBarActivity implements
LocationListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener,
GoogleMap.OnInfoWindowClickListener {

		private GoogleMap map;
		Utils_HelperGPSTracker gps;
		private double latitude;
		private double longitude;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

		setUpMapIfNeeded();
		setUpMap();
		

	}


	public void setUpMap() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						map.addMarker(new MarkerOptions().position(new LatLng(-12.101472, -76.948639)).title("Juan Espinoza"));
						map.addMarker(new MarkerOptions().position(new LatLng(-12.09172, -76.965555)).title("Renzo Santillan"));
						map.addMarker(new MarkerOptions().position(new LatLng(-12.151472, -76.99777)).title("José Quispe"));
						
					}
				});
			}

		}).start();
	}

	private void setUpMapIfNeeded() {

		map = ((SupportMapFragment) getSupportFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);

		map.setOnMarkerClickListener((OnMarkerClickListener) this);
		map.setOnInfoWindowClickListener((OnInfoWindowClickListener) this);

		gps = new Utils_HelperGPSTracker(Activity_mapa.this);

		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			CameraUpdate camera1 = CameraUpdateFactory.newLatLngZoom(
					new LatLng(latitude, longitude), 12f);
			map.animateCamera(camera1);

			// setUpMap();
		} else {
			gps.showSettingsAlert();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		
		if (marker.getTitle().equals("Renzo Santillan")){
		Intent i = new Intent (getApplicationContext(), Activity_perfil1.class);
		startActivity(i);
		}
		
		if (marker.getTitle().equals("José Quispe")){
			Intent i = new Intent (getApplicationContext(), Activity_perfil2.class);
			startActivity(i);
			}
		
		if (marker.getTitle().equals("Juan Espinoza")){
			Intent i = new Intent (getApplicationContext(), Activity_perfil3.class);
			startActivity(i);
			}
		
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMyLocationButtonClick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// // dialogo que solicita la habilitación del GPS
		AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
		builder.setTitle("El GPS está desactivado");
		builder.setCancelable(false);
		builder.setPositiveButton("Habilitar GPS",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent iniciarGPS = new Intent(
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(iniciarGPS);
					}
				});
		builder.setNegativeButton("Dejar GPS inhabilitado",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
		
	}




