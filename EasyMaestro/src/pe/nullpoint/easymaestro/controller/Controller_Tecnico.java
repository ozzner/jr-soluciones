package pe.nullpoint.easymaestro.controller;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import pe.nullpoint.easymaestro.constant.Constants_server;
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
		
		URL = URI.create( Constants_server.SERVER 
				+ Constants_server.API_V1 
				+ Constants_server.GET_TECNICO);
		
		InputStream in = null;
		JSONObject oJson = null;
		
		
		ArrayList<Model_Tecnico> lista = new ArrayList<Model_Tecnico>();
		
		try {
			
			in = request.httpGet(URL.toString());
			oJson = jsonPaser.parserToJsonObject(in);
			JSONArray data = oJson.getJSONArray("data");
			JSONObject tecnicos ;
			
			for (int i = 0; i < data.length(); i++) {
				
				tecnicos = data.getJSONObject(i);
				Model_Tecnico oTec =  new Model_Tecnico();
				
				oTec.setTecnicoID(tecnicos.getInt("tecnicoID"));
				oTec.setNombre(tecnicos.getString("nombre"));
				oTec.setProfesion(tecnicos.getString("profesion"));
				oTec.setDni(tecnicos.getString("dni"));
				oTec.setDireccion(tecnicos.getString("direccion"));
				oTec.setCelular(tecnicos.getInt("celular"));
				oTec.setLat(tecnicos.getDouble("latitud"));
				oTec.setLon(tecnicos.getDouble("longitud"));
				oTec.setCalificacion(Float.parseFloat(tecnicos.getString("calificacion")));
//				oTec.setComentario(tecnicos.getString("comentario"));
				
				lista.add(oTec);
				
			}
			
		} catch (Exception e) {
			
		}
		
		return lista;
	}

}
