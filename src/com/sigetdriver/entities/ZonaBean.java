package com.sigetdriver.entities;

import java.io.Serializable;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.dsbmobile.dsbframework.controller.persistence.TableHelper;
import com.sigetdriver.persistence.DatabaseConstants;
import com.sigetdriver.persistence.SigetDriverEntityFactory;

public class ZonaBean extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLES
	private String id = "";
	private String nombre = "";
	private String descripcion = "";
	private String poligono = "";
	private String estado = "";
	private String urbana = "";
	
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
	public String getPoligono() {
		return poligono;
	}
	public void setPoligono(String poligono) {
		this.poligono = poligono;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getUrbana() {
		return urbana;
	}
	public void setUrbana(String urbana) {
		this.urbana = urbana;
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
	public static final String POLIGONO_COLUMN_NAME = "POLIGONO";
	public static final String POLIGONO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(1000)";
	public static final int POLIGONO_COLUMN_ORDER = 4;
	// COLUMNA 5
	public static final String ESTADO_COLUMN_NAME = "ESTADO";
	public static final String ESTADO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ESTADO_COLUMN_ORDER = 5;
	// COLUMNA 6
	public static final String URBANA_COLUMN_NAME = "URBANA";
	public static final String URBANA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int URBANA_COLUMN_ORDER = 6;
	
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
		case POLIGONO_COLUMN_ORDER:
			return poligono;
		case ESTADO_COLUMN_ORDER:
			return estado;
		case URBANA_COLUMN_ORDER:
			return urbana;	
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
		case POLIGONO_COLUMN_ORDER:
			poligono = (String) object;
			break;
		case ESTADO_COLUMN_ORDER:
			estado = (String) object;
			break;
		case URBANA_COLUMN_ORDER:
			urbana = (String) object;
			break;	
		}
	}
	
	public static final TableHelper tableHelper = new TableHelper(
			DatabaseConstants.ZONA_TABLE_NAME, new SigetDriverEntityFactory())
			.addColumn(ID_COLUMN_NAME, ID_COLUMN_TYPE, ID_COLUMN_ORDER)
			.addColumn(NOMBRE_COLUMN_NAME, NOMBRE_COLUMN_TYPE, NOMBRE_COLUMN_ORDER)
			.addColumn(DESCRIPCION_COLUMN_NAME, DESCRIPCION_COLUMN_TYPE, DESCRIPCION_COLUMN_ORDER)
			.addColumn(POLIGONO_COLUMN_NAME, POLIGONO_COLUMN_TYPE, POLIGONO_COLUMN_ORDER)
			.addColumn(ESTADO_COLUMN_NAME, ESTADO_COLUMN_TYPE, ESTADO_COLUMN_ORDER)
			.addColumn(URBANA_COLUMN_NAME, URBANA_COLUMN_TYPE, URBANA_COLUMN_ORDER)
			;
	
}
