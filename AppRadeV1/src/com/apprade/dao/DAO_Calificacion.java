		package com.apprade.dao;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

import com.apprade.entity.Entity_Calificacion;
import com.apprade.helper.Helper_Http_Method;
import com.apprade.helper.Helper_JSONStatus;

/**
 * @author Renzo
 *
 */
public class DAO_Calificacion {
	
	private DAO_Conexion conn;
	private static URI URL;
	private static String ENTITY = "calificacion";
	public Entity_Calificacion oCali ;
	public  Helper_JSONStatus oJsonStatus;
	private Helper_Http_Method oHttp;
		

	public DAO_Calificacion() {
		oCali = new Entity_Calificacion();
		oJsonStatus =  new Helper_JSONStatus();
		oHttp = new Helper_Http_Method();
		conn = new DAO_Conexion();
	}
	
	
	public boolean registrarCalificacion(String usuario, String establecimiento,String cola){
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null; 
		boolean bEstado = false;
		
	List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("usuario", usuario));
		parametros.add( new BasicNameValuePair("establecimiento", establecimiento));
		parametros.add( new BasicNameValuePair("cola", cola));
		
		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		try {						
//			    in =  oHttp.httpPost(URL + "?" + paramsString);
//			    oJson =oHttp.parserToJsonObject(in);
//			    
				boolean bEStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				
				if(!bEStatus){
					JSONObject oUserData =  oJson.getJSONObject("data");
					oJsonStatus.setMessage(oUserData.getString("message"));						
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

	public boolean listarCalificacion(String usuarioID)
	{	
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null; 
		boolean bEstado = false;
				
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("usuarioID", usuarioID));

		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		try {						
			    in =  oHttp.httpGet(URL + "?" + paramsString);
			   // oJson =oHttp.parserToJsonObject(in);
			    
				boolean bEStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				
				if(!bEStatus){
					JSONObject oUserData =  oJson.getJSONObject("data").getJSONObject("rating1");
					
					oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
//					
//					oUsuario.setUsuarioID(Integer.parseInt(oUserData.getString("userID")));
//					oUsuario.setEmail(oUserData.getString("email"));
//					oUsuario.setSexo(oUserData.getString("sex").charAt(0));
//					oUsuario.setNombre(oUserData.getString("name"));
//					oUsuario.setFechaNacimiento(oUserData.getString("date_birth"));
//					oUsuario.setFechaRegistro(oUserData.getString("date_at"));
//					oUsuario.setApPaterno(oUserData.getString("last_name1"));
//					oUsuario.setApMaterno(oUserData.getString("last_name2"));
//					oUsuario.setRate(Integer.parseInt(oUserData.getString("rate")));
//					oUsuario.setUid(oUserData.getString("Api_key"));
					
											
					bEstado = true;
					
				}else{
					oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
					
					JSONObject oErrorData=  oJson.getJSONObject("data");
					oJsonStatus.setMessage(oErrorData.getString("message"));
					oJsonStatus.setInfo(oErrorData.getString("info"));
				}
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		
		return bEstado;
	}

		 		

	
	
	
	
	
	
	
	
}
