package com.apprade.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * @author Renzo
 *
 */
public class Helper_JSONParser{
	
	
	static InputStream inStream = null;//Leertor de bytes
    static JSONObject oJson = null;//Objeto json
    static String json = ""; //Variable
    
	public Helper_JSONParser() {
		// TODO Auto-generated constructor stub
	}
	
	  public JSONObject httpPost(URI uRL, List<NameValuePair> parametros) {
	    		    		
	    	try {	
	    		
	    	   	DefaultHttpClient httpClient = new DefaultHttpClient();

	    		HttpParams httpParamentros = httpClient.getParams();
	    		HttpConnectionParams.setConnectionTimeout(httpParamentros, 8000);
	    		HttpConnectionParams.setSoTimeout(httpParamentros, 8000);

	    		HttpPost post = new HttpPost();
	    		post.setURI(uRL);
	    		post.setParams((HttpParams) parametros);	    		
	    		HttpResponse httpResponse = httpClient.execute(post);

	    		HttpEntity httpEntity = httpResponse.getEntity();
	    		inStream = httpEntity.getContent();
	    		
			} catch (UnsupportedEncodingException e) {
	            Log.e("Error, Codificacion no soportada", e.getMessage());
	        } catch (ClientProtocolException e) {
	        	Log.e("Error, Protocolo", e.getMessage());
	        } catch(ConnectTimeoutException e){
	            Log.e("Exception: Timeout", e.toString());
	        } catch(SocketTimeoutException e){    
	            Log.e("Exception: Timeout", e.toString());
	        } catch (Exception e) {
	            Log.e("log_tag", "Error in http connection "+e.toString());
	        }
	    	Log.e("TAG-INPUTSTREAM", inStream +"");
	    	
	    	JSONObject json=parserToJsonObject(inStream);
	    		    	
	    	return json;
	  }
	  
	  
	  public JSONObject httpGet(URI url,List<NameValuePair> params) {
	    	
	    	try {	
	    		
	    		DefaultHttpClient httpClient = new DefaultHttpClient();

	    		HttpParams httpParamentros = httpClient.getParams();
	    		HttpConnectionParams.setConnectionTimeout(httpParamentros, 8000);
	    		HttpConnectionParams.setSoTimeout(httpParamentros, 8000);
	    		
	    		HttpGet get = new HttpGet();
	    		get.setURI(url);
	    		get.setParams((HttpParams) params);

	    		HttpResponse httpResponse = httpClient.execute(get);
	    		HttpEntity httpEntity = httpResponse.getEntity();
	    		inStream = httpEntity.getContent();
	    		
			} catch (UnsupportedEncodingException e) {
	            Log.e("Error, Codificacion no soportada", e.getMessage());
	        } catch (ClientProtocolException e) {
	        	Log.e("Error, Protocolo", e.getMessage());
	        } catch(ConnectTimeoutException e){
	            Log.e("Exception: Timeout", e.toString());
	        } catch(SocketTimeoutException e){    
	            Log.e("Exception: Timeout", e.toString());
	        } catch (Exception e) {
	            Log.e("log_tag", "Error in http connection "+e.toString());
	        }
	    	Log.e("TAG-INPUTSTREAM", inStream +"");
	    	
	    	JSONObject json=parserToJsonObject(inStream);
	    		    	
	    	return json;
	  }
	  
	  

	@SuppressWarnings("unused")
	private JSONObject parserToJsonObject(InputStream inStream){
		  
		  if(inStream!=null)	{
			  	/* Recibe la data, la almacena y la transforma a una cadena String*/
			  	try {
			  		/*Permite manejar el flujo de caracteres de entrada almacenado en búfer.*/
			  	    BufferedReader buffReader = new BufferedReader(new InputStreamReader(inStream,"UTF-8"),8);
			  	    StringBuilder sBuider = new StringBuilder(); //Crear string, mejor manejo de memoria.
			  	    String sLinea = null;
			  	    while((sLinea = buffReader.readLine()) != null)
			  	    {
			  	    	sBuider.append(sLinea+"\n"); //Agrega los datos a stringBuider
			  	    }
			  	    inStream.close();//Cerramos el string
			  	    json = sBuider.toString();
			  	    Log.e("JSON-PARSER", json);
			  	    
					} catch (Exception e) {
						Log.e("Error Buffer", "Error convirtiendo el resultado " + e.toString());
					}
			  	
			  	/* El analizador de String tranforma a Objeto JSON */
			  	try {
						oJson = new JSONObject(json);
					} catch (Exception e) {
						 Log.e("JSON Parser", "Error al analizar Data" + e.toString());
					}
			  	
			  }else{
			  	oJson = null;
			  }
			  	
			  	return oJson; //devuelve el Objeto JSON
			  	
			  }
		  
	  
}
