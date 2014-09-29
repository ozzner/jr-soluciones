package com.sigetdriver.controller;

import java.util.ArrayList;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.sigetdriver.entities.EmpresaBean;
import com.sigetdriver.entities.SedeBean;

public class EmpresaController {
	
	public static EmpresaController instance;
	
	public static EmpresaController getInstance() {
		if (instance == null) {
			instance = new EmpresaController();
		}
		return instance;
	}
	
//	public void actualizarEmpresas(ArrayList<EmpresaBean> listaEmpresas) {
//		
//		for (EmpresaBean empresa : listaEmpresas) {			
////			SigetDriver.getDB().beginTransaction();			
//			long result = EmpresaBean.tableHelper.updateEntity(
//					empresa, 
//						EmpresaBean.COD_EMPRESA_COLUMN_NAME + "=?", 
//							new String[] { empresa.getCodEmpresa() });			
//			if (result == 0) {				
//				result = EmpresaBean.tableHelper.insertEntity(empresa);				
//			}
////			if (result == 1) {
////				SigetDriver.getDB().setTransactionSuccessful();			
////				SigetDriver.getDB().endTransaction();
////			}			
//		}		
//		
//	}
	
	public void actualizarEmpresa(EmpresaBean empresa) {
		
		long result = 1;
		try {
			 result = EmpresaBean.tableHelper.updateEntity(empresa, EmpresaBean.COD_EMPRESA_COLUMN_NAME + "=?",new String[] { empresa.getCodEmpresa()});
		} catch (Exception e) {
			
		}			
		
		
		if (result == 0) {				
			result = EmpresaBean.tableHelper.insertEntity(empresa);				
		}	
		
	}
	
	public ArrayList<SedeBean> obtenerSedesPorEmpresa(String idEmpresa) {
		ArrayList<SedeBean> listaSedes = new ArrayList<SedeBean>();
		ArrayList<Entity> entidades = SedeBean.tableHelper.getEntities(
				SedeBean.EMPRESA_COLUMN_NAME + "=?", 
					new String[] { idEmpresa } );
		for (Entity entidad : entidades) {
			listaSedes.add((SedeBean) entidad);
		}
		System.out.println("TAMAÑO SEDES: " + listaSedes.size());
		return listaSedes;
	}
	
	public String buscarEmpresaPorNombre(String codEmpresa) {
		System.out.println("buscarEmpresa: "+codEmpresa);
		ArrayList<Entity> listaEmpresas = EmpresaBean.tableHelper.getEntities(
				EmpresaBean.RAZON_SOCIAL_COLUMN_NAME + "=?", 
					new String[] { codEmpresa } );
		if (listaEmpresas.size()>0) {
			return ((EmpresaBean) listaEmpresas.get(0)).getCodEmpresa();
		} else {
			return null;
		}
	}

}
