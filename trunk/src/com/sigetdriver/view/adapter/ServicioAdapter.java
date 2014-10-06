package com.sigetdriver.view.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sigetdriver.R;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.util.SigetDriverUtil;

public class ServicioAdapter extends ArrayAdapter<ServicioBean> {
	
	private List<ServicioBean> items;
	int resource;

	public ServicioAdapter(Context context, int resource,
			List<ServicioBean> items) {
		super(context, resource);
		this.items = items;
		this.resource = resource;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ServicioBean item = getItem(position);
		LinearLayout nuevaVista;

		if (convertView == null) {
			nuevaVista = new LinearLayout(getContext());
			inflater.inflate(resource, nuevaVista, true);

		} else {
			nuevaVista = (LinearLayout) convertView;
		}

		LinearLayout linServicio = (LinearLayout) nuevaVista.findViewById(R.id.linServicio);
		TextView txtIdServicio = (TextView) nuevaVista.findViewById(R.id.txtIdServicio);
		TextView txtFecha = (TextView) nuevaVista.findViewById(R.id.txtFecha);
		TextView txtHora = (TextView) nuevaVista.findViewById(R.id.txtHora);
		TextView txtTiempoRestante = (TextView) nuevaVista.findViewById(R.id.txtTiempoRestante);
		ImageView imgNew = (ImageView) nuevaVista.findViewById(R.id.imgNuevo);
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		
		Date tiempo = new Date(Long.parseLong(item.getFecha()));
		
		txtIdServicio.setText(item.getIdServicio());
		txtFecha.setText(formatoFecha.format(tiempo));
		txtHora.setText(formatoHora.format(tiempo));
		txtTiempoRestante.setText(
				SigetDriverUtil.obtenerDiferenciaTimer(
						Long.parseLong(item.getFecha())));
				
		if (item.getAceptado().equals(ServicioBean.ACEPTADO_NO)) {
			imgNew.setVisibility(View.VISIBLE);
		} else {
			imgNew.setVisibility(View.INVISIBLE);
		}
		
		if (item.getEstado().equals(ServicioBean.ESTADO_ACTIVO)) {
			txtIdServicio.setTextColor(Color.parseColor("#FF0000"));
			txtFecha.setTextColor(Color.parseColor("#FF0000"));
			txtHora.setTextColor(Color.parseColor("#FF0000"));
			txtTiempoRestante.setTextColor(Color.parseColor("#FF0000"));
		} else {
			txtIdServicio.setTextColor(Color.parseColor("#000000"));
			txtFecha.setTextColor(Color.parseColor("#000000"));
			txtHora.setTextColor(Color.parseColor("#000000"));
			txtTiempoRestante.setTextColor(Color.parseColor("#000000"));
		}
		
		return nuevaVista;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public ServicioBean getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
