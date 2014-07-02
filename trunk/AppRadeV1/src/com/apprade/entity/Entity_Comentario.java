/**
 * 
 */
package com.apprade.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Renzo
 *
 */
public class Entity_Comentario {

	private int comentarioID;
	private String fecha;
	private String mensaje;
	private List<Entity_Usuario> usuario = new ArrayList<Entity_Usuario>(); 
	private List<Entity_Establecimiento> establecimiento = new ArrayList<Entity_Establecimiento>(); 
	
	
	public Entity_Comentario() {
	
	}


	public Entity_Comentario(int comentarioID, String fecha, String mensaje,
			Entity_Usuario oUsuario, Entity_Establecimiento oEstablecimiento) {
		super();
		this.comentarioID = comentarioID;
		this.fecha = fecha;
		this.mensaje = mensaje;
	}


	public int getComentarioID() {
		return comentarioID;
	}

	public void setComentarioID(int comentarioID) {
		this.comentarioID = comentarioID;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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
