package com.apprade.entity;

public class Entity_Establecimiento {
	
	private String establecimientoID;
	private String nombre;
	private String latitud;
	private String longitud;
	
	
	/*CONSTRUCTORES*/
	public Entity_Establecimiento() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public Entity_Establecimiento(String establecimientoID, String nombre,
			String latitud, String longitud) {
		super();
		this.establecimientoID = establecimientoID;
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
	}


	/* SET - GET*/
	public String getEstablecimientoID() {
		return establecimientoID;
	}
	public void setEstablecimientoID(String establecimientoID) {
		this.establecimientoID = establecimientoID;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	

}
