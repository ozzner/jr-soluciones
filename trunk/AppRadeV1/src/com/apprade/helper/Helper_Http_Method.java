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
			    		HttpResponse response = httpClient.execute(get);			    		
			    		in = response.getEntity().getContent();		    		
			    		
			        } catch(ConnectTimeoutException e){
			            Log.e("Exception: Timeout", e.toString());
			        } catch (Exception e) {
			            Log.e("log_tag", "Error in http connection "+e.toString());
			        }
			    				
    	return in;
			 		
	}
	
	
	
	public InputStream httpPost(URI url, List<NameValuePair> parametros) {
		InputStream in = null;
		
		try {	
    		
    		DefaultHttpClient httpClient = new DefaultHttpClient();
    		/* timeout*/
    		HttpParams httpParamentros = httpClient.getParams();
    		HttpConnectionParams.setConnectionTimeout(httpParamentros, 8000);
    		HttpConnectionParams.setSoTimeout(httpParamentros, 8000);
    		
    		HttpPost post = new HttpPost(url);
    		post.setEntity(new UrlEncodedFormEntity(parametros));
    	    //URI www = new URI(url);
    	    // post.setURI(www); 		
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

}
