package com.sigetdriver;

import android.content.Context;

import com.dsbmobile.dsbframework.DSBApplication;
import com.sigetdriver.persistence.DatabaseConstants;
import com.sigetdriver.persistence.SigetDriverDatabaseAdapter;

public class SigetDriver extends DSBApplication {
	
	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();

		context = getApplicationContext();
		SigetDriverDatabaseAdapter.open(context, DatabaseConstants.DATABASE_PATH
				+ "/" + DatabaseConstants.DATABASE_NAME,
				DatabaseConstants.DATABASE_VERSION);
		
	}
	
}
