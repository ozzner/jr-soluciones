/**
 * 
 */
package com.apprade.modelo;

/**
 * @author Renzo
 *
 */
public class MODELO_Ranking {

	private int rankingID;
	private String nombre;
	
	public MODELO_Ranking() {
		// TODO Auto-generated constructor stub
	}

	
	public MODELO_Ranking(int rankingID, String nombre) {
		super();
		this.rankingID = rankingID;
		this.nombre = nombre;
	}

	
	public int getRankingID() {
		return rankingID;
	}

	public void setRankingID(int rankingID) {
		this.rankingID = rankingID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
