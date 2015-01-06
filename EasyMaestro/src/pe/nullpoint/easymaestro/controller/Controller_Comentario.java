package pe.nullpoint.easymaestro.controller;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.cookie.BasicPathHandler;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import pe.nullpoint.easymaestro.constant.Constants_server;
import pe.nullpoint.easymaestro.model.Model_Comentarios;
import pe.nullpoint.easymaestro.model.Model_Tecnico;
import pe.nullpoint.easymaestro.utils.Utils_JsonParser;
import pe.nullpoint.easymaestro.utils.Utils_JsonRequest;

public class Controller_Comentario {
	
	private URI URL;
	
	private final String TAG = Controller_Tecnico.class.getSimpleName();
	private Utils_JsonRequest request;
	private Utils_JsonParser jsonPaser;
	/**
	 * 
	 */
	public Controller_Comentario() {
		super();
		request = new Utils_JsonRequest();
		jsonPaser = new Utils_JsonParser();
	}
	
	
	public ArrayList<Model_Comentarios> getComentariosByTecnicoID(int tecnicoID){
		
		URL = URI.create( Constants_server.SERVER 
						+ Constants_server.API_V1 
						+ Constants_server.GET_COMENTARIO);
		
		InputStream in = null;
		JSONObject oJson = null;

		
		ArrayList<Model_Comentarios> lista = new ArrayList<Model_Comentarios>();
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		parametros.add(new BasicNameValuePair("tecnicoID", String.valueOf(tecnicoID)));
		String encodeParams = URLEncodedUtils.format(parametros, "UTF-8");
		
		
		try {
			
			in = request.httpGet(URL.toString() + "?" + encodeParams);
			oJson = jsonPaser.parserToJsonObject(in);
			JSONArray data = oJson.getJSONArray("data");
			JSONObject comentarios ;
			Model_Comentarios oComm;
			
			for (int i = 0; i < data.length(); i++) {
				
			comentarios = data.getJSONObject(i);
			String mensaje = comentarios.getString("mensaje");
			String tipo = comentarios.getString("tipo");
			
			oComm = new Model_Comentarios(mensaje, tipo);
			lista.add(oComm);

			}
		
		} catch (Exception e) {
			
		}

		
		return lista;
		
		
	}
	

}
