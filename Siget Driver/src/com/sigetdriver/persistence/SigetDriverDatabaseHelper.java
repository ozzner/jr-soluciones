package com.sigetdriver.persistence;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dsbmobile.dsbframework.controller.persistence.DatabaseHelper;
import com.dsbmobile.dsbframework.controller.persistence.TableHelper;
import com.sigetdriver.entities.CentroCostoBean;
import com.sigetdriver.entities.EmpresaBean;
import com.sigetdriver.entities.PasajeroBean;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.SedeBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.entities.TarifaZonaBean;
import com.sigetdriver.entities.ZonaBean;

public class SigetDriverDatabaseHelper extends DatabaseHelper {

	private ArrayList<TableHelper> tableHelpers;
	
	public SigetDriverDatabaseHelper(Context context, String databaseName,
			int databaseVersion) {
		super(context, databaseName, databaseVersion);
		tableHelpers = new ArrayList<TableHelper>();
		tableHelpers.add(ServicioBean.tableHelper);
		tableHelpers.add(PuntoBean.tableHelper);
		tableHelpers.add(CentroCostoBean.tableHelper);
		tableHelpers.add(EmpresaBean.tableHelper);
		tableHelpers.add(PasajeroBean.tableHelper);
		tableHelpers.add(SedeBean.tableHelper);
		tableHelpers.add(TarifaZonaBean.tableHelper);
		tableHelpers.add(ZonaBean.tableHelper);
	}

	@Override
	public void executeCreates(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.beginTransaction();

		for (TableHelper tableHelper : tableHelpers) {
			db.execSQL(tableHelper.getCreateSentence());
		}
		db.setTransactionSuccessful();
		db.endTransaction();

	}

	@Override
	public void executeDrops(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.beginTransaction();
		for (TableHelper tableHelper : tableHelpers) {
			db.execSQL(tableHelper.getDropSentence());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}
	
}
