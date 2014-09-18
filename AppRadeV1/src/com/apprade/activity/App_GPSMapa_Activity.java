package com.apprade.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.apprade.R;
import com.apprade.dao.DAO_Establecimiento;
import com.apprade.dao.DAO_Usuario;
import com.apprade.entity.Entity_Coordenadas;
import com.apprade.entity.Entity_Establecimiento;
import com.apprade.helper.Helper_GPS_Tracker;
import com.apprade.helper.Helper_JSONStatus;
import com.apprade.helper.Helper_SharedPreferences;
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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class App_GPSMapa_Activity extends FragmentActivity implements
		OnMarkerClickListener, OnInfoWindowClickListener {

	private GoogleMap map;

	Helper_GPS_Tracker gps;
	private double latitude;
	private double longitude;
	private int usuarioID;
	private ActionBar actionBar;
	private PopupWindow popWin;
	private Button btnCancel;
	private DAO_Establecimiento dao;
	private ProgressDialog proDialog;
	public Entity_Establecimiento ettEst;
	private Helper_JSONStatus status;
	private DAO_Usuario daoUser;
	private FragmentCalificar mFragment;
	String[] arrNomEst = null;
	String[] arrDirEst = null;
	int[] arrIdEstt;

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
		daoUser = new DAO_Usuario();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps_mapa);

		ImageView ivNoCola = (ImageView) findViewById(R.id.iv_no_hay_cola);
		ImageView ivPocaCola = (ImageView) findViewById(R.id.iv_poca_cola);
		ImageView ivColaModerada = (ImageView) findViewById(R.id.iv_cola_moderada);
		ImageView ivAltaCola = (ImageView) findViewById(R.id.iv_alta_cola);

		
		actionBar = getActionBar();
		
		ivNoCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"No hay cola!", Toast.LENGTH_LONG).show();
			}
		});

		ivPocaCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"Poca cola!", Toast.LENGTH_LONG).show();
			}
		});

		ivColaModerada.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"Cola moderada!", Toast.LENGTH_LONG).show();
			}
		});

		ivAltaCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"alta cola!", Toast.LENGTH_LONG).show();
			}
		});

		try {
			
			Bundle oBundle = getIntent().getExtras();
			usuarioID = oBundle.getInt("user_id");
			String user = oBundle.getString("user");
			actionBar.setTitle(user);

		} catch (Exception e) {
			ArrayList<String> datos = new ArrayList<String>();

			Helper_SharedPreferences oShared = new Helper_SharedPreferences();
			datos = oShared.getAlldataStore(getApplicationContext());
			usuarioID = Integer.parseInt(datos.get(2));
			String sName = datos.get(0).toString();
//			Toast.makeText(getApplicationContext(),"sName " +sName+ "- Get1 "+ datos.get(1)+ "- Get2 "+ datos.get(2)+ "- Get3 "+ datos.get(3), Toast.LENGTH_LONG).show();
			actionBar.setTitle(sName);
		}
		hideFragment();
		setUpMapIfNeeded();
		
	} //End onCreate
	
	

	private void showFragment() {

		mFragment = (FragmentCalificar) (getSupportFragmentManager()
				.findFragmentById(R.id.fragment_calificar));
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().show(mFragment).commit();
	}

	private void hideFragment() {

		mFragment = (FragmentCalificar) (getSupportFragmentManager()
				.findFragmentById(R.id.fragment_calificar));
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().hide(mFragment).commit();
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

	public void setUpMap(final float lat, final float lon, final String nom,
			final String dir) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						map.addMarker(new MarkerOptions().position(
								new LatLng(lat, lon)).title(nom + "  - " + dir) 
								.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
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
		// String[] arrNomEst = null;
		// String[] arrDirEst = null;
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
				arrIdEstt = new int[lista_establecimiento.size()];

				int a = 0;
				for (Entity_Establecimiento esta : lista_establecimiento) {
					lista_coordenadas = esta.getCoordenadas();

					arrNomEst[a] = esta.getNombre();
					arrDirEst[a] = esta.getDireccion();
					arrIdEstt[a] = esta.getEstablecimientoID();
					a++;
				}

				int c = 0;
				for (Entity_Coordenadas coor : lista_coordenadas) {

					lat = coor.getLatitud();
					lon = coor.getLongitud();
					// arraymapas[c] = c;

					setUpMap(lat, lon, arrNomEst[c], arrDirEst[c]);

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
				Toast.makeText(getApplicationContext(), "¡Listo!",
						Toast.LENGTH_SHORT).show();
			} else {
				actionBar.setSubtitle("Error!");
				Toast.makeText(getApplicationContext(), "¡Hubo un error!",
						Toast.LENGTH_SHORT).show();
			}
		}

		private void showDialogo() {

			proDialog = new ProgressDialog(App_GPSMapa_Activity.this);
			proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			proDialog.setMessage("Cargando...");
			proDialog.show();

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
		case R.id.cargar_establ_acc:
			exeHttpAsync();
			break;

		case R.id.logout_acc:
			actionBar.setSubtitle("Chau");
			Helper_SharedPreferences oShared = new Helper_SharedPreferences();
			oShared.storeStatus(0, getApplicationContext());// 0 => Inicia desde
															// el login

			Intent i = new Intent(getApplicationContext(),
					Usuario_Login_Activity.class);
			startActivity(i);
			finish();
			break;

		default:
			break;
		}

		return true;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// abriendo y pasando datos al otro activity

		Log.e("ID", marker.getId());
		String identificador = marker.getId();

		String contador = identificador.substring(1, identificador.length());
		Log.e("ID-EXTRAC", contador);

		int count = Integer.parseInt(contador);

		Log.e("ESTABLECIMIENTO", arrNomEst[count]);
		Log.e("ESTABLECIMIENTO", arrDirEst[count]);

		hideFragment();

		Intent intent = new Intent(getApplicationContext(),
				Usuario_Comentar_Activity.class);

		intent.putExtra("establecimientoID", arrIdEstt[count]);
		intent.putExtra("nomEstablecimiento", arrNomEst[count]);
		intent.putExtra("direccion", arrDirEst[count]);
		intent.putExtra("usuarioID", usuarioID);
		startActivity(intent);

	}

	// public int getPosicion(String idestablecimiento){
	// int posicion=0;
	// for (int i = 0; i < 524; i++) {
	// if(arrNomEst[i]==(titulo)){
	// posicion = i;
	// }
	// }
	// return posicion;
	// }

	@Override
	public boolean onMarkerClick(Marker arg0) {
		showFragment();
		return false;
	}

}