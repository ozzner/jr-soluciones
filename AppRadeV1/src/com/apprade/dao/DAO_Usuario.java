/**
 * 
 */
package com.apprade.dao;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.apprade.helper.Helper_JSONParser;

import android.util.Log;

/**
 * @author Renzo
 *
 */
public class DAO_Usuario {

	private static URI URL = URI.create("http://192.168.1.100/api/v1/");
	private static String ENTITY = "usuario";
	private Helper_JSONParser oJson;
	
	public DAO_Usuario() {
		oJson = new Helper_JSONParser();
	}

	
	public JSONObject registarUsuario(String email , String password)
	{	
		JSONObject json = null; 

		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("email", email));
		parametros.add( new BasicNameValuePair("password", password));

		try {
			json = oJson.httpPost(URL, parametros);
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		return json;
	}
	
	
	
	
}
