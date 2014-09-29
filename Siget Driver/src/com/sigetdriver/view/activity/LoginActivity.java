package com.sigetdriver.view.activity;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.google.android.gcm.GCMRegistrar;
import com.sigetdriver.R;
import com.sigetdriver.ServerConstants;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.controller.CentroCostoController;
import com.sigetdriver.controller.EmpresaController;
import com.sigetdriver.controller.PasajeroController;
import com.sigetdriver.controller.SedeController;
import com.sigetdriver.controller.TarifaZonaController;
import com.sigetdriver.controller.ZonaController;
import com.sigetdriver.entities.CentroCostoBean;
import com.sigetdriver.entities.EmpresaBean;
import com.sigetdriver.entities.PasajeroBean;
import com.sigetdriver.entities.SedeBean;
import com.sigetdriver.entities.TarifaZonaBean;
import com.sigetdriver.entities.ZonaBean;
import com.sigetdriver.service.TrackingService;
import com.sigetdriver.util.SigetDriverUtil;

public class LoginActivity extends Activity {
	
	private Context context;
	private EditText edtUsuario;
	private EditText edtPassword;
	private CheckBox chkRecordarme;
	private Button btnIngresar;
	private String[] parametros = new String[4];
	private int contadorMaestro = 0;
	private int contadorProceso = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Action Bar
		setTitle("Login");		
		getActionBar().setIcon(
				   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		context = getApplicationContext();
		edtUsuario = (EditText) findViewById(R.id.edtUsuario);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		chkRecordarme = (CheckBox) findViewById(R.id.chkRecordarme);
		btnIngresar = (Button) findViewById(R.id.btnIngresar);
		
		// Revisamos si hay usuario y password guardado
		SharedPreferences prefs = getSharedPreferences("login", Context.MODE_PRIVATE);
		String usuario = prefs.getString("usuario", "");
		String password = prefs.getString("password", "");
		final String fechaSin = prefs.getString("fechaSin", "0");
		
		if (!usuario.isEmpty() && !password.isEmpty()) {
			chkRecordarme.setChecked(true);
		}
		
		edtUsuario.setText(usuario);
		edtPassword.setText(password);
		
		/*** PUSH ***/
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		
		if (GCMRegistrar.isRegistered(context)) {
			System.out.println("---SI ESTAS REGISTRADO---");
		} else {
			System.out.println("---NO ESTAS REGISTRADO---");
			GCMRegistrar.register(context, ServerConstants.SENDER_ID);
		}
		
		String regId = GCMRegistrar.getRegistrationId(context);
		System.out.println("REG ID = " + regId);
		
		parametros[2] = regId;
		
		btnIngresar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (validarCampos()) {
					
					String usuario = edtUsuario.getText().toString().trim();
					String password = edtPassword.getText().toString().trim();
										
					parametros[0] = usuario;					
					parametros[1] = SigetDriverUtil.encryptPassword(password);
					parametros[2] = GCMRegistrar.getRegistrationId(LoginActivity.this);
					parametros[3] = fechaSin;
					new loginValidar().execute(parametros);					
				} else {
					Toast.makeText(getApplicationContext(), "Complete los campos", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private boolean validarCampos() {
		if (edtUsuario.getText().toString().length() == 0 ||
				edtUsuario.getText().toString().equals("")) {
			return false;
		} else if (edtPassword.getText().toString().length() == 0 ||
				edtPassword.getText().toString().equals("")) {
			return false;
		}
		return true;
	}
	
	// LOGIN - TAREA ASINCRONA
	class loginValidar extends AsyncTask<String, Void, Void> {
		
		private boolean login;
		private ProgressDialog Dialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Dialog = new ProgressDialog(LoginActivity.this);
			Dialog.setCancelable(false);
            Dialog.setMessage("Cargando...");
            Dialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String URL = ServerConstants.SERVER_IP + ServerConstants.LOGIN +
						  "conductor" + "," + "20279a754ba32ba1d9559b7a88303fd873b15eee" + "," + "111";	
			
			
			System.out.println("---URL---\n"+URL);
			String respuesta = SigetDriverUtil.connect("http://54.69.22.121:8080/taxis/rest/conductor/login/conductor,20279a754ba32ba1d9559b7a88303fd873b15eee,111");
			System.out.println("---RESPUESTA---\n"+respuesta);
			
			int autenticacion = 0;		
			try {
				
				StringReader stream = new StringReader(respuesta);
				JsonFactory jfactory = new JsonFactory();
				JsonParser jParser = jfactory.createJsonParser(stream);					
				while (jParser.nextToken() != JsonToken.END_OBJECT) {								 
					String fieldname = jParser.getCurrentName();
					if ("autenticacion".equals(fieldname)) {
						jParser.nextToken();
						autenticacion = jParser.getIntValue();
					} else if ("nombreConductor".equals(fieldname)) {
						jParser.nextToken();
						ServicioWorkingSet.nombreConductor = jParser.getText();
					} else if ("codigoUnidad".equals(fieldname)) {
						jParser.nextToken();
						ServicioWorkingSet.codigoUnidad = jParser.getText();
					}
				}						
			} catch (Exception e) {
				System.out.println("ERROR PARSING LOGIN");
			}
			
			if (autenticacion == 1) {
				login = true;
			} else {
				login = false;
			}			
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Dialog.dismiss(); 
			
			if (login) {
				
				System.out.println("***Intent Tracking Service***");
				startService(new Intent(context, TrackingService.class));
				
				// Seteamos al WorkingSet
				ServicioWorkingSet.usuarioLogin = 
						edtUsuario.getText().toString().trim();
				ServicioWorkingSet.passwordLogin = 
						SigetDriverUtil.encryptPassword(edtPassword.getText().toString().trim());
				
				
				if (chkRecordarme.isChecked()) {
					// Grabamos el usuario y password
					SharedPreferences prefs = getSharedPreferences("login", Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putString("usuario", edtUsuario.getText().toString().trim());
					editor.putString("password", edtPassword.getText().toString().trim());
					editor.commit();
				}
				new preSincronizarDatos().execute(parametros);

			} else {
				Toast.makeText(getApplicationContext(), "Login Incorrecto, vuelva a intentarlo", Toast.LENGTH_SHORT).show();
			}			

		}
		
	}
	
	// PRESINCRONIZACION - TAREA ASINCRONA
	class preSincronizarDatos extends AsyncTask<String, Void, Void> {
		
		private ProgressDialog Dialog;		

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Dialog = new ProgressDialog(LoginActivity.this);
			Dialog.setCancelable(false);
            Dialog.setMessage("Sincronizando...");
            Dialog.show();
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			String URL = ServerConstants.SERVER_IP + 
					ServerConstants.PRESINCRONIZACION +
					params[0] +
					"," + params[1] +
					"/" + params[3];								
			
			System.out.println("---URL---\n"+URL);
			String respuesta = SigetDriverUtil.connect("http://54.69.22.121:8080/taxis/rest/conductor/sinc/contar/conductor,20279a754ba32ba1d9559b7a88303fd873b15eee/144606760000");
			
			try {
				
				StringReader stream = new StringReader(respuesta);
				JsonFactory jfactory = new JsonFactory();
				JsonParser jParser = jfactory.createJsonParser(stream);
				
				while (jParser.nextToken() != JsonToken.END_OBJECT) {
							 
					String fieldname = jParser.getCurrentName();
					
					// [EMPRESAS]
					if ("empresas".equals(fieldname)) {	
						jParser.nextToken();
						contadorMaestro += jParser.getIntValue();
						System.out.println("EMPRESAS = " + jParser.getIntValue());
					}
					// [PASAJEROS]
					if ("pasajeros".equals(fieldname)) {	
						jParser.nextToken();
						contadorMaestro += jParser.getIntValue();
						System.out.println("PASAJEROS = " + jParser.getIntValue());
					}
					// [ZONAS]
					if ("zonas".equals(fieldname)) {
						jParser.nextToken();
						contadorMaestro += jParser.getIntValue();
						System.out.println("ZONAS = " + jParser.getIntValue());
					}
					// [SEDES]
					if ("sedes".equals(fieldname)) {	
						jParser.nextToken();
						contadorMaestro += jParser.getIntValue();
						System.out.println("SEDES = " + jParser.getIntValue());
					}
					// [TARIFAS ZONAS]
					if ("tarifasZonas".equals(fieldname)) {	
						jParser.nextToken();
						contadorMaestro += jParser.getIntValue();
						System.out.println("TARIFAS ZONAS = " + jParser.getIntValue());
					}
					// [CENTROS COSTOS]
					if ("centroCostos".equals(fieldname)) {	
						jParser.nextToken();
						contadorMaestro += jParser.getIntValue();
						System.out.println("CENTROS COSTOS = " + jParser.getIntValue());
					}
				}
				
				jParser.close();
				
			} catch (JsonGenerationException e) {
				 
		 		e.printStackTrace();
			 
		    } catch (JsonProcessingException e) {
		 
				e.printStackTrace();
			 
		    } catch (IOException e) {
			 
				e.printStackTrace();
			 
		    }
				return null;
			}
		
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			Dialog.dismiss(); 
			
			System.out.println("DATOS A SINCRONIZAR: " + contadorMaestro);
			
			if (contadorMaestro != 0) {				
				System.out.println("INICIO SINCRONIZACION");								
				new sincronizarDatos().execute(parametros);				
			} else {
				System.out.println("NADA QUE SINCRONIZAR");	
				Toast.makeText(getApplicationContext(), "Datos Actualizados", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "Bienvenido: " + ServicioWorkingSet.nombreConductor, Toast.LENGTH_SHORT).show();
//				Intent i = new Intent(LoginActivity.this, PruebaActivity.class);
//				startActivity(i);
//				finish();
			}

		}
		
	}
	
	// SINCRONIZACION - TAREA ASINCRONA
	class sincronizarDatos extends AsyncTask<String, Integer, Integer> {
		
		private ProgressDialog Dialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Dialog = new ProgressDialog(LoginActivity.this);
			Dialog.setTitle("Progreso");
			Dialog.setCancelable(true);
            Dialog.setMessage("Descargando...");
            Dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            Dialog.setProgress(0);
            Dialog.setMax(100);
            Dialog.show();
		}

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String URL = ServerConstants.SERVER_IP + 
					ServerConstants.SINCRONIZACION +
					params[0] +
					"," + params[1] +
					"/" + params[3];								
			
			System.out.println("---URL---\n"+URL);
			String respuesta = SigetDriverUtil.connect("http://54.69.22.121:8080/taxis/rest/conductor/sinc/conductor,20279a754ba32ba1d9559b7a88303fd873b15eee/144606760000");
			
			int contaEmpresas = 0;
			int contaPasajeros = 0;
			int contaZonas = 0;
			int contaSedes = 0;
			int contaTarifasZonas = 0;
			int contaCentroCostos = 0;
			
			ArrayList<EmpresaBean> listaEmpresas = new ArrayList<EmpresaBean>();
			ArrayList<PasajeroBean> listaPasajeros = new ArrayList<PasajeroBean>();
			ArrayList<ZonaBean> listaZonas = new ArrayList<ZonaBean>();
			ArrayList<SedeBean> listaSedes = new ArrayList<SedeBean>();
			ArrayList<TarifaZonaBean> listaTarifasZonas = new ArrayList<TarifaZonaBean>();
			ArrayList<CentroCostoBean> listaCentroCostos = new ArrayList<CentroCostoBean>();
			
			System.out.println("---FLAGS---");
			System.out.println("CONTADOR MAESTRO = " + contadorMaestro);
			System.out.println("CONTADOR PROCESO = " + contadorProceso);

			try {
				System.out.println("---TRY---");
				StringReader stream = new StringReader(respuesta);
				JsonFactory jfactory = new JsonFactory();
				JsonParser jParser = jfactory.createJsonParser(stream);
				
				while (jParser.nextToken() != JsonToken.END_OBJECT) {
					System.out.println("--- WHILE 1 ---");
					String fieldname = jParser.getCurrentName();
					
					// [EMPRESAS]
					if ("empresas".equals(fieldname)) {	
						System.out.println("--- EMPRESAS ---");
					    jParser.nextToken();
					    while (jParser.nextToken() != JsonToken.END_ARRAY) {	
					    	System.out.println("--- WHILE 2 ---");
					    	if ("codEmpresa".equals(jParser.getCurrentName())) {
					    		contaEmpresas++;	
					    		EmpresaBean empresa = new EmpresaBean();
				    			// 1-codEmpresa
				    			jParser.nextToken();
				    			empresa.setCodEmpresa(jParser.getText());
				    			jParser.nextToken();					    			
				    			// 2-ruc
				    			jParser.nextToken();
				    			empresa.setRuc(jParser.getText());
				    			jParser.nextToken();					    			
				    			// 3-razonSocial
				    			jParser.nextToken();
				    			empresa.setRazonSocial(jParser.getText());
				    			jParser.nextToken();					    			
				    			// 4-tipoPago
				    			jParser.nextToken();
				    			empresa.setTipoPago(jParser.getText());
				    			jParser.nextToken();
				    			// 5-observacion
				    			jParser.nextToken();
				    			empresa.setObservacion(jParser.getText());
				    			jParser.nextToken();
				    			// 6-notaReferencia
				    			jParser.nextToken();
				    			empresa.setNotaReferencia(jParser.getText());
				    			jParser.nextToken();
				    			// 7-flagBaja
				    			jParser.nextToken();
				    			empresa.setFlagBaja(jParser.getText());
				    			jParser.nextToken();
				    			// 8-montoPuntoAPunto
				    			jParser.nextToken();
				    			empresa.setMontoPuntoAPunto(jParser.getText());
				    			jParser.nextToken();
				    			// 9-montoCourier
				    			jParser.nextToken();
				    			empresa.setMontoCourier(jParser.getText());
				    			jParser.nextToken();
				    			// 10-montoHoraUrbana
				    			jParser.nextToken();
				    			empresa.setMontoHoraUrbana(jParser.getText());
				    			jParser.nextToken();
				    			// 11-montoHoraInterurbana
				    			jParser.nextToken();
				    			empresa.setMontoHoraInterurbana(jParser.getText());
				    			jParser.nextToken();
				    			// 12-montoDiaUrbano
				    			jParser.nextToken();
				    			empresa.setMontoDiaUrbano(jParser.getText());
				    			jParser.nextToken();
				    			// 13-montoDiaInterurbano
				    			jParser.nextToken();
				    			empresa.setMontoDiaInterurbano(jParser.getText());
				    			jParser.nextToken();
				    			// 14-montoCancelacionUrbana
				    			jParser.nextToken();
				    			empresa.setMontoCancelacionUrbana(jParser.getText());
				    			jParser.nextToken();
				    			// 15-montoCancelacionInterurbana
				    			jParser.nextToken();
				    			empresa.setMontoCancelacionInterurbana(jParser.getText());
				    			jParser.nextToken();
				    			// 16-montoCancelacionAeropuerto
				    			jParser.nextToken();
				    			empresa.setMontoCancelacionAeropuerto(jParser.getText());
				    			jParser.nextToken();
				    			// 17-minTiempoEspera1
				    			jParser.nextToken();
				    			empresa.setMinTiempoEspera1(jParser.getText());
				    			jParser.nextToken();
				    			// 18-minTiempoEspera2
				    			jParser.nextToken();
				    			empresa.setMinTiempoEspera2(jParser.getText());
				    			jParser.nextToken();
				    			// 19-minTiempoEspera3
				    			jParser.nextToken();
				    			empresa.setMinTiempoEspera3(jParser.getText());
				    			jParser.nextToken();
				    			// 20-minTiempoEsperaRecurrente
				    			jParser.nextToken();
				    			empresa.setMinTiempoEsperaRecurrente(jParser.getText());
				    			jParser.nextToken();
				    			// 21-montoTiempoEspera1
				    			jParser.nextToken();
				    			empresa.setMontoTiempoEspera1(jParser.getText());
				    			jParser.nextToken();
				    			// 22-montoTiempoEspera2
				    			jParser.nextToken();
				    			empresa.setMontoTiempoEspera2(jParser.getText());
				    			jParser.nextToken();
				    			// 23-montoTiempoEspera3
				    			jParser.nextToken();
				    			empresa.setMontoTiempoEspera3(jParser.getText());
				    			jParser.nextToken();
				    			// 24-montoTiempoEsperaRecurrente
				    			jParser.nextToken();
				    			empresa.setMontoTiempoEsperaRecurrente(jParser.getText());
				    			jParser.nextToken();
				    			// 25-sobrecargoDesvio
				    			jParser.nextToken();
				    			empresa.setSobrecargaDesvio(jParser.getText());
				    			
//				    			EmpresaController.getInstance().actualizarEmpresa(empresa);
				    			
				    			listaEmpresas.add(empresa);
//				    	dfdf		
				    			contadorProceso++;
				    			double proceso = ((double) contadorProceso / (double) contadorMaestro) * 100;
				    			int porcentaje = (int) proceso;
				    			publishProgress(porcentaje);
					    	}			    
					    }		 
					}	
					// [PASAJEROS]
					if ("pasajeros".equals(fieldname)) {	
					    jParser.nextToken();
					    while (jParser.nextToken() != JsonToken.END_ARRAY) {	
					    	System.out.println("--- PASAJERO WHILE ---");
					    	if ("codigo".equals(jParser.getCurrentName())) {
					    		contaPasajeros++;
					    		PasajeroBean pasajero = new PasajeroBean();
					    		// 1-codigo
					    		jParser.nextToken();
					    		pasajero.setCodigo(jParser.getText());
					    		jParser.nextToken();
					    		// 2-cargo
					    		jParser.nextToken();
					    		pasajero.setCargo(jParser.getText());
					    		jParser.nextToken();
					    		// 3-flagPertenece
					    		jParser.nextToken();
					    		pasajero.setFlagPertenece(jParser.getText());
					    		jParser.nextToken();
					    		// 4-empresa
					    		jParser.nextToken();
					    		pasajero.setEmpresa(jParser.getText());
					    		jParser.nextToken();
					    		// 5-nombres
					    		jParser.nextToken();
					    		pasajero.setNombres(jParser.getText());
					    		jParser.nextToken();
					    		// 6-apellidos
					    		jParser.nextToken();
					    		pasajero.setApellidos(jParser.getText());
					    		jParser.nextToken();
					    		// 7-correo
					    		jParser.nextToken();
					    		pasajero.setCorreo(jParser.getText());
					    		jParser.nextToken();
					    		// 8-tipoDocumento
					    		jParser.nextToken();
					    		pasajero.setTipoDocumento(jParser.getText());
					    		jParser.nextToken();
					    		// 9-numDocumento
					    		jParser.nextToken();
					    		pasajero.setNumDocumento(jParser.getText());
					    		jParser.nextToken();
					    		// 10-fechaNacimiento
					    		jParser.nextToken();
					    		pasajero.setFechaNacimiento(jParser.getText());
					    		jParser.nextToken();
					    		// 11-flagBaja
					    		jParser.nextToken();
					    		pasajero.setFlagBaja(jParser.getText());
					    		jParser.nextToken();
					    		// 12-todosTelefonos
					    		jParser.nextToken();
					    		pasajero.setTodosTelefonos(jParser.getText());
					    		
//					    		PasajeroController.getInstance().actualizarPasajero(pasajero);
					    		
					    		listaPasajeros.add(pasajero);
				    			
				    			contadorProceso++;
				    			double proceso = ((double) contadorProceso / (double) contadorMaestro) * 100;
				    			int porcentaje = (int) proceso;
				    			publishProgress(porcentaje);
					    	}			    
					    }		 
					}
					// [ZONAS]
					if ("zonas".equals(fieldname)) {	
					    jParser.nextToken();
					    while (jParser.nextToken() != JsonToken.END_ARRAY) {	
					    	System.out.println("--- ZONAS WHILE ---");
					    	if ("id".equals(jParser.getCurrentName())) {
					    		contaZonas++;
					    		ZonaBean zona = new ZonaBean();
					    		// 1-id
					    		jParser.nextToken();	
					    		zona.setId(jParser.getText());
					    		jParser.nextToken();
					    		// 2-nombre
					    		jParser.nextToken();	
					    		zona.setNombre(jParser.getText());
					    		jParser.nextToken();
					    		// 3-descripcion
					    		jParser.nextToken();	
					    		zona.setDescripcion(jParser.getText());
					    		jParser.nextToken();
					    		// 4-poligono
					    		jParser.nextToken();
					    		zona.setPoligono(jParser.getText());
//					    		zona.setPoligono(jParser.getText().replace("\\", "\\\\"));
					    		jParser.nextToken();
					    		// 5-estado
					    		jParser.nextToken();	
					    		zona.setEstado(jParser.getText());
					    		jParser.nextToken();
					    		// 6-urbana
					    		jParser.nextToken();	
					    		zona.setUrbana(jParser.getText());
					    		
//					    		ZonaController.getInstance().actualizarZona(zona);
					    		
					    		listaZonas.add(zona);
				    			
				    			contadorProceso++;
				    			double proceso = ((double) contadorProceso / (double) contadorMaestro) * 100;
				    			int porcentaje = (int) proceso;
				    			publishProgress(porcentaje);
					    	}			    
					    }		 
					}
					// [SEDES]
					if ("sedes".equals(fieldname)) {	
					    jParser.nextToken();
					    while (jParser.nextToken() != JsonToken.END_ARRAY) {	
					    	System.out.println("--- SEDES WHILE ---");
					    	if ("id".equals(jParser.getCurrentName())) {
					    		contaSedes++;
					    		SedeBean sede = new SedeBean();
					    		// 1-id
					    		jParser.nextToken();	
					    		sede.setId(jParser.getText());
					    		jParser.nextToken();
					    		// 2-nombre
					    		jParser.nextToken();	
					    		sede.setNombre(jParser.getText());
					    		jParser.nextToken();
					    		// 3-latitud
					    		jParser.nextToken();	
					    		sede.setLatitud(jParser.getText());
					    		jParser.nextToken();
					    		// 4-longitud
					    		jParser.nextToken();	
					    		sede.setLongitud(jParser.getText());
					    		jParser.nextToken();
					    		// 5-empresa
					    		jParser.nextToken();	
					    		sede.setEmpresa(jParser.getText());
					    		jParser.nextToken();
					    		// 6-telefono
					    		jParser.nextToken();	
					    		sede.setTelefono(jParser.getText());
					    		jParser.nextToken();
					    		// 7-radioRelevancia
					    		jParser.nextToken();	
					    		sede.setRadioRelevancia(jParser.getText());
					    		jParser.nextToken();
					    		// 8-principal
					    		jParser.nextToken();	
					    		sede.setPrincipal(jParser.getText());
					    		jParser.nextToken();
					    		// 9-flagBaja
					    		jParser.nextToken();	
					    		sede.setFlagBaja(jParser.getText());
					    		
//					    		SedeController.getInstance().actualizarSede(sede);
					    		
					    		listaSedes.add(sede);
				    			
				    			contadorProceso++;
				    			double proceso = ((double) contadorProceso / (double) contadorMaestro) * 100;
				    			int porcentaje = (int) proceso;
				    			publishProgress(porcentaje);
					    	}			    
					    }		 
					}
					// [TARIFAS ZONAS]
					if ("tarifasZonas".equals(fieldname)) {	
					    jParser.nextToken();
					    while (jParser.nextToken() != JsonToken.END_ARRAY) {
					    	System.out.println("--- TARIFIAS WHILE ---");
					    	if ("id".equals(jParser.getCurrentName())) {
					    		contaTarifasZonas++;
					    		TarifaZonaBean tarifaZona = new TarifaZonaBean();
					    		// 1-id
					    		jParser.nextToken();
					    		tarifaZona.setId(jParser.getText());
					    		jParser.nextToken();
					    		// 2-zonaOrigen
					    		jParser.nextToken();
					    		tarifaZona.setZonaOrigen(jParser.getText());
					    		jParser.nextToken();
					    		// 3-zonaDestino
					    		jParser.nextToken();
					    		tarifaZona.setZonaDestino(jParser.getText());
					    		jParser.nextToken();
					    		// 4-tarifa
					    		jParser.nextToken();
					    		tarifaZona.setTarifa(jParser.getText());
					    		jParser.nextToken();
					    		// 5-sede
					    		jParser.nextToken();
					    		tarifaZona.setSede(jParser.getText());
					    		jParser.nextToken();
					    		// 6-empresa
					    		jParser.nextToken();
					    		tarifaZona.setEmpresa(jParser.getText());
					    		
//					    		TarifaZonaController.getInstance().actualizarTarifaZona(tarifaZona);
					    		
					    		listaTarifasZonas.add(tarifaZona);
				    			
				    			contadorProceso++;
				    			double proceso = ((double) contadorProceso / (double) contadorMaestro) * 100;
				    			int porcentaje = (int) proceso;
				    			publishProgress(porcentaje);
					    	}			    
					    }		 
					}
					// [CENTRO COSTOS]
					if ("centroCostos".equals(fieldname)) {	
					    jParser.nextToken();
					    while (jParser.nextToken() != JsonToken.END_ARRAY) {	
					    	System.out.println("--- CENTRO DE COSTOS ---");
					    	if ("id".equals(jParser.getCurrentName())) {
					    		contaCentroCostos++;
					    		CentroCostoBean centroCosto = new CentroCostoBean();
					    		// 1-id					    		
					    		jParser.nextToken();	
					    		centroCosto.setId(jParser.getText());
					    		jParser.nextToken();
					    		// 2-nombre					    		
					    		jParser.nextToken();	
					    		centroCosto.setNombre(jParser.getText());
					    		jParser.nextToken();
					    		// 3-descripcion					    		
					    		jParser.nextToken();	
					    		centroCosto.setDescripcion(jParser.getText());
					    		jParser.nextToken();
					    		// 4-empresa					    		
					    		jParser.nextToken();	
					    		centroCosto.setEmpresa(jParser.getText());
					    		jParser.nextToken();
					    		// 5-flagBaja					    		
					    		jParser.nextToken();	
					    		centroCosto.setFlagBaja(jParser.getText());
					    		
//					    		CentroCostoController.getInstance().actualizarCentroCosto(centroCosto);
					    		
					    		listaCentroCostos.add(centroCosto);
				    			
				    			contadorProceso++;
				    			double proceso = ((double) contadorProceso / (double) contadorMaestro) * 100;
				    			int porcentaje = (int) proceso;
				    			publishProgress(porcentaje);
					    	}			    
					    }		 
					}
					// FECHA SINCRONIZACION
					if ("fechaSincronizacion".equals(fieldname)) {
						jParser.nextToken();
						String fechaSincronizacion = jParser.getText();
						System.out.println("FECHA SINCRONIZACION: " + fechaSincronizacion);
						
						// Grabamos la fecha de sincronización
						SharedPreferences prefs = getSharedPreferences("login", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = prefs.edit();
						editor.putString("fechaSin", fechaSincronizacion);
						editor.commit();
					}
				}
				jParser.close();
				
			} catch (JsonGenerationException e) {
				 
	     		e.printStackTrace();
			 
		    } catch (JsonProcessingException e) {
		 
				e.printStackTrace();
			 
		    } catch (IOException e) {
			 
				e.printStackTrace();
			 
		    }
			
			System.out.println("---SINCRONIZACION CONTADORES---");
			System.out.println("[EMPRESAS] = " + contaEmpresas);
			System.out.println("[PASAJEROS] = " + contaPasajeros);
			System.out.println("[ZONAS] = " + contaZonas);
			System.out.println("[SEDES] = " + contaSedes);
			System.out.println("[TARIFAS ZONAS] = " + contaTarifasZonas);
			System.out.println("[CENTROS COSTOS] = " + contaCentroCostos);
			System.out.println("-------------------------------\n");
			
			System.out.println("---SINCRONIZACION CONTADORES---");
			System.out.println("[EMPRESAS] = " + listaEmpresas.size());
			System.out.println("[PASAJEROS] = " + listaPasajeros.size());
			System.out.println("[ZONAS] = " + listaZonas.size());
			System.out.println("[SEDES] = " + listaSedes.size());
			System.out.println("[TARIFAS ZONAS] = " + listaTarifasZonas.size());
			System.out.println("[CENTROS COSTOS] = " + listaCentroCostos.size());
			System.out.println("-------------------------------\n");
			
//			System.out.println("---SINCRONIZACION SQLITE---");
//			System.out.println("[EMPRESAS] = " + EmpresaBean.tableHelper.countAllRows());
//			System.out.println("[PASAJEROS] = " + PasajeroBean.tableHelper.countAllRows());
//			System.out.println("[ZONAS] = " + ZonaBean.tableHelper.countAllRows());
//			System.out.println("[SEDES] = " + SedeBean.tableHelper.countAllRows());
//			System.out.println("[TARIFAS ZONAS] = " + TarifaZonaBean.tableHelper.countAllRows());
//			System.out.println("[CENTROS COSTOS] = " + CentroCostoBean.tableHelper.countAllRows());
//			System.out.println("-------------------------------\n");
						
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			Dialog.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Dialog.dismiss(); 
			
			Toast.makeText(getApplicationContext(), "Bienvenido: " + ServicioWorkingSet.nombreConductor, Toast.LENGTH_SHORT).show();
			Intent i = new Intent(LoginActivity.this, EstadoActivity.class);
			startActivity(i);
			finish();			

		}
		
	}

	@Override
	public void onBackPressed() {
		System.out.println("FIN APLICACION");
		GCMRegistrar.unregister(getApplicationContext());
		stopService(new Intent(LoginActivity.this, TrackingService.class));
		finish();
	}
}
