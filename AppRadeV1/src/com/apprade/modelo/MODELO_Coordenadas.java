/**
 * 
 */
package com.apprade.modelo;

/**
 * @author Renzo
 *
 */
public class MODELO_Coordenadas {

	private int coordenadasID;
	private float latitud;
	private float longitud;
	private String referencia;
	
	public MODELO_Coordenadas() {
		// TODO Auto-generated constructor stub
	}

	public MODELO_Coordenadas(int coordenadasID, float latitud, float longitud,
			String referencia) {
		super();
		this.coordenadasID = coordenadasID;
		this.latitud = latitud;
		this.longitud = longitud;
		this.referencia = referencia;
	}

	public int getCoordenadasID() {
		return coordenadasID;
	}

	public void setCoordenadasID(int coordenadasID) {
		this.coordenadasID = coordenadasID;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	

}