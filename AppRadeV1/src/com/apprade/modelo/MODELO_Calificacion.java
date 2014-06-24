/**
 * 
 */
package com.apprade.modelo;

/**
 * @author Renzo
 *
 */
public class MODELO_Calificacion {

	private String cola;
	private String hora;
	private String fecha;
	private MODELO_Usuario oUsuario;
	private MODELO_Establecimiento oEstablecimiento;
	
	public MODELO_Calificacion() {
		oUsuario = new MODELO_Usuario();
		oEstablecimiento =  new MODELO_Establecimiento();
	}	

	public MODELO_Calificacion(String cola, String hora, String fecha,
			MODELO_Usuario oUsuario, MODELO_Establecimiento oEstablecimiento) {
		super();
		this.cola = cola;
		this.hora = hora;
		this.fecha = fecha;
		this.oUsuario = oUsuario;
		this.oEstablecimiento = oEstablecimiento;
	}
	
	public MODELO_Usuario getoUsuario() {
		return oUsuario;
	}


	public void setoUsuario(MODELO_Usuario oUsuario) {
		this.oUsuario = oUsuario;
	}


	public MODELO_Establecimiento getoEstablecimiento() {
		return oEstablecimiento;
	}


	public void setoEstablecimiento(MODELO_Establecimiento oEstablecimiento) {
		this.oEstablecimiento = oEstablecimiento;
	}


	public String getCola() {
		return cola;
	}

	public void setCola(String cola) {
		this.cola = cola;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	

}
