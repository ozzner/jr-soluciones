package com.sigetdriver.view.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import com.dsbmobile.dsbframework.util.GPSTracker;
import com.fasterxml.jackson.core.JsonParseException;
import com.google.android.gcm.GCMRegistrar;
import com.sigetdriver.R;
import com.sigetdriver.ServerConstants;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.controller.ServicioController;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.service.TrackingService;
import com.sigetdriver.util.SigetDriverUtil;
import com.sigetdriver.view.adapter.ServicioAdapter;

public class EstadoActivity extends Activity {
		
	private ListView lista;
	private ServicioAdapter adapter;
	private ArrayList<ServicioBean> listaServicios;
	private ArrayList<PuntoBean> listaPuntos;
	private ServicioBean servicio;
	private Switch swtServicios;
	private Timer timer;
	private final int CERRAR_SESION = 0;
	private final int CREAR_DATOS_PRUEBA = 1;
	
	private double lat = 0;
	private double lon = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		if (!ServicioController.getInstance().verificarLogin()) {
        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(i);
		}
		
		setContentView(R.layout.activity_estado);
		
		setTitle("Unidad móvil [" + ServicioWorkingSet.codigoUnidad + "] - " + ServicioWorkingSet.nombreConductor);
		
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		lista = (ListView) findViewById(R.id.listaServicios);
		
		
		swtServicios = (Switch) findViewById(R.id.swtServicios);
				
		swtServicios.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (swtServicios.isChecked()) {
					ServicioWorkingSet.recibirServicios = true;
					Toast.makeText(
							getApplicationContext(), "Empieza a recibir servicios", Toast.LENGTH_SHORT).show();
				} else {
					ServicioWorkingSet.recibirServicios = false;
					Toast.makeText(
							getApplicationContext(), "Deja de recibir servicios", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		listaServicios = ServicioController.getInstance().obtenerServiciosPendientes();		
		adapter = new ServicioAdapter(getApplicationContext(), R.layout.item_servicio, listaServicios);
		lista.setAdapter(adapter);
		
		
		lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {		
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long arg3) {
            	// Recuperamos la lista de puntos y seteamos
            	// el servicio en el working set
            	servicio = listaServicios.get(pos);
            	if (servicio.getAceptado().equals(ServicioBean.ACEPTADO_NO)) {            		
            		servicio.setAceptado(ServicioBean.ACEPTADO_SI);
            		boolean ok = ServicioController.getInstance().actualizarServicio(servicio);
            		if (ok) {
            			GPSTracker gps = new GPSTracker(getApplicationContext());
            			lat = gps.getLatitude();
            			lon = gps.getLongitude();
            			new confirmarServicio().execute();
            			Toast.makeText(getApplicationContext(), "Servicio Aceptado", Toast.LENGTH_SHORT).show();
            		}
            	}
            	if (pos == 0) {
            		ServicioWorkingSet.primerServicio = true;
            	} else {
            		ServicioWorkingSet.primerServicio = false;
            	}
            	listaPuntos = ServicioController
            			.getInstance().obtenerPuntosPorServicio(servicio.getIdServicio());
            	servicio.setPuntos(listaPuntos);            	
            	ServicioWorkingSet.servicio = servicio;
            	ServicioWorkingSet.puntoActual = Integer.parseInt(servicio.getPuntoActual());
            	ServicioWorkingSet.puntoFinal = (listaPuntos.size()) * 2;
            	System.out.println("Punto Final: "+ServicioWorkingSet.puntoFinal);
            	Intent i = new Intent(EstadoActivity.this, ServicioActivity.class);
            	startActivity(i);
                return false;
            }
        });
		popular();
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
				     @Override
				     public void run() {
//				    	 System.out.println("Funciono!");
				    	 adapter.notifyDataSetChanged();				    	 
				    }
				});						
			}
		}, 0, 1000);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, CERRAR_SESION, 0,
				getString(R.string.Cerrar_Sesion)).setIcon(
				R.drawable.logout);
		menu.add(1, CREAR_DATOS_PRUEBA, 1,
				getString(R.string.Crear_Datos_Prueba)).setIcon(
				R.drawable.logout);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case CERRAR_SESION:	
			
			//Traemos el resultado del CHECK
			Bundle oBundle = getIntent().getExtras();
			String check_result = oBundle.getString("CHECK");
			
			// Borramos los datos de logueo del aplicativo
			SharedPreferences prefs = getSharedPreferences("login", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
				
			//Validamos el resultado del CHECK
			if(check_result.equals("desactivado")){
				editor.putString("usuario", "");
				editor.putString("password", "");
			}
			editor.commit();
			
			// Iniciamos la pantalla de login
			Intent i = new Intent(EstadoActivity.this, LoginActivity.class);
			startActivity(i);
			finish();
			break;
		case CREAR_DATOS_PRUEBA:			
			boolean ok = ServicioController.getInstance().crearDatosPrueba();
			if (ok) {
				Toast.makeText(getApplicationContext(), "Datos de Prueba Creados", Toast.LENGTH_SHORT);
				onResume();
			}
			break;
		}
		return false;
	}
	
	public void popular() {
		if (ServicioWorkingSet.recibirServicios) {
			swtServicios.setChecked(true);
		} else {
			swtServicios.setChecked(false);
		}		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		popular();
		listaServicios = ServicioController.getInstance().obtenerServiciosPendientes();
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onBackPressed() {
		System.out.println("FIN APLICACION");
		GCMRegistrar.unregister(getApplicationContext());
		stopService(new Intent(EstadoActivity.this, TrackingService.class));
		finish();
	}
	
	// CONFIRMAR
	class confirmarServicio extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			String URL = ServerConstants.SERVER_IP + 
					"/ordenServicio/" +
					servicio.getIdServicio() +
					"/confirma";								
			
			System.out.println("---URL---\n"+URL);
			String respuesta = SigetDriverUtil.connect(URL);
			System.out.println("---RESPUESTA---\n"+respuesta);
			
			// TRACKING
			String recibir = "";
			if (ServicioWorkingSet.recibirServicios) {
				recibir = "Activo";
			} else {
				recibir = "Inactivo";
			}
			
			String URL2 = ServerConstants.SERVER_IP + 
					"/unidadMovil/" + ServicioWorkingSet.codigoUnidad + "/ubicacion/" +
					lat +
					"," + lon +
					"/" + recibir;								
			
			System.out.println("---URL---\n"+URL2);
			String respuesta2 = SigetDriverUtil.connectPost(URL2);
			System.out.println("---RESPUESTA---\n"+respuesta2);
			
			try {
				JSONObject jsonResponse = new JSONObject(respuesta2);
				int estado = jsonResponse.getInt("estado");
				
				if (estado == 1) {
					System.out.println("Ubicacion OK");
				} else {
					System.out.println("Ubicacion ERROR");
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
	}
	
	public void btnActualizar_onClick (View v) {

		listaServicios = ServicioController.getInstance().obtenerServiciosPendientes();		
		adapter = new ServicioAdapter(getApplicationContext(), R.layout.item_servicio, listaServicios);
		lista.setAdapter(adapter);
		Log.e("LISTA", listaServicios+"");
		
	}
		
		
	}


