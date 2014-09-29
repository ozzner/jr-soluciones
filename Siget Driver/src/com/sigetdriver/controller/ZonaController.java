package com.sigetdriver.controller;

import java.util.ArrayList;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ZonaBean;

public class ZonaController {
	
	public static ZonaController instance;
	
	public static ZonaController getInstance() {
		if (instance == null) {
			instance = new ZonaController();
		}
		return instance;
	}
	
	public void actualizarZona(ZonaBean zona) {
				
		long result = ZonaBean.tableHelper.updateEntity(
				zona, 
					ZonaBean.ID_COLUMN_NAME + "=?", 
						new String[] { zona.getId() });			
		if (result == 0) {				
			result = ZonaBean.tableHelper.insertEntity(zona);				
		}	
		
	}
	
	public ZonaBean ubicarZona(double latitud, double longitud) {
		PuntoBean aux = new PuntoBean();
		aux.setLatitudPunto(String.valueOf(latitud));
		aux.setLongitudPunto(String.valueOf(longitud));
		ArrayList<ZonaBean> zonas = buscarTodas();
		for(ZonaBean zona : zonas){
			if(isPointInPolygon(aux, decodePoly(zona.getPoligono()))){		
				return zona;
			}
		}
		return null;
	}
	
	public ArrayList<ZonaBean> buscarTodas() {		
		ArrayList<ZonaBean> listaZonas = new ArrayList<ZonaBean>();
		ArrayList<Entity> entities = ZonaBean.tableHelper.getEntities(null,	null);		 
		for (Entity entity : entities) {
			listaZonas.add((ZonaBean) entity);
		}
		return listaZonas;		
	}
	
	public static boolean isPointInPolygon(PuntoBean tap, ArrayList<PuntoBean> vertices) {
		int i;
	    int j;
	    double latitudPunto = Double.parseDouble(tap.getLatitudPunto());
	    double longitudPunto = Double.parseDouble(tap.getLongitudPunto());
	    boolean result = false;
	     for (i = 0, j = vertices.size() - 1; i < vertices.size(); j = i++) {
	    	 double ilat = Double.parseDouble(vertices.get(i).getLatitudPunto());
	    	 double ilon = Double.parseDouble(vertices.get(i).getLongitudPunto());
	    	 double jlat = Double.parseDouble(vertices.get(j).getLatitudPunto());
	    	 double jlon = Double.parseDouble(vertices.get(j).getLongitudPunto());
	    	 
	     if ((ilon > longitudPunto) != (jlon > longitudPunto) &&
	          (latitudPunto < (jlat - ilat) * (longitudPunto - ilon) / (jlon-ilon) + ilat)) {
	        result = !result;
	       }
	   }
	   return result;

	}

	public static ArrayList<PuntoBean> decodePoly(String encoded) {
		ArrayList<PuntoBean> poly = new ArrayList<PuntoBean>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;
		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;
			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;
			PuntoBean p = new PuntoBean();
			p.setLatitudPunto(String.valueOf(((double) lat / 1E5)));
			p.setLongitudPunto(String.valueOf(((double) lng / 1E5)));
			poly.add(p);
		}
		return poly;
	}
	
	public String obtenerIdZonaPorNombre(String zona) {
		String id = null;
		ArrayList<Entity> entities = ZonaBean.tableHelper.getEntities(
				ZonaBean.NOMBRE_COLUMN_NAME + "=?",
					new String[] { zona });
		id = ((ZonaBean) entities.get(0)).getId();
		return id;
	}

}
