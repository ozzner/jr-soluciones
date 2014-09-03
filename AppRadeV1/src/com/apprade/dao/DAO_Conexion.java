/**
 * 
 */
package com.apprade.dao;

/**
 * @author renzo
 */
public class DAO_Conexion {

	private String url = "http://apprade.com/api/v1/";

	/**
	 * @param url
	 */
	
	public DAO_Conexion(String url) {
		super();
		this.url = url;
	}
	
	public DAO_Conexion() {
		super();	
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
		
}
