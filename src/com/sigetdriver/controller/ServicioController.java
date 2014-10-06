package com.sigetdriver.controller;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dsbmobile.dsbframework.controller.persistence.Entity;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.sigetdriver.ServerConstants;
import com.sigetdriver.ServicioWorkingSet;
import com.sigetdriver.SigetDriver;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.util.AppController;
import com.sigetdriver.util.SigetDriverUtil;
import com.sigetdriver.view.activity.LoginActivity;

public class ServicioController {

	public static ServicioController instance;

	public static ServicioController getInstance() {
		if (instance == null) {
			instance = new ServicioController();
		}
		return instance;
	}

	static ArrayList<ServicioBean> listaServicios = new ArrayList<ServicioBean>();

	public ArrayList<ServicioBean> obtenerServiciosPendientes() {

		if (listaServicios.size() > 0) {
			listaServicios.clear();
			descargarServicio("96");
		}

		ArrayList<Entity> entities = ServicioBean.tableHelper.getEntities(
				ServicioBean.ESTADO_COLUMN_NAME + "=? OR "
						+ ServicioBean.ESTADO_COLUMN_NAME + "=?", new String[] {
						ServicioBean.ESTADO_PENDIENTE,
						ServicioBean.ESTADO_ACTIVO });

		for (Entity entity : entities) {
			listaServicios.add((ServicioBean) entity);
		}

		// ORDENAR POR MAS RECIENTE
		int len = listaServicios.size();
		
		System.out.println("TAMAÑO: " + len);
		for (int i = (len - 1); i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (Long.parseLong(listaServicios.get(j).getFecha()) > Long
						.parseLong(listaServicios.get(j + 1).getFecha())) {
					ServicioBean aux = listaServicios.get(j);
					listaServicios.set(j, listaServicios.get(j + 1));
					listaServicios.set(j + 1, aux);
				}
			}
		}

