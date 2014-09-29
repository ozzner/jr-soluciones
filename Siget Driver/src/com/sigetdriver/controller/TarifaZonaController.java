package com.sigetdriver.controller;

import java.util.ArrayList;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.sigetdriver.SigetDriver;
import com.sigetdriver.entities.TarifaZonaBean;
import com.sigetdriver.entities.ZonaBean;

public class TarifaZonaController {
	
	public static TarifaZonaController instance;
	
	public static TarifaZonaController getInstance() {
		if (instance == null) {
			instance = new TarifaZonaController();
		}
		return instance;
	}
	
	public void actualizarTarifasZonas(ArrayList<TarifaZonaBean> listaTarifasZona) {
		
		for (TarifaZonaBean tarifaZona : listaTarifasZona) {		
			long result = TarifaZonaBean.tableHelper.updateEntity(
					tarifaZona, 
						TarifaZonaBean.ID_COLUMN_NAME + "=?", 
							new String[] { tarifaZona.getId() });			
			if (result == 0) {				
				result = TarifaZonaBean.tableHelper.insertEntity(tarifaZona);				
			}		
		}		
		
	}
	
	public void actualizarTarifaZona(TarifaZonaBean tarifaZona) {
			
		long result = TarifaZonaBean.tableHelper.updateEntity(
				tarifaZona, 
					TarifaZonaBean.ID_COLUMN_NAME + "=?", 
						new String[] { tarifaZona.getId() });			
		if (result == 0) {				
			result = TarifaZonaBean.tableHelper.insertEntity(tarifaZona);				
		}		
		
	}
	
	public String calcularTarifaOffline(String idOrigen, String idDestino) {
		String tarifa = "0";
		ArrayList<TarifaZonaBean> tarifas = new ArrayList<TarifaZonaBean>();
		ArrayList<Entity> entities = TarifaZonaBean.tableHelper.getEntities(
				TarifaZonaBean.ZONA_ORIGEN_COLUMN_NAME + "=?", 
					new String[] { idOrigen });		 
		for (Entity entity : entities) {
			String zonaDestino = ((TarifaZonaBean) entity).getZonaDestino();
			if (zonaDestino.equals(idDestino)) {
				return ((TarifaZonaBean) entity).getTarifa();
			}
		}
		return tarifa;
	}

}
