package com.sigetdriver.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dsbmobile.dsbframework.controller.persistence.DatabaseAdapter;

public class SigetDriverDatabaseAdapter extends DatabaseAdapter {
	
	public static SQLiteDatabase open(Context context, String databaseName, int databaseVersion) {
		return DatabaseAdapter.open(new SigetDriverDatabaseHelper(context, databaseName, databaseVersion));
	}

	public static void close() {
		DatabaseAdapter.close();
	}

}
