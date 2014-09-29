package com.sigetdriver.entities;

import java.io.Serializable;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.dsbmobile.dsbframework.controller.persistence.TableHelper;
import com.sigetdriver.persistence.DatabaseConstants;
import com.sigetdriver.persistence.SigetDriverEntityFactory;

public class PuntoBean extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLES
	private String idServicio = "";
	private String latitudPunto = "0.0";
	private String longitudPunto = "0.0";
	private String direccion = "";
	private String zona = "";
	private String tarifa = "0";
	private String orden = "";
	
	private String horaIr = "";
	private String horaLlegada = "";
	private String latitudLlegada = "0.0";
	private String longitudLlegada = "0.0";
	private String gastoTiempoEspera = "0";	
	private String gastoEstacionamiento = "0";
	private String gastoPeaje = "0";
	private String gastoOtros = "0";	
	
	private ZonaBean zonaObjeto = null;

	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}
	public String getLatitudPunto() {
		return latitudPunto;
	}
	public void setLatitudPunto(String latitudPunto) {
		this.latitudPunto = latitudPunto;
	}
	public String getLongitudPunto() {
		return longitudPunto;
	}
	public void setLongitudPunto(String longitudPunto) {
		this.longitudPunto = longitudPunto;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getHoraIr() {
		return horaIr;
	}
	public void setHoraIr(String horaIr) {
		this.horaIr = horaIr;
	}
	public String getHoraLlegada() {
		return horaLlegada;
	}
	public void setHoraLlegada(String horaLlegada) {
		this.horaLlegada = horaLlegada;
	}
	public String getLatitudLlegada() {
		return latitudLlegada;
	}
	public void setLatitudLlegada(String latitudLlegada) {
		this.latitudLlegada = latitudLlegada;
	}
	public String getLongitudLlegada() {
		return longitudLlegada;
	}
	public void setLongitudLlegada(String longitudLlegada) {
		this.longitudLlegada = longitudLlegada;
	}
	public String getGastoTiempoEspera() {
		return gastoTiempoEspera;
	}
	public void setGastoTiempoEspera(String gastoTiempoEspera) {
		this.gastoTiempoEspera = gastoTiempoEspera;
	}
	public String getGastoEstacionamiento() {
		return gastoEstacionamiento;
	}
	public void setGastoEstacionamiento(String gastoEstacionamiento) {
		this.gastoEstacionamiento = gastoEstacionamiento;
	}
	public String getGastoPeaje() {
		return gastoPeaje;
	}
	public void setGastoPeaje(String gastoPeaje) {
		this.gastoPeaje = gastoPeaje;
	}
	public String getGastoOtros() {
		return gastoOtros;
	}
	public void setGastoOtros(String gastoOtros) {
		this.gastoOtros = gastoOtros;
	}
	public ZonaBean getZonaObjeto() {
		return zonaObjeto;
	}
	public void setZonaObjeto(ZonaBean zonaObjeto) {
		this.zonaObjeto = zonaObjeto;
	}

	/**
	 * SQLITE
	 */
	
	// COLUMNA 1
	public static final String ID_SERVICIO_COLUMN_NAME = "ID_SERVICIO";
	public static final String ID_SERVICIO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ID_SERVICIO_COLUMN_ORDER = 1;
	// COLUMNA 2
	public static final String LATITUD_PUNTO_COLUMN_NAME = "LATITUD_PUNTO";
	public static final String LATITUD_PUNTO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int LATITUD_PUNTO_COLUMN_ORDER = 2;
	// COLUMNA 3
	public static final String LONGITUD_PUNTO_COLUMN_NAME = "LONGITUD_PUNTO";
	public static final String LONGITUD_PUNTO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int LONGITUD_PUNTO_COLUMN_ORDER = 3;
	// COLUMNA 4
	public static final String DIRECCION_COLUMN_NAME = "DIRECCION";
	public static final String DIRECCION_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int DIRECCION_COLUMN_ORDER = 4;
	// COLUMNA 5
	public static final String ZONA_COLUMN_NAME = "ZONA";
	public static final String ZONA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ZONA_COLUMN_ORDER = 5;
	// COLUMNA 6
	public static final String TARIFA_COLUMN_NAME = "TARIFA";
	public static final String TARIFA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int TARIFA_COLUMN_ORDER = 6;
	// COLUMNA 7
	public static final String ORDEN_COLUMN_NAME = "ORDEN";
	public static final String ORDEN_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ORDEN_COLUMN_ORDER = 7;
	// COLUMNA 8
	public static final String HORA_IR_COLUMN_NAME = "HORA_IR";
	public static final String HORA_IR_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int HORA_IR_COLUMN_ORDER = 8;	
	// COLUMNA 9
	public static final String HORA_LLEGADA_COLUMN_NAME = "HORA_LLEGADA";
	public static final String HORA_LLEGADA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int HORA_LLEGADA_COLUMN_ORDER = 9;	
	// COLUMNA 10
	public static final String LATITUD_LLEGADA_COLUMN_NAME = "LATITUD_LLEGADA";
	public static final String LATITUD_LLEGADA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int LATITUD_LLEGADA_COLUMN_ORDER = 10;
	// COLUMNA 11
	public static final String LONGITUD_LLEGADA_COLUMN_NAME = "LONGITUD_LLEGADA";
	public static final String LONGITUD_LLEGADA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int LONGITUD_LLEGADA_COLUMN_ORDER = 11;
	// COLUMNA 12
	public static final String GASTO_TIEMPO_ESPERA_COLUMN_NAME = "GASTO_TIEMPO_ESPERA";
	public static final String GASTO_TIEMPO_ESPERA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int GASTO_TIEMPO_ESPERA_COLUMN_ORDER = 12;
	// COLUMNA 13
	public static final String GASTO_ESTACIONAMIENTO_COLUMN_NAME = "GASTO_ESTACIONAMIENTO";
	public static final String GASTO_ESTACIONAMIENTO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int GASTO_ESTACIONAMIENTO_COLUMN_ORDER = 13;
	// COLUMNA 14
	public static final String GASTO_PEAJE_COLUMN_NAME = "GASTO_PEAJE";
	public static final String GASTO_PEAJE_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int GASTO_PEAJE_COLUMN_ORDER = 14;
	// COLUMNA 15
	public static final String GASTO_OTROS_COLUMN_NAME = "GASTO_OTROS";
	public static final String GASTO_OTROS_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int GASTO_OTROS_COLUMN_ORDER = 15;
	
	@Override
	public Object getColumnValue(int column) {
		// TODO Auto-generated method stub
		switch (column) {
		case ID_SERVICIO_COLUMN_ORDER:
			return idServicio;
		case LATITUD_PUNTO_COLUMN_ORDER:
			return latitudPunto;
		case LONGITUD_PUNTO_COLUMN_ORDER:
			return longitudPunto;
		case DIRECCION_COLUMN_ORDER:
			return direccion;
		case ZONA_COLUMN_ORDER:
			return zona;
		case TARIFA_COLUMN_ORDER:
			return tarifa;
		case ORDEN_COLUMN_ORDER:
			return orden;		
		case HORA_IR_COLUMN_ORDER:
			return horaIr;
		case HORA_LLEGADA_COLUMN_ORDER:
			return horaLlegada;
		case LATITUD_LLEGADA_COLUMN_ORDER:
			return latitudLlegada;
		case LONGITUD_LLEGADA_COLUMN_ORDER:
			return longitudLlegada;
		case GASTO_TIEMPO_ESPERA_COLUMN_ORDER:
			return gastoTiempoEspera;
		case GASTO_ESTACIONAMIENTO_COLUMN_ORDER:
			return gastoEstacionamiento;
		case GASTO_PEAJE_COLUMN_ORDER:
			return gastoPeaje;
		case GASTO_OTROS_COLUMN_ORDER:
			return gastoOtros;
		}
		return null;
	}
	
	@Override
	public void setColumnValue(int column, Object object) {
		switch (column) {
		case ID_SERVICIO_COLUMN_ORDER:
			idServicio = (String) object;
			break;
		case LATITUD_PUNTO_COLUMN_ORDER:
			latitudPunto = (String) object;
			break;
		case LONGITUD_PUNTO_COLUMN_ORDER:
			longitudPunto = (String) object;
			break;
		case DIRECCION_COLUMN_ORDER:
			direccion = (String) object;
			break;
		case ZONA_COLUMN_ORDER:
			zona = (String) object;
			break;
		case TARIFA_COLUMN_ORDER:
			tarifa = (String) object;
			break;
		case ORDEN_COLUMN_ORDER:
			orden = (String) object;
			break;
		case HORA_IR_COLUMN_ORDER:
			horaIr = (String) object;
			break;
		case HORA_LLEGADA_COLUMN_ORDER:
			horaLlegada = (String) object;
			break;
		case LATITUD_LLEGADA_COLUMN_ORDER:
			latitudLlegada = (String) object;
			break;
		case LONGITUD_LLEGADA_COLUMN_ORDER:
			longitudLlegada = (String) object;
			break;
		case GASTO_TIEMPO_ESPERA_COLUMN_ORDER:
			gastoTiempoEspera = (String) object;
			break;
		case GASTO_ESTACIONAMIENTO_COLUMN_ORDER:
			gastoEstacionamiento = (String) object;
			break;
		case GASTO_PEAJE_COLUMN_ORDER:
			gastoPeaje = (String) object;
			break;
		case GASTO_OTROS_COLUMN_ORDER:
			gastoOtros = (String) object;
			break;
		}
	}
	
	public static final TableHelper tableHelper = new TableHelper(
			DatabaseConstants.PUNTO_TABLE_NAME, new SigetDriverEntityFactory())
			.addColumn(ID_SERVICIO_COLUMN_NAME, ID_SERVICIO_COLUMN_TYPE, ID_SERVICIO_COLUMN_ORDER)
			.addColumn(LATITUD_PUNTO_COLUMN_NAME, LATITUD_PUNTO_COLUMN_TYPE, LATITUD_PUNTO_COLUMN_ORDER)
			.addColumn(LONGITUD_PUNTO_COLUMN_NAME, LONGITUD_PUNTO_COLUMN_TYPE, LONGITUD_PUNTO_COLUMN_ORDER)
			.addColumn(DIRECCION_COLUMN_NAME, DIRECCION_COLUMN_TYPE, DIRECCION_COLUMN_ORDER)
			.addColumn(ZONA_COLUMN_NAME, ZONA_COLUMN_TYPE, ZONA_COLUMN_ORDER)
			.addColumn(TARIFA_COLUMN_NAME, TARIFA_COLUMN_TYPE, TARIFA_COLUMN_ORDER)
			.addColumn(ORDEN_COLUMN_NAME, ORDEN_COLUMN_TYPE, ORDEN_COLUMN_ORDER)
			.addColumn(HORA_IR_COLUMN_NAME, HORA_IR_COLUMN_TYPE, HORA_IR_COLUMN_ORDER)
			.addColumn(HORA_LLEGADA_COLUMN_NAME, HORA_LLEGADA_COLUMN_TYPE, HORA_LLEGADA_COLUMN_ORDER)
			.addColumn(LATITUD_LLEGADA_COLUMN_NAME, LATITUD_LLEGADA_COLUMN_TYPE, LATITUD_LLEGADA_COLUMN_ORDER)
			.addColumn(LONGITUD_LLEGADA_COLUMN_NAME, LONGITUD_LLEGADA_COLUMN_TYPE, LONGITUD_LLEGADA_COLUMN_ORDER)
			.addColumn(GASTO_TIEMPO_ESPERA_COLUMN_NAME, GASTO_TIEMPO_ESPERA_COLUMN_TYPE, GASTO_TIEMPO_ESPERA_COLUMN_ORDER)
			.addColumn(GASTO_ESTACIONAMIENTO_COLUMN_NAME, GASTO_ESTACIONAMIENTO_COLUMN_TYPE, GASTO_ESTACIONAMIENTO_COLUMN_ORDER)
			.addColumn(GASTO_PEAJE_COLUMN_NAME, GASTO_PEAJE_COLUMN_TYPE, GASTO_PEAJE_COLUMN_ORDER)
			.addColumn(GASTO_OTROS_COLUMN_NAME, GASTO_OTROS_COLUMN_TYPE, GASTO_OTROS_COLUMN_ORDER)
			;

}
