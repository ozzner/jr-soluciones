package com.apprade.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.apprade.R;
import com.apprade.dao.DAO_Establecimiento;
import com.apprade.entity.Entity_Coordenadas;
import com.apprade.entity.Entity_Establecimiento;
import com.apprade.helper.Helper_GPS_Tracker;
import com.apprade.helper.Helper_JSONStatus;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;

import android.util.Log;
import android.view.View.OnClickListener;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class App_GPSMapa_Activity extends FragmentActivity implements OnMarkerClickListener, OnInfoWindowClickListener {

	private GoogleMap map;

	Helper_GPS_Tracker gps;
	private double latitude;
	private double longitude;
	private String nombre;
	private ActionBar actionBar;
	private PopupWindow popWin;
	private Button btnCancel;
	private DAO_Establecimiento dao;
	private ProgressDialog proDialog;
	public Entity_Establecimiento ettEst;
	private Helper_JSONStatus status;
	
	
	String[] arrNomEst = null;
	String[] arrDirEst = null;
	
	HashMap<String, String> arreglo = new HashMap<String, String>();

	
	int arraymapas[] = new int[1000];
	String titulo;
	
	/**
	 * BOB El constructor
	 */

	public App_GPSMapa_Activity() {
		super();
		dao = new DAO_Establecimiento();
		ettEst = new Entity_Establecimiento();
		status = new Helper_JSONStatus();
	}

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

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		
		map.setOnMarkerClickListener(this);
		map.setOnInfoWindowClickListener(this);
		
		gps = new Helper_GPS_Tracker(App_GPSMapa_Activity.this);

		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			
			

			// Toast.makeText(getApplicationContext(),
			// "Your Location is - \nLat: " +
			// latitude + "\nLong: " + longitude + "\n" + nombre,
			// Toast.LENGTH_LONG).show();

			CameraUpdate camera1 = CameraUpdateFactory.newLatLngZoom(
					new LatLng(latitude, longitude), 15f);
			map.animateCamera(camera1);

			// setUpMap();
		} else {

			gps.showSettingsAlert();
		}

	}
	
	// public void btnLogin_onClick (View v){
	//
	// Intent intent = new Intent(this, Usuario_Login_Activity.class);
	//
	// startActivity(intent);
	//
	// }

	public void setUpMap(final float lat, final float lon, final String nom, final String dir, final String contador) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						
						
						Marker dodo = map.addMarker(new MarkerOptions().position(
								new LatLng(lat, lon)).title(nom + "  - "+ dir ));
																				
						arreglo.put(dodo.getId(), contador);
						
						
//						Log.e("BBBBBBB", arreglo+"");
//						Log.e("CCCCCCC", contador);
						
						

					}
				});
			}

		}).start();
	}

	/**
	 * AsynTask class
	 */

	private void exeHttpAsync() {
		TaskHttpMethodAsync task = new TaskHttpMethodAsync();
		task.execute();
	}

	class TaskHttpMethodAsync extends AsyncTask<String, Void, Boolean> {
//		String[] arrNomEst = null;
//		String[] arrDirEst = null;
		List<Entity_Establecimiento> lista_establecimiento = new ArrayList<Entity_Establecimiento>();

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;
			float lat = 0, lon = 0;

			lista_establecimiento = dao.listarTodoEstablecimiento();
			

			bRequest = status.getError_status();

			if (!bRequest) {
				List<Entity_Coordenadas> lista_coordenadas = new ArrayList<Entity_Coordenadas>();
				arrNomEst = new String[lista_establecimiento.size()];
				arrDirEst = new String[lista_establecimiento.size()];

				int a = 0;
				for (Entity_Establecimiento esta : lista_establecimiento) {
					lista_coordenadas = esta.getCoordenadas();

					arrNomEst[a] = esta.getNombre();
					arrDirEst[a] = esta.getDireccion();
					
					
					a++;
				}

				int c = 0;
				for (Entity_Coordenadas coor : lista_coordenadas) {

					lat = coor.getLatitud();
					lon = coor.getLongitud();
//					arraymapas[c] = c;
					
					
					setUpMap(lat, lon, arrNomEst[c], arrDirEst[c], c+"");
					
					c++;

				}
			}
			return bRequest;
		}

		@Override
		protected void onPreExecute() {

			showDialogo();

			proDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					TaskHttpMethodAsync.this.cancel(true);
					Toast.makeText(getApplicationContext(), "Cancelado!",
							Toast.LENGTH_SHORT).show();
				}
			});
			proDialog.setProgress(0);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialog.dismiss();

			if (!result) {
				actionBar.setSubtitle("Ok!");
				

			} else {
				actionBar.setSubtitle("Error!");

			}
		}

		private void showDialogo() {

			proDialog = new ProgressDialog(App_GPSMapa_Activity.this);
			proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// proDialog.setMessage("cargando...");
			proDialog.show();

		}
	}

	
	/*
	 * POPUP CONFIGURATIONS
	 */

	private void initiatePopupWindow() {
		try {
			// We need to get the instance of the LayoutInflater
			LayoutInflater inflater = (LayoutInflater) App_GPSMapa_Activity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.popup_comentario,
					(ViewGroup) findViewById(R.id.popup_element));
			popWin = new PopupWindow(layout, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT, true);
			popWin.showAtLocation(layout, Gravity.CENTER, 0, 0);

			btnCancel = (Button) layout.findViewById(R.id.btn_cancel_comen);
			btnCancel
					.setOnClickListener(cancel_button_click_listener);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private OnClickListener cancel_button_click_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			popWin.dismiss();
		}
	};

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
			// Toast.makeText(this, "Acción comentar",
			// Toast.LENGTH_SHORT).show();
			actionBar.setSubtitle("cargando...");
//			Intent i = new Intent(getApplicationContext(), Usuario_Comentar_Activity.class);
//			startActivity(i);
			// initiatePopupWindow();
			exeHttpAsync();
			break;

		default:
			break;
		}
		
		return true;
	}

		
	@Override
	public void onInfoWindowClick(Marker marker) {
		// abriendo y pasando datos al otro activity
		
		titulo = marker.getId();
		int posicion=this.getPosicion(marker.getTitle());
		Log.e("AAAAAAAAAA", posicion+"");
//		Intent intent = new Intent(getApplicationContext() ,Usuario_Comentar_Activity.class);
//		startActivity(intent);
		//Log.e("AAAAAAAAAA", arraymapas[1]+"");
		
	}

	
	public int getPosicion(String idestablecimiento){
		int posicion=0;
		for (int i = 0; i < 524; i++) {
			if(arrNomEst[i]==(titulo)){
				posicion = i;
			}
		}
		return posicion;
	}
	
	@Override
	public boolean onMarkerClick(Marker arg0) {
		return false;
	}

	

}