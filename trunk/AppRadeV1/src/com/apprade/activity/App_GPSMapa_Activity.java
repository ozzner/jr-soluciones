package com.apprade.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.adapter.Adapter_InfoWindow;
import com.apprade.adapter.Adapter_SpinnerItem;
import com.apprade.adapter.Adapter_SpinnerNavActionBar;
import com.apprade.dao.DAO_Calificacion;
import com.apprade.dao.DAO_Establecimiento;
import com.apprade.entity.Entity_Coordenadas;
import com.apprade.entity.Entity_Establecimiento;
import com.apprade.helper.Helper_GPS_Tracker;
import com.apprade.helper.Helper_JSONStatus;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
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
		OnMarkerClickListener, OnInfoWindowClickListener,
		ActionBar.OnNavigationListener {

	private static final String TAG_NO_HAY_COLA = "No hay cola";
	private static final String TAG_POCA_COLA = "Poca cola";
	private static final String TAG_COLA_MODERADA = "Cola moderada";
	private static final String TAG_ALTA_COLA = "Alta cola";
	private static GoogleMap map;
	private static MarkerOptions markerOptions = new MarkerOptions();
	private MenuItem refreshMenuItem;
	Helper_GPS_Tracker gps;
	private ArrayList<Adapter_SpinnerItem> arrAdpSpinner;
	private Adapter_SpinnerNavActionBar oAdpSpinner;
	private double latitude;
	private double longitude;
	private int usuarioID;
	private ActionBar actionBar;
	private AdView adView;
	private DAO_Establecimiento dao;
	private ProgressDialog proDialog;
	public Entity_Establecimiento ettEst;
	private Helper_JSONStatus status;
	private Fragment_Calificar mFragment;
	private DAO_Calificacion oCalificar;
	private Helper_SubRoutines routine;
	private Helper_SharedPreferences oPrefe;
	private Adapter_InfoWindow oInfoWindow;
	private Helper_SubRoutines oRoutine;
	private String arrValue[];
	private static String mensaje;
	private static final String TAG_UPDATE = "update";
	private static final String AD_UNIT_ID = "ca-app-pub-0771856019955508/3441120220";
	private static final String TEST_DEVICE_ID = "B58F8443FD40945F763B77E7BC6B2A2F";
	private static Marker myMarker;
	private static Map<Integer, String> map2_IdEs_Cola = new HashMap<Integer,String>();
	String arrParams[] = new String[4];
	String arrCategory[] = new String[2];
	String arrKeys[] = new String[10];
	String arrValues[] = new String[10];
	String[] arrNomEst = null;
	String[] arrDirEst = null;
	private int[] arrIdEstt;
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
		App_GPSMapa_Activity.mensaje = mensaje;
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
		App_GPSMapa_Activity.myMarker = myMarker;
	}

	/**
	 * BOB El constructor
	 */

	public App_GPSMapa_Activity() {
		super();
		dao = new DAO_Establecimiento();
		ettEst = new Entity_Establecimiento();
		status = new Helper_JSONStatus();
		oCalificar = new DAO_Calificacion();
		routine = new Helper_SubRoutines();
		oPrefe = new Helper_SharedPreferences();
		oRoutine = new Helper_SubRoutines();
		oInfoWindow = new Adapter_InfoWindow();
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

		hideFragment();
		loadSpinnerNav();
		
		if (oRoutine.isOnline(getApplicationContext())) 
			loadAdView();
		
	

		ivNoCola.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				arrParams[0] = String.valueOf(oInfoWindow.getIdEst());
				arrParams[1] = TAG_NO_HAY_COLA;

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
				arrParams[0] = String.valueOf(oInfoWindow.getIdEst());
				arrParams[1] = TAG_POCA_COLA;

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
				
				arrParams[0] = String.valueOf(oInfoWindow.getIdEst());
				arrParams[1] = TAG_COLA_MODERADA;

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
				
				arrParams[0] = String.valueOf(oInfoWindow.getIdEst());
				arrParams[1] = TAG_ALTA_COLA;

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
			actionBar.setTitle(sName);
		}
		setUpMapIfNeeded();

	} // End onCreate

	
	  @Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) {
	      adView.resume();
	    }
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) {
	      adView.pause();
	    }
	    super.onPause();
	  }

	  /** Called before the activity is destroyed. */
	  @Override
	  public void onDestroy() {
	    // Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }
	
	private void loadAdView() {
		
		 	adView = new AdView(this);
		    adView.setAdSize(AdSize.SMART_BANNER);
		    adView.setAdUnitId(AD_UNIT_ID);
		    adView.setBackgroundColor(Color.parseColor("#000000"));
		    
		    LinearLayout layout = (LinearLayout) findViewById(R.id.lay_mapa);
		    layout.addView(adView);
		
		    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .addTestDevice(TEST_DEVICE_ID)
	        .build();
		
		    adView.loadAd(adRequest);
	}

	protected boolean enviarCalificacion() {
		boolean bMsj = false;

		String currentTime = routine
				.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_SHORT);
		/* KEYS - VALUES */
		arrKeys[0] = "currentTime";
		arrValues[0] = currentTime;
		arrKeys[1] = "establishmentID";
		arrValues[1] = String.valueOf(arrParams[0]);
		arrKeys[2] = "userID";
		arrValues[2] = String.valueOf(usuarioID);

		Log.e("CALIFICACION", "Cal:" + arrValues[1] + ":" + usuarioID + ":"
				+ arrKeys[0]);

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
				Log.e("for_get_any_sha", sValue[i]);
			}
			setArrValue(sValue);

		}

		return bMsj;
	}

	private void chkTimeCalificacion() {

		String sMySharedPref = "Cal" + arrValues[1] + usuarioID;
		String sCurrentTime = routine
				.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_SHORT);
		String sValue[] = new String[arrKeys.length];
		String sMsj = "";

		sValue = getArrValue();
		/* Valido que los datos sean del mismo establecimiento y usuario */
		if (sValue[1].equals(arrValues[1]) && sValue[2].equals(arrValues[2])) {
			Log.e("VAL_EST_USU", "OK");
			/*
			 * Obtengo la diferencia en minutos (tiempo_alamcenado -
			 * tiempo_actual)
			 */

			int min_dif = oRoutine.dateDiferent(sValue[0], sCurrentTime,
					"minutos");

			Log.e("VAL_MIN", "OK-" + min_dif);
			/* Evaluo cuanto tiempo ha pasado */
			if (min_dif < 5) {
				sMsj = "En " + (5 - min_dif)
						+ " min podrá volver a calificar este establecimiento.";
				routine.showToast(getApplicationContext(), sMsj);
			} else {
				oPrefe.updateMyCurretTime(sMySharedPref,
						getApplicationContext(), sCurrentTime);
				getMyMarker().hideInfoWindow();
				exeAsyncTask(arrParams);
			}

		} else {
			/* Actualizo mi preferencia personalizada con otro establecimiento */
			oPrefe.storeMyCustomPreferences(arrKeys, arrValues, "Cal"
					+ arrValues[1] + usuarioID, getApplicationContext());
			Log.e("STORE NEW", "Cal" + arrValues[1] + usuarioID);
		}
	}

	private void showFragment(Marker marker) {

		mFragment = (Fragment_Calificar) (getSupportFragmentManager()
				.findFragmentById(R.id.fragment_calificar));
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().show(mFragment).commit();

		setMyMarker(marker);
	}

	private void hideFragment() {

		mFragment = (Fragment_Calificar) (getSupportFragmentManager()
				.findFragmentById(R.id.fragment_calificar));
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().hide(mFragment).commit();

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
			final String dir, final int idEst) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {

						map.addMarker(markerOptions
								.position(new LatLng(lat, lon))
								.title(nom)
								.snippet(dir + idEst)
								.flat(true)
								.alpha(0.8f)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.ic_map)));

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

					int iMin = oRoutine.dateDiferent(
							oRoutine.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_SHORT),
							sFecha, Helper_SubRoutines.TAG_MINUTOS);


					/* Evaluo cuanto tiempo ha pasado */
					if (iMin > 15) {

						switch (sCola) {

						case "No hay cola":
							// TODO no hace nada
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
					} else {
						Log.e(TAG_UPDATE, "Menor de 5 min" + iMin);
						// TODO
					}

				} else {
					Log.e(TAG_UPDATE, "NO HAY CALIFICACIONES");
					sCola = oCalificar.oJsonStatus.getInfo();
				}

