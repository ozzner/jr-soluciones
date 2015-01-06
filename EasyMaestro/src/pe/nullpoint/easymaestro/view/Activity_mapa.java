package pe.nullpoint.easymaestro.view;

import java.util.ArrayList;

import pe.nullpoint.easymaestro.R;
import pe.nullpoint.easymaestro.controller.Controller_Tecnico;
import pe.nullpoint.easymaestro.model.Model_Tecnico;
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
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Activity_mapa extends FragmentActivity implements
		LocationListener, GoogleMap.OnMyLocationButtonClickListener,
		GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

	private GoogleMap map;
	Utils_HelperGPSTracker gps;
	private double latitude;
	private double longitude;
	private ArrayList<Model_Tecnico> lista;
	private final String TAG = Activity_mapa.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa);

		setUpMapIfNeeded();
		setUpMap();

	}

	public void setUpMap() {
		lista = new ArrayList<Model_Tecnico>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				Controller_Tecnico controller = new Controller_Tecnico();
				lista = controller.getTecnicos();

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						for (int i = 0; i < lista.size(); i++) {

							map.addMarker(new MarkerOptions()
									.position(new LatLng(lista.get(i).getLat(),lista.get(i).getLon()))
									.snippet(lista.get(i).getProfesion())
									.title(lista.get(i).getNombre()));

							Log.e(TAG, "Nombre->" + lista.get(i).getNombre());
						}

					}
				});
			}

		}).start();
	}

	
	private void setUpMapIfNeeded() {

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);

		map.setOnMarkerClickListener(this);
		map.setOnInfoWindowClickListener(this);

		gps = new Utils_HelperGPSTracker(Activity_mapa.this);

		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			CameraUpdate camera1 = CameraUpdateFactory.newLatLngZoom(
					new LatLng(latitude, longitude), 15f);
			map.animateCamera(camera1);

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

		Toast.makeText(this, "Codi_" + marker.getId(), Toast.LENGTH_SHORT).show();
		
		String sAll = marker.getId();
		int iId = Integer.parseInt(sAll.substring(1,sAll.length()));
		
		Intent i = new Intent (getApplicationContext(), Activity_perfil1.class);
		
		i.putExtra("tecnicoID", lista.get(iId).getTecnicoID());
		i.putExtra("nombre", lista.get(iId).getNombre());
		i.putExtra("profesion", lista.get(iId).getProfesion());
		i.putExtra("dni", lista.get(iId).getDni());
		i.putExtra("direccion", lista.get(iId).getDireccion());
		i.putExtra("celular", lista.get(iId).getCelular());
		i.putExtra("calificacion", lista.get(iId).getCalificacion());

		
		startActivity(i);
		
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
		AlertDialog.Builder builder = new AlertDialog.Builder(
				getApplicationContext());
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
