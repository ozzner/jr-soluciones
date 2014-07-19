package com.apprade.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Renzo
 *
 */
public class Helper_Http_Method {

    static JSONObject oJson = null;//Objeto sJson
    static String sJson = ""; //Variable
    
	public Helper_Http_Method() {
		// TODO Auto-generated constructor stub
	}
	
	
	public  InputStream httpGet(String url){
	
		InputStream in = null;
	
		try {				    		
			    	   	HttpClient httpClient = new DefaultHttpClient();
		
			    		HttpParams httpParamentros = httpClient.getParams();
			    		HttpConnectionParams.setConnectionTimeout(httpParamentros, 8000);
			    		HttpConnectionParams.setSoTimeout(httpParamentros, 8000);
			    							    	
			    		URI www = new URI(url);			    		
			    		HttpGet get= new HttpGet();				    		
			    		get.setURI(www); 					    		
			    		HttpResponse response = httpClient.execute( get);			    		
			    		in = response.getEntity().getContent();		    		
			    		
			        } catch(ConnectTimeoutException e){
			            Log.e("Exception: Timeout", e.toString());
			        } catch (Exception e) {
			            Log.e("log_tag", "Error in http connection "+e.toString());
			        }
			    				
    	return in;
			 		
	}
	
	
	public  InputStream httpPost(String url )
	{
		InputStream in = null;
		
			try {	
	    		
	    		DefaultHttpClient httpClient = new DefaultHttpClient();
	    		/* timeout*/
	    		HttpParams httpParamentros = httpClient.getParams();
	    		HttpConnectionParams.setConnectionTimeout(httpParamentros, 8000);
	    		HttpConnectionParams.setSoTimeout(httpParamentros, 8000);
	    		
	    		HttpPost post = new HttpPost();
	    	    URI www = new URI(url);
	    	    post.setURI(www); 		
	    		HttpResponse response = httpClient.execute(post);
	    		in = response.getEntity().getContent();
	    	
	    		Log.e("TAG-INSTREAM",in+"");

	        } catch(ConnectTimeoutException e){
	            Log.e("Exception: Timeout", e.toString());
	        } catch (Exception e) {
	            Log.e("log_tag", "Error in http connection "+e.toString());
	        }
	    	Log.e("TAG-INPUTSTREAM", in +"");
		
	    	return in;
	}

	
	public JSONObject parserToJsonObject(InputStream inStream){
		  boolean isOk = false;  	
		  
		  if(inStream!=null)	{
			  	/* Recibe la data, la almacena y la transforma a una cadena String*/
			  	try {
			  		/*Permite manejar el flujo de caracteres de entrada almacenado en búfer.*/
			  	    BufferedReader buffReader = new BufferedReader(new InputStreamReader(inStream,"UTF-8"),8);
			  	    StringBuilder sBuider = new StringBuilder(); //Crear string, mejor manejo de memoria.
			  	    String sLinea = null;
			  	    String sNewLine = System.getProperty("line.separator");
			  	    
			  	    while((sLinea = buffReader.readLine()) != null)
			  	    {
			  	    	sBuider.append(sLinea + sNewLine); //Agrega los datos a stringBuider
			  	    }
			  	    inStream.close();//Cerramos el string
			  	    sJson = sBuider.toString();
			  	    Log.e("JSON-PARSER", sJson);
			  	     isOk = true;
					} catch (Exception e) {
						Log.e("Error Buffer", "Error convirtiendo el resultado " + e.toString());
						isOk=false;
					}
			  	
			  	if (isOk) {
			  		try {
						oJson = new JSONObject(sJson); //Parseo
						Log.e("JSON.OBJ", oJson+"");
					} catch (Exception e) {
						 Log.e("JSON Parser", "Error al analizar Data" + e.toString());
					}
				}
			  	
		  }	  	
			  	return oJson; //devuelve el Objeto JSON			  	
	}

		  
	  
}