//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//
//						oRoutine.showToast(getApplicationContext(),
//								oCalificar.oJsonStatus.getMessage() + ": "
//										+ sCola);
//					}
//				});
			}

		}).start();
	}

	public void updateRating(final String cola, final int establishmentID) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				boolean bResult = oCalificar.registrarCalificacion(
						String.valueOf(usuarioID),
						String.valueOf(establishmentID), cola);

				if (bResult)
					map2_IdEs_Cola.put(oInfoWindow.getIdEst(), cola);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//TODO
					}
					
					
				});

			}
		}).start();

	}



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
				}

			});
			proDialog.setProgress(0);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialog.dismiss();

			if (result) {
				map2_IdEs_Cola.put(oInfoWindow.getIdEst(), arrParams[1]);
				oRoutine.showToast(getApplicationContext(),
						oCalificar.oJsonStatus.getMessage());
				hideFragment();
			} else {
				actionBar.setSubtitle("¡Error!");
			}
		}

		public void showDialogo() {

			proDialog = new ProgressDialog(App_GPSMapa_Activity.this);
			proDialog.setProgressStyle(DialogInterface.BUTTON_NEUTRAL);
			proDialog.setMessage("Calificando...");
			proDialog.show();
		}
	}

	/**
	 * AsynTask class listar establecimiento
	 */


	class EstablecimientoAsync extends AsyncTask<String, Void, Boolean> {

		List<Entity_Establecimiento> lista_establecimiento = new ArrayList<Entity_Establecimiento>();

		@Override
		protected void onPreExecute() {
			
			lista_establecimiento.clear();
			App_GPSMapa_Activity oApp = new App_GPSMapa_Activity();
			String sMensaje = oApp.getMensaje();

			try {

				if (sMensaje.equals("update")) {
					refreshMenuItem
							.setActionView(R.layout.action_progressbar_refresh);
					refreshMenuItem.expandActionView();
				}

			} catch (Exception e) {
				showDialogo();
				proDialog.setProgress(0);
			}

			try {

				proDialog.setOnCancelListener(new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

						EstablecimientoAsync.this.cancel(true);

					}

				});
				proDialog.setProgress(0);
			} catch (Exception e) {

			}
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;
			float lat = 0, lon = 0;

			try {

				lista_establecimiento = dao
						.listarEstablecimientoPorCategoriaID(params[0]);

				bRequest = status.getError_status();

				if (!bRequest) {
					
					List<Entity_Coordenadas> lista_coordenadas = new ArrayList<Entity_Coordenadas>();
					arrNomEst = new String[lista_establecimiento.size()];
					arrDirEst = new String[lista_establecimiento.size()];
					arrIdEstt = new int[lista_establecimiento.size()];
					
					map2_IdEs_Cola = DAO_Establecimiento.getMap_IdEs_Cola();

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
						
						setUpMap(lat, lon, arrNomEst[c], arrDirEst[c] , arrIdEstt[c]);
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
				Log.e("TAG-PROGGRESS", "No se pudo cerrar el dialogo");
			}

			if (!result) {

				try {
					refreshMenuItem.collapseActionView();
					refreshMenuItem.setActionView(null);
				} catch (Exception e) {

				}

				actionBar.setSubtitle("Ok!");
			} else {

				try {
					refreshMenuItem.collapseActionView();
					refreshMenuItem.setActionView(null);
				} catch (Exception e) {

				}

				actionBar.setSubtitle("Error!");
			}
		}

		public void showDialogo() {

			proDialog = new ProgressDialog(App_GPSMapa_Activity.this);
			proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			proDialog.setMessage("Buscando...");
			proDialog.show();

		}
	} // End Async


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

			if (!oRoutine.isOnline(getApplicationContext()))
				Toast.makeText(getApplicationContext(),
						"Necesita tener conexión a Internet.",
						Toast.LENGTH_SHORT).show();
			else {

				setMensaje(TAG_UPDATE);

				try {
					refreshMenuItem = item;
					hideFragment();
					myMarker.hideInfoWindow();
					map.clear();

					new EstablecimientoAsync().execute(arrCategory);

				} catch (Exception e) {
					map.clear();
					new EstablecimientoAsync().execute(arrCategory);
				}
			}

			break;

		case R.id.about_acc:
			LoadInfo();
			break;

		case R.id.logout_acc:
			logout();
			break;

		default:
			break;
		}

		return true;
	}

	private void LoadInfo() {
		final View v;
		
		AlertDialog.Builder adInfo = new AlertDialog.Builder(
				App_GPSMapa_Activity.this);

		LayoutInflater layInfo = this.getLayoutInflater();
		v = layInfo.inflate(R.layout.dialog_custom_info, null);
		adInfo.setView(v);

		adInfo.setNeutralButton("Okay", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

			}
		});

		adInfo.show();
	}

	@Override
	public void onInfoWindowClick(final Marker marker) {
		
		map.setInfoWindowAdapter(new Adapter_InfoWindow(getLayoutInflater()));
		
		
		Intent intent = new Intent(getApplicationContext(),
				Usuario_Comentar_Activity.class);

		intent.putExtra("establecimientoID", oInfoWindow.getIdEst());
		intent.putExtra("nomEstablecimiento", oInfoWindow.getNombre());
		intent.putExtra("direccion", oInfoWindow.getDireccion());
		intent.putExtra("usuarioID", usuarioID);
		intent.putExtra("cola", oInfoWindow.getCola());
		
		startActivity(intent);

	}


	@Override
	public final boolean onMarkerClick(final Marker arg0) {

	map.setInfoWindowAdapter(new Adapter_InfoWindow(getLayoutInflater()));
		
		String sAll = arg0.getSnippet();
		String sIdEst = sAll.substring(sAll.length() - 4);
		String sDirec = sAll.substring(0, sAll.length() - 4);
		
		oInfoWindow.setDireccion(sDirec);
		oInfoWindow.setNombre(arg0.getTitle());
		oInfoWindow.setCola(map2_IdEs_Cola.get(Integer.parseInt(sIdEst)));
		
//		String identificador = arg0.getId();
//		Log.e("ESTA_ID_2", arg0.getPosition()+ "");
//		String identificador = String.valueOf(arg0.getPosition());
//		String contador = identificador.substring(1, identificador.length());
//		int count = Integer.parseInt(contador) - marker_count;
//		int count = Integer.parseInt(identificador) ;
//		position = count;
//		arrParams[0] = arrIdEstt[count] + "";
//		Log.e("ESTA_ID_1", arrIdEstt[count] + "");
		
		runAsyncGetLasRate(Integer.parseInt(sIdEst));
		
		showFragment(arg0);
		
		return false;
	}

	
	private void loadSpinnerNav() {

		actionBar = getActionBar();

		actionBar.setDisplayShowTitleEnabled(false);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		arrAdpSpinner = new ArrayList<Adapter_SpinnerItem>();
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.ic_action_place,
				"-- Seleccione --"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.fast_foods,
				"Fast foods"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.cines, "Cines"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.cafes, "Cafes"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.restaurantes,
				"Restaurantes"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.banco, "Bancos"));
		arrAdpSpinner.add(new Adapter_SpinnerItem(R.drawable.organismos,
				"Organización"));

		oAdpSpinner = new Adapter_SpinnerNavActionBar(getApplicationContext(),
				arrAdpSpinner);
		actionBar.setListNavigationCallbacks(oAdpSpinner, this);
	}
	


	
	public void logout() {
		final View v;

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				App_GPSMapa_Activity.this);

		LayoutInflater layInflater = this.getLayoutInflater();
		v = layInflater.inflate(R.layout.dialog_custom_logout, null);
		alertDialog.setView(v);

		/* When positive (yes/ok) is clicked */
		alertDialog.setPositiveButton("No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						
						
					}
				});

		/* When negative (No/cancel) button is clicked */
		alertDialog.setNegativeButton("Si",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						actionBar.setSubtitle("Chau");
						Helper_SharedPreferences oShared = new Helper_SharedPreferences();
						oShared.storeStatus(0, getApplicationContext());// 0 =>
																		// Inicia
																		// desde
						Intent i = new Intent(getApplicationContext(),
								Usuario_Login_Activity.class);
						startActivity(i);

						finish();

					}
				});
		alertDialog.show();
	}

	
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		String sCatagoria = new String();
		sCatagoria = arrAdpSpinner.get(itemPosition).getCategory();

		if (!sCatagoria.equals("-- Seleccione --")) {
			map.clear();
			setMensaje(TAG_UPDATE);
		}

		if (!oRoutine.isOnline(getApplicationContext()))
			Toast.makeText(getApplicationContext(),
					"Necesita tener conexión a Internet.", Toast.LENGTH_SHORT)
					.show();
		else {

			switch (sCatagoria) {
			case "Fast foods":
				arrCategory[0] = String.valueOf(1);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Cines":
				arrCategory[0] = String.valueOf(2);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Cafes":
				arrCategory[0] = String.valueOf(3);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Restaurantes":
				arrCategory[0] = String.valueOf(4);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Bancos":
				arrCategory[0] = String.valueOf(5);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			case "Organización":
				arrCategory[0] = String.valueOf(6);
				new EstablecimientoAsync().execute(arrCategory);
				break;

			default:
				break;
			}
		}

		return false;
	}

}
