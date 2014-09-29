package com.sigetdriver.controller;

import java.util.ArrayList;

import com.sigetdriver.entities.PasajeroBean;

public class PasajeroController {
	
	public static PasajeroController instance;
	
	public static PasajeroController getInstance() {
		if (instance == null) {
			instance = new PasajeroController();
		}
		return instance;
	}
	
	public void actualizarPasajeros(ArrayList<PasajeroBean> listaPasajeros) {
		
		for (PasajeroBean pasajero : listaPasajeros) {			
//			SigetDriver.getDB().beginTransaction();			
			long result = PasajeroBean.tableHelper.updateEntity(
					pasajero, 
						PasajeroBean.CODIGO_COLUMN_NAME + "=?", 
							new String[] { pasajero.getCodigo() });			
			if (result == 0) {				
				result = PasajeroBean.tableHelper.insertEntity(pasajero);				
			}
//			if (result == 1) {
//				SigetDriver.getDB().setTransactionSuccessful();			
//				SigetDriver.getDB().endTransaction();
//			}			
		}		
		
	}
	
	public void actualizarPasajero(PasajeroBean pasajero) {
				
		long result = PasajeroBean.tableHelper.updateEntity(
				pasajero, 
					PasajeroBean.CODIGO_COLUMN_NAME + "=?", 
						new String[] { pasajero.getCodigo() });			
		if (result == 0) {				
			result = PasajeroBean.tableHelper.insertEntity(pasajero);				
		}	
		
	}

}
