package com.sigetdriver.controller;

import java.util.ArrayList;

import com.sigetdriver.SigetDriver;
import com.sigetdriver.entities.SedeBean;

public class SedeController {
	
	public static SedeController instance;
	
	public static SedeController getInstance() {
		if (instance == null) {
			instance = new SedeController();
		}
		return instance;
	}
	
	public void actualizarSedes(ArrayList<SedeBean> listaSedes) {
		
		for (SedeBean sede : listaSedes) {			
//			SigetDriver.getDB().beginTransaction();			
			long result = SedeBean.tableHelper.updateEntity(
					sede, 
						SedeBean.ID_COLUMN_NAME + "=?", 
							new String[] { sede.getId() });			
			if (result == 0) {				
				result = SedeBean.tableHelper.insertEntity(sede);				
			}
//			if (result == 1) {
//				SigetDriver.getDB().setTransactionSuccessful();			
//				SigetDriver.getDB().endTransaction();
//			}			
		}		
		
	}
	
	public void actualizarSede(SedeBean sede) {
				
		long result = SedeBean.tableHelper.updateEntity(
				sede, 
					SedeBean.ID_COLUMN_NAME + "=?", 
						new String[] { sede.getId() });			
		if (result == 0) {				
			result = SedeBean.tableHelper.insertEntity(sede);				
		}		
		
	}

}
