package com.sigetdriver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class SigetDriverUtil {
	
	public static String obtenerHoraActual() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String obtenerDiferenciaHoras(String hora1, String hora2) {
		String tiempoEspera = "";
		long horas;
		long minutos;
		Date date1 = null;
		Date date2 = null;
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");			
		try {				 
			date1 = formatter.parse(hora1);
			date2 = formatter.parse(hora2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try { 
			//in milliseconds
			long diff = date1.getTime() - date2.getTime(); 
			minutos = diff / (60 * 1000) % 60;
			horas = diff / (60 * 60 * 1000) % 24;			
			tiempoEspera = horas + ":" + minutos;			
//			System.out.println("DIFERENCIA: " + tiempoEspera); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tiempoEspera.contains("-")) {
			tiempoEspera = "-";
		} 
		return tiempoEspera;
	}
	
	public static String obtenerDiferenciaTimer(Long tiempoServicio) {
		String tiempoRestante = "";
		// CONSTANTES
		long TIEMPO_SEGUNDO = 1 * 1000;
		long TIEMPO_MINUTO = 60 * TIEMPO_SEGUNDO;
		long TIEMPO_HORA = 60 * TIEMPO_MINUTO;
		long TIEMPO_DIA = 24 * TIEMPO_HORA;
		
		// HORA SISTEMA
		long tiempoActual = new Date().getTime();
		
		// EVALUAMOS LA DIFERENCIA
		long difTiempo = tiempoServicio - tiempoActual;
		if (difTiempo <= 0) {
			tiempoRestante = "00:00:00";
		} else {
			int cantidadDias;
			int cantidadHoras;
			int cantidadMinutos;
			
			cantidadDias = (int) (difTiempo / TIEMPO_DIA);
			cantidadHoras = (int) ((difTiempo % TIEMPO_DIA) / TIEMPO_HORA);
			cantidadMinutos = (int) (((difTiempo % TIEMPO_DIA) % TIEMPO_HORA) / TIEMPO_MINUTO);
			
			if (cantidadDias > 0) {
				// DIAS
				if (cantidadDias < 10) {
					tiempoRestante += "0" + cantidadDias;
				} else {
					tiempoRestante += cantidadDias;
				}
			} else {
				tiempoRestante += "00";
			}
			tiempoRestante += ":";
			if (cantidadHoras > 0) {
				// DIAS
				if (cantidadHoras < 10) {
					tiempoRestante += "0" + cantidadHoras;
				} else {
					tiempoRestante += cantidadHoras;
				}
			} else {
				tiempoRestante += "00";
			}
			tiempoRestante += ":";
			if (cantidadMinutos > 0) {
				// DIAS
				if (cantidadMinutos < 10) {
					tiempoRestante += "0" + cantidadMinutos;
				} else {
					tiempoRestante += cantidadMinutos;
				}
			} else {
				tiempoRestante += "00";
			}
		}
		return tiempoRestante;
	}
	
	public static String connect(String url)
	{
		String result = "";
	    HttpClient httpclient = new DefaultHttpClient();

	    // Prepare a request object
	    HttpGet httpget = new HttpGet(url); 

	    // Execute the request
	    HttpResponse response;
	    try {
	        response = httpclient.execute(httpget);
	        // Examine the response status
	        Log.i("STATUS", response.getStatusLine().toString());

	        // Get hold of the response entity
	        HttpEntity entity = response.getEntity();
	        // If the response does not enclose an entity, there is no need
	        // to worry about connection release

	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            result = convertStreamToString(instream);
	            // now you have the string representation of the HTML request
	            instream.close();
	        }


	    } catch (Exception e) {}
	    
	    return result;
	}
	
	public static String connectPost(String url)
	{
		String result = "";
	    HttpClient httpclient = new DefaultHttpClient();

	    // Prepare a request object
	    HttpPost httppost = new HttpPost(url); 

	    // Execute the request
	    HttpResponse response;
	    try {
	        response = httpclient.execute(httppost);
	        // Examine the response status
	        Log.i("STATUS", response.getStatusLine().toString());

	        // Get hold of the response entity
	        HttpEntity entity = response.getEntity();
	        // If the response does not enclose an entity, there is no need
	        // to worry about connection release

	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            result = convertStreamToString(instream);
	            // now you have the string representation of the HTML request
	            instream.close();
	        }


	    } catch (Exception e) {}
	    
	    return result;
	}

    private static String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
//	    String retorno = new String(sb.toString().getBytes("ISO-8859-1"));
	    return sb.toString();
//	    return retorno;
	}
    
    public static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    public static String connectPostWithData(String path, String json) throws Exception 
    {
    	String result = null;
    	JSONObject params = new JSONObject(json);
    	// request method is POST
		// defaultHttpClient
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);
		// httpPost.setHeader("Content-type", "application/json");
		httpPost.setEntity(new StringEntity(params.toString()));
		// if (params != null)
		// httpPost.setEntity(new UrlEncodedFormEntity(params));

		HttpResponse httpResponse = httpClient.execute(httpPost);
        	    
        //Handles what is returned from the page 
        ResponseHandler responseHandler = new BasicResponseHandler();
        
        try {
        	httpResponse = httpClient.execute(httpPost, responseHandler);
	        // Examine the response status
	        Log.i("STATUS", httpResponse.getStatusLine().toString());

	        // Get hold of the response entity
	        HttpEntity entity = httpResponse.getEntity();
	        // If the response does not enclose an entity, there is no need
	        // to worry about connection release

	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            result = convertStreamToString(instream);
	            // now you have the string representation of the HTML request
	            instream.close();
	        }
	        
	        return result;


	    } catch (Exception e) {
	    	
	    	System.out.println("ERROR: " + e.toString());
	    	
	    }
		return result;
	    
    }
    
	static InputStream is = null;
	static JSONArray jArray = null;
	static String json = "";
    
    public static String makeHttpRequest(String url, String method, JSONObject params) {

		// Making HTTP request
		try {

			// check for request method
			if (method == "POST") {
				// request method is POST
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				httpPost.setHeader("Content-type", "application/json");
				httpPost.setEntity(new StringEntity(params.toString()));
				// if (params != null)
				// httpPost.setEntity(new UrlEncodedFormEntity(params));

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			} else if (method == "GET") {
				// request method is GET
				DefaultHttpClient httpClient = new DefaultHttpClient();
				// if (params != null)
				// url += "?" + URLEncodedUtils.format(params, "utf-8");

				Log.i("url", url);
				HttpGet httpGet = new HttpGet(url);

				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		return json;

	}

}
