package com.apprade.entity;

import java.util.ArrayList;
import java.util.List;

public class Entity_Establecimiento {
	
	private String establecimientoID;
	private String nombre;
	private String latitud;
	private String longitud;
	private List<Entity_Categoria> categoria = new ArrayList<Entity_Categoria>();
	private List<Entity_Distrito>distrito =  new ArrayList<Entity_Distrito>();
	private List<Entity_Coordenadas>coordenadas = new ArrayList<Entity_Coordenadas>();
	
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

	public List<Entity_Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<Entity_Categoria> categoria) {
		this.categoria = categoria;
	}

	public List<Entity_Distrito> getDistrito() {
		return distrito;
	}

	public void setDistrito(List<Entity_Distrito> distrito) {
		this.distrito = distrito;
	}

	public List<Entity_Coordenadas> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<Entity_Coordenadas> coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	
	

}
