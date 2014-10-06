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
	public static final String TAG_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss:SSS";
	public static final String TAG_FORMAT_SHORT = "yyyy-MM-dd HH:mm:ss";
	public static final String TAG_FORMAT_DATE_MM = "yyyy-MM-dd";
	public static final String TAG_FORMAT_DATE_MMM = "yyyy-MMM-dd";
	public static final String TAG_FORMAT_TIME = "HH:mm:ss";

	/**
	 * 
	 */
	public Helper_SubRoutines() {
		// TODO Auto-generated constructor stub
	}

	public String getCurrentTime(String Myformat) {
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(Myformat);
		String strDate = sdf.format(c.getTime());

		return strDate;
	}

	public String customDateConverter(String Mydate, String Myformat) {
		String inputStringDate = Mydate;
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date inputDate = null;
		
		try {
			inputDate = inputFormat.parse(inputStringDate);
		} catch (ParseException ex) {
		}

		SimpleDateFormat outputFormat = new SimpleDateFormat(
				Myformat);
		String outputStringDate = outputFormat.format(inputDate);

		return outputStringDate;
	}

	
	public int dateDiferent(String fecha_i, String fecha_f,
			String dias_horas_minutos_segundos) {

		String tiempo = dias_horas_minutos_segundos;
		Date date_i = null;
		Date date_f = null;
		long time = (long) 0.0;

		SimpleDateFormat formato = new SimpleDateFormat(TAG_FORMAT_SHORT);

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
	
	
	public void showToast(Context context, String sms) {
		Toast.makeText(context, sms, Toast.LENGTH_SHORT).show();
	}
	

}
