package com.sigetdriver.entities;

import java.io.Serializable;
import java.util.ArrayList;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.dsbmobile.dsbframework.controller.persistence.TableHelper;
import com.sigetdriver.persistence.DatabaseConstants;
import com.sigetdriver.persistence.SigetDriverEntityFactory;

public class ServicioBean extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// ESTADO SERVICIO
	public final static String ESTADO_PENDIENTE = "0";
	public final static String ESTADO_ACTIVO = "1";
	public final static String ESTADO_TERMINADO = "2";
	public final static String ESTADO_CANCELADO = "3";
	
	// ESTADO ENVIADO
	public final static String ENVIADO_SI = "1";
	public final static String ENVIADO_NO = "0";
	
	// ESTADO ACEPTADO
	public final static String ACEPTADO_SI = "1";
	public final static String ACEPTADO_NO = "0";
	
	// TIPO DE PAGO
	public final static String TIPO_PAGO_CREDITO = "1";
	public final static String TIPO_PAGO_CONTADO = "2";
	
	// TIPO SERVICIO
	public final static String TIPO_SERVICIO_PTO_A_PTO = "1";
	public final static String TIPO_SERVICIO_COURIER = "2";
	public final static String TIPO_SERVICIO_HORA_URBANA = "3";
	public final static String TIPO_SERVICIO_HORA_INTERURBANA = "4";
	
	// PUNTO FINAL CONFIRMADO
	public final static int CONFIRMADO_SI = 1;
	public final static int CONFIRMADO_NO = 0;
	
	// VARIABLES
	private String idServicio = "";
	private String fecha = "";
	private String hora = "";
	private String empresa = "";
	private String codEmpresa = "";
	private String pasajero = "";
	private String celular = "";
	private String observaciones = "";
	private String estado = "";
	private String enviado = "";
	private String aceptado = "";
	private String tipoPago = "";
	private String tipoServicio = "";
	private String codigo = "";
	private String centroCosto = "";
	private String puntoActual = "1";
	private int puntoFinalConfirmado = 0;
	private ArrayList<PuntoBean> puntos = new ArrayList<PuntoBean>();
	
	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getPasajero() {
		return pasajero;
	}
	public void setPasajero(String pasajero) {
		this.pasajero = pasajero;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getEnviado() {
		return enviado;
	}
	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}	
	public String getAceptado() {
		return aceptado;
	}
	public void setAceptado(String aceptado) {
		this.aceptado = aceptado;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public String getTipoServicio() {
		return tipoServicio;
	}
	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public ArrayList<PuntoBean> getPuntos() {
		return puntos;
	}
	public void setPuntos(ArrayList<PuntoBean> puntos) {
		this.puntos = puntos;
	}
	public String getCentroCosto() {
		return centroCosto;
	}
	public void setCentroCosto(String centroCosto) {
		this.centroCosto = centroCosto;
	}
	public int getPuntoFinalConfirmado() {
		return puntoFinalConfirmado;
	}
	public void setPuntoFinalConfirmado(int puntoFinalConfirmado) {
		this.puntoFinalConfirmado = puntoFinalConfirmado;
	}
	public String getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	public String getPuntoActual() {
		return puntoActual;
	}
	public void setPuntoActual(String puntoActual) {
		this.puntoActual = puntoActual;
	}

	/**
	 * SQLITE
	 */
	
	// COLUMNA 1
	public static final String ID_SERVICIO_COLUMN_NAME = "ID_SERVICIO";
	public static final String ID_SERVICIO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ID_SERVICIO_COLUMN_ORDER = 1;
	// COLUMNA 2
	public static final String FECHA_COLUMN_NAME = "FECHA";
	public static final String FECHA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int FECHA_COLUMN_ORDER = 2;
	// COLUMNA 3
	public static final String HORA_COLUMN_NAME = "HORA";
	public static final String HORA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int HORA_COLUMN_ORDER = 3;
	// COLUMNA 4
	public static final String EMPRESA_COLUMN_NAME = "EMPRESA";
	public static final String EMPRESA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int EMPRESA_COLUMN_ORDER = 4;
	// COLUMNA 5
	public static final String PASAJERO_COLUMN_NAME = "PASAJERO";
	public static final String PASAJERO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int PASAJERO_COLUMN_ORDER = 5;
	// COLUMNA 6
	public static final String CELULAR_COLUMN_NAME = "CELULAR";
	public static final String CELULAR_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int CELULAR_COLUMN_ORDER = 6;
	// COLUMNA 7
	public static final String OBSERVACIONES_COLUMN_NAME = "OBSERVACIONES";
	public static final String OBSERVACIONES_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int OBSERVACIONES_COLUMN_ORDER = 7;
	// COLUMNA 8
	public static final String ESTADO_COLUMN_NAME = "ESTADO";
	public static final String ESTADO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ESTADO_COLUMN_ORDER = 8;
	// COLUMNA 9
	public static final String ENVIADO_COLUMN_NAME = "ENVIADO";
	public static final String ENVIADO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ENVIADO_COLUMN_ORDER = 9;
	// COLUMNA 10
	public static final String ACEPTADO_COLUMN_NAME = "ACEPTADO";
	public static final String ACEPTADO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int ACEPTADO_COLUMN_ORDER = 10;
	// COLUMNA 11
	public static final String TIPO_PAGO_COLUMN_NAME = "TIPO_PAGO";
	public static final String TIPO_PAGO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int TIPO_PAGO_COLUMN_ORDER = 11;
	// COLUMNA 12
	public static final String TIPO_SERVICIO_COLUMN_NAME = "TIPO_SERVICIO";
	public static final String TIPO_SERVICIO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int TIPO_SERVICIO_COLUMN_ORDER = 12;
	// COLUMNA 13
	public static final String CODIGO_COLUMN_NAME = "CODIGO";
	public static final String CODIGO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int CODIGO_COLUMN_ORDER = 13;
	// COLUMNA 14
	public static final String CENTRO_COSTO_COLUMN_NAME = "CENTRO_COSTO";
	public static final String CENTRO_COSTO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int CENTRO_COSTO_COLUMN_ORDER = 14;
	// COLUMNA 15
	public static final String PUNTO_ACTUAL_COLUMN_NAME = "PUNTO_ACTUAL";
	public static final String PUNTO_ACTUAL_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int PUNTO_ACTUAL_COLUMN_ORDER = 15;
	
	@Override
	public Object getColumnValue(int column) {
		// TODO Auto-generated method stub
		switch (column) {
		case ID_SERVICIO_COLUMN_ORDER:
			return idServicio;
		case FECHA_COLUMN_ORDER:
			return fecha;
		case HORA_COLUMN_ORDER:
			return hora;
		case EMPRESA_COLUMN_ORDER:
			return empresa;
		case PASAJERO_COLUMN_ORDER:
			return pasajero;
		case CELULAR_COLUMN_ORDER:
			return celular;
		case OBSERVACIONES_COLUMN_ORDER:
			return observaciones;
		case ESTADO_COLUMN_ORDER:
			return estado;
		case ENVIADO_COLUMN_ORDER:
			return enviado;
		case ACEPTADO_COLUMN_ORDER:
			return aceptado;
		case TIPO_PAGO_COLUMN_ORDER:
			return tipoPago;
		case TIPO_SERVICIO_COLUMN_ORDER:
			return tipoServicio;
		case CODIGO_COLUMN_ORDER:
			return codigo;
		case CENTRO_COSTO_COLUMN_ORDER:
			return centroCosto;
		case PUNTO_ACTUAL_COLUMN_ORDER:
			return puntoActual;
		}
		return null;
	}
	
	@Override
	public void setColumnValue(int column, Object object) {
		switch (column) {
		case ID_SERVICIO_COLUMN_ORDER:
			idServicio = (String) object;
			break;
		case FECHA_COLUMN_ORDER:
			fecha = (String) object;
			break;
		case HORA_COLUMN_ORDER:
			hora = (String) object;
			break;
		case EMPRESA_COLUMN_ORDER:
			empresa = (String) object;
			break;
		case PASAJERO_COLUMN_ORDER:
			pasajero = (String) object;
			break;
		case CELULAR_COLUMN_ORDER:
			celular = (String) object;
			break;
		case OBSERVACIONES_COLUMN_ORDER:
			observaciones = (String) object;
			break;
		case ESTADO_COLUMN_ORDER:
			estado = (String) object;
			break;
		case ENVIADO_COLUMN_ORDER:
			enviado = (String) object;
			break;
		case ACEPTADO_COLUMN_ORDER:
			aceptado = (String) object;
			break;
		case TIPO_PAGO_COLUMN_ORDER:
			tipoPago = (String) object;
			break;
		case TIPO_SERVICIO_COLUMN_ORDER:
			tipoServicio = (String) object;
			break;
		case CODIGO_COLUMN_ORDER:
			codigo = (String) object;
			break;
		case CENTRO_COSTO_COLUMN_ORDER:
			centroCosto = (String) object;
			break;
		case PUNTO_ACTUAL_COLUMN_ORDER:
			puntoActual = (String) object;
			break;
		}
	}
	
	public static final TableHelper tableHelper = new TableHelper(
			DatabaseConstants.SERVICIO_TABLE_NAME, new SigetDriverEntityFactory())
			.addColumn(ID_SERVICIO_COLUMN_NAME, ID_SERVICIO_COLUMN_TYPE, ID_SERVICIO_COLUMN_ORDER)
			.addColumn(FECHA_COLUMN_NAME, FECHA_COLUMN_TYPE, FECHA_COLUMN_ORDER)
			.addColumn(HORA_COLUMN_NAME, HORA_COLUMN_TYPE, HORA_COLUMN_ORDER)
			.addColumn(EMPRESA_COLUMN_NAME, EMPRESA_COLUMN_TYPE, EMPRESA_COLUMN_ORDER)
			.addColumn(PASAJERO_COLUMN_NAME, PASAJERO_COLUMN_TYPE, PASAJERO_COLUMN_ORDER)
			.addColumn(CELULAR_COLUMN_NAME, CELULAR_COLUMN_TYPE, CELULAR_COLUMN_ORDER)
			.addColumn(OBSERVACIONES_COLUMN_NAME, OBSERVACIONES_COLUMN_TYPE, OBSERVACIONES_COLUMN_ORDER)
			.addColumn(ESTADO_COLUMN_NAME, ESTADO_COLUMN_TYPE, ESTADO_COLUMN_ORDER)
			.addColumn(ENVIADO_COLUMN_NAME, ENVIADO_COLUMN_TYPE, ENVIADO_COLUMN_ORDER)
			.addColumn(ACEPTADO_COLUMN_NAME, ACEPTADO_COLUMN_TYPE, ACEPTADO_COLUMN_ORDER)
			.addColumn(TIPO_PAGO_COLUMN_NAME, TIPO_PAGO_COLUMN_TYPE, TIPO_PAGO_COLUMN_ORDER)
			.addColumn(TIPO_SERVICIO_COLUMN_NAME, TIPO_SERVICIO_COLUMN_TYPE, TIPO_SERVICIO_COLUMN_ORDER)
			.addColumn(CODIGO_COLUMN_NAME, CODIGO_COLUMN_TYPE, CODIGO_COLUMN_ORDER)
			.addColumn(CENTRO_COSTO_COLUMN_NAME, CENTRO_COSTO_COLUMN_TYPE, CENTRO_COSTO_COLUMN_ORDER)
			.addColumn(PUNTO_ACTUAL_COLUMN_NAME, PUNTO_ACTUAL_COLUMN_TYPE, PUNTO_ACTUAL_COLUMN_ORDER)
			;

}
