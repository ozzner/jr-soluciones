package com.sigetdriver.view.popup;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.sigetdriver.R;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.entities.ServicioBean;

public class DetalleOrdenPopup {
	
	private Context context;
	public Dialog dialog;
	private TextView txtEmpresa;
	private TextView txtPasajero;
	private TextView txtCelular;
	private TextView txtTipoServicio;
	private TextView txtFecha;
	private TextView txtHora;
	private TextView txtObservacion;
	
	public void dialog(final Context _context) {
		
		context = _context;
		dialog = new Dialog(_context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_detalle);
		dialog.setCanceledOnTouchOutside(true);		
		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				
		popular();
		
		dialog.show();		
	}
	
	public void popular() {
		txtEmpresa = (TextView) dialog.findViewById(R.id.txtEmpresa);
		txtPasajero = (TextView) dialog.findViewById(R.id.txtPasajero);
		txtCelular = (TextView) dialog.findViewById(R.id.txtCelular);
		txtTipoServicio = (TextView) dialog.findViewById(R.id.txtTipoServicio);
		txtFecha = (TextView) dialog.findViewById(R.id.txtFecha);
		txtHora = (TextView) dialog.findViewById(R.id.txtHora);
		txtObservacion = (TextView) dialog.findViewById(R.id.txtObservacion);
		
		txtEmpresa.setText(ServicioWorkingSet.servicio.getEmpresa());
		txtPasajero.setText(ServicioWorkingSet.servicio.getPasajero());
		txtCelular.setText(ServicioWorkingSet.servicio.getCelular());
		if (ServicioWorkingSet.servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_PTO_A_PTO)) {
			txtTipoServicio.setText("Punto a punto");
		} else if (ServicioWorkingSet.servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_COURIER)) {
			txtTipoServicio.setText("Courier");
		} else if (ServicioWorkingSet.servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_HORA_URBANA)) {
			txtTipoServicio.setText("Hora urbana");
		} else if (ServicioWorkingSet.servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_HORA_INTERURBANA)) {
			txtTipoServicio.setText("Hora interurbana");
		}
		txtFecha.setText(ServicioWorkingSet.servicio.getFecha());
		txtHora.setText(ServicioWorkingSet.servicio.getHora());
		txtObservacion.setText(ServicioWorkingSet.servicio.getObservaciones());
	}

}
