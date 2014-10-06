package com.sigetdriver.persistence;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.dsbmobile.dsbframework.controller.persistence.EntityFactory;
import com.sigetdriver.entities.CentroCostoBean;
import com.sigetdriver.entities.EmpresaBean;
import com.sigetdriver.entities.PasajeroBean;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.SedeBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.entities.TarifaZonaBean;
import com.sigetdriver.entities.ZonaBean;

public class SigetDriverEntityFactory extends EntityFactory {

	@Override
	public Entity newEntity(String tableName) {		
		
		if (tableName.equals(DatabaseConstants.SERVICIO_TABLE_NAME)) {
			return new ServicioBean();
		}
		if (tableName.equals(DatabaseConstants.PUNTO_TABLE_NAME)) {
			return new PuntoBean();
		}
		if (tableName.equals(DatabaseConstants.CENTRO_COSTO_TABLE_NAME)) {
			return new CentroCostoBean();
		}
		if (tableName.equals(DatabaseConstants.EMPRESA_TABLE_NAME)) {
			return new EmpresaBean();
		}
		if (tableName.equals(DatabaseConstants.PASAJERO_TABLE_NAME)) {
			return new PasajeroBean();
		}
		if (tableName.equals(DatabaseConstants.SEDE_TABLE_NAME)) {
			return new SedeBean();
		}
		if (tableName.equals(DatabaseConstants.TARIFA_ZONA_TABLE_NAME)) {
			return new TarifaZonaBean();
		}
		if (tableName.equals(DatabaseConstants.ZONA_TABLE_NAME)) {
			return new ZonaBean();
		}
		return null;
	}
	
}
