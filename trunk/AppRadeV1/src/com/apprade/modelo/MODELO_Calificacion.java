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
	private Object oEstablecimiento;
	
	public MODELO_Calificacion() {
		oUsuario = new MODELO_Usuario();
		oEstablecimiento =  new MODELO_Calificacion();
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
