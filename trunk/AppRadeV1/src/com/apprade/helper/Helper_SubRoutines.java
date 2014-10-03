/**
 * 
 */
package com.apprade.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * @author renzo santillán
 *
 */
public class Helper_SubRoutines {
	
	public static final String TAG_DIAS = "dias";
	public static final String TAG_HORAS = "horas";
	public static final String TAG_MINUTOS = "minutos";
	public static final String TAG_SEGUNDOS = "segundos";

	/**
	 * 
	 */
	public Helper_SubRoutines() {
		// TODO Auto-generated constructor stub
	}

	public String getCurrentTime() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(c.getTime());

		return strDate;
	}

	public String getCurrentTimeShort() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(c.getTime());

		return strDate;
	}
	
	public void showToast(Context context, String sms) {
		Toast.makeText(context, sms, Toast.LENGTH_SHORT).show();
	}

	public int dateDiferent(String fecha_i, String fecha_f,
			String dias_horas_minutos_segundos) {
		
		String tiempo = dias_horas_minutos_segundos;
		Date date_i = null;
		Date date_f = null;
		long time = (long) 0.0;

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			date_i = formato.parse(fecha_i);
			date_f = formato.parse(fecha_f);

		} catch (ParseException e) {
			Log.e("TAG", "Funcion diferenciaFechas: Error Parse " + e);
		} catch (Exception e) {
			Log.e("TAG", "Funcion diferenciaFechas: Error " + e);
		}

		Calendar cal_i = Calendar.getInstance();
		Calendar cal_f = Calendar.getInstance();

		cal_i.setTime(date_i);
		cal_f.setTime(date_f);

		long ms_i = cal_i.getTimeInMillis();
		long ms_f = cal_f.getTimeInMillis();

		long result_ms = ms_f - ms_i;

		switch (tiempo) {
		case "segundos":
			time = Math.abs(result_ms / 1000);
			break;
		case "minutos":
			time = Math.abs(result_ms / (60 * 1000));
			break;
		case "horas":
			time = (result_ms / (60 * 60 * 1000));
			break;
		case "dias":
			 time = Math.abs(result_ms / (24 * 60 * 60 * 1000));
			break;
		default:
			time = result_ms;
			break;
		}

		return ((int) time);
	}

}
