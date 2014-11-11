/**
 * 
 */
package com.apprade.dao;

/**
 * @author renzo
 */
public class DAO_Conexion {

	private static String URL = "http://apprade.com/api/v1/";
	/**
	 * @param url
	 */
	
	public DAO_Conexion(String url) {
		super();
		DAO_Conexion.URL = url;
	}
	
	public DAO_Conexion() {
		super();	
	}

	public String getUrl() {
		return URL;
	}
	
	public void setUrl(String url) {
		DAO_Conexion.URL = url;
	}
		
}
