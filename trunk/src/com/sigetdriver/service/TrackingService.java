package com.sigetdriver.service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.dsbmobile.dsbframework.util.GPSTracker;
import com.sigetdriver.ServerConstants;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.util.SigetDriverUtil;

public class TrackingService extends Service {
	
	private Context context;
	private GPSTracker gps;
	private SharedPreferences prefs;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {		
		System.out.println("---TRACKING SERVICE---");
		System.out.println("---[INICIO]---");
		context = getApplicationContext();
		prefs = getSharedPreferences("ubicacion", Context.MODE_PRIVATE);
		// Iniciamos el timer que contara cada 5 segundos    	
		iniciarTimerUbicacion();
		// Iniciamos el GPSTracker
		gps = new GPSTracker(context);
	}
	
	private void iniciarTimerUbicacion() {
		Timer timerUbicacion = new Timer();
		timerUbicacion.schedule(new taskUbicacion(), 5*1000, 5*1000);
	}
	
	private class taskUbicacion extends TimerTask {
		@Override
		public void run() {
			if (gps.getLatitude()!=0 && gps.getLongitude()!=0) {
				if (validarEnvioUbicacion(gps.getLatitude(), gps.getLongitude(), new Date().getTime())) {					
					enviarUbicacion(gps.getLatitude(), gps.getLongitude());
				}
			}			
		}		
	}
	
	private boolean validarEnvioUbicacion(double lat, double lon, double time) {
		// Checamos el tiempo 3 min. y la distancia 100 m.		
		String tiempo = prefs.getString("tiempo", "");
		String latitud = prefs.getString("latitud", "");
		String longitud = prefs.getString("longitud", "");
		if (tiempo.isEmpty() && latitud.isEmpty() && longitud.isEmpty()) {
			guardarPreferences(lat, lon, time);
			return false;
		} else {
			// Validamos el tiempo
			if (calcularDifTiempo(tiempo, time)) {
				Log.i(getClass().getCanonicalName(), "llegaste a 3 minutos");
				guardarPreferences(lat, lon, time);
				return true;
			} else if (calcularDifDistancia(latitud, longitud, lat, lon)) {
				Log.i(getClass().getCanonicalName(), "llegaste a 100 metros");
				guardarPreferences(lat, lon, time);
				return true;
			} else {
				return false;
			}
		}		
	}
	
	public boolean calcularDifTiempo(String tiempo, double time) {
		double tiempoTrans = time-(Double.parseDouble(tiempo));
//		System.out.println("-TIEMPO TRANSCURRIDO: " + tiempoTrans);
		if (tiempoTrans>=(3*60*1000)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean calcularDifDistancia(String latOri, String lonOri, double latDes, double lonDes) {
		double distancia = gps.distFrom(Double.parseDouble(latOri), Double.parseDouble(lonOri), latDes, lonDes)*1000*1000;
//		System.out.println("-DISTANCIA CALCULADA: " + distancia);
		if (distancia>=100) {			
			return true;
		} else {			
			return false;
		}
	}
	
	public void guardarPreferences(double lat, double lon, double time) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("tiempo", String.valueOf(time));
		editor.putString("latitud", String.valueOf(lat));
		editor.putString("longitud", String.valueOf(lon));
		editor.commit();
	}
	
	private void enviarUbicacion(double lat, double lon) {
		String[] parametros = new String[2];
		parametros[0] = String.valueOf(lat);
		parametros[1] = String.valueOf(lon);
		
		new enviarUbicacion().execute(parametros);
	}
	
	// UBICACION - TAREA ASINCRONA
	class enviarUbicacion extends AsyncTask<String, Void, Void> {
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String recibir = "";
			if (ServicioWorkingSet.recibirServicios) {
				recibir = "Activo";
			} else {
				recibir = "Inactivo";
			}
			
			String URL = ServerConstants.SERVER_IP + 
					"/unidadMovil/" + ServicioWorkingSet.codigoUnidad + "/ubicacion/" +
					params[0] +
					"," + params[1] +
					"/" + recibir;								
			
			System.out.println("---URL---\n"+URL);
			String respuesta = SigetDriverUtil.connectPost(URL);
			System.out.println("---RESPUESTA---\n"+respuesta);
			
			try {
				JSONObject jsonResponse = new JSONObject(respuesta);
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

}
