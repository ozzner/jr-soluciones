package com.sigetdriver.entities;

import java.io.Serializable;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.dsbmobile.dsbframework.controller.persistence.TableHelper;
import com.sigetdriver.persistence.DatabaseConstants;
import com.sigetdriver.persistence.SigetDriverEntityFactory;

public class SedeBean extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLES
	private String id = "";
	private String nombre = "";
	private String latitud = "0.0";
	private String longitud = "0.0";
	private String empresa = "";
	private String telefono = "";
	private String radioRelevancia = "";
	private String principal = "";
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
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getRadioRelevancia() {
		return radioRelevancia;
	}
	public void setRadioRelevancia(String radioRelevancia) {
		this.radioRelevancia = radioRelevancia;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
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
	public static final String LATITUD_COLUMN_NAME = "LATITUD";
	public static final String LATITUD_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int LATITUD_COLUMN_ORDER = 3;
	// COLUMNA 4
	public static final String LONGITUD_COLUMN_NAME = "LONGITUD";
	public static final String LONGITUD_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int LONGITUD_COLUMN_ORDER = 4;
	// COLUMNA 5
	public static final String EMPRESA_COLUMN_NAME = "EMPRESA";
	public static final String EMPRESA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int EMPRESA_COLUMN_ORDER = 5;
	// COLUMNA 6
	public static final String TELEFONO_COLUMN_NAME = "TELEFONO";
	public static final String TELEFONO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int TELEFONO_COLUMN_ORDER = 6;
	// COLUMNA 7
	public static final String RADIO_RELEVANCIA_COLUMN_NAME = "RADIO_RELEVANCIA";
	public static final String RADIO_RELEVANCIA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int RADIO_RELEVANCIA_COLUMN_ORDER = 7;
	// COLUMNA 8
	public static final String PRINCIPAL_COLUMN_NAME = "PRINCIPAL";
	public static final String PRINCIPAL_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int PRINCIPAL_COLUMN_ORDER = 8;	
	// COLUMNA 9
	public static final String FLAG_BAJA_COLUMN_NAME = "FLAG_BAJA";
	public static final String FLAG_BAJA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int FLAG_BAJA_COLUMN_ORDER = 9;
	
	@Override
	public Object getColumnValue(int column) {
		// TODO Auto-generated method stub
		switch (column) {
		case ID_COLUMN_ORDER:
			return id;
		case NOMBRE_COLUMN_ORDER:
			return nombre;
		case LATITUD_COLUMN_ORDER:
			return latitud;
		case LONGITUD_COLUMN_ORDER:
			return longitud;
		case EMPRESA_COLUMN_ORDER:
			return empresa;
		case TELEFONO_COLUMN_ORDER:
			return telefono;
		case RADIO_RELEVANCIA_COLUMN_ORDER:
			return radioRelevancia;		
		case PRINCIPAL_COLUMN_ORDER:
			return principal;
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
		case LATITUD_COLUMN_ORDER:
			latitud = (String) object;
			break;
		case LONGITUD_COLUMN_ORDER:
			longitud = (String) object;
			break;
		case EMPRESA_COLUMN_ORDER:
			empresa = (String) object;
			break;
		case TELEFONO_COLUMN_ORDER:
			telefono = (String) object;
			break;
		case RADIO_RELEVANCIA_COLUMN_ORDER:
			radioRelevancia = (String) object;
			break;
		case PRINCIPAL_COLUMN_ORDER:
			principal = (String) object;
			break;
		case FLAG_BAJA_COLUMN_ORDER:
			flagBaja = (String) object;
			break;		
		}
	}
	
	public static final TableHelper tableHelper = new TableHelper(
			DatabaseConstants.SEDE_TABLE_NAME, new SigetDriverEntityFactory())
			.addColumn(ID_COLUMN_NAME, ID_COLUMN_TYPE, ID_COLUMN_ORDER)
			.addColumn(NOMBRE_COLUMN_NAME, NOMBRE_COLUMN_TYPE, NOMBRE_COLUMN_ORDER)
			.addColumn(LATITUD_COLUMN_NAME, LATITUD_COLUMN_TYPE, LATITUD_COLUMN_ORDER)
			.addColumn(LONGITUD_COLUMN_NAME, LONGITUD_COLUMN_TYPE, LONGITUD_COLUMN_ORDER)
			.addColumn(EMPRESA_COLUMN_NAME, EMPRESA_COLUMN_TYPE, EMPRESA_COLUMN_ORDER)
			.addColumn(TELEFONO_COLUMN_NAME, TELEFONO_COLUMN_TYPE, TELEFONO_COLUMN_ORDER)
			.addColumn(RADIO_RELEVANCIA_COLUMN_NAME, RADIO_RELEVANCIA_COLUMN_TYPE, RADIO_RELEVANCIA_COLUMN_ORDER)
			.addColumn(PRINCIPAL_COLUMN_NAME, PRINCIPAL_COLUMN_TYPE, PRINCIPAL_COLUMN_ORDER)
			.addColumn(FLAG_BAJA_COLUMN_NAME, FLAG_BAJA_COLUMN_TYPE, FLAG_BAJA_COLUMN_ORDER)
			;

}
