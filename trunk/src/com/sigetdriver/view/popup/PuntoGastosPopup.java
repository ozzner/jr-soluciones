package com.sigetdriver.view.popup;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

import com.sigetdriver.R;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.controller.PuntoController;
import com.sigetdriver.view.activity.VoucherActivity;

public class PuntoGastosPopup {
	
	private Context context;
	public Dialog dialog;
	private EditText edtTiempoEspera;
	private EditText edtEstacionamiento;
	private EditText edtPeaje;
	private EditText edtOtros;
	private Button btnGrabar;
	private Button btnCerrar;
		
	public void dialog(Context _context, final int indexPunto, final int flagOrigen) {
		
		context = _context;
		dialog = new Dialog(_context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_gastos);
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);	
		
		edtTiempoEspera = (EditText) dialog.findViewById(R.id.edtTiempoEspera);
		edtEstacionamiento = (EditText) dialog.findViewById(R.id.edtEstacionamiento);
		edtPeaje = (EditText) dialog.findViewById(R.id.edtPeaje);
		edtOtros = (EditText) dialog.findViewById(R.id.edtOtros);
		
		String TiempoEspera = ServicioWorkingSet.servicio.getPuntos().get(indexPunto).getGastoTiempoEspera();
		String Estacionamiento = ServicioWorkingSet.servicio.getPuntos().get(indexPunto).getGastoEstacionamiento();
		String Peaje = ServicioWorkingSet.servicio.getPuntos().get(indexPunto).getGastoPeaje();
		String Otros = ServicioWorkingSet.servicio.getPuntos().get(indexPunto).getGastoOtros();
		
		if (TiempoEspera.equals("0")) {
			TiempoEspera = "";
		} else {
			TiempoEspera = String.format( "%.2f", Double.parseDouble(TiempoEspera)).replace(',', '.');
		}
		if (Estacionamiento.equals("0")) {
			Estacionamiento = "";
		} else {
			Estacionamiento = String.format( "%.2f", Double.parseDouble(Estacionamiento)).replace(',', '.');
		}
		if (Peaje.equals("0")) {
			Peaje = "";
		} else {
			Peaje = String.format( "%.2f", Double.parseDouble(Peaje)).replace(',', '.');
		}
		if (Otros.equals("0")) {
			Otros = "";
		} else {
			Otros = String.format( "%.2f", Double.parseDouble(Otros)).replace(',', '.');
		}
		
		edtTiempoEspera.setText(TiempoEspera);
		edtEstacionamiento.setText(Estacionamiento);
		edtPeaje.setText(Peaje);
		edtOtros.setText(Otros);
				
		btnCerrar = (Button) dialog.findViewById(R.id.btnCerrar);
		btnCerrar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		btnGrabar = (Button) dialog.findViewById(R.id.btnGrabar);
		btnGrabar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tiempoEspera = edtTiempoEspera.getText().toString();
				String estacionamiento = edtEstacionamiento.getText().toString();
				String peaje = edtPeaje.getText().toString();
				String otros = edtOtros.getText().toString();
				
				if (tiempoEspera.equals("")) {
					tiempoEspera = "0";
				}
				if (estacionamiento.equals("")) {
					estacionamiento = "0";
				}
				if (peaje.equals("")) {
					peaje = "0";
				}
				if (otros.equals("")) {
					otros = "0";
				}
				
				tiempoEspera = String.valueOf(Double.parseDouble(tiempoEspera));
				estacionamiento = String.valueOf(Double.parseDouble(estacionamiento));
				peaje = String.valueOf(Double.parseDouble(peaje));
				otros = String.valueOf(Double.parseDouble(otros));
				
				ServicioWorkingSet.servicio.getPuntos().get(indexPunto).setGastoTiempoEspera(tiempoEspera);
				ServicioWorkingSet.servicio.getPuntos().get(indexPunto).setGastoEstacionamiento(estacionamiento);
				ServicioWorkingSet.servicio.getPuntos().get(indexPunto).setGastoPeaje(peaje);
				ServicioWorkingSet.servicio.getPuntos().get(indexPunto).setGastoOtros(otros);
				if (flagOrigen == 2) {
					((VoucherActivity) context).repopular();
				}
				PuntoController.getInstance().actualizarPuntosPorServicio(ServicioWorkingSet.servicio);
				dialog.dismiss();
			}
		});
		dialog.show();		
		
	}
	
}
