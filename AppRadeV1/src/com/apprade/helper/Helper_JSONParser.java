package com.apprade.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import android.util.Log;

public class Helper_JSONParser {


    static JSONObject oJson = null;//Objeto sJson
    static String sJson = ""; //Variable
	
	

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
			  	    Log.e("JSON-Parserr", sJson);
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
