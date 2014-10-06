package com.sigetdriver.entities;

import java.io.Serializable;
import java.util.ArrayList;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.dsbmobile.dsbframework.controller.persistence.TableHelper;
import com.sigetdriver.persistence.DatabaseConstants;
import com.sigetdriver.persistence.SigetDriverEntityFactory;

public class EmpresaBean extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLES
	private String codEmpresa = "";
	private String ruc = "";
	private String razonSocial = "";
	private String tipoPago = "";
	private String observacion = "";
	private String notaReferencia = "";	
	private String flagBaja = "";
	private String montoPuntoAPunto = "0.0";
	private String montoCourier = "0.0";
	private String montoHoraUrbana = "0.0";
	private String montoHoraInterurbana = "0.0";	
	private String montoDiaUrbano = "0.0";
	private String montoDiaInterurbano = "0.0";
	private String montoCancelacionUrbana = "0.0";
	private String montoCancelacionInterurbana = "0.0";
	private String montoCancelacionAeropuerto = "0.0";
	private String minTiempoEspera1 = "0.0";
	private String minTiempoEspera2 = "0.0";
	private String minTiempoEspera3 = "0.0";
	private String minTiempoEsperaRecurrente = "0.0";
	private String montoTiempoEspera1 = "0.0";
	private String montoTiempoEspera2 = "0.0";
	private String montoTiempoEspera3 = "0.0";
	private String montoTiempoEsperaRecurrente = "0.0";
	private String sobrecargaDesvio = "0.0";
	
	private ArrayList<SedeBean> listaSedes = new ArrayList<SedeBean>();
	
	public String getCodEmpresa() {
		return codEmpresa;
	}
	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getNotaReferencia() {
		return notaReferencia;
	}
	public void setNotaReferencia(String notaReferencia) {
		this.notaReferencia = notaReferencia;
	}
	public String getFlagBaja() {
		return flagBaja;
	}
	public void setFlagBaja(String flagBaja) {
		this.flagBaja = flagBaja;
	}
	public String getMontoPuntoAPunto() {
		return montoPuntoAPunto;
	}
	public void setMontoPuntoAPunto(String montoPuntoAPunto) {
		this.montoPuntoAPunto = montoPuntoAPunto;
	}
	public String getMontoCourier() {
		return montoCourier;
	}
	public void setMontoCourier(String montoCourier) {
		this.montoCourier = montoCourier;
	}
	public String getMontoHoraUrbana() {
		return montoHoraUrbana;
	}
	public void setMontoHoraUrbana(String montoHoraUrbana) {
		this.montoHoraUrbana = montoHoraUrbana;
	}
	public String getMontoHoraInterurbana() {
		return montoHoraInterurbana;
	}
	public void setMontoHoraInterurbana(String montoHoraInterurbana) {
		this.montoHoraInterurbana = montoHoraInterurbana;
	}
	public String getMontoDiaUrbano() {
		return montoDiaUrbano;
	}
	public void setMontoDiaUrbano(String montoDiaUrbano) {
		this.montoDiaUrbano = montoDiaUrbano;
	}
	public String getMontoDiaInterurbano() {
		return montoDiaInterurbano;
	}
	public void setMontoDiaInterurbano(String montoDiaInterurbano) {
		this.montoDiaInterurbano = montoDiaInterurbano;
	}
	public String getMontoCancelacionUrbana() {
		return montoCancelacionUrbana;
	}
	public void setMontoCancelacionUrbana(String montoCancelacionUrbana) {
		this.montoCancelacionUrbana = montoCancelacionUrbana;
	}
	public String getMontoCancelacionInterurbana() {
		return montoCancelacionInterurbana;
	}
	public void setMontoCancelacionInterurbana(String montoCancelacionInterurbana) {
		this.montoCancelacionInterurbana = montoCancelacionInterurbana;
	}
	public String getMontoCancelacionAeropuerto() {
		return montoCancelacionAeropuerto;
	}
	public void setMontoCancelacionAeropuerto(String montoCancelacionAeropuerto) {
		this.montoCancelacionAeropuerto = montoCancelacionAeropuerto;
	}
	public String getMinTiempoEspera1() {
		return minTiempoEspera1;
	}
	public void setMinTiempoEspera1(String minTiempoEspera1) {
		this.minTiempoEspera1 = minTiempoEspera1;
	}
	public String getMinTiempoEspera2() {
		return minTiempoEspera2;
	}
	public void setMinTiempoEspera2(String minTiempoEspera2) {
		this.minTiempoEspera2 = minTiempoEspera2;
	}
	public String getMinTiempoEspera3() {
		return minTiempoEspera3;
	}
	public void setMinTiempoEspera3(String minTiempoEspera3) {
		this.minTiempoEspera3 = minTiempoEspera3;
	}
	public String getMinTiempoEsperaRecurrente() {
		return minTiempoEsperaRecurrente;
	}
	public void setMinTiempoEsperaRecurrente(String minTiempoEsperaRecurrente) {
		this.minTiempoEsperaRecurrente = minTiempoEsperaRecurrente;
	}
	public String getMontoTiempoEspera1() {
		return montoTiempoEspera1;
	}
	public void setMontoTiempoEspera1(String montoTiempoEspera1) {
		this.montoTiempoEspera1 = montoTiempoEspera1;
	}
	public String getMontoTiempoEspera2() {
		return montoTiempoEspera2;
	}
	public void setMontoTiempoEspera2(String montoTiempoEspera2) {
		this.montoTiempoEspera2 = montoTiempoEspera2;
	}
	public String getMontoTiempoEspera3() {
		return montoTiempoEspera3;
	}
	public void setMontoTiempoEspera3(String montoTiempoEspera3) {
		this.montoTiempoEspera3 = montoTiempoEspera3;
	}
	public String getMontoTiempoEsperaRecurrente() {
		return montoTiempoEsperaRecurrente;
	}
	public void setMontoTiempoEsperaRecurrente(String montoTiempoEsperaRecurrente) {
		this.montoTiempoEsperaRecurrente = montoTiempoEsperaRecurrente;
	}
	public String getSobrecargaDesvio() {
		return sobrecargaDesvio;
	}
	public void setSobrecargaDesvio(String sobrecargaDesvio) {
		this.sobrecargaDesvio = sobrecargaDesvio;
	}
	public ArrayList<SedeBean> getListaSedes() {
		return listaSedes;
	}
	public void setListaSedes(ArrayList<SedeBean> listaSedes) {
		this.listaSedes = listaSedes;
	}

	/**
	 * SQLITE
	 */
	
	// COLUMNA 1
	public static final String COD_EMPRESA_COLUMN_NAME = "COD_EMPRESA";
	public static final String COD_EMPRESA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int COD_EMPRESA_COLUMN_ORDER = 1;
	// COLUMNA 2
	public static final String RUC_COLUMN_NAME = "RUC";
	public static final String RUC_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int RUC_COLUMN_ORDER = 2;
	// COLUMNA 3
	public static final String RAZON_SOCIAL_COLUMN_NAME = "RAZON_SOCIAL";
	public static final String RAZON_SOCIAL_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int RAZON_SOCIAL_COLUMN_ORDER = 3;
	// COLUMNA 4
	public static final String TIPO_PAGO_COLUMN_NAME = "TIPO_PAGO";
	public static final String TIPO_PAGO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int TIPO_PAGO_COLUMN_ORDER = 4;
	// COLUMNA 5
	public static final String OBSERVACION_COLUMN_NAME = "OBSERVACION";
	public static final String OBSERVACION_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int OBSERVACION_COLUMN_ORDER = 5;
	// COLUMNA 6
	public static final String NOTA_REFERENCIA_COLUMN_NAME = "NOTA_REFERENCIA";
	public static final String NOTA_REFERENCIA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int NOTA_REFERENCIA_COLUMN_ORDER = 6;
	// COLUMNA 7
	public static final String FLAG_BAJA_COLUMN_NAME = "FLAG_BAJA";
	public static final String FLAG_BAJA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int FLAG_BAJA_COLUMN_ORDER = 7;
	// COLUMNA 8
	public static final String MONTO_PUNTO_A_PUNTO_COLUMN_NAME = "MONTO_PUNTO_A_PUNTO";
	public static final String MONTO_PUNTO_A_PUNTO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_PUNTO_A_PUNTO_COLUMN_ORDER = 8;	
	// COLUMNA 9
	public static final String MONTO_COURIER_COLUMN_NAME = "MONTO_COURIER";
	public static final String MONTO_COURIER_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_COURIER_COLUMN_ORDER = 9;	
	// COLUMNA 10
	public static final String MONTO_HORA_URBANA_COLUMN_NAME = "MONTO_HORA_URBANA";
	public static final String MONTO_HORA_URBANA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_HORA_URBANA_COLUMN_ORDER = 10;
	// COLUMNA 11
	public static final String MONTO_HORA_INTERURBANA_COLUMN_NAME = "MONTO_HORA_INTERURBANA";
	public static final String MONTO_HORA_INTERURBANA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_HORA_INTERURBANA_COLUMN_ORDER = 11;
	// COLUMNA 12
	public static final String MONTO_DIA_URBANO_COLUMN_NAME = "MONTO_DIA_URBANO";
	public static final String MONTO_DIA_URBANO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_DIA_URBANO_COLUMN_ORDER = 12;
	// COLUMNA 13
	public static final String MONTO_DIA_INTERURBANO_COLUMN_NAME = "MONTO_DIA_INTERURBANO";
	public static final String MONTO_DIA_INTERURBANO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_DIA_INTERURBANO_COLUMN_ORDER = 13;
	// COLUMNA 14
	public static final String MONTO_CANCELACION_URBANA_COLUMN_NAME = "MONTO_CANCELACION_URBANA";
	public static final String MONTO_CANCELACION_URBANA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_CANCELACION_URBANA_COLUMN_ORDER = 14;
	// COLUMNA 15
	public static final String MONTO_CANCELACION_INTERURBANA_COLUMN_NAME = "MONTO_CANCELACION_INTERURBANA";
	public static final String MONTO_CANCELACION_INTERURBANA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_CANCELACION_INTERURBANA_COLUMN_ORDER = 15;
	// COLUMNA 16
	public static final String MONTO_CANCELACION_AEROPUERTO_COLUMN_NAME = "MONTO_CANCELACION_AEROPUERTO";
	public static final String MONTO_CANCELACION_AEROPUERTO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_CANCELACION_AEROPUERTO_COLUMN_ORDER = 16;
	// COLUMNA 17
	public static final String MIN_TIEMPO_ESPERA1_COLUMN_NAME = "MIN_TIEMPO_ESPERA1";
	public static final String MIN_TIEMPO_ESPERA1_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MIN_TIEMPO_ESPERA1_COLUMN_ORDER = 17;
	// COLUMNA 18
	public static final String MIN_TIEMPO_ESPERA2_COLUMN_NAME = "MIN_TIEMPO_ESPERA2";
	public static final String MIN_TIEMPO_ESPERA2_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MIN_TIEMPO_ESPERA2_COLUMN_ORDER = 18;
	// COLUMNA 19
	public static final String MIN_TIEMPO_ESPERA3_COLUMN_NAME = "MIN_TIEMPO_ESPERA3";
	public static final String MIN_TIEMPO_ESPERA3_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MIN_TIEMPO_ESPERA3_COLUMN_ORDER = 19;
	// COLUMNA 20
	public static final String MIN_TIEMPO_ESPERA_RECURRENTE_COLUMN_NAME = "MIN_TIEMPO_ESPERA_RECURRENTE";
	public static final String MIN_TIEMPO_ESPERA_RECURRENTE_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MIN_TIEMPO_ESPERA_RECURRENTE_COLUMN_ORDER = 20;
	// COLUMNA 21
	public static final String MONTO_TIEMPO_ESPERA1_COLUMN_NAME = "MONTO_TIEMPO_ESPERA1";
	public static final String MONTO_TIEMPO_ESPERA1_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_TIEMPO_ESPERA1_COLUMN_ORDER = 21;
	// COLUMNA 22
	public static final String MONTO_TIEMPO_ESPERA2_COLUMN_NAME = "MONTO_TIEMPO_ESPERA2";
	public static final String MONTO_TIEMPO_ESPERA2_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_TIEMPO_ESPERA2_COLUMN_ORDER = 22;
	// COLUMNA 23
	public static final String MONTO_TIEMPO_ESPERA3_COLUMN_NAME = "MONTO_TIEMPO_ESPERA3";
	public static final String MONTO_TIEMPO_ESPERA3_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_TIEMPO_ESPERA3_COLUMN_ORDER = 23;	
	// COLUMNA 24
	public static final String MONTO_TIEMPO_ESPERA_RECURRENTE_COLUMN_NAME = "MONTO_TIEMPO_ESPERA_RECURRENTE";
	public static final String MONTO_TIEMPO_ESPERA_RECURRENTE_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int MONTO_TIEMPO_ESPERA_RECURRENTE_COLUMN_ORDER = 24;	
	// COLUMNA 25
	public static final String SOBRECARGA_DESVIO_COLUMN_NAME = "SOBRECARGA_DESVIO";
	public static final String SOBRECARGA_DESVIO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int SOBRECARGA_DESVIO_COLUMN_ORDER = 25;
	
	@Override
	public Object getColumnValue(int column) {
		// TODO Auto-generated method stub
		switch (column) {
		case COD_EMPRESA_COLUMN_ORDER:
			return codEmpresa;
		case RUC_COLUMN_ORDER:
			return ruc;
		case RAZON_SOCIAL_COLUMN_ORDER:
			return razonSocial;
		case TIPO_PAGO_COLUMN_ORDER:
			return tipoPago;
		case OBSERVACION_COLUMN_ORDER:
			return observacion;
		case NOTA_REFERENCIA_COLUMN_ORDER:
			return notaReferencia;
		case FLAG_BAJA_COLUMN_ORDER:
			return flagBaja;		
		case MONTO_PUNTO_A_PUNTO_COLUMN_ORDER:
			return montoPuntoAPunto;
		case MONTO_COURIER_COLUMN_ORDER:
			return montoCourier;
		case MONTO_HORA_URBANA_COLUMN_ORDER:
			return montoHoraUrbana;
		case MONTO_HORA_INTERURBANA_COLUMN_ORDER:
			return montoHoraInterurbana;
		case MONTO_DIA_URBANO_COLUMN_ORDER:
			return montoDiaUrbano;
		case MONTO_DIA_INTERURBANO_COLUMN_ORDER:
			return montoDiaInterurbano;
		case MONTO_CANCELACION_URBANA_COLUMN_ORDER:
			return montoCancelacionUrbana;
		case MONTO_CANCELACION_INTERURBANA_COLUMN_ORDER:
			return montoCancelacionInterurbana;
		case MONTO_CANCELACION_AEROPUERTO_COLUMN_ORDER:
			return montoCancelacionAeropuerto;
		case MIN_TIEMPO_ESPERA1_COLUMN_ORDER:
			return minTiempoEspera1;
		case MIN_TIEMPO_ESPERA2_COLUMN_ORDER:
			return minTiempoEspera2;
		case MIN_TIEMPO_ESPERA3_COLUMN_ORDER:
			return minTiempoEspera3;
		case MIN_TIEMPO_ESPERA_RECURRENTE_COLUMN_ORDER:
			return minTiempoEsperaRecurrente;
		case MONTO_TIEMPO_ESPERA1_COLUMN_ORDER:
			return montoTiempoEspera1;
		case MONTO_TIEMPO_ESPERA2_COLUMN_ORDER:
			return montoTiempoEspera2;
		case MONTO_TIEMPO_ESPERA3_COLUMN_ORDER:
			return montoTiempoEspera3;
		case MONTO_TIEMPO_ESPERA_RECURRENTE_COLUMN_ORDER:
			return montoTiempoEsperaRecurrente;
		case SOBRECARGA_DESVIO_COLUMN_ORDER:
			return sobrecargaDesvio;
		}
		return null;
	}
	
	@Override
	public void setColumnValue(int column, Object object) {
		switch (column) {
		case COD_EMPRESA_COLUMN_ORDER:
			codEmpresa = (String) object;
			break;
		case RUC_COLUMN_ORDER:
			ruc = (String) object;
			break;
		case RAZON_SOCIAL_COLUMN_ORDER:
			razonSocial = (String) object;
			break;
		case TIPO_PAGO_COLUMN_ORDER:
			tipoPago = (String) object;
			break;
		case OBSERVACION_COLUMN_ORDER:
			observacion = (String) object;
			break;
		case NOTA_REFERENCIA_COLUMN_ORDER:
			notaReferencia = (String) object;
			break;
		case FLAG_BAJA_COLUMN_ORDER:
			flagBaja = (String) object;
			break;
		case MONTO_PUNTO_A_PUNTO_COLUMN_ORDER:
			montoPuntoAPunto = (String) object;
			break;
		case MONTO_COURIER_COLUMN_ORDER:
			montoCourier = (String) object;
			break;
		case MONTO_HORA_URBANA_COLUMN_ORDER:
			montoHoraUrbana = (String) object;
			break;
		case MONTO_HORA_INTERURBANA_COLUMN_ORDER:
			montoHoraInterurbana = (String) object;
			break;
		case MONTO_DIA_URBANO_COLUMN_ORDER:
			montoDiaUrbano = (String) object;
			break;
		case MONTO_DIA_INTERURBANO_COLUMN_ORDER:
			montoDiaInterurbano = (String) object;
			break;
		case MONTO_CANCELACION_URBANA_COLUMN_ORDER:
			montoCancelacionUrbana = (String) object;
			break;
		case MONTO_CANCELACION_INTERURBANA_COLUMN_ORDER:
			montoCancelacionInterurbana = (String) object;
			break;
		case MONTO_CANCELACION_AEROPUERTO_COLUMN_ORDER:
			montoCancelacionAeropuerto = (String) object;
			break;
		case MIN_TIEMPO_ESPERA1_COLUMN_ORDER:
			minTiempoEspera1 = (String) object;
			break;
		case MIN_TIEMPO_ESPERA2_COLUMN_ORDER:
			minTiempoEspera2 = (String) object;
			break;
		case MIN_TIEMPO_ESPERA3_COLUMN_ORDER:
			minTiempoEspera3 = (String) object;
			break;
		case MIN_TIEMPO_ESPERA_RECURRENTE_COLUMN_ORDER:
			minTiempoEsperaRecurrente = (String) object;
			break;
		case MONTO_TIEMPO_ESPERA1_COLUMN_ORDER:
			montoTiempoEspera1 = (String) object;
			break;
		case MONTO_TIEMPO_ESPERA2_COLUMN_ORDER:
			montoTiempoEspera2 = (String) object;
			break;
		case MONTO_TIEMPO_ESPERA3_COLUMN_ORDER:
			montoTiempoEspera3 = (String) object;
			break;
		case MONTO_TIEMPO_ESPERA_RECURRENTE_COLUMN_ORDER:
			montoTiempoEsperaRecurrente = (String) object;
			break;
		case SOBRECARGA_DESVIO_COLUMN_ORDER:
			sobrecargaDesvio = (String) object;
			break;
		}
	}
	
	public static final TableHelper tableHelper = new TableHelper(
			DatabaseConstants.EMPRESA_TABLE_NAME, new SigetDriverEntityFactory())
			.addColumn(COD_EMPRESA_COLUMN_NAME, COD_EMPRESA_COLUMN_TYPE, COD_EMPRESA_COLUMN_ORDER)
			.addColumn(RUC_COLUMN_NAME, RUC_COLUMN_TYPE, RUC_COLUMN_ORDER)
			.addColumn(RAZON_SOCIAL_COLUMN_NAME, RAZON_SOCIAL_COLUMN_TYPE, RAZON_SOCIAL_COLUMN_ORDER)
			.addColumn(TIPO_PAGO_COLUMN_NAME, TIPO_PAGO_COLUMN_TYPE, TIPO_PAGO_COLUMN_ORDER)
			.addColumn(OBSERVACION_COLUMN_NAME, OBSERVACION_COLUMN_TYPE, OBSERVACION_COLUMN_ORDER)
			.addColumn(NOTA_REFERENCIA_COLUMN_NAME, NOTA_REFERENCIA_COLUMN_TYPE, NOTA_REFERENCIA_COLUMN_ORDER)
			.addColumn(FLAG_BAJA_COLUMN_NAME, FLAG_BAJA_COLUMN_TYPE, FLAG_BAJA_COLUMN_ORDER)
			.addColumn(MONTO_PUNTO_A_PUNTO_COLUMN_NAME, MONTO_PUNTO_A_PUNTO_COLUMN_TYPE, MONTO_PUNTO_A_PUNTO_COLUMN_ORDER)
			.addColumn(MONTO_COURIER_COLUMN_NAME, MONTO_COURIER_COLUMN_TYPE, MONTO_COURIER_COLUMN_ORDER)
			.addColumn(MONTO_HORA_URBANA_COLUMN_NAME, MONTO_HORA_URBANA_COLUMN_TYPE, MONTO_HORA_URBANA_COLUMN_ORDER)
			.addColumn(MONTO_HORA_INTERURBANA_COLUMN_NAME, MONTO_HORA_INTERURBANA_COLUMN_TYPE, MONTO_HORA_INTERURBANA_COLUMN_ORDER)
			.addColumn(MONTO_DIA_URBANO_COLUMN_NAME, MONTO_DIA_URBANO_COLUMN_TYPE, MONTO_DIA_URBANO_COLUMN_ORDER)
			.addColumn(MONTO_DIA_INTERURBANO_COLUMN_NAME, MONTO_DIA_INTERURBANO_COLUMN_TYPE, MONTO_DIA_INTERURBANO_COLUMN_ORDER)
			.addColumn(MONTO_CANCELACION_URBANA_COLUMN_NAME, MONTO_CANCELACION_URBANA_COLUMN_TYPE, MONTO_CANCELACION_URBANA_COLUMN_ORDER)
			.addColumn(MONTO_CANCELACION_INTERURBANA_COLUMN_NAME, MONTO_CANCELACION_INTERURBANA_COLUMN_TYPE, MONTO_CANCELACION_INTERURBANA_COLUMN_ORDER)
			.addColumn(MONTO_CANCELACION_AEROPUERTO_COLUMN_NAME, MONTO_CANCELACION_AEROPUERTO_COLUMN_TYPE, MONTO_CANCELACION_AEROPUERTO_COLUMN_ORDER)
			.addColumn(MIN_TIEMPO_ESPERA1_COLUMN_NAME, MIN_TIEMPO_ESPERA1_COLUMN_TYPE, MIN_TIEMPO_ESPERA1_COLUMN_ORDER)
			.addColumn(MIN_TIEMPO_ESPERA2_COLUMN_NAME, MIN_TIEMPO_ESPERA2_COLUMN_TYPE, MIN_TIEMPO_ESPERA2_COLUMN_ORDER)
			.addColumn(MIN_TIEMPO_ESPERA3_COLUMN_NAME, MIN_TIEMPO_ESPERA3_COLUMN_TYPE, MIN_TIEMPO_ESPERA3_COLUMN_ORDER)
			.addColumn(MIN_TIEMPO_ESPERA_RECURRENTE_COLUMN_NAME, MIN_TIEMPO_ESPERA_RECURRENTE_COLUMN_TYPE, MIN_TIEMPO_ESPERA_RECURRENTE_COLUMN_ORDER)
			.addColumn(MONTO_TIEMPO_ESPERA1_COLUMN_NAME, MONTO_TIEMPO_ESPERA1_COLUMN_TYPE, MONTO_TIEMPO_ESPERA1_COLUMN_ORDER)
			.addColumn(MONTO_TIEMPO_ESPERA2_COLUMN_NAME, MONTO_TIEMPO_ESPERA2_COLUMN_TYPE, MONTO_TIEMPO_ESPERA2_COLUMN_ORDER)
			.addColumn(MONTO_TIEMPO_ESPERA3_COLUMN_NAME, MONTO_TIEMPO_ESPERA3_COLUMN_TYPE, MONTO_TIEMPO_ESPERA3_COLUMN_ORDER)
			.addColumn(MONTO_TIEMPO_ESPERA_RECURRENTE_COLUMN_NAME, MONTO_TIEMPO_ESPERA_RECURRENTE_COLUMN_TYPE, MONTO_TIEMPO_ESPERA_RECURRENTE_COLUMN_ORDER)
			.addColumn(SOBRECARGA_DESVIO_COLUMN_NAME, SOBRECARGA_DESVIO_COLUMN_TYPE, SOBRECARGA_DESVIO_COLUMN_ORDER)
			;
	
}
