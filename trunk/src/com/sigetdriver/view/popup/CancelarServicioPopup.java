package com.sigetdriver.view.popup;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sigetdriver.R;
import com.sigetdriver.view.activity.ServicioActivity;

public class CancelarServicioPopup {

	private Context context;
	public Dialog dialog;
	private RadioGroup rdgCancelado;
	private Button btnGrabar;
	
	public void dialog(final Context _context) {
			
		context = _context;
		dialog = new Dialog(_context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_cancelar);
		dialog.setCanceledOnTouchOutside(false);		
		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				
		rdgCancelado = (RadioGroup) dialog.findViewById(R.id.rdgCancelado);
				
		btnGrabar = (Button) dialog.findViewById(R.id.btnGrabar);
		btnGrabar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int checkedRadioButton = rdgCancelado.getCheckedRadioButtonId();
				switch (checkedRadioButton) {
				  case R.id.radioConductor: 
					  // Agregar logica cuando se seleccione Conductor
					  Toast.makeText(context, "Servicio Cancelado", Toast.LENGTH_SHORT).show();
					  dialog.dismiss();
					  ((ServicioActivity) context).finish();
       	              break;
				  case R.id.radioPasajero: 
					  // Agregar logica cuando se seleccione Pasajero
					  Toast.makeText(context, "Servicio Cancelado", Toast.LENGTH_SHORT).show();
					  dialog.dismiss();
					  ((ServicioActivity) context).finish();
                      break;
				  default:
					  Toast.makeText(context, "Debe seleccionar una opción", Toast.LENGTH_SHORT).show();
					  break;
				}				
			}
		});
		dialog.show();		
	}
	
}
