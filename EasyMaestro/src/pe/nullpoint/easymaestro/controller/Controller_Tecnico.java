package pe.nullpoint.easymaestro.controller;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;

import pe.nullpoint.easymaestro.model.Model_Tecnico;
import pe.nullpoint.easymaestro.utils.Utils_JsonParser;
import pe.nullpoint.easymaestro.utils.Utils_JsonRequest;

public class Controller_Tecnico {
	
	private URI URL;
	
	private final String TAG = Controller_Tecnico.class.getSimpleName();
	private Utils_JsonRequest request;
	private Utils_JsonParser jsonPaser;

	public Controller_Tecnico() {
		super();
		request = new Utils_JsonRequest();
		jsonPaser = new Utils_JsonParser();
		
	}
	
	
	
	public ArrayList<Model_Tecnico> getTecnicos(){
		
		URL = URI.create("http://192.168.1.34/api_easymaestro/v1/");
		InputStream in = null;
		JSONObject oJson = null;
		
		ArrayList<Model_Tecnico> lista = new ArrayList<Model_Tecnico>();
		
		try {
			
			in = request.httpGet(URL.toString());
			oJson = jsonPaser.parserToJsonObject(in);
			
			Log.e(TAG, oJson+"");
			
		} catch (Exception e) {

			
			
			
		}
		
		
		
		return null;
		
	}

}
