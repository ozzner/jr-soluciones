package com.apprade.dao;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import com.apprade.entity.Entity_Ranking;
import com.apprade.entity.Entity_Usuario;

import com.apprade.helper.Helper_Http_Method;

import com.apprade.helper.Helper_JSONStatus;

import android.util.Log;

/**
 * @author Renzo Santillan
 *
 */
public class DAO_Usuario{

	private static URI URL = URI.create("http://192.168.1.100/api/v1/");
	private static String ENTITY = "usuario";
	public Entity_Usuario oUsuario ;
	public  Helper_JSONStatus oJsonStatus;
	private Helper_Http_Method oHttp;
	private Entity_Ranking oRank;
	
	
	public DAO_Usuario() {
		oUsuario = new Entity_Usuario();
		oJsonStatus =  new Helper_JSONStatus();
		oHttp = new Helper_Http_Method();
			}
	
	public boolean loginUsuario(String email , String password)
	{	InputStream in = null;
		JSONObject oJson = null; 
		boolean bEstado = false;
				
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("email", email));
		parametros.add( new BasicNameValuePair("password", password));

		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		try {						
			    in =  oHttp.httpGet(URL + "?" + paramsString);
			    oJson =oHttp.parserToJsonObject(in);
			    
				boolean bEStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				
				if(!bEStatus){
					JSONObject oUserData =  oJson.getJSONObject("data").getJSONObject("user1");
					
					oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
					
					oUsuario.setUsuarioID(Integer.parseInt(oUserData.getString("userID")));
					oUsuario.setEmail(oUserData.getString("email"));
					oUsuario.setSexo(oUserData.getString("sex").charAt(0));
					oUsuario.setNombre(oUserData.getString("name"));
					oUsuario.setFechaNacimiento(oUserData.getString("date_birth"));
					oUsuario.setFechaRegistro(oUserData.getString("date_at"));
					oUsuario.setApPaterno(oUserData.getString("last_name1"));
					oUsuario.setApMaterno(oUserData.getString("last_name2"));
					oUsuario.setRate(Integer.parseInt(oUserData.getString("rate")));
					oUsuario.setUid(oUserData.getString("Api_key"));
					
											
					bEstado = true;
					
				}else{
					oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
					
					JSONObject oErrorData=  oJson.getJSONObject("data");
					oJsonStatus.setError_cod(Double.parseDouble(oErrorData.getString("error_cod")));
					oJsonStatus.setMessage(oErrorData.getString("message"));
					oJsonStatus.setInfo(oErrorData.getString("info"));
				}
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		
		return bEstado;
	}

	public boolean registarUsuario(String nombres, String apellidos,String correo,String pass){
		InputStream in = null;
		JSONObject oJson = null; 
		boolean bEstado = false;
		
	List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("email", correo));
		parametros.add( new BasicNameValuePair("sexo", pass));
		parametros.add( new BasicNameValuePair("nombre", pass));
		parametros.add( new BasicNameValuePair("fecha", pass));
		parametros.add( new BasicNameValuePair("apellido1", pass));
		parametros.add( new BasicNameValuePair("password", pass));
		
		return false;
		
	
	
	
	
	
	
	}

	
		 		
		
	
	
	
	
	
}
