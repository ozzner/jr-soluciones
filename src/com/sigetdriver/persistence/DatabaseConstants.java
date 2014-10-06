package com.sigetdriver.persistence;

import android.os.Environment;

public class DatabaseConstants {
	
	public static String DATABASE_NAME = "siget_driver";
	public static String DATABASE_PATH = Environment
			.getExternalStorageDirectory().toString();

	public static int DATABASE_VERSION = 5;

	public static final String SERVICIO_TABLE_NAME = "SERVICIO";
	public static final String PUNTO_TABLE_NAME = "PUNTO";
	public static final String VOUCHER_TABLE_NAME = "VOUCHER";
	public static final String EMPRESA_TABLE_NAME = "EMPRESA";
	public static final String PASAJERO_TABLE_NAME = "PASAJERO";
	public static final String ZONA_TABLE_NAME = "ZONA";
	public static final String SEDE_TABLE_NAME = "SEDE";
	public static final String TARIFA_ZONA_TABLE_NAME = "TARIFA_ZONA";
	public static final String CENTRO_COSTO_TABLE_NAME = "CENTRO_COSTO";

}
