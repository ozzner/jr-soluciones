package com.sigetdriver.entities;

import java.io.Serializable;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.dsbmobile.dsbframework.controller.persistence.TableHelper;
import com.sigetdriver.persistence.DatabaseConstants;
import com.sigetdriver.persistence.SigetDriverEntityFactory;

public class CentroCostoBean extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLES
	private String id = "";
	private String nombre = "";
	private String descripcion = "";
	private String empresa = "";
	private String flagBaja = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getFlagBaja() {
		return flagBaja;
	}
	public void setFlagBaja(String flagBaja) {
		this.flagBaja = flagBaja;
	}
	
	/**
	 * SQLITE
	 */
	
	// COLUMNA 1
	public static final String ID_COLUMN_NAME = "ID";
	public static final String ID_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ID_COLUMN_ORDER = 1;
	// COLUMNA 2
	public static final String NOMBRE_COLUMN_NAME = "NOMBRE";
	public static final String NOMBRE_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int NOMBRE_COLUMN_ORDER = 2;
	// COLUMNA 3
	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";
	public static final String DESCRIPCION_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int DESCRIPCION_COLUMN_ORDER = 3;
	// COLUMNA 4
	public static final String EMPRESA_COLUMN_NAME = "EMPRESA";
	public static final String EMPRESA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int EMPRESA_COLUMN_ORDER = 4;
	// COLUMNA 5
	public static final String FLAG_BAJA_COLUMN_NAME = "FLAG_BAJA";
	public static final String FLAG_BAJA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int FLAG_BAJA_COLUMN_ORDER = 5;
	
	@Override
	public Object getColumnValue(int column) {
		// TODO Auto-generated method stub
		switch (column) {
		case ID_COLUMN_ORDER:
			return id;
		case NOMBRE_COLUMN_ORDER:
			return nombre;
		case DESCRIPCION_COLUMN_ORDER:
			return descripcion;
		case EMPRESA_COLUMN_ORDER:
			return empresa;
		case FLAG_BAJA_COLUMN_ORDER:
			return flagBaja;		
		}
		return null;
	}
	
	@Override
	public void setColumnValue(int column, Object object) {
		switch (column) {
		case ID_COLUMN_ORDER:
			id = (String) object;
			break;
		case NOMBRE_COLUMN_ORDER:
			nombre = (String) object;
			break;
		case DESCRIPCION_COLUMN_ORDER:
			descripcion = (String) object;
			break;
		case EMPRESA_COLUMN_ORDER:
			empresa = (String) object;
			break;
		case FLAG_BAJA_COLUMN_ORDER:
			flagBaja = (String) object;
			break;		
		}
	}
	
	public static final TableHelper tableHelper = new TableHelper(
			DatabaseConstants.CENTRO_COSTO_TABLE_NAME, new SigetDriverEntityFactory())
			.addColumn(ID_COLUMN_NAME, ID_COLUMN_TYPE, ID_COLUMN_ORDER)
			.addColumn(NOMBRE_COLUMN_NAME, NOMBRE_COLUMN_TYPE, NOMBRE_COLUMN_ORDER)
			.addColumn(DESCRIPCION_COLUMN_NAME, DESCRIPCION_COLUMN_TYPE, DESCRIPCION_COLUMN_ORDER)
			.addColumn(EMPRESA_COLUMN_NAME, EMPRESA_COLUMN_TYPE, EMPRESA_COLUMN_ORDER)
			.addColumn(FLAG_BAJA_COLUMN_NAME, FLAG_BAJA_COLUMN_TYPE, FLAG_BAJA_COLUMN_ORDER)
			;

}
