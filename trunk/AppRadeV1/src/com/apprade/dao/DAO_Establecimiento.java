package com.apprade.dao;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnConnectionParamBean;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;

import com.apprade.entity.Entity_Categoria;
import com.apprade.entity.Entity_Coordenadas;
import com.apprade.entity.Entity_Distrito;
import com.apprade.entity.Entity_Establecimiento;
import com.apprade.entity.Entity_Usuario;
import com.apprade.helper.Helper_Http_Method;
import com.apprade.helper.Helper_JSONParser;
import com.apprade.helper.Helper_JSONStatus;


public class DAO_Establecimiento {
	
	
	public Entity_Establecimiento ettEstab ;
	public  Helper_JSONStatus oJsonStatus;	
	private Helper_JSONParser oParser;
	private Helper_Http_Method oHttp;
	private static DAO_Conexion conn;
	private static URI URL ;
	private static final String ENTITY = "establecimiento";
	private static ArrayList<String> lsColas = new ArrayList<>();
	
	/**
	 * @param oEstable
	 * @param oJsonStatus
	 * @param oParser
	 * @param oHttp
	 */
	public DAO_Establecimiento(Entity_Establecimiento oEstable,
			Helper_JSONStatus oJsonStatus, Helper_JSONParser oParser,
			Helper_Http_Method oHttp) {
		super();
		this.ettEstab = oEstable;
		this.oJsonStatus = oJsonStatus;
		this.oParser = oParser;
		this.oHttp = oHttp;
	}
	/**
	 * 
	 */
	public DAO_Establecimiento() {
		super();
		oHttp = new Helper_Http_Method();
		oParser = new Helper_JSONParser();
		oJsonStatus = new Helper_JSONStatus();
		conn = new DAO_Conexion();
	}

	/**
	 * @return the lsColas
	 */
	public  ArrayList<String> getLsColas() {
		return lsColas;
	}
	/**
	 * @param lsColas the lsColas to set
	 */
	public void setLsColas(ArrayList<String> lsColas) {
		this.lsColas = lsColas;
	}
	
	
	public List<Entity_Establecimiento> listarEstablecimientoPorCategoriaID(String categoriaID)
	{	
		URL= URI.create(conn.getUrl());
		InputStream in = null;
		JSONObject oJson = null;
		
	    List<Entity_Establecimiento> lista = new ArrayList<Entity_Establecimiento>();
	    List<Entity_Distrito> listaDis = new ArrayList<Entity_Distrito>();	
	    List<Entity_Coordenadas> listaCoo = new ArrayList<Entity_Coordenadas>();
	    List<Entity_Categoria> listaCat = new ArrayList<Entity_Categoria>();	
	    
		List<NameValuePair> parametros = new ArrayList<NameValuePair>();
		
		parametros.add( new BasicNameValuePair("entity", ENTITY));
		parametros.add( new BasicNameValuePair("categoriaID", categoriaID));
		String paramsString = URLEncodedUtils.format(parametros, "UTF-8");
		
		try {						
			    in =  oHttp.httpGet(URL + "?" + paramsString);
			    oJson =oParser.parserToJsonObject(in);
			    
			    boolean bStatus = Boolean.parseBoolean(oJson.getString("error_status"));
				
				if(!bStatus){
					JSONObject oData =  oJson.getJSONObject("data");
					int iNum = oData.length();
					lsColas.clear();
					
						for (int i = 0; i < iNum; i++) {
							
							JSONObject oEstabli =  oData.getJSONObject("establishment"+(i+1));	
							
							int iIdEst = Integer.parseInt(oEstabli.getString("establishmentID"));
							String sNameEst = oEstabli.getString("name");
							String sDireccion = oEstabli.getString("address");
//							int iRUC = Integer.parseInt(oEstabli.getString("ruc"));
							
							JSONObject oDistric =  oEstabli.getJSONObject("district");							
							int iIdDis = Integer.parseInt(oDistric.getString("districtID"));
							String sNameDis = oDistric.getString("name");
							Entity_Distrito ettDis = new Entity_Distrito(iIdDis, sNameDis, null);
							listaDis.add(ettDis);
							
							JSONObject oCateg =  oEstabli.getJSONObject("category");
							String sNameCat = oCateg.getString("name");
							int iIdCat = Integer.parseInt(oCateg.getString("categoryID"));
							Entity_Categoria ettCat = new Entity_Categoria(iIdCat, sNameCat);
							listaCat.add(ettCat);
																										
							JSONObject oCoodin =  oEstabli.getJSONObject("coordinates");
							int iIdCoo = Integer.parseInt(oCoodin.getString("coordinatesID"));
							float fLatit = Float.parseFloat(oCoodin.getString("latitude"));
							float fLongi = Float.parseFloat(oCoodin.getString("longitude"));							
							Entity_Coordenadas ettCoor = new Entity_Coordenadas(iIdCoo, fLatit, fLongi, null);
							listaCoo.add(ettCoor);
						
							Entity_Establecimiento ettEst = new Entity_Establecimiento
									(iIdEst, sNameEst, sDireccion, -1, listaCat, listaDis, listaCoo);
							
							lista.add(ettEst);//Lista final
							
							try {
						
								JSONObject oRate =  oEstabli.getJSONObject("rating");
								if (!oRate.getString("cal_cola").toString().equals("")) {
									lsColas.add(oRate.getString("cal_cola"));
								}else{
									lsColas.add(oRate.getString("No hay cola"));
								}
								
							} catch (Exception e) {
								lsColas.add("No hay cola");
							}
							
							
						}
					
					oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
					
				}else{
					oJsonStatus.setHttpCode(Integer.parseInt(oJson.getString("httpCode")));
					lista=null; //Temporal
				}
			
		} catch (Exception e) {
			Log.e("URL", e.getMessage());
			}
		
		return lista;
	}
	
	

	
}
    
 

