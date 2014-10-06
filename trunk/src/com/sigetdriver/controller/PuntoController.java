package com.sigetdriver.controller;

import java.util.ArrayList;

import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ServicioBean;


public class PuntoController {
	
	public static PuntoController instance;
	
	public static PuntoController getInstance() {
		if (instance == null) {
			instance = new PuntoController();
		}
		return instance;
	}
	
	public void actualizarPuntosPorServicio(ServicioBean servicio) {
		ArrayList<PuntoBean> puntos = servicio.getPuntos();
		eliminarPuntosPorServicio(servicio);
		for (PuntoBean punto : puntos) {
			PuntoBean.tableHelper.insertEntity(punto);
		}
	}
	
	private void eliminarPuntosPorServicio(ServicioBean servicio) {
		PuntoBean.tableHelper.deleteEntity(
				PuntoBean.ID_SERVICIO_COLUMN_NAME + "=?", 
				new String[] { servicio.getIdServicio() } );
	}

}
