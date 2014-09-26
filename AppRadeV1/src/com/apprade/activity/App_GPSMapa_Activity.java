package com.apprade.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.adapter.Adapter_InfoWindow;
import com.apprade.dao.DAO_Calificacion;
import com.apprade.dao.DAO_Establecimiento;
import com.apprade.dao.DAO_Usuario;
import com.apprade.entity.Entity_Coordenadas;
import com.apprade.entity.Entity_Establecimiento;
import com.apprade.helper.Helper_GPS_Tracker;
import com.apprade.helper.Helper_JSONStatus;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class App_GPSMapa_Activity extends FragmentActivity implements
		OnMarkerClickListener, OnInfoWindowClickListener {

	private GoogleMap map;
	private MenuItem refreshMenuItem;
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
	private DAO_Calificacion oCalificar;
	private Helper_SubRoutines routine;
	private Helper_SharedPreferences oPrefe;
	private Adapter_InfoWindow oInfoWindow;
	private Helper_SubRoutines oRoutine;
	private String arrValue[];
	private int count;
	private static String mensaje;
	private static final String TAG_ONCREATE = "oncreate";
	private static final String TAG_ONRESTART = "onrestart";
	private static final String TAG_UPDATE = "update";
	private static Marker myMarker;
	String arrParams[] = new String[4];
	String arrKeys[] = new String[10];
	String arrValues[] = new String[10];
	String[] arrNomEst = null;
	String[] arrDirEst = null;
	int[] arrIdEstt;
	int arraymapas[] = new int[1000];
	String titulo;

	/* SET GET */
	/**
	 * @return the arrValue
	 */
	public String[] getArrValue() {
		return arrValue;
	}

	/**
	 * @param sValue
	 *            the arrValue to set
	 */
	public void setArrValue(String[] sValue) {
		this.arrValue = sValue;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the myMarker
	 */
	public Marker getMyMarker() {
		return myMarker;
	}

	/**
	 * @param myMarker
	 *            the myMarker to set
	 */
	public void setMyMarker(Marker myMarker) {
		this.myMarker = myMarker;
	}

	/**
	 * BOB El constructor
	 */

	public App_GPSMapa_Activity() {
		super();
		dao = new DAO_Establecimiento();
		ettEst = new Entity_Establecimiento();
		status = new Helper_JSONStatus();
		daoUser = new DAO_Usuario();
		oCalificar = new DAO_Calificacion();
		routine = new Helper_SubRoutines();
		oPrefe = new Helper_SharedPreferences();
		oRoutine = new Helper_SubRoutines();
		oInfoWindow = new Adapter_InfoWindow();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestart()
	 */
	@Override
	protected void onRestart() {

		setMensaje(TAG_ONRESTART);
		exeHttpAsync();

		super.onRestart();
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

		setMensaje(TAG_ONCREATE);
		exeHttpAsync();
		hideFragment();

		ivNoCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				arrParams[1] = "No hay cola";

				if (!enviarCalificacion())
					chkTimeCalificacion();
				else {
					hideFragment();
					exeAsyncTask(arrParams);
				}

			}
		});

		ivPocaCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				arrParams[1] = "Poca cola";

				if (!enviarCalificacion())
					chkTimeCalificacion();
				else {
					hideFragment();
					exeAsyncTask(arrParams);
				}

			}
		});

		ivColaModerada.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				arrParams[1] = "Cola moderada";

				if (!enviarCalificacion())
					chkTimeCalificacion();
				else {
					hideFragment();
					exeAsyncTask(arrParams);
				}

			}
		});

		ivAltaCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				arrParams[1] = "Alta cola";

				if (!enviarCalificacion())
					chkTimeCalificacion();
				else {
					hideFragment();
					exeAsyncTask(arrParams);
				}

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
			datos = oShared.getAllLoginDataStored(getApplicationContext());
			usuarioID = Integer.parseInt(datos.get(2));
			String sName = datos.get(0).toString();
			// Toast.makeText(getApplicationContext(),"sName " +sName+
			// "- Get1 "+ datos.get(1)+ "- Get2 "+ datos.get(2)+ "- Get3 "+
			// datos.get(3), Toast.LENGTH_LONG).show();
			actionBar.setTitle(sName);
		}
		setUpMapIfNeeded();

	} // End onCreate

	protected boolean enviarCalificacion() {
		boolean bMsj = false;

		String currentTime = routine.getCurrentTime();
		/* KEYS - VALUES */
		arrKeys[0] = "currentTime";
		arrValues[0] = currentTime;
		arrKeys[1] = "establishmentID";
		arrValues[1] = String.valueOf(arrParams[0]);
		arrKeys[2] = "userID";
		arrValues[2] = String.valueOf(usuarioID);

		/* Valida si existe la preferencia almacenada (False) */
		if (!oPrefe.checkMyCustomPreference("Cal" + arrValues[1] + usuarioID,
				getApplicationContext(), arrKeys[0])) {

			/* Inserto mi preferencia personalizada */
			oPrefe.storeMyCustomPreferences(arrKeys, arrValues, "Cal"
					+ arrValues[1] + usuarioID, getApplicationContext());

			bMsj = true;

		} else {
			/* Si esta almacenada, obtengo los datos */
			String sValue[] = new String[arrKeys.length];
			for (int i = 0; i < arrKeys.length; i++) {

				sValue[i] = oPrefe.getAnyValueToMyCustomPreferences(
						getApplicationContext(), "Cal" + arrValues[1]
								+ usuarioID, arrKeys[i]);
			}
			setArrValue(sValue);

		}

		return bMsj;
	}

	private void chkTimeCalificacion() {

		String currentTime = routine.getCurrentTime();
		String sValue[] = new String[arrKeys.length];
		String sMsj = "";

		sValue = getArrValue();
		/* Valido que los datos sean del mismo establecimiento y usuario */
		if (sValue[1].equals(arrValues[1]) && sValue[2].equals(arrValues[2])) {

			/*
			 * Obtengo la diferencia en minutos (tiempo_alamcenado -
			 * tiempo_actual)
			 */

			int min_dif = oRoutine.dateDiferent(sValue[0], currentTime,
					"minutos");
			/* Evaluo cuanto tiempo ha pasado */
			if (min_dif < 5) {
				sMsj = "En " + (5 - min_dif)
						+ " min podr� volver a calificar este establecimiento.";
				routine.showToast(getApplicationContext(), sMsj);
			} else {
				getMyMarker().hideInfoWindow();
				exeAsyncTask(arrParams);
			}

		} else {
			/* Actualizo mi preferencia personalizada con otro establecimiento */
			oPrefe.storeMyCustomPreferences(arrKeys, arrValues, "Cal"
					+ arrValues[1] + usuarioID, getApplicationContext());
		}
	}

	private void showFragment(Marker marker) {

		mFragment = (FragmentCalificar) (getSupportFragmentManager()
				.findFragmentById(R.id.fragment_calificar));
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().show(mFragment).commit();

		setMyMarker(marker);
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

		try {

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
		} catch (Exception e) {

		}

	}

	public void setUpMap(final float lat, final float lon, final String nom,
			final String dir) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						map.addMarker(new MarkerOptions()
								.position(new LatLng(lat, lon))
								.title(nom)
								.snippet(dir)
								.flat(true)
								.alpha(0.8f)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.ic_map)));

						Adapter_InfoWindow adpInWin = new Adapter_InfoWindow();
						adpInWin.setCola("Poca cola");

						map.setInfoWindowAdapter(new Adapter_InfoWindow(
								getLayoutInflater()));

					}
				});
			}

		}).start();
	}

	public void runAsyncGetLasRate(final int establishmentID) {

		new Thread(new Runnable() {
			String sCola = "", sFecha = "Nada";

			@Override
			public void run() {

				boolean bResult = false;
				bResult = oCalificar.obtenerUltimaCalificacionPorEstabID(String
						.valueOf(establishmentID));

				if (bResult) {
					sCola = oCalificar.oCali.getCola();
					sFecha = oCalificar.oCali.getFecha();

					int iMin = oRoutine.dateDiferent(oRoutine.getCurrentTime(),
							sFecha, Helper_SubRoutines.TAG_MINUTOS);

					/* Evaluo cuanto tiempo ha pasado */
					if (iMin > 15) {

						switch (sCola) {
						
						case "No hay cola":
							//TODO no hace nada
							break;
						default:
							updateRating("No hay cola", establishmentID);
							break;
						}
						
						
					} else if (iMin >= 10 && iMin <= 15) {

						switch (sCola) {
						case "Alta cola":
							updateRating("Poca cola", establishmentID);
							break;
						case "Cola moderada":
							updateRating("No hay cola", establishmentID);
							break;
						default:
							updateRating("No hay cola", establishmentID);
							break;
						}

					} else if (iMin >= 5 && iMin < 10) {

						switch (sCola) {
						case "Alta cola":
							updateRating("Cola moderada", establishmentID);
							break;
						case "Cola moderada":
							updateRating("Poca cola", establishmentID);
							break;
						case "Poca cola":
							updateRating("No hay cola", establishmentID);
							break;
						default:
							Log.e(TAG_UPDATE, "Entre 5 y 10");
							break;
						}
					}else{
						Log.e(TAG_UPDATE, "Menor de 5 min" + iMin);
						//TODO
					}


				}else{
					Log.e(TAG_UPDATE, "NO HAY CALIFICACIONES");
					sCola = oCalificar.oJsonStatus.getInfo();
				}
				
				
				

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						oRoutine.showToast(getApplicationContext(), sCola);
					}
				});
			}

		}).start();

	}

	
	
	
	
	public void updateRating(final String cola, final int establishmentID) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				oCalificar.registrarCalificacion(String.valueOf(usuarioID),
						String.valueOf(establishmentID), cola);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						oRoutine.showToast(getApplicationContext(),
								oCalificar.oJsonStatus.getMessage());
					}
				});

			}
		}).start();

	}

	/**
	 * @param arg
	 *            [0] =>
	 * @param arg
	 *            [1] => No hay cola, Poca cola, Cola moderada y Alta cola
	 * @param arg
	 *            [2] =>
	 * @param arg
	 *            [3] =>
	 */

	public void exeAsyncTask(String... args) {
		CalificarAsync task = new CalificarAsync();
		task.execute(args);
		getMyMarker().hideInfoWindow();
		hideFragment();
	}

	class CalificarAsync extends AsyncTask<String, Void, Boolean> {

		boolean bOk = false;

		@Override
		protected Boolean doInBackground(String... params) {

			if (oCalificar.registrarCalificacion(usuarioID + "", params[0],
					params[1]))
				bOk = true;

			return bOk;
		}

		@Override
		protected void onPreExecute() {

			showDialogo();

			proDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					CalificarAsync.this.cancel(true);
					Toast.makeText(getApplicationContext(),
							"Servicio en segundo plano", Toast.LENGTH_SHORT)
							.show();
				}

			});
			proDialog.setProgress(0);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialog.dismiss();

			if (result) {
				actionBar.setSubtitle("Calificaci�n OK");
				Toast.makeText(getApplicationContext(), "�Gracias!",
						Toast.LENGTH_SHORT).show();
				hideFragment();
			} else {
				actionBar.setSubtitle("�Error!");
				Toast.makeText(getApplicationContext(),
						oCalificar.oJsonStatus.getInfo(), Toast.LENGTH_SHORT)
						.show();
			}
		}

		public void showDialogo() {

			proDialog = new ProgressDialog(App_GPSMapa_Activity.this);
			proDialog.setProgressStyle(ProgressDialog.BUTTON_NEUTRAL);
			proDialog.setMessage("Calificando...");
			proDialog.show();
		}
	}

	/**
	 * AsynTask class listar establecimiento
	 */

	private void exeHttpAsync(String... strings) {
		TaskHttpMethodAsync task = new TaskHttpMethodAsync();
		task.execute(strings);
	}

	class TaskHttpMethodAsync extends AsyncTask<String, Void, Boolean> {

		List<Entity_Establecimiento> lista_establecimiento = new ArrayList<Entity_Establecimiento>();

		@Override
		protected void onPreExecute() {

			App_GPSMapa_Activity oApp = new App_GPSMapa_Activity();
			String sMensaje = oApp.getMensaje();

			if (sMensaje.equals("update")) {
				refreshMenuItem
						.setActionView(R.layout.action_progressbar_refresh);
				refreshMenuItem.expandActionView();

			} else if (sMensaje.equals("oncreate")) {
				showDialogo();
			} else {

			}

			proDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {

					TaskHttpMethodAsync.this.cancel(true);

					Toast.makeText(getApplicationContext(),
							"Servicio en segundo plano", Toast.LENGTH_SHORT)
							.show();
				}

			});
			proDialog.setProgress(0);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;
			float lat = 0, lon = 0;

			try {

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

			} catch (Exception e) {
				bRequest = true; // Hubo error
			}

			return bRequest;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			try {
				proDialog.dismiss();
			} catch (Exception e) {
				Log.e("TAG-PROGGRESS", "No se pudo cerrar");
			}

			if (!result) {

				try {
					refreshMenuItem.collapseActionView();
					refreshMenuItem.setActionView(null);
				} catch (Exception e) {

				}

				actionBar.setSubtitle("Ok!");
				Toast.makeText(getApplicationContext(), "�Listo!",
						Toast.LENGTH_SHORT).show();
			} else {
				actionBar.setSubtitle("Error!");
				Toast.makeText(getApplicationContext(), "�Oops!",
						Toast.LENGTH_SHORT).show();
			}
		}

		public void showDialogo() {

			proDialog = new ProgressDialog(App_GPSMapa_Activity.this);
			proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			proDialog.setMessage("Iniciando...");
			proDialog.show();

		}
	} // End Async

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
			refreshMenuItem = item;
			setMensaje(TAG_UPDATE);
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

		String identificador = marker.getId();

		String contador = identificador.substring(1, identificador.length());

		count = Integer.parseInt(contador);

		// hideFragment();

		Intent intent = new Intent(getApplicationContext(),
				Usuario_Comentar_Activity.class);

		intent.putExtra("establecimientoID", arrIdEstt[count]);
		intent.putExtra("nomEstablecimiento", arrNomEst[count]);
		intent.putExtra("direccion", arrDirEst[count]);
		intent.putExtra("usuarioID", usuarioID);
		startActivity(intent);

	}

	@Override
	public boolean onMarkerClick(Marker arg0) {

		String identificador = arg0.getId();
		String contador = identificador.substring(1, identificador.length());
		int count = Integer.parseInt(contador);

		arrParams[0] = arrIdEstt[count] + "";

		runAsyncGetLasRate(arrIdEstt[count]);
		showFragment(arg0);
		return false;
	}

}