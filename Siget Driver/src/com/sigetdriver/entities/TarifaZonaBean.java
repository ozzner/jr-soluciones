package com.sigetdriver.entities;

import java.io.Serializable;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.dsbmobile.dsbframework.controller.persistence.TableHelper;
import com.sigetdriver.persistence.DatabaseConstants;
import com.sigetdriver.persistence.SigetDriverEntityFactory;

public class TarifaZonaBean extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLES	
	private String id = "";
	private String zonaOrigen = "";
	private String zonaDestino = "";
	private String tarifa = "";
	private String sede = "";
	private String empresa = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getZonaOrigen() {
		return zonaOrigen;
	}
	public void setZonaOrigen(String zonaOrigen) {
		this.zonaOrigen = zonaOrigen;
	}
	public String getZonaDestino() {
		return zonaDestino;
	}
	public void setZonaDestino(String zonaDestino) {
		this.zonaDestino = zonaDestino;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}	
	
	/**
	 * SQLITE
	 */
	
	// COLUMNA 1
	public static final String ID_COLUMN_NAME = "ID";
	public static final String ID_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ID_COLUMN_ORDER = 1;
	// COLUMNA 2
	public static final String ZONA_ORIGEN_COLUMN_NAME = "ZONA_ORIGEN";
	public static final String ZONA_ORIGEN_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ZONA_ORIGEN_COLUMN_ORDER = 2;
	// COLUMNA 3
	public static final String ZONA_DESTINO_COLUMN_NAME = "ZONA_DESTINO";
	public static final String ZONA_DESTINO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ZONA_DESTINO_COLUMN_ORDER = 3;
	// COLUMNA 4
	public static final String TARIFA_COLUMN_NAME = "TARIFA";
	public static final String TARIFA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int TARIFA_COLUMN_ORDER = 4;
	// COLUMNA 5
	public static final String SEDE_COLUMN_NAME = "SEDE";
	public static final String SEDE_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int SEDE_COLUMN_ORDER = 5;
	// COLUMNA 6
	public static final String EMPRESA_COLUMN_NAME = "EMPRESA";
	public static final String EMPRESA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int EMPRESA_COLUMN_ORDER = 6;
	
	@Override
	public Object getColumnValue(int column) {
		// TODO Auto-generated method stub
		switch (column) {
		case ID_COLUMN_ORDER:
			return id;
		case ZONA_ORIGEN_COLUMN_ORDER:
			return zonaOrigen;
		case ZONA_DESTINO_COLUMN_ORDER:
			return zonaDestino;
		case TARIFA_COLUMN_ORDER:
			return tarifa;
		case SEDE_COLUMN_ORDER:
			return sede;
		case EMPRESA_COLUMN_ORDER:
			return empresa;			
		}
		return null;
	}
	
	@Override
	public void setColumnValue(int column, Object object) {
		switch (column) {
		case ID_COLUMN_ORDER:
			id = (String) object;
			break;
		case ZONA_ORIGEN_COLUMN_ORDER:
			zonaOrigen = (String) object;
			break;
		case ZONA_DESTINO_COLUMN_ORDER:
			zonaDestino = (String) object;
			break;
		case TARIFA_COLUMN_ORDER:
			tarifa = (String) object;
			break;
		case SEDE_COLUMN_ORDER:
			sede = (String) object;
			break;
		case EMPRESA_COLUMN_ORDER:
			empresa = (String) object;
			break;		
		}
	}
	
	public static final TableHelper tableHelper = new TableHelper(
			DatabaseConstants.TARIFA_ZONA_TABLE_NAME, new SigetDriverEntityFactory())
			.addColumn(ID_COLUMN_NAME, ID_COLUMN_TYPE, ID_COLUMN_ORDER)
			.addColumn(ZONA_ORIGEN_COLUMN_NAME, ZONA_ORIGEN_COLUMN_TYPE, ZONA_ORIGEN_COLUMN_ORDER)
			.addColumn(ZONA_DESTINO_COLUMN_NAME, ZONA_DESTINO_COLUMN_TYPE, ZONA_DESTINO_COLUMN_ORDER)
			.addColumn(TARIFA_COLUMN_NAME, TARIFA_COLUMN_TYPE, TARIFA_COLUMN_ORDER)
			.addColumn(SEDE_COLUMN_NAME, SEDE_COLUMN_TYPE, SEDE_COLUMN_ORDER)
			.addColumn(EMPRESA_COLUMN_NAME, EMPRESA_COLUMN_TYPE, EMPRESA_COLUMN_ORDER)
			;

}
