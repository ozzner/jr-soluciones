/**
 * 
 */
package com.apprade.modelo;

/**
 * @author Renzo
 *
 */
public class MODELO_Distrito {

	private int distritoID;
	private String nombre;
	private String zipcode;
	
	public MODELO_Distrito() {
		// TODO Auto-generated constructor stub
	}

	public MODELO_Distrito(int distritoID, String nombre, String zipcode) {
		super();
		this.distritoID = distritoID;
		this.nombre = nombre;
		this.zipcode = zipcode;
	}

	public int getDistritoID() {
		return distritoID;
	}

	public void setDistritoID(int distritoID) {
		this.distritoID = distritoID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	
	
	
}
