/**
 * @author renzo santillán
 *
 */
package com.apprade.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.apprade.R;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class Adapter_InfoWindow implements InfoWindowAdapter {
	LayoutInflater inflater = null;
	private static String cola;

	/**
	 * @return the cola
	 */
	public String getCola() {
		return cola;
	}

	/**
	 * @param cola the cola to set
	 */
	public void setCola(String cola) {
		this.cola = cola;
	}

	/**
	 * @param inflater
	 */
	public Adapter_InfoWindow(LayoutInflater inflater) {
		super();
		this.inflater = inflater;
	}

	public Adapter_InfoWindow() {
		super();/*Default status*/
	}

	@Override
	public View getInfoContents(Marker makerMap) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		View infoWindows = inflater.inflate(
				R.layout.adapter_calificacion_infowindow, null);

		TextView tvNombre = (TextView) infoWindows.findViewById(R.id.title);
		TextView tvDireccion = (TextView) infoWindows.findViewById(R.id.snippet);
		ImageView ivCola = (ImageView) infoWindows.findViewById(R.id.imgMarker);

		tvNombre.setText(arg0.getTitle());
		tvDireccion.setText(arg0.getSnippet());

		String sCola = getCola();

		switch (sCola) {
		
		case "Alta cola":
			ivCola.setImageResource(R.drawable.img4);
			break;

		case "Cola moderada":
			ivCola.setImageResource(R.drawable.img3);
			break;

		case "Poca cola":
			ivCola.setImageResource(R.drawable.img2);
			break;
			
		case "No hay cola":
			ivCola.setImageResource(R.drawable.img1);
			break;
			
		default:
			ivCola.setImageResource(R.drawable.img1);
			break;
		}

		return (infoWindows);
	}

}