		return listaServicios;
	}

	static ArrayList<PuntoBean> listaPuntos = new ArrayList<PuntoBean>();

	public ArrayList<PuntoBean> obtenerPuntosPorServicio(String idServicio) {

		if (listaPuntos.size() > 0) {
			listaPuntos.clear();
		}

		ArrayList<Entity> entities = PuntoBean.tableHelper.getEntities(
				ServicioBean.ID_SERVICIO_COLUMN_NAME + "=?",
				new String[] { idServicio });

		for (Entity entity : entities) {
			listaPuntos.add((PuntoBean) entity);
		}

		return listaPuntos;
	}

	public boolean actualizarServicio(ServicioBean servicio) {
		SigetDriver.getDB().beginTransaction();
		try {
			long value = ServicioBean.tableHelper.updateEntity(servicio,
					ServicioBean.ID_SERVICIO_COLUMN_NAME + "=?",
					new String[] { servicio.getIdServicio() });

			// Falta grabar los puntos
			SigetDriver.getDB().setTransactionSuccessful();
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
		SigetDriver.getDB().endTransaction();
		return true;
	}

	public ArrayList<ServicioBean> descargarServicio(String id) {

		final ArrayList<ServicioBean> listServicio = new ArrayList<ServicioBean>();

		int resultServicio = ServicioBean.tableHelper.deleteEntity(
				ServicioBean.ID_SERVICIO_COLUMN_NAME + "=?",
				new String[] { id });
		int resultPunto = PuntoBean.tableHelper.deleteEntity(
				PuntoBean.ID_SERVICIO_COLUMN_NAME + "=?", new String[] { id });
		String URL = ServerConstants.SERVER_IP + ServerConstants.SERVICIO + id;

		System.out.println("---URL---\n" + URL);
		// String respuesta = SigetDriverUtil.connect(URL);
		String respuesta = SigetDriverUtil
				.connect(URL);
		System.out.println("---RESPUESTA---\n" + respuesta);

		try {

			JSONObject jsonPadre = new JSONObject(respuesta);
			// Servicio
			ServicioBean servicio = new ServicioBean();
			servicio.setIdServicio(jsonPadre.getString("numReserva"));
			String fecha = jsonPadre.getString("fechaServicio");
			String hora = jsonPadre.getString("fechaServicio");
			// Date dateFecha = new Date(Long.parseLong(fecha));
			// Date dateHora = new Date(Long.parseLong(hora));
			// DateFormat formatFecha = new SimpleDateFormat("dd/MM/yy");
			// DateFormat formatHora = new SimpleDateFormat("HH:mm");
			servicio.setFecha(fecha);
			servicio.setHora(hora);
			servicio.setEmpresa(jsonPadre.getString("empresaRazonSocial"));
			servicio.setPasajero(jsonPadre.getString("pasajeroNombres") + " "
					+ jsonPadre.getString("pasajeroApellidos"));
			servicio.setCelular(jsonPadre.getString("pasajeroTelefonos"));
			servicio.setObservaciones(jsonPadre.getString("observacion"));
			servicio.setCodigo(jsonPadre.getString("codigoVerificacion"));

			servicio.setEstado(ServicioBean.ESTADO_PENDIENTE);
			servicio.setEnviado(ServicioBean.ENVIADO_SI);
			servicio.setAceptado(ServicioBean.ACEPTADO_NO);
			servicio.setTipoPago(ServicioBean.TIPO_PAGO_CREDITO);
			servicio.setTipoServicio(ServicioBean.TIPO_SERVICIO_PTO_A_PTO);
			ServicioBean.tableHelper.insertEntity(servicio);

			listServicio.add(servicio);

			int orden = 1;

			// Punto Origen
			PuntoBean puntoOrigen = new PuntoBean();
			puntoOrigen.setIdServicio(jsonPadre.getString("numReserva"));
			puntoOrigen.setLatitudPunto(jsonPadre.getString("origenLatitud"));
			puntoOrigen.setLongitudPunto(jsonPadre.getString("origenLongitud"));
			puntoOrigen.setDireccion(jsonPadre.getString("origenDireccion"));
			puntoOrigen.setZona(jsonPadre.getString("origenZonaNombre"));
			puntoOrigen.setOrden(String.valueOf(orden));
			PuntoBean.tableHelper.insertEntity(puntoOrigen);

			orden = orden + 2;

			// Destinos
			JSONArray destinos = jsonPadre.getJSONArray("destinos");
			for (int i = 0; i < destinos.length(); i++) {
				JSONObject destino = (JSONObject) destinos.get(i);
				PuntoBean puntoDestino = new PuntoBean();
				puntoDestino.setIdServicio(jsonPadre.getString("numReserva"));
				puntoDestino.setLatitudPunto(destino.getString("puntoLatitud"));
				puntoDestino.setLongitudPunto(destino
						.getString("puntoLongitud"));
				puntoDestino.setDireccion(destino.getString("puntoDireccion"));
				puntoDestino.setZona(destino.getString("puntoZonaNombre"));
				puntoDestino.setOrden(String.valueOf(orden));
				orden = orden + 2;
				PuntoBean.tableHelper.insertEntity(puntoDestino);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("ERROR", "Se cayo " + listServicio + " " + "");
		}

		return listServicio;
	}

	public static boolean crearDatosPrueba() {

		// REGISTROS DE PRUEBA
		ServicioBean.tableHelper.deleteAllEntities();
		PuntoBean.tableHelper.deleteAllEntities();

		ServicioBean servicio1 = new ServicioBean();
		servicio1.setIdServicio("101");
		servicio1.setFecha("1404833700000");
		servicio1.setHora("1404833700000");
		servicio1.setEmpresa("Rimac Seguros");
		servicio1.setPasajero("Pedro Rojas");
		servicio1.setCelular("993145254");
		servicio1.setObservaciones("Estacionarse en la puerta lateral");
		servicio1.setCodigo("12345");
		servicio1.setEstado(ServicioBean.ESTADO_PENDIENTE);
		servicio1.setEnviado(ServicioBean.ENVIADO_SI);
		servicio1.setAceptado(ServicioBean.ACEPTADO_NO);
		servicio1.setTipoPago(ServicioBean.TIPO_PAGO_CREDITO);
		servicio1.setTipoServicio(ServicioBean.TIPO_SERVICIO_PTO_A_PTO);
		ServicioBean.tableHelper.insertEntity(servicio1);

		PuntoBean punto1 = new PuntoBean();
		punto1.setIdServicio("101");
		punto1.setLatitudPunto("-12.092685826249703");
		punto1.setLongitudPunto("-77.02428131946414");
		punto1.setDireccion("Cl. Las Begonias #138");
		punto1.setZona("San Isidro");
		punto1.setOrden("1");
		punto1.setTarifa("10");
		PuntoBean.tableHelper.insertEntity(punto1);

		PuntoBean punto2 = new PuntoBean();
		punto2.setIdServicio("101");
		punto2.setLatitudPunto("-12.065780912063525");
		punto2.setLongitudPunto("-77.09542959579306");
		punto2.setDireccion("Av. Parque de las Leyendas #184");
		punto2.setZona("San Miguel");
		punto2.setOrden("3");
		punto2.setTarifa("10");
		PuntoBean.tableHelper.insertEntity(punto2);

		ServicioBean servicio2 = new ServicioBean();
		servicio2.setIdServicio("102");
		servicio2.setFecha("1404920100000");
		servicio2.setHora("1404920100000");
		servicio2.setEmpresa("Interbank");
		servicio2.setPasajero("Aurelio Gomez");
		servicio2.setCelular("993254145");
		servicio2.setObservaciones("Puerta principal");
		servicio2.setCodigo("12345");
		servicio2.setEstado(ServicioBean.ESTADO_PENDIENTE);
		servicio2.setEnviado(ServicioBean.ENVIADO_SI);
		servicio2.setAceptado(ServicioBean.ACEPTADO_NO);
		servicio2.setTipoPago(ServicioBean.TIPO_PAGO_CREDITO);
		servicio2.setTipoServicio(ServicioBean.TIPO_SERVICIO_PTO_A_PTO);
		ServicioBean.tableHelper.insertEntity(servicio2);

		PuntoBean punto3 = new PuntoBean();
		punto3.setIdServicio("102");
		punto3.setLatitudPunto("-12.092685826249703");
		punto3.setLongitudPunto("-77.02428131946414");
		punto3.setDireccion("Cl. Las Begonias #138");
		punto3.setZona("San Isidro");
		punto3.setOrden("1");
		punto3.setTarifa("10");
		PuntoBean.tableHelper.insertEntity(punto3);

		PuntoBean punto4 = new PuntoBean();
		punto4.setIdServicio("102");
		punto4.setLatitudPunto("-12.065780912063525");
		punto4.setLongitudPunto("-77.09542959579306");
		punto4.setDireccion("Av. Parque de las Leyendas #184");
		punto4.setZona("San Miguel");
		punto4.setOrden("3");
		punto4.setTarifa("10");
		PuntoBean.tableHelper.insertEntity(punto4);

		ServicioBean servicio3 = new ServicioBean();
		servicio3.setIdServicio("103");
		servicio3.setFecha("1404747300000");
		servicio3.setHora("1404747300000");
		servicio3.setEmpresa("Ripley");
		servicio3.setPasajero("Juan Vargas");
		servicio3.setCelular("993981145");
		servicio3.setObservaciones("Puerta posterior, en el estacionamiento");
		servicio3.setCodigo("12345");
		servicio3.setEstado(ServicioBean.ESTADO_PENDIENTE);
		servicio3.setEnviado(ServicioBean.ENVIADO_SI);
		servicio3.setAceptado(ServicioBean.ACEPTADO_NO);
		servicio3.setTipoPago(ServicioBean.TIPO_PAGO_CREDITO);
		servicio3.setTipoServicio(ServicioBean.TIPO_SERVICIO_PTO_A_PTO);
		ServicioBean.tableHelper.insertEntity(servicio3);

		PuntoBean punto5 = new PuntoBean();
		punto5.setIdServicio("103");
		punto5.setLatitudPunto("-12.092685826249703");
		punto5.setLongitudPunto("-77.02428131946414");
		punto5.setDireccion("Cl. Las Begonias #138");
		punto5.setZona("San Isidro");
		punto5.setOrden("1");
		punto5.setTarifa("10");
		PuntoBean.tableHelper.insertEntity(punto5);

		PuntoBean punto6 = new PuntoBean();
		punto6.setIdServicio("103");
		punto6.setLatitudPunto("-12.065780912063525");
		punto6.setLongitudPunto("-77.09542959579306");
		punto6.setDireccion("Av. Parque de las Leyendas #184");
		punto6.setZona("San Miguel");
		punto6.setOrden("3");
		punto6.setTarifa("10");
		PuntoBean.tableHelper.insertEntity(punto6);

		return true;

	}

	public boolean verificarLogin() {
		if (ServicioWorkingSet.codigoUnidad == null
				|| ServicioWorkingSet.nombreConductor == null
				|| ServicioWorkingSet.usuarioLogin == null
				|| ServicioWorkingSet.passwordLogin == null) {
			return false;
		} else {
			return true;
		}
	}

}
