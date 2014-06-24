/**
 * 
 */
package com.apprade.entity;

/**
 * @author Renzo
 *
 */
public class Entity_Calificacion {

	private String cola;
	private String hora;
	private String fecha;
	private Entity_Usuario oUsuario;
	private Entity_Establecimiento oEstablecimiento;
	
	public Entity_Calificacion() {
		oUsuario = new Entity_Usuario();
		oEstablecimiento =  new Entity_Establecimiento();
	}	

	public Entity_Calificacion(String cola, String hora, String fecha,
			Entity_Usuario oUsuario, Entity_Establecimiento oEstablecimiento) {
		super();
		this.cola = cola;
		this.hora = hora;
		this.fecha = fecha;
		this.oUsuario = oUsuario;
		this.oEstablecimiento = oEstablecimiento;
	}
	
	public Entity_Usuario getoUsuario() {
		return oUsuario;
	}


	public void setoUsuario(Entity_Usuario oUsuario) {
		this.oUsuario = oUsuario;
	}


	public Entity_Establecimiento getoEstablecimiento() {
		return oEstablecimiento;
	}


	public void setoEstablecimiento(Entity_Establecimiento oEstablecimiento) {
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
