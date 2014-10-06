package com.sigetdriver.view.activity;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import com.dsbmobile.dsbframework.util.GPSTracker;
import com.sigetdriver.R;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.controller.PuntoController;
import com.sigetdriver.controller.ServicioController;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.util.Helper;
import com.sigetdriver.util.SigetDriverUtil;
import com.sigetdriver.view.adapter.PuntoAdapter;
import com.sigetdriver.view.popup.CancelarServicioPopup;
import com.sigetdriver.view.popup.DetalleOrdenPopup;
import com.sigetdriver.view.popup.PuntoGastosPopup;

public class ServicioActivity extends Activity {
	
	private Switch swtServicios;
	public ServicioBean servicio;
	private ListView list;
	private PuntoAdapter adapter;
	private ArrayList<PuntoBean> listaPuntos;
    private GPSTracker gps;
	
	// Opciones
    private final int VER_DETALLE = 1;
	private final int INSERTAR_DESTINO = 2;
	private final int CANCELAR_SERVICIO = 3;
	// Contextual	
	private final int COMO_LLEGAR = 0;
	private final int GASTOS = 1;
	private final int ELIMINAR_DESTINO = 2;
	private final int EDITAR_DESTINO = 3;
	private final int CONFIRMAR_DESTINO = 4;
		
