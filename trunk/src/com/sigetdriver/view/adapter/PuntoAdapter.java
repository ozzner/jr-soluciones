package com.sigetdriver.view.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsbmobile.dsbframework.util.GPSTracker;
import com.sigetdriver.R;
import com.sigetdriver.ServerConstants;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.controller.PuntoController;
import com.sigetdriver.controller.ServicioController;
import com.sigetdriver.controller.TarifaZonaController;
import com.sigetdriver.controller.ZonaController;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.entities.ZonaBean;
import com.sigetdriver.util.ConnectionDetector;
import com.sigetdriver.util.SigetDriverUtil;
import com.sigetdriver.view.activity.ServicioActivity;
import com.sigetdriver.view.activity.VoucherActivity;

public class PuntoAdapter extends ArrayAdapter<PuntoBean> {
	
	private Context context;
	private List<PuntoBean> items;
	int resource;
	private double lat = 0;
	private double lon = 0;

	public PuntoAdapter(Context context, int resource,
			List<PuntoBean> items) {
		super(context, resource);
		this.context = context;
		this.items = items;
		this.resource = resource;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final PuntoBean item = getItem(position);
		final LinearLayout nuevaVista;

		if (convertView == null) {
			nuevaVista = new LinearLayout(getContext());
			inflater.inflate(resource, nuevaVista, true);
		} else {
			nuevaVista = (LinearLayout) convertView;
		}

		final LinearLayout linDatos = (LinearLayout) nuevaVista.findViewById(R.id.linDatos);
		TextView txtTipoPunto = (TextView) nuevaVista.findViewById(R.id.txtTipoPunto);
		TextView txtFinal = (TextView) nuevaVista.findViewById(R.id.txtFinal);
		TextView txtZona = (TextView) nuevaVista.findViewById(R.id.txtZona);
		TextView txtDireccion = (TextView) nuevaVista.findViewById(R.id.txtDireccion);
		Button btnAccion = (Button) nuevaVista.findViewById(R.id.btnAccion);
		
		final int orden = Integer.parseInt(item.getOrden());
		if (ServicioWorkingSet.primerServicio) {
			if (ServicioWorkingSet.puntoActual == orden ||
					ServicioWorkingSet.puntoActual == (orden+1)) {
				btnAccion.setEnabled(true);			
				if (ServicioWorkingSet.puntoActual == orden) {
					btnAccion.setText("IR");
				} else if (ServicioWorkingSet.puntoActual == (orden+1)) {
					btnAccion.setText("LLEGAR");
				}
			} else {
				btnAccion.setEnabled(false);
				btnAccion.setText("IR");
			}
		} else {
			btnAccion.setEnabled(false);
			btnAccion.setText("IR");
		}		
		
		btnAccion.setOnTouchListener(new OnTouchListener() {
			
			private GestureDetector gestureDetectorPunto = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
		        @Override
		        public boolean onDoubleTap(MotionEvent e) {
		        	
		        	Log.i("PuntoAdapter","puntoActual: "+ServicioWorkingSet.puntoActual+
							"\npuntoFinal:"+ServicioWorkingSet.puntoFinal+
							"\nitemOrden:"+item.getOrden()+"\n-----------");
		        	
		        	if (ServicioWorkingSet.puntoActual == orden) {
		        		// ACCION IR
		        		if (item.getOrden().equals("1")) {
							if (ServicioWorkingSet.servicio.getEstado().equals(ServicioBean.ESTADO_PENDIENTE)) {								
//								Toast.makeText(getContext(), "Servicio Iniciado", Toast.LENGTH_SHORT).show();
								ServicioWorkingSet.servicio.setEstado(ServicioBean.ESTADO_ACTIVO);
								ServicioWorkingSet.puntoActual++;
								
								// ENVIO GET PARA INICIAR ORIGEN
		            			GPSTracker gps = new GPSTracker(context);
		            			lat = gps.getLatitude();
		            			lon = gps.getLongitude();
								new inicioOrigen().execute();
							}
						} else {
							if (ServicioWorkingSet.servicio.getEstado().equals(ServicioBean.ESTADO_ACTIVO) &&
									ServicioWorkingSet.puntoActual == orden) {
//								Toast.makeText(getContext(), "Servicio Actualizado", Toast.LENGTH_SHORT).show();
								if (ServicioWorkingSet.puntoActual == 3) {
			        				// ENVIO GET PARA LLEGAR ORIGEN
			            			GPSTracker gps = new GPSTracker(context);
			            			lat = gps.getLatitude();
			            			lon = gps.getLongitude();
									new primerDestino().execute();
			        			}
								ServicioWorkingSet.puntoActual++;
							}
						}
		        		
		        		// Hora Ir
						ServicioWorkingSet.servicio.getPuntos()
							.get((Integer.parseInt(item.getOrden())-1)/2)
							.setHoraIr(SigetDriverUtil.obtenerHoraActual());
						System.out.println("Actualizando la hora ir para: "+(Integer.parseInt(item.getOrden())-1)/2);
								        		
		        	} else if (ServicioWorkingSet.puntoActual == (orden+1)) {
		        		
		        		// Hora Llegar
						ServicioWorkingSet.servicio.getPuntos()
							.get((Integer.parseInt(item.getOrden())-1)/2)
								.setHoraLlegada(SigetDriverUtil.obtenerHoraActual());
						System.out.println("Actualizando la hora llegar para: "+(Integer.parseInt(item.getOrden())-1)/2);
						
						// Coordenada Llegar
						GPSTracker gps = new GPSTracker(context);
						double lat = gps.getLatitude();
						double lon = gps.getLongitude();
						ServicioWorkingSet.servicio.getPuntos()
							.get((Integer.parseInt(item.getOrden())-1)/2)
								.setLatitudLlegada(String.valueOf(lat));
						ServicioWorkingSet.servicio.getPuntos()
							.get((Integer.parseInt(item.getOrden())-1)/2)
								.setLongitudLlegada(String.valueOf(lon));
		        		
		        		// ACCION LLEGAR
		        		if (ServicioWorkingSet.puntoActual == ServicioWorkingSet.puntoFinal) {
		        			if (ServicioWorkingSet.servicio.getPuntos().size()<2) {
		        				Toast.makeText(context, "Se necesita al menos un destino para calcular la tarifa", Toast.LENGTH_LONG).show();
		        			} else {			        			
			        			ServicioWorkingSet.servicio.setEstado(ServicioBean.ESTADO_TERMINADO);
			        			ConnectionDetector cd = new ConnectionDetector(context);		        			 
			        			Boolean isInternetPresent = cd.isConnectingToInternet();
			        			if (isInternetPresent) {
			        				// SI HAY INTERNET
//			        				Toast.makeText(getContext(), "Servicio Terminado", Toast.LENGTH_SHORT).show();
			        				new calcularTarifaOnline().execute();
			        			} else {
			        				// NO HAY INTERNET
			        				if (ServicioWorkingSet.servicio.getPuntos().size()==2) {
			        					// CALCULO OFFLINE
//			        					Toast.makeText(getContext(), "Servicio Terminado", Toast.LENGTH_SHORT).show();
			        					System.out.println("---OFFLINE---");
			        					String zona_origen = ZonaController.getInstance().obtenerIdZonaPorNombre(
			        							ServicioWorkingSet.servicio.getPuntos().get(0).getZona());
			        					String zona_destino = ZonaController.getInstance().obtenerIdZonaPorNombre(
			        							ServicioWorkingSet.servicio.getPuntos().get(1).getZona());
			        					System.out.println("ORIGEN: "+zona_origen);
			        					System.out.println("DESTINO: "+zona_destino);
			        					String tarifa = TarifaZonaController.getInstance().calcularTarifaOffline(
			        										zona_origen, 
			        											zona_destino);
			        					if (!tarifa.equals("0")) {
			        						ServicioWorkingSet.servicio.getPuntos().get(1).setTarifa(tarifa);
				        					Intent i = new Intent(((ServicioActivity) context), VoucherActivity.class);
				        					ServicioWorkingSet.servicio = ((ServicioActivity) context).servicio;
				        					((ServicioActivity) context).startActivity(i);
				        					((ServicioActivity) context).finish();
			        					} else {
			        						Toast.makeText(context, "Error de calculo de tarifa offline", Toast.LENGTH_LONG).show();
			        					}
			        				} else {
			        					Toast.makeText(context, "Error de conexión a internet, vuelva a intentar", Toast.LENGTH_LONG).show();
			        				}
			        			}
		        			}		        					        			
		        		} else {
//		        			Toast.makeText(getContext(), "Servicio Actualizado", Toast.LENGTH_SHORT).show();
		        			if (ServicioWorkingSet.puntoActual == 2) {
		        				// ENVIO GET PARA LLEGAR ORIGEN
								new llegadaOrigen().execute();
		        			}
		        			ServicioWorkingSet.puntoActual++;		        										
		        		}
						
						ZonaBean zona = ZonaController.getInstance().ubicarZona(lat, lon);
						ServicioWorkingSet.servicio.getPuntos()
							.get((Integer.parseInt(item.getOrden())-1)/2)
								.setZona(zona.getNombre());
						ServicioWorkingSet.servicio.getPuntos()
							.get((Integer.parseInt(item.getOrden())-1)/2)
								.setZonaObjeto(zona);
						
						System.out.println("---COORDENADA LLEGAR---");
						System.out.println("[LATITUD] = "+String.valueOf(gps.getLatitude()));
						System.out.println("[LONGITUD] = "+String.valueOf(gps.getLongitude()));
						System.out.println("-----------------------");
						
		        	}
		        	// Actualizamos los datos del servicio en BD
		        	// SERVICIO
		        	ServicioWorkingSet.servicio.setPuntoActual(String.valueOf(ServicioWorkingSet.puntoActual));
		        	ServicioController.getInstance().actualizarServicio(ServicioWorkingSet.servicio);
		        	// PUNTOS
		        	PuntoController.getInstance().actualizarPuntosPorServicio(ServicioWorkingSet.servicio);
		        	
		        	((ServicioActivity) context).repopular();		        	
		            return super.onDoubleTap(e);
		        }
		    });
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				gestureDetectorPunto.onTouchEvent(event);
				return true;
			}
		});
		
		if (item.getOrden().equals("1")) {
			txtTipoPunto.setText("PUNTO ORIGEN");
			txtTipoPunto.setTextColor(Color.parseColor("#088A08"));
		} else {
			txtTipoPunto.setText("PUNTO DESTINO");	
			txtTipoPunto.setTextColor(Color.parseColor("#0040FF"));
		}
		if (ServicioWorkingSet.servicio.getPuntoFinalConfirmado() == ServicioBean.CONFIRMADO_SI &&
				item.getOrden().equals(String.valueOf((ServicioWorkingSet.puntoFinal-1)))) {
			txtFinal.setVisibility(View.VISIBLE);
		} else {
			txtFinal.setVisibility(View.INVISIBLE);
		}
		txtZona.setText(item.getZona());
		txtDireccion.setText(item.getDireccion());
		
		return nuevaVista;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public PuntoBean getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	// TARIFA ONLINE - TAREA ASINCRONA
	class calcularTarifaOnline extends AsyncTask<Void, Void, Void> {
		
		private ProgressDialog Dialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Dialog = new ProgressDialog(getContext());
			Dialog.setCancelable(false);
            Dialog.setMessage("Calculando Tarifa...");
            Dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub						
			String URL = ServerConstants.SERVER_IP + 
					ServerConstants.TARIFA + 
					"1" + "/";
			System.out.println("---URL---\n"+URL);
			String respuesta = null;
			
			ArrayList<PuntoBean> puntos = ServicioWorkingSet.servicio.getPuntos();
			
			try {
				JSONObject jsonPadre = new JSONObject();
				jsonPadre.accumulate("tipoServicio", "punto a punto");
				
				// PUNTO ORIGEN
				JSONObject jsonHijo = new JSONObject();
				jsonPadre.accumulate("origenLatitud", Double.parseDouble(puntos.get(0).getLatitudLlegada()));
				jsonPadre.accumulate("origenLongitud", Double.parseDouble(puntos.get(0).getLongitudLlegada()));
				
				// DESTINOS
				JSONArray jsonArrayHijo = new JSONArray();
				JSONObject jsonNieto = new JSONObject();
				for (int i=1 ; i<puntos.size() ; i++) {
					jsonHijo = new JSONObject();
					jsonHijo.accumulate("puntoLatitud", Double.parseDouble(puntos.get(i).getLatitudLlegada()));
					jsonHijo.accumulate("puntoLongitud", Double.parseDouble(puntos.get(i).getLongitudLlegada()));
					jsonArrayHijo.put(jsonHijo);
				}
				
				jsonPadre.accumulate("destinos", jsonArrayHijo);
				
				System.out.println("---JSON A ENVIAR---\n"+jsonPadre);
				
				respuesta = SigetDriverUtil.makeHttpRequest(URL, "POST", jsonPadre).toString();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("---RESPUESTA---\n"+respuesta);
			
			try {
				JSONObject jsonRespuesta = new JSONObject(respuesta);
				JSONArray jsonDestinos = new JSONArray(jsonRespuesta.getString("destinos"));
				for (int i=0 ; i<jsonDestinos.length() ; i++) {
					String tarifa = ((JSONObject) jsonDestinos.get(i)).getString("tarifa");
					System.out.println("TARIFA: "+tarifa);
					ServicioWorkingSet.servicio.getPuntos().get(i+1).setTarifa(tarifa);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Dialog.dismiss(); 		
			Intent i = new Intent(((ServicioActivity) context), VoucherActivity.class);
			ServicioWorkingSet.servicio = ((ServicioActivity) context).servicio;
			((ServicioActivity) context).startActivity(i);
			((ServicioActivity) context).finish();
		}
		
	}
	
	// INICIO ORIGEN
	class inicioOrigen extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			String URL = ServerConstants.SERVER_IP + 
					"/ordenServicio/" +
					ServicioWorkingSet.servicio.getIdServicio() +
					"/inicioOrigen";								
			
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
	
	// PRIMER DESTINO
	class primerDestino extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			String URL = ServerConstants.SERVER_IP + 
					"/ordenServicio/" +
					ServicioWorkingSet.servicio.getIdServicio() +
					"/inicioPrimerDestino";								
			
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
	
	// LLEGADA ORIGEN
	class llegadaOrigen extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			String URL = ServerConstants.SERVER_IP + 
					"/ordenServicio/" +
					ServicioWorkingSet.servicio.getIdServicio() +
					"/llegadaOrigen";								
			
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

}
