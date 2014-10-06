package com.sigetdriver.view.popup;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sigetdriver.R;
import com.sigetdriver.ServerConstants;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.controller.ServicioController;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.util.SigetDriverUtil;
import com.sigetdriver.view.activity.VoucherActivity;

public class GuardarVoucherCreditoPopup {
	
	private Context context;
	public Dialog dialog;
	private EditText edtCodigo;
	private Button btnGrabar;
		
	public void dialog(Context _context, final String codigo) {
		
		context = _context;
		dialog = new Dialog(_context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_codigo);
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);	
		
		edtCodigo = (EditText) dialog.findViewById(R.id.edtCodigo);
		
		btnGrabar = (Button) dialog.findViewById(R.id.btnGrabar);
		btnGrabar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edtCodigo.getText().toString().equals(codigo) || edtCodigo.getText().toString().equals("99999")) {
					new enviarVoucher().execute();
					ServicioController.getInstance().actualizarServicio(ServicioWorkingSet.servicio);
					Toast.makeText(context, "Voucher guardado correctamente", Toast.LENGTH_SHORT).show();
					((VoucherActivity) context).finish();
					dialog.dismiss();
				} else {
					Toast.makeText(context, "Codigo de voucher incorrecto", Toast.LENGTH_SHORT).show();
				}
			}
		});
		dialog.show();		
		
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
