package pe.nullpoint.easymaestro.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONObject;
import android.util.Log;



public class Utils_JsonParser {

	private static final String MAIN_ERROR_STATUS = "error_status";
	@SuppressWarnings("unused")
	private static final String MAIN_HTTP_CODE = "httpCode";
	private static final String MAIN_DATA = "data";
	private static final String DATA_MESSAGE = "message";
	private static final String DATA_INFO = "info";
	@SuppressWarnings("unused")
	private static final String DATA_ERROR_COD = "error_cod";



	    static JSONObject oJson = null;//Objeto sJson
	    static String sJson = ""; //Variable
		private Utils_JsonRequest oHttp;
		




		public Utils_JsonParser() {
			super();
			oHttp = new Utils_JsonRequest();			
		}





		public JSONObject parserToJsonObject(InputStream inStream){
			  boolean isOk = false;  	
			  
//				if (!oHttp.isbTimeout()) {
//					Log.e(DATA_INFO, "IS TIME OUT: " + oHttp.isbTimeout());
					 if(inStream!=null)	{
						  	/* Recibe la data, la almacena y la transforma a una cadena String*/
						  	try {
						  		/*Permite manejar el flujo de caracteres de entrada almacenado en búfer.*/
						  	    BufferedReader buffReader = new BufferedReader(new InputStreamReader(inStream,"UTF-8"));
						  	    StringBuilder sBuider = new StringBuilder(); //Crear string, mejor manejo de memoria.
						  	    String sLinea = null;
						  	    String sNewLine = System.getProperty("line.separator");
						  	    
						  	   
						  	    while((sLinea = buffReader.readLine()) != null)
						  	    {
						  	    	sBuider.append(sLinea + sNewLine); //Agrega los datos a stringBuider
						  	    }
						  	    inStream.close();//Cerramos el string
						  	    sJson = sBuider.toString();
						  	    
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
						  	
//					  }
					 
				}else{
					
					try {
						sJson = "{\""+MAIN_ERROR_STATUS+"\":"+true+",\""+MAIN_DATA+"\":{\""+ DATA_MESSAGE+"\":\"Timeout\",\""+ DATA_INFO+"\":\"El servidor no responde\"}}";
						Log.e(MAIN_DATA, sJson);
					   oJson = new JSONObject(sJson); //Parseo
						Log.e("timeout", oJson+"");
					} catch (Exception e) {
						 Log.e("JSON Parser", "Error al analizar Data" + e.toString());
					}
				}
			  
				  	return oJson; //Devuelve el Objeto JSON			  	
		}
	}
