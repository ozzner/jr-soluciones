package com.sigetdriver;

import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.sigetdriver.controller.ServicioController;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.view.activity.EstadoActivity;

public class GCMIntentService extends GCMBaseIntentService {
	
	private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        //super(SENDER_ID);
    }

	@Override
	protected void onError(Context arg0, String arg1) {
		
	}

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("mensaje"); 
        System.out.println("MENSAJE:\n"+message);
        try {
        	JSONObject mensaje = new JSONObject(message);
        	String estado = mensaje.getString("estado");
        	String numReserva = mensaje.getString("numReserva");
        	
        	System.out.println("---ESTADO = "+estado);
        	System.out.println("---NUM RESERVA = "+numReserva);
        	
        	if (estado.equals("1")) {
        		ServicioController.getInstance().descargarServicio(numReserva);
        		generateNotification(context, "Nuevo Servicio Asignado");
        	} else {
        		ServicioBean.tableHelper.deleteEntity(ServicioBean.ID_SERVICIO_COLUMN_NAME + "=?", new String[] { numReserva } );
        		generateNotification(context, "Servicio Desasignado");
        	}
        	
        	System.out.println("------");
        	System.out.println("getIdServicio: "+ServicioWorkingSet.servicio.getIdServicio());
        	System.out.println("puntoActual: "+ServicioWorkingSet.puntoActual);
        	System.out.println("puntoFinal: "+ServicioWorkingSet.puntoFinal);
        	System.out.println("primerServicio: "+ServicioWorkingSet.primerServicio);
        	System.out.println("recibirServicios: "+ServicioWorkingSet.recibirServicios);
        	System.out.println("codigoUnidad: "+ServicioWorkingSet.codigoUnidad);
        	System.out.println("nombreConductor: "+ServicioWorkingSet.nombreConductor);
        	System.out.println("usuarioLogin: "+ServicioWorkingSet.usuarioLogin);
        	System.out.println("passwordLogin: "+ServicioWorkingSet.passwordLogin);
        	System.out.println("------");
        	
        } catch (Exception e) {
        	System.out.println("ERROR JSON PUSH");
        }
    }

	@Override
	protected void onRegistered(Context context, String arg1) {
		System.out.println("onRegistered");
	}

	@Override
	protected void onUnregistered(Context context, String arg1) {
		System.out.println("onUnregistered");
	}
	
	/**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
         
        String title = context.getString(R.string.app_name);
         
        Intent notificationIntent = new Intent(context, EstadoActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
         
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
         
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification); 
    }

}
