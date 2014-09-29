package com.sigetdriver.view.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sigetdriver.R;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.util.SigetDriverUtil;

public class VoucherAdapter extends ArrayAdapter<PuntoBean> {
	
	private List<PuntoBean> items;
	int resource;

	public VoucherAdapter(Context context, int resource,
			List<PuntoBean> items) {
		super(context, resource);
		this.items = items;
		this.resource = resource;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final PuntoBean item = getItem(position);
		LinearLayout nuevaVista;

		if (convertView == null) {
			nuevaVista = new LinearLayout(getContext());
			inflater.inflate(resource, nuevaVista, true);

		} else {
			nuevaVista = (LinearLayout) convertView;
		}		
		TextView txtTipoPunto = (TextView) nuevaVista.findViewById(R.id.txtTipoPunto);
		TextView txtZona = (TextView) nuevaVista.findViewById(R.id.txtZona);
		TextView txtDireccion = (TextView) nuevaVista.findViewById(R.id.txtDireccion);
		TextView txtTiempoEspera = (TextView) nuevaVista.findViewById(R.id.txtTiempoEspera);
		TextView txtEstacionamiento = (TextView) nuevaVista.findViewById(R.id.txtEstacionamiento);
		TextView txtPeaje = (TextView) nuevaVista.findViewById(R.id.txtPeaje);
		TextView txtOtros = (TextView) nuevaVista.findViewById(R.id.txtOtros);
		LinearLayout linTarifa = (LinearLayout) nuevaVista.findViewById(R.id.linTarifa);
		TextView txtTarifa = (TextView) nuevaVista.findViewById(R.id.txtTarifa);
		LinearLayout linHora3 = (LinearLayout) nuevaVista.findViewById(R.id.linHora3);
		TextView lblHora1 = (TextView) nuevaVista.findViewById(R.id.lblHora1);
		TextView txtHora1 = (TextView) nuevaVista.findViewById(R.id.txtHora1);
		TextView txtHora2 = (TextView) nuevaVista.findViewById(R.id.txtHora2);
		TextView txtHora3 = (TextView) nuevaVista.findViewById(R.id.txtHora3);
				
		String ordenFinal = String.valueOf(ServicioWorkingSet.puntoFinal-1);
		String tiempoEspera;
		if (item.getOrden().equals("1")) {
			// Punto Origen
			txtTipoPunto.setText("PUNTO ORIGEN");
			lblHora1.setText("Hora Servicio");
			SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
			Date horaServicio = new Date(Long.parseLong(ServicioWorkingSet.servicio.getHora()));
			String horaFormateada = formatoHora.format(horaServicio);
			txtHora1.setText(horaFormateada);
			txtHora2.setText(item.getHoraLlegada());			
			tiempoEspera = SigetDriverUtil.obtenerDiferenciaHoras(
					ServicioWorkingSet.servicio.getPuntos().get(position+1).getHoraIr(),
					horaFormateada);
			txtHora3.setText(tiempoEspera);
			linHora3.setVisibility(View.VISIBLE);
			linTarifa.setVisibility(View.GONE);
		} else {
			// Punto Destino
			txtTipoPunto.setText("PUNTO DESTINO");	
			lblHora1.setText("Hora Ir");
			txtHora1.setText(item.getHoraIr());
			txtHora2.setText(item.getHoraLlegada());
			if (item.getOrden().equals(ordenFinal)) {
				linHora3.setVisibility(View.GONE);
			} else {
				linHora3.setVisibility(View.VISIBLE);
				tiempoEspera = SigetDriverUtil.obtenerDiferenciaHoras(
						ServicioWorkingSet.servicio.getPuntos().get(position+1).getHoraIr(), 
						item.getHoraLlegada());
				txtHora3.setText(tiempoEspera);
			}
			linTarifa.setVisibility(View.VISIBLE);
		} 
		txtZona.setText(item.getZona());
		txtDireccion.setText(item.getDireccion());
		
		int recargoCourier = 0;
		if (ServicioWorkingSet.servicio.getTipoServicio().equals(ServicioBean.TIPO_SERVICIO_COURIER)) {
			recargoCourier = 5;
		}
		
		String gastoTiempoEspera = item.getGastoTiempoEspera();
		String gastoEstacionamiento = item.getGastoEstacionamiento();
		String gastoPeaje = item.getGastoPeaje();
		String gastoOtros = item.getGastoOtros();
		
		if (gastoTiempoEspera.equals("0")) gastoTiempoEspera = "0.00";
		if (gastoEstacionamiento.equals("0")) gastoEstacionamiento = "0.00";
		if (gastoPeaje.equals("0")) gastoPeaje = "0.00";
		if (gastoOtros.equals("0")) gastoOtros = "0.00";
		
		txtTiempoEspera.setText("S/. "+String.format( "%.2f", Double.parseDouble(gastoTiempoEspera)).replace(',', '.'));
		txtEstacionamiento.setText("S/. "+String.format( "%.2f", Double.parseDouble(gastoEstacionamiento)).replace(',', '.'));
		txtPeaje.setText("S/. "+String.format( "%.2f", Double.parseDouble(gastoPeaje)).replace(',', '.'));
		txtOtros.setText("S/. "+String.format( "%.2f", Double.parseDouble(gastoOtros)).replace(',', '.'));
		txtTarifa.setText("S/. "+String.format( "%.2f", Double.parseDouble(
				String.valueOf((Double.parseDouble(item.getTarifa())+recargoCourier)))).replace(',', '.'));
								
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

}
