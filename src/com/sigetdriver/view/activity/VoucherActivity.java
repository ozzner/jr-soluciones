package com.sigetdriver.view.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dsbmobile.dsbframework.util.GPSTracker;
import com.sigetdriver.R;
import com.sigetdriver.ServerConstants;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.controller.ServicioController;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.util.SigetDriverUtil;
import com.sigetdriver.view.adapter.VoucherAdapter;
import com.sigetdriver.view.popup.GuardarVoucherCreditoPopup;
import com.sigetdriver.view.popup.PuntoGastosPopup;

public class VoucherActivity extends Activity {
	
	private Switch swtServicios;
	private TextView txtEmpresa;
	private TextView txtPasajero;
	private TextView txtCelular;
	private TextView txtFecha;
	private TextView txtHora;
	private TextView txtTotal;
	private ServicioBean servicio;
	private ListView listPuntos;
	private VoucherAdapter adapter;
	private Spinner spnCentroCosto;
	private Spinner spnTipoServicio;
	private Spinner spnTipoPago;
	
	// Opciones
	private final int GUARDAR = 0;
	// Contextual
	private final int GASTOS = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!ServicioController.getInstance().verificarLogin()) {
        	Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(i);
		}
		
		setContentView(R.layout.activity_voucher);
		
		setTitle("Unidad móvil [" + ServicioWorkingSet.codigoUnidad + "] - " + ServicioWorkingSet.nombreConductor);
		
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		System.out.println("DATA HORAS\n----------");
		for (PuntoBean punto : ServicioWorkingSet.servicio.getPuntos()) {
			System.out.println("Hora Ir:"+punto.getHoraIr());
			System.out.println("Hora Llegar:"+punto.getHoraLlegada());
			System.out.println("-----------------");
		}
		
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
		
		txtEmpresa = (TextView) findViewById(R.id.txtEmpresa);
		txtPasajero = (TextView) findViewById(R.id.txtPasajero);
		txtCelular = (TextView) findViewById(R.id.txtCelular);txtFecha = (TextView) findViewById(R.id.txtFecha);
		txtHora = (TextView) findViewById(R.id.txtHora);
		txtTotal = (TextView) findViewById(R.id.txtTotal);
		
		listPuntos = (ListView) findViewById(R.id.listPuntos);
		spnCentroCosto = (Spinner) findViewById(R.id.spnCentroCosto);
		spnTipoServicio = (Spinner) findViewById(R.id.spnTipoServicio);
		spnTipoPago = (Spinner) findViewById(R.id.spnTipoPago);
		
		servicio = ServicioWorkingSet.servicio;
		
		txtEmpresa.setText(servicio.getEmpresa());
		txtPasajero.setText(servicio.getPasajero());
		txtCelular.setText(servicio.getCelular());
		txtFecha.setText(servicio.getFecha());
		txtHora.setText(servicio.getHora());
		
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		List<String> listCentroCosto = new ArrayList<String>();
		listCentroCosto.add("Gerencia");
		listCentroCosto.add("Ventas");
		listCentroCosto.add("Operaciones");
		List<String> listTipoServicio = new ArrayList<String>();
		listTipoServicio.add("Punto a Punto");
		listTipoServicio.add("Courier");
		listTipoServicio.add("Hora Urbana");
		listTipoServicio.add("Hora Interurbana");
		List<String> listTipoPago = new ArrayList<String>();
		listTipoPago.add("Credito");
		listTipoPago.add("Al Contado");
		
		ArrayAdapter<String> adapterCentroCosto = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, listCentroCosto);
		ArrayAdapter<String> adapterTipoServicio = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, listTipoServicio);
		ArrayAdapter<String> adapterTipoPago = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, listTipoPago);		
		
		spnCentroCosto.setAdapter(adapterCentroCosto);
		spnTipoServicio.setAdapter(adapterTipoServicio);
		spnTipoPago.setAdapter(adapterTipoPago);
		
		adapter = new VoucherAdapter(VoucherActivity.this, R.layout.item_voucher, servicio.getPuntos());
		listPuntos.setAdapter(adapter);
		registerForContextMenu(listPuntos);
		
		if (servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_PTO_A_PTO)) {
			spnTipoServicio.setSelection(0);
		} else if (servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_COURIER)) {
			spnTipoServicio.setSelection(1);
		} else if (servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_HORA_URBANA)) {
			spnTipoServicio.setSelection(2);
		} else if (servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_HORA_INTERURBANA)) {
			spnTipoServicio.setSelection(3);
		}
		
		if (servicio.getTipoPago().equals(ServicioBean.TIPO_PAGO_CREDITO)) {
			spnTipoPago.setSelection(0);
		} else if (servicio.getTipoPago().equals(ServicioBean.TIPO_PAGO_CONTADO)) {
			spnTipoPago.setSelection(1);
		}
		
		spnTipoServicio.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        if (position == 1) {
		        	ServicioWorkingSet.servicio.setTipoServicio(ServicioBean.TIPO_SERVICIO_COURIER);
		        } else {
		        	if (position == 0) {
		        		ServicioWorkingSet.servicio.setTipoServicio(ServicioBean.TIPO_SERVICIO_PTO_A_PTO);
		        	} else if (position == 2) {
		        		ServicioWorkingSet.servicio.setTipoServicio(ServicioBean.TIPO_SERVICIO_HORA_URBANA);
		        	} else if (position == 3) {
		        		ServicioWorkingSet.servicio.setTipoServicio(ServicioBean.TIPO_SERVICIO_HORA_INTERURBANA);
		        	}
		        }
		        adapter.notifyDataSetChanged();
		        pintarTotal();
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {		        
		    }
		});
		
		pintarTotal();
	}
	
	public void pintarTotal() {
		// Determinamos el total del servicio
		double total = 0;
		for (int i=0 ; i<servicio.getPuntos().size() ; i++) {
			PuntoBean punto = servicio.getPuntos().get(i);
			total += Double.parseDouble(punto.getGastoTiempoEspera());
			total += Double.parseDouble(punto.getGastoEstacionamiento());
			total += Double.parseDouble(punto.getGastoPeaje());
			total += Double.parseDouble(punto.getGastoOtros());			
			if (i!=0) {
				total += Double.parseDouble(punto.getTarifa());
				if (ServicioWorkingSet.servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_COURIER)) {
					total += 5;
				}
			}				
		}
		txtTotal.setText("S/. " + String.valueOf(total));
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Por favor, finalice la liquidación", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * MENU OPCIONES
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, GUARDAR, 0,
				getString(R.string.Guardar)).setIcon(
				R.drawable.logout);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case GUARDAR:
			// Almacenar datos
			
			int tipoServicio = spnTipoServicio.getSelectedItemPosition();
			switch (tipoServicio) {
			case 0:
				ServicioWorkingSet.servicio.setTipoServicio(ServicioBean.TIPO_SERVICIO_PTO_A_PTO);
				break;
			case 1:
				ServicioWorkingSet.servicio.setTipoServicio(ServicioBean.TIPO_SERVICIO_COURIER);
				break;
			case 2:
				ServicioWorkingSet.servicio.setTipoServicio(ServicioBean.TIPO_SERVICIO_HORA_URBANA);
				break;
			case 3:
				ServicioWorkingSet.servicio.setTipoServicio(ServicioBean.TIPO_SERVICIO_HORA_INTERURBANA);
				break;
			}
			int tipoPago = spnTipoPago.getSelectedItemPosition();
			switch (tipoPago) {
			case 0:
				ServicioWorkingSet.servicio.setTipoPago(ServicioBean.TIPO_PAGO_CREDITO);
				break;
			case 1:
				ServicioWorkingSet.servicio.setTipoPago(ServicioBean.TIPO_PAGO_CONTADO);
				break;
			}
			if (ServicioWorkingSet.servicio.getEstado().equals(ServicioBean.ESTADO_TERMINADO)) {
				// Verificar el tipo de pago
				if (ServicioWorkingSet.servicio.getTipoPago().equals(ServicioBean.TIPO_PAGO_CREDITO)) {
					// Iniciamos el popup de codigo de voucher
					GuardarVoucherCreditoPopup popup = new GuardarVoucherCreditoPopup();
					popup.dialog(VoucherActivity.this, ServicioWorkingSet.servicio.getCodigo());
				} else if (ServicioWorkingSet.servicio.getTipoPago().equals(ServicioBean.TIPO_PAGO_CONTADO)) {
					new enviarVoucher().execute();
					// Iniciamos la pantalla del menu
					ServicioController.getInstance().actualizarServicio(ServicioWorkingSet.servicio);
					Intent i = new Intent(VoucherActivity.this, EstadoActivity.class);
					startActivity(i);
					Toast.makeText(getApplicationContext(), "Voucher guardado correctamente", Toast.LENGTH_SHORT).show();
					finish();
				}
			} else {
				finish();
			}
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
    	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
    		menu.setHeaderTitle("Menu Destino");
    		String[] menuItems = new String[]{"Gastos"}; 
    		for (int i = 0; i<menuItems.length; i++) {
    			menu.add(Menu.NONE, i, i, menuItems[i]);
			}
    	}
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
	    final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	    int menuItemIndex = item.getItemId();
		switch (menuItemIndex) {		
		case GASTOS:
			PuntoGastosPopup popup = new PuntoGastosPopup();
			popup.dialog(VoucherActivity.this, (info.position), 2);
			break;
		}	    
    	return true;
    }
    
    public void repopular() {
    	System.out.println("Entre a repopular");
    	adapter.notifyDataSetChanged();
    	pintarTotal();
    }
    
	// ENVIAR VOUCHER
	class enviarVoucher extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			String URL = ServerConstants.SERVER_IP + 
					"/ordenServicio/";								
			
			System.out.println("---URL---\n"+URL);
			String respuesta = "";
			
			ArrayList<PuntoBean> puntos = ServicioWorkingSet.servicio.getPuntos();
			
			try {
				JSONObject jsonPadre = new JSONObject();
				JSONObject jsonHijo = new JSONObject();
				
				double totalEstacionamiento = 0;
				double totalOtros = 0;
				double totalPeaje = 0;
				double totalTarifa = 0;
				double totalServicio = 0;
				
				for (int i=0 ; i<puntos.size() ; i++) {
					PuntoBean punto = puntos.get(i);
					totalEstacionamiento += Double.parseDouble(punto.getGastoEstacionamiento());
					totalOtros += Double.parseDouble(punto.getGastoOtros());
					totalPeaje += Double.parseDouble(punto.getGastoPeaje());
					if (i!=0) {
						totalTarifa += Double.parseDouble(punto.getTarifa());
					}					
				}
				
				totalServicio = totalEstacionamiento + totalOtros + totalPeaje + totalTarifa;			
				
				jsonPadre.accumulate("numReserva", Integer.valueOf(ServicioWorkingSet.servicio.getIdServicio()));
				jsonPadre.accumulate("origenDireccion", puntos.get(0).getDireccion());
				jsonPadre.accumulate("origenLatitud", Double.parseDouble(puntos.get(0).getLatitudLlegada()));
				jsonPadre.accumulate("origenLongitud", Double.parseDouble(puntos.get(0).getLongitudLlegada()));
				jsonPadre.accumulate("observacion", ServicioWorkingSet.servicio.getObservaciones());
				if (ServicioWorkingSet.servicio.getTipoPago().equals(ServicioBean.TIPO_PAGO_CONTADO)) {
					jsonPadre.accumulate("tipoPago", "Contado");
				} else if (ServicioWorkingSet.servicio.getTipoPago().equals(ServicioBean.TIPO_PAGO_CREDITO)) {
					jsonPadre.accumulate("tipoPago", "Credito");
				}
				jsonPadre.accumulate("totalEstacionamiento", totalEstacionamiento);
				jsonPadre.accumulate("totalOtros", totalOtros);
				jsonPadre.accumulate("totalPeaje", totalPeaje);
				jsonPadre.accumulate("totalTarifa", totalTarifa);
				jsonPadre.accumulate("totalServicio", totalServicio);
												
				// DESTINOS
				JSONArray jsonArrayHijo = new JSONArray();
				JSONObject jsonNieto = new JSONObject();
				for (int i=1 ; i<puntos.size() ; i++) {
					jsonHijo = new JSONObject();
					jsonHijo.accumulate("puntoDireccion", puntos.get(i).getDireccion());
					jsonHijo.accumulate("puntoLatitud", Double.parseDouble(puntos.get(i).getLatitudLlegada()));
					jsonHijo.accumulate("puntoLongitud", Double.parseDouble(puntos.get(i).getLongitudLlegada()));
					try {
						jsonHijo.accumulate("horaInicioDestino", Integer.parseInt(puntos.get(i).getHoraIr()));						
					} catch (Exception e) {
						jsonHijo.accumulate("horaInicioDestino", null);
					}
					try {
						jsonHijo.accumulate("horaLlegadaDestino", Integer.parseInt(puntos.get(i).getHoraLlegada()));
					} catch (Exception e) {
						jsonHijo.accumulate("horaLlegadaDestino", null);
					}
					jsonHijo.accumulate("peaje", Double.parseDouble(puntos.get(i).getGastoPeaje()));
					jsonHijo.accumulate("estacionamiento", Double.parseDouble(puntos.get(i).getGastoEstacionamiento()));
					jsonHijo.accumulate("otros", Double.parseDouble(puntos.get(i).getGastoOtros()));
					jsonHijo.accumulate("tarifa", Double.parseDouble(puntos.get(i).getTarifa()));
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
			
			return null;
		}
		
	}

}
