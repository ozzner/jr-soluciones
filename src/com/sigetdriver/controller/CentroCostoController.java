package com.sigetdriver.controller;

import java.util.ArrayList;

import com.sigetdriver.entities.CentroCostoBean;

public class CentroCostoController {
	
	public static CentroCostoController instance;
	
	public static CentroCostoController getInstance() {
		if (instance == null) {
			instance = new CentroCostoController();
		}
		return instance;
	}
	
	public void actualizarCentroCostos(ArrayList<CentroCostoBean> listaCentrosCosto) {
		
		for (CentroCostoBean centroCosto : listaCentrosCosto) {			
//			SigetDriver.getDB().beginTransaction();			
			long result = CentroCostoBean.tableHelper.updateEntity(
					centroCosto, 
						CentroCostoBean.ID_COLUMN_NAME + "=?", 
							new String[] { centroCosto.getId() });			
			if (result == 0) {				
				result = CentroCostoBean.tableHelper.insertEntity(centroCosto);				
			}
//			if (result == 1) {
//				SigetDriver.getDB().setTransactionSuccessful();			
//				SigetDriver.getDB().endTransaction();
//			}			
		}		
		
	}
	
	public void actualizarCentroCosto(CentroCostoBean centroCosto) {
		
		long result = CentroCostoBean.tableHelper.updateEntity(
				centroCosto, 
					CentroCostoBean.ID_COLUMN_NAME + "=?", 
						new String[] { centroCosto.getId() });			
		if (result == 0) {				
			result = CentroCostoBean.tableHelper.insertEntity(centroCosto);				
		}	
		
	}

}
