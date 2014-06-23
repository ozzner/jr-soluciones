/**
 * 
 */
package com.apprade.modelo;

/**
 * @author Renzo
 *
 */
public class MODELO_Categoria {

	private int categoriaID;
	private String nombre;
		
	public MODELO_Categoria() {
		// TODO Auto-generated constructor stub
	}

	
	public MODELO_Categoria(int categoriaID, String nombre) {
		super();
		this.categoriaID = categoriaID;
		this.nombre = nombre;
	}


	public int getCategoriaID() {
		return categoriaID;
	}

	public void setCategoriaID(int categoriaID) {
		this.categoriaID = categoriaID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
