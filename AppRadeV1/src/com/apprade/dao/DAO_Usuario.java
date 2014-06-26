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
 *
 */
public class DAO_Usuario {

	private static URI URL = URI.create("http://192.168.1.100/api/v1/");
	private static String ENTITY = "usuario";
	private Entity_Usuario oUsuario ;
	private Helper_JSONStatus oJsonStatus;
	private Helper_Http_Method oHttp;
	
	
	public DAO_Usuario() {
		oUsuario = new Entity_Usuario();
		oJsonStatus =  new Helper_JSONStatus();
		oHttp = new Helper_Http_Method();
			}
	
	public boolean loginUsuario(String email , String password)
	{	
		JSONObject oJson = null; 
		boolean bEstado = false;
				
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("email", email));
		parametros.add( new BasicNameValuePair("password", password));

		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		Log.e("PARAMETROS",paramsString+"");
		try {						
			    InputStream in =  oHttp.httpGet(URL + "?" + paramsString);
			    oJson =oHttp.parserToJsonObject(in);
			    Log.e("OBJ",oJson+"");
				boolean bEStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				JSONObject oJsonData =  oJson.getJSONObject("data").getJSONObject("user1");
				Log.e("DATA",oJsonData+"");
				if(!bEStatus){
													
					oUsuario.setUsuarioID(Integer.parseInt(oJsonData.getString("userID")));
					oUsuario.setEmail(oJsonData.getString("email"));
					Log.e("EMAIL",oUsuario.getEmail());
					oUsuario.setSexo(oJsonData.getString("sex").charAt(0));
					oUsuario.setNombre(oJsonData.getString("name"));
					oUsuario.setFechaNacimiento(oJsonData.getString("date_birth"));
					oUsuario.setFechaRegistro(oJsonData.getString("date_at"));
					oUsuario.setApPaterno(oJsonData.getString("last_name1"));
					oUsuario.setApMaterno(oJsonData.getString("last_name2"));
					oUsuario.setRate(Integer.parseInt(oJsonData.getString("rate")));
					oUsuario.setUid(oJsonData.getString("Api_key"));
					
					JSONObject oJsonRanking = oJson.getJSONObject("ranking");
					Log.e("ranking",oJsonRanking+"");
					oUsuario.setIdRanking(Integer.parseInt(oJsonRanking.getString("rankingID")));
					oUsuario.setApMaterno(oJsonRanking.getString("name"));				
					
					oJsonStatus.setHttpCode(Integer.parseInt(oJsonData.getString("httpCode")));
					bEstado = true;
					
				}else{
					oJsonStatus.setError_cod(Double.parseDouble(oJsonData.getString("error_cod")));
					oJsonStatus.setMessage(oJsonData.getString("message"));
					oJsonStatus.setInfo(oJsonData.getString("info"));
				}
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		
		return bEstado;
	}
	

	
		 		
		
	
	
	
	
	
}
