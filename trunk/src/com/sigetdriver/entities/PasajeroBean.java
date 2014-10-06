package com.sigetdriver.entities;

import java.io.Serializable;

import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.dsbmobile.dsbframework.controller.persistence.TableHelper;
import com.sigetdriver.persistence.DatabaseConstants;
import com.sigetdriver.persistence.SigetDriverEntityFactory;

public class PasajeroBean extends Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLES
	private String codigo = "";
	private String cargo = "";
	private String flagPertenece = "";
	private String empresa = "";
	private String nombres = "";
	private String apellidos = "";
	private String correo = "";
	private String tipoDocumento = "";
	private String numDocumento = "";
	private String fechaNacimiento = "";
	private String flagBaja = "";
	private String todosTelefonos = "";
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getFlagPertenece() {
		return flagPertenece;
	}
	public void setFlagPertenece(String flagPertenece) {
		this.flagPertenece = flagPertenece;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumDocumento() {
		return numDocumento;
	}
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getFlagBaja() {
		return flagBaja;
	}
	public void setFlagBaja(String flagBaja) {
		this.flagBaja = flagBaja;
	}
	public String getTodosTelefonos() {
		return todosTelefonos;
	}
	public void setTodosTelefonos(String todosTelefonos) {
		this.todosTelefonos = todosTelefonos;
	}	
	
	/**
	 * SQLITE
	 */
	
	// COLUMNA 1
	public static final String CODIGO_COLUMN_NAME = "CODIGO";
	public static final String CODIGO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int CODIGO_COLUMN_ORDER = 1;
	// COLUMNA 2
	public static final String CARGO_COLUMN_NAME = "CARGO";
	public static final String CARGO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int CARGO_COLUMN_ORDER = 2;
	// COLUMNA 3
	public static final String FLAG_PERTENECE_COLUMN_NAME = "FLAG_PERTENECE";
	public static final String FLAG_PERTENECE_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int FLAG_PERTENECE_COLUMN_ORDER = 3;
	// COLUMNA 4
	public static final String EMPRESA_COLUMN_NAME = "EMPRESA";
	public static final String EMPRESA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int EMPRESA_COLUMN_ORDER = 4;
	// COLUMNA 5
	public static final String NOMBRES_COLUMN_NAME = "NOMBRES";
	public static final String NOMBRES_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int NOMBRES_COLUMN_ORDER = 5;
	// COLUMNA 6
	public static final String APELLIDOS_COLUMN_NAME = "APELLIDOS";
	public static final String APELLIDOS_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int APELLIDOS_COLUMN_ORDER = 6;
	// COLUMNA 7
	public static final String CORREO_COLUMN_NAME = "CORREO";
	public static final String CORREO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int CORREO_COLUMN_ORDER = 7;
	// COLUMNA 8
	public static final String TIPO_DOCUMENTO_COLUMN_NAME = "TIPO_DOCUMENTO";
	public static final String TIPO_DOCUMENTO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int TIPO_DOCUMENTO_COLUMN_ORDER = 8;	
	// COLUMNA 9
	public static final String NUM_DOCUMENTO_COLUMN_NAME = "NUM_DOCUMENTO";
	public static final String NUM_DOCUMENTO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int NUM_DOCUMENTO_COLUMN_ORDER = 9;	
	// COLUMNA 10
	public static final String FECHA_NACIMIENTO_COLUMN_NAME = "FECHA_NACIMIENTO";
	public static final String FECHA_NACIMIENTO_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int FECHA_NACIMIENTO_COLUMN_ORDER = 10;
	// COLUMNA 11
	public static final String FLAG_BAJA_COLUMN_NAME = "FLAG_BAJA";
	public static final String FLAG_BAJA_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int FLAG_BAJA_COLUMN_ORDER = 11;
	// COLUMNA 12
	public static final String TELEFONOS_COLUMN_NAME = "TELEFONOS";
	public static final String TELEFONOS_COLUMN_TYPE = TableHelper.VARCHAR_DATATYPE_NAME + "(200)";
	public static final int TELEFONOS_COLUMN_ORDER = 12;
	
	@Override
	public Object getColumnValue(int column) {
		// TODO Auto-generated method stub
		switch (column) {
		case CODIGO_COLUMN_ORDER:
			return codigo;
		case CARGO_COLUMN_ORDER:
			return cargo;
		case FLAG_PERTENECE_COLUMN_ORDER:
			return flagPertenece;
		case EMPRESA_COLUMN_ORDER:
			return empresa;
		case NOMBRES_COLUMN_ORDER:
			return nombres;
		case APELLIDOS_COLUMN_ORDER:
			return apellidos;
		case CORREO_COLUMN_ORDER:
			return correo;		
		case TIPO_DOCUMENTO_COLUMN_ORDER:
			return tipoDocumento;
		case NUM_DOCUMENTO_COLUMN_ORDER:
			return numDocumento;
		case FECHA_NACIMIENTO_COLUMN_ORDER:
			return fechaNacimiento;
		case FLAG_BAJA_COLUMN_ORDER:
			return flagBaja;
		case TELEFONOS_COLUMN_ORDER:
			return todosTelefonos;		
		}
		return null;
	}
	
	@Override
	public void setColumnValue(int column, Object object) {
		switch (column) {
		case CODIGO_COLUMN_ORDER:
			codigo = (String) object;
			break;
		case CARGO_COLUMN_ORDER:
			cargo = (String) object;
			break;
		case FLAG_PERTENECE_COLUMN_ORDER:
			flagPertenece = (String) object;
			break;
		case EMPRESA_COLUMN_ORDER:
			empresa = (String) object;
			break;
		case NOMBRES_COLUMN_ORDER:
			nombres = (String) object;
			break;
		case APELLIDOS_COLUMN_ORDER:
			apellidos = (String) object;
			break;
		case CORREO_COLUMN_ORDER:
			correo = (String) object;
			break;
		case TIPO_DOCUMENTO_COLUMN_ORDER:
			tipoDocumento = (String) object;
			break;
		case NUM_DOCUMENTO_COLUMN_ORDER:
			numDocumento = (String) object;
			break;
		case FECHA_NACIMIENTO_COLUMN_ORDER:
			fechaNacimiento = (String) object;
			break;
		case FLAG_BAJA_COLUMN_ORDER:
			flagBaja = (String) object;
			break;
		case TELEFONOS_COLUMN_ORDER:
			todosTelefonos = (String) object;
			break;		
		}
	}
	
	public static final TableHelper tableHelper = new TableHelper(
			DatabaseConstants.PASAJERO_TABLE_NAME, new SigetDriverEntityFactory())
			.addColumn(CODIGO_COLUMN_NAME, CODIGO_COLUMN_TYPE, CODIGO_COLUMN_ORDER)
			.addColumn(CARGO_COLUMN_NAME, CARGO_COLUMN_TYPE, CARGO_COLUMN_ORDER)
			.addColumn(FLAG_PERTENECE_COLUMN_NAME, FLAG_PERTENECE_COLUMN_TYPE, FLAG_PERTENECE_COLUMN_ORDER)
			.addColumn(EMPRESA_COLUMN_NAME, EMPRESA_COLUMN_TYPE, EMPRESA_COLUMN_ORDER)
			.addColumn(NOMBRES_COLUMN_NAME, NOMBRES_COLUMN_TYPE, NOMBRES_COLUMN_ORDER)
			.addColumn(APELLIDOS_COLUMN_NAME, APELLIDOS_COLUMN_TYPE, APELLIDOS_COLUMN_ORDER)
			.addColumn(FLAG_BAJA_COLUMN_NAME, FLAG_BAJA_COLUMN_TYPE, CORREO_COLUMN_ORDER)
			.addColumn(TIPO_DOCUMENTO_COLUMN_NAME, TIPO_DOCUMENTO_COLUMN_TYPE, TIPO_DOCUMENTO_COLUMN_ORDER)
			.addColumn(NUM_DOCUMENTO_COLUMN_NAME, NUM_DOCUMENTO_COLUMN_TYPE, NUM_DOCUMENTO_COLUMN_ORDER)
			.addColumn(FECHA_NACIMIENTO_COLUMN_NAME, FECHA_NACIMIENTO_COLUMN_TYPE, FECHA_NACIMIENTO_COLUMN_ORDER)
			.addColumn(FLAG_BAJA_COLUMN_NAME, FLAG_BAJA_COLUMN_TYPE, FLAG_BAJA_COLUMN_ORDER)
			.addColumn(TELEFONOS_COLUMN_NAME, TELEFONOS_COLUMN_TYPE, TELEFONOS_COLUMN_ORDER)
			;

}
