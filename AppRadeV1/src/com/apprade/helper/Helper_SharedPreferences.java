package com.apprade.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Helper_SharedPreferences  {

	private SharedPreferences shaPreLogin;

	private static final String MyPREFERENCES = "Login";
	private static final int UserID = 103095;
	private static final String Email = "apprade@apprade.com";
	private static final int Status = -1;
	private Context contexto;
	
	
	public void run() {
		
		shaPreLogin = contexto.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		SharedPreferences.Editor editorLogin = shaPreLogin.edit();
		editorLogin.putString("email", Email);
		editorLogin.putInt("userID", UserID);
		editorLogin.putInt("status", Status);
		editorLogin.commit();

	}
	

	public String checkLogin(Context context) {
		String sChek = null;
		contexto=context;
		
		shaPreLogin = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		Log.e("Shared", shaPreLogin+"");
		
		if (!shaPreLogin.contains("userID")) {		
//			Log.e("USERID", shaPreLogin.contains("userID")+"");
			sChek = "registro";
			run();
				
		} else {
			int key = shaPreLogin.getInt("status", 0);
//			Log.e("KEY",key+"");
			
			switch (key) {
			case -1:
				sChek = "registro";
				break;
			case 0:
				sChek = "login";
				break;
			case 1:
				sChek = "mapa";
				break;
			default:
				sChek = "mapa";
				break;
			}
		}

		return sChek;
	}
	
	
	public void storeLogin(int status, String email, int userID) {

		shaPreLogin = contexto.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		SharedPreferences.Editor editorLogin = shaPreLogin.edit();
		editorLogin.putString("email", email);
		editorLogin.putInt("userID", userID);
		editorLogin.putInt("status", status);
		editorLogin.commit();

	}

}
