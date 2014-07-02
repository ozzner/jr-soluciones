/**
 * 
 */
package com.apprade.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Renzo
 *
 */
public class Entity_Calificacion {

	private String cola;
	private String hora;
	private String fecha;
	
	private List<Entity_Usuario> usuario = new ArrayList<Entity_Usuario>();
	private List<Entity_Establecimiento> establecimiento = new ArrayList<Entity_Establecimiento>() ;
	
	public Entity_Calificacion() {

	}	

	public Entity_Calificacion(String cola, String hora, String fecha,
			Entity_Usuario oUsuario, Entity_Establecimiento oEstablecimiento) {
		super();
		this.cola = cola;
		this.hora = hora;
		this.fecha = fecha;
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

	public List<Entity_Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Entity_Usuario> usuario) {
		this.usuario = usuario;
	}

	public List<Entity_Establecimiento> getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(List<Entity_Establecimiento> establecimiento) {
		this.establecimiento = establecimiento;
	}

	

}
