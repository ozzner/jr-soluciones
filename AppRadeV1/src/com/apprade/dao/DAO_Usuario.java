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

import com.apprade.entity.Entity_Ranking;
import com.apprade.entity.Entity_Usuario;
import com.apprade.helper.Helper_JSONParser;
import com.apprade.helper.Helper_JSONStatus;

import android.util.Log;

/**
 * @author Renzo
 *
 */
public class DAO_Usuario {

	private static URI URL = URI.create("http://192.168.1.100/api/v1/");
	private static String ENTITY = "usuario";
	private Helper_JSONParser oJson;
	private Entity_Usuario oUsuario ;
	private Helper_JSONStatus oJsonStatus;
	
	public DAO_Usuario() {
		oJson = new Helper_JSONParser();
		oUsuario = new Entity_Usuario();
		oJsonStatus =  new Helper_JSONStatus();
	}
	
	public JSONObject loginUsuario(String email , String password)
	{	
		JSONObject json = null; 
		
		
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("email", email));
		parametros.add( new BasicNameValuePair("password", password));

		try {
			json = oJson.httpGet(URL, parametros);			
			boolean bEStatus = Boolean.parseBoolean(json.getString("error_status"));
			JSONObject oJsonData =  json.getJSONObject("data").getJSONObject("user1");
			
			if(!bEStatus){
												
				oUsuario.setUsuarioID(Integer.parseInt(oJsonData.getString("userID")));
				oUsuario.setEmail(oJsonData.getString("email"));
				oUsuario.setSexo(oJsonData.getString("sex").charAt(0));
				oUsuario.setNombre(oJsonData.getString("name"));
				oUsuario.setFechaNacimiento(oJsonData.getString("date_birth"));
				oUsuario.setFechaRegistro(oJsonData.getString("date_at"));
				oUsuario.setApPaterno(oJsonData.getString("last_name1"));
				oUsuario.setApMaterno(oJsonData.getString("last_name2"));
				oUsuario.setRate(Integer.parseInt(oJsonData.getString("rate")));
				oUsuario.setUid(oJsonData.getString("Api_key"));
				
				JSONObject oJsonRanking = json.getJSONObject("ranking");
				oUsuario.setIdRanking(Integer.parseInt(oJsonRanking.getString("rankingID")));
				oUsuario.setApMaterno(oJsonRanking.getString("name"));
				
				oJsonStatus.setHttpCode(Integer.parseInt(oJsonData.getString("httpCode")));
			}else{
				oJsonStatus.setError_cod(Double.parseDouble(oJsonData.getString("error_cod")));
				oJsonStatus.setMessage(oJsonData.getString("message"));
				oJsonStatus.setInfo(oJsonData.getString("info"));
			}
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		return json;
	}
	

	
		 		
		
	
	
	
	
	
}
