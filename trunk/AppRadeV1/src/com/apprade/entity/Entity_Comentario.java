/**
 * 
 */
package com.apprade.entity;

/**
 * @author Renzo
 *
 */
public class Entity_Comentario {

	private int comentarioID;
	private String fecha;
	private String mensaje;
	private Entity_Usuario oUsuario; 
	private Entity_Establecimiento oEstablecimiento; 
	
	
	public Entity_Comentario() {
		oUsuario =  new Entity_Usuario();
		oEstablecimiento =  new Entity_Establecimiento();		
	}


	public Entity_Comentario(int comentarioID, String fecha, String mensaje,
			Entity_Usuario oUsuario, Entity_Establecimiento oEstablecimiento) {
		super();
		this.comentarioID = comentarioID;
		this.fecha = fecha;
		this.mensaje = mensaje;
		this.oUsuario = oUsuario;
		this.oEstablecimiento = oEstablecimiento;
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
	
	
	
	
	

}