	private final int RESULT_INSERTAR_DESTINO = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!ServicioController.getInstance().verificarLogin()) {
        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(i);
		}
		
		setContentView(R.layout.activity_servicio);
		
		setTitle("Unidad móvil [" + ServicioWorkingSet.codigoUnidad + "] - " + ServicioWorkingSet.nombreConductor);
		
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
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
		if (ServicioWorkingSet.recibirServicios) {
			swtServicios.setChecked(true);
		} else {
			swtServicios.setChecked(false);
		}	
		list = (ListView) findViewById(R.id.listPuntos);		
		servicio = ServicioWorkingSet.servicio;		
		listaPuntos = servicio.getPuntos();
		adapter = new PuntoAdapter(ServicioActivity.this, R.layout.item_punto, listaPuntos);
		list.setAdapter(adapter);
		Helper.getListViewSize(list);
		registerForContextMenu(list);
	}
	
	@Override
	public void onBackPressed() {
		if (ServicioWorkingSet.servicio.getEstado().equals(ServicioBean.ESTADO_ACTIVO)) {
			Toast.makeText(getApplicationContext(), "Para regresar debe cancelar el servicio", Toast.LENGTH_SHORT).show();			
		} else {
			finish();
		}
	}
	
	/**
	 * MENU OPCIONES
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, VER_DETALLE, 1,
				getString(R.string.Ver_Detalle)).setIcon(
				R.drawable.logout);
		menu.add(2, INSERTAR_DESTINO, 2,
				getString(R.string.Insertar_Destino)).setIcon(
				R.drawable.logout);
		menu.add(3, CANCELAR_SERVICIO, 3,
				getString(R.string.Cancelar_Servicio)).setIcon(
				R.drawable.logout);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case VER_DETALLE:
			DetalleOrdenPopup popup = new DetalleOrdenPopup();
			popup.dialog(ServicioActivity.this);
			break;
		case INSERTAR_DESTINO:
			if (ServicioWorkingSet.servicio.getPuntoFinalConfirmado() == ServicioBean.CONFIRMADO_NO) {
				Intent i2 = new Intent(ServicioActivity.this, InsertarDestinoActivity.class);
				startActivityForResult(i2, RESULT_INSERTAR_DESTINO);
			} else {
				Toast.makeText(getApplicationContext(), "No se pueden agregar mas destinos", Toast.LENGTH_SHORT).show();
			}			
			break;
		case CANCELAR_SERVICIO:			
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Regresar")
			.setMessage(
					"Está seguro de que desea cancelar el servicio iniciado?")
			.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
						}
					})
			.setPositiveButton("Si",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							servicio.setEstado(ServicioBean.ESTADO_CANCELADO);
							boolean ok = ServicioController.getInstance().actualizarServicio(servicio);
							if (ok) {
								CancelarServicioPopup popup = new CancelarServicioPopup();
								popup.dialog(ServicioActivity.this);
							}
						}
			}).show();					
			break;
		}
		return false;
	}
	
	/**
	 * MENU CONTEXTUAL
	 */
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	if (v.getId()==R.id.listPuntos) {
    		String[] menuItems;
    	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;   	      	    
    		menu.setHeaderTitle("Menu Destino");
    		menuItems = new String[]{"Cómo Llegar", "Gastos", "Eliminar", "Editar", "¿Destino Final?"};
    		for (int i = 0; i<menuItems.length; i++) {
    			menu.add(Menu.NONE, i, i, menuItems[i]);
			}
    		if (info.position == 0) {
    			menu.getItem(2).setVisible(false);
    			menu.getItem(3).setVisible(false);
    			menu.getItem(4).setVisible(false);
    		} else {
    			int orden = Integer.parseInt(listaPuntos.get(info.position).getOrden());
    			menu.getItem(4).setVisible(false);
    			System.out.println("ORDEN: "+orden);
    			System.out.println("ACTUAL: "+ServicioWorkingSet.puntoActual);
    			if (info.position == (listaPuntos.size()-1)) {
    				System.out.println("DESTINO FINAL");
    				// Destino Final
    				if ((orden+1) == ServicioWorkingSet.puntoActual) {
    					System.out.println("ELIMINABLE: NO");
    					menu.getItem(2).setVisible(false);
    					System.out.println("---");
    					System.out.println(listaPuntos.get(info.position).getLatitudPunto());
    					System.out.println(listaPuntos.get(info.position).getLongitudPunto());
    					System.out.println("---");
    					if (!listaPuntos.get(info.position).getLatitudPunto().equals("0.0") &&
    							!listaPuntos.get(info.position).getLongitudPunto().equals("0.0")) {
    						menu.getItem(4).setVisible(true);
    						if (ServicioWorkingSet.servicio.getPuntoFinalConfirmado() == ServicioBean.CONFIRMADO_SI) {
    							menu.getItem(4).setTitle("Desconfirmar");
    							System.out.println("DESCONFIRMABLE: SI");
    						} else {
    							System.out.println("CONFIRMABLE: SI");
    						}    						
    					} else {
    						System.out.println("CONFIRMABLE: NO");
    					}
    				}
    			} else {
    				// Destino Otro 
    				System.out.println("DESTINO OTRO");
    				if ((ServicioWorkingSet.puntoActual % 2) == 0 &&
    						orden < ServicioWorkingSet.puntoActual) {
    					System.out.println("ELIMINABLE: NO");
    					menu.getItem(2).setVisible(false);
    				} else if (orden < ServicioWorkingSet.puntoActual) {
    					System.out.println("ELIMINABLE: NO");
    					menu.getItem(2).setVisible(false);
    				} else {
    					System.out.println("ELIMINABLE: SI");
    				}
    			}
    		}
    	}
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
	    final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	    int menuItemIndex = item.getItemId();
		switch (menuItemIndex) {		
		case COMO_LLEGAR:			
			gps = new GPSTracker(ServicioActivity.this);			
			// check if GPS enabled     
            if(gps.canGetLocation()){
                 
                String latOrigen = String.valueOf(gps.getLatitude());
                String lonOrigen = String.valueOf(gps.getLongitude());
                
                String latDestino = servicio.getPuntos().get(info.position).getLatitudPunto();
                String lonDestino = servicio.getPuntos().get(info.position).getLongitudPunto();
                
                if (!latDestino.equals("0.0") &&
                		!lonDestino.equals("0.0")) {
                    Toast.makeText(getApplicationContext(), "Tu ubicación es - \nLat: " + latOrigen + "\nLong: " + lonOrigen, Toast.LENGTH_LONG).show();    
                    
        			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
    				Uri.parse("http://maps.google.com/maps?saddr=" + latOrigen + "," + lonOrigen + "&daddr=" + latDestino + "," +lonDestino));
    				startActivity(intent); 
                } else {
                	Toast.makeText(getApplicationContext(), "No se ha definido un punto", Toast.LENGTH_LONG).show();
                }           
                
            }else{
                gps.showSettingsAlert();
            }
			break;
		case GASTOS:
			PuntoGastosPopup popup = new PuntoGastosPopup();
			popup.dialog(ServicioActivity.this, (info.position), 1);
			break;
		case ELIMINAR_DESTINO:
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Confirmar")
			.setMessage("Está seguro de eliminar?")
			.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
						}
					})
			.setPositiveButton("Si",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							listaPuntos.remove(info.position);
							for (int i=0 ; i<listaPuntos.size() ; i++) {
								PuntoBean punto = listaPuntos.get(i);
								punto.setOrden(String.valueOf((i*2)+1));								
								System.out.println("orden:"+punto.getOrden());
							}
							servicio.setPuntos(listaPuntos);
							ServicioWorkingSet.servicio = servicio;
							ServicioWorkingSet.puntoFinal = listaPuntos.size()*2;
							System.out.println("-Despues de Eliminar");
							System.out.println("Punto Final:"+ServicioWorkingSet.puntoFinal);
							System.out.println("------");
							PuntoController.getInstance().actualizarPuntosPorServicio(ServicioWorkingSet.servicio);
							repopular();
						}
			}).show();							
			break;
		case EDITAR_DESTINO:
			Intent i = new Intent(ServicioActivity.this, EditarDestinoActivity.class);
			i.putExtra("id", info.position);
			startActivityForResult(i, RESULT_INSERTAR_DESTINO);
			break;
		case CONFIRMAR_DESTINO:
			if (ServicioWorkingSet.servicio.getPuntoFinalConfirmado() == ServicioBean.CONFIRMADO_SI) {				
				ServicioWorkingSet.servicio.setPuntoFinalConfirmado(ServicioBean.CONFIRMADO_NO);
				
				// ENVIO GET NO DESTINO FINAL
				new envioNoDestinoFinal().execute();
				
			} else if (ServicioWorkingSet.servicio.getPuntoFinalConfirmado() == ServicioBean.CONFIRMADO_NO) {
				ServicioWorkingSet.servicio.setPuntoFinalConfirmado(ServicioBean.CONFIRMADO_SI);
				
				// ENVIO GET DESTINO FINAL
				new envioDestinoFinal().execute();
				
			}  
			adapter.notifyDataSetChanged();
			break;
		}	    
    	return true;
    }
    
    public void repopular() {
    	System.out.println("Entre a repopular");
    	adapter.notifyDataSetChanged();
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		switch (requestCode) {
		case RESULT_INSERTAR_DESTINO:		
			servicio = ServicioWorkingSet.servicio;
			listaPuntos = servicio.getPuntos();
			repopular();
		default:	
			break;
		}
		
	}
    
	class envioDestinoFinal extends AsyncTask<Void, Void, Void> {
		
		private ProgressDialog Dialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Dialog = new ProgressDialog(ServicioActivity.this);
			Dialog.setCancelable(false);
            Dialog.setMessage("Cargando...");
            Dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			System.out.println("---DESTINO FINAL---");
			String respuesta = SigetDriverUtil.connect("http://54.69.22.121:8080/taxis/rest/unidadMovil/7/destinoFinal");
			System.out.println("---RESPUESTA---\n"+respuesta);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Dialog.dismiss();
		}
		
	}
	
	class envioNoDestinoFinal extends AsyncTask<Void, Void, Void> {
		
		private ProgressDialog Dialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Dialog = new ProgressDialog(ServicioActivity.this);
			Dialog.setCancelable(false);
            Dialog.setMessage("Cargando...");
            Dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			System.out.println("---NO DESTINO FINAL---");
			String respuesta = SigetDriverUtil.connect("http://54.69.22.121:8080/taxis/rest/unidadMovil/7/deshacerDestinoFinal");
			System.out.println("---RESPUESTA---\n"+respuesta);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Dialog.dismiss();
		}
		
	}
	
}
