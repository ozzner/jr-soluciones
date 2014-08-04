package com.apprade.entity;

import java.util.ArrayList;
import java.util.List;

public class Entity_Establecimiento {
	
	private String establecimientoID;
	private String nombre;
	private String direccion;	
	private String latitud;
	private int ruc;
	private String longitud;
	
	private List<Entity_Categoria>categoria = new ArrayList<Entity_Categoria>();
	private List<Entity_Distrito>distrito =  new ArrayList<Entity_Distrito>();
	private List<Entity_Coordenadas>coordenadas = new ArrayList<Entity_Coordenadas>();
	
	/*CONSTRUCTORES*/
	
	/**
	 * @param establecimientoID
	 * @param nombre
	 * @param direccion
	 * @param latitud
	 * @param ruc
	 * @param longitud
	 * @param categoria
	 * @param distrito
	 * @param coordenadas
	 */
	public Entity_Establecimiento(String establecimientoID, String nombre,
			String direccion, String latitud, int ruc, String longitud,
			List<Entity_Categoria> categoria, List<Entity_Distrito> distrito,
			List<Entity_Coordenadas> coordenadas) {
		super();
		this.establecimientoID = establecimientoID;
		this.nombre = nombre;
		this.direccion = direccion;
		this.latitud = latitud;
		this.ruc = ruc;
		this.longitud = longitud;
		this.categoria = categoria;
		this.distrito = distrito;
		this.coordenadas = coordenadas;
	}

	/**
	 * 
	 */
	public Entity_Establecimiento() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the establecimientoID
	 */
	public String getEstablecimientoID() {
		return establecimientoID;
	}

	/**
	 * @param establecimientoID the establecimientoID to set
	 */
	public void setEstablecimientoID(String establecimientoID) {
		this.establecimientoID = establecimientoID;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the latitud
	 */
	public String getLatitud() {
		return latitud;
	}

	/**
	 * @param latitud the latitud to set
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	/**
	 * @return the ruc
	 */
	public int getRuc() {
		return ruc;
	}

	/**
	 * @param ruc the ruc to set
	 */
	public void setRuc(int ruc) {
		this.ruc = ruc;
	}

	/**
	 * @return the longitud
	 */
	public String getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	/**
	 * @return the categoria
	 */
	public List<Entity_Categoria> getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(List<Entity_Categoria> categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the distrito
	 */
	public List<Entity_Distrito> getDistrito() {
		return distrito;
	}

	/**
	 * @param distrito the distrito to set
	 */
	public void setDistrito(List<Entity_Distrito> distrito) {
		this.distrito = distrito;
	}

	/**
	 * @return the coordenadas
	 */
	public List<Entity_Coordenadas> getCoordenadas() {
		return coordenadas;
	}

	/**
	 * @param coordenadas the coordenadas to set
	 */
	public void setCoordenadas(List<Entity_Coordenadas> coordenadas) {
		this.coordenadas = coordenadas;
	}

	
	
	
	
	
}
