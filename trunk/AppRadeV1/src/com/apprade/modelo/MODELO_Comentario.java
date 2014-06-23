/**
 * 
 */
package com.apprade.modelo;

/**
 * @author Renzo
 *
 */
public class MODELO_Comentario {

	private int comentarioID;
	private String fecha;
	private String mensaje;
	private MODELO_Usuario oUsuario; 
	private MODELO_Establecimiento oEstablecimiento; 
	
	
	public MODELO_Comentario() {
		oUsuario =  new MODELO_Usuario();
		oEstablecimiento =  new MODELO_Establecimiento();		
	}


	public MODELO_Comentario(int comentarioID, String fecha, String mensaje,
			MODELO_Usuario oUsuario, MODELO_Establecimiento oEstablecimiento) {
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
	
	
	
	
	

}
