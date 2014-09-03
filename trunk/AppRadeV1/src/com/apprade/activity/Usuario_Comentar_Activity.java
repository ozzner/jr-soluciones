/**
 * 
 */
package com.apprade.activity;


import com.apprade.R;



import com.apprade.activity.Usuario_Login_Activity.TaskHttpMethodAsync;
import com.apprade.dao.DAO_Comentario;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import android.widget.Toast;


/**
 * @author Julio
 *
 */
public class Usuario_Comentar_Activity extends Activity {

	private Button btnCancel;
	private ImageView ivEnviarComentario;
	private EditText etComentario;
	private DAO_Comentario dao;
	private ProgressDialog proDialogo;
	
	/**
	 * 
	 */
	public Usuario_Comentar_Activity() {
		super();
		dao = new DAO_Comentario();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup);
		
		btnCancel = (Button)findViewById(R.id.btn_cancel_comen);
	    ivEnviarComentario = (ImageView)findViewById(R.id.iv_enviar_comentario);
	    etComentario = (EditText)findViewById(R.id.et_comentario);
		
	    btnCancel.setOnClickListener((android.view.View.OnClickListener) cancel_button_click_listener);

	    ivEnviarComentario.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exeHttpAsync();
			}
		});

	}
	
	protected void showDialogo(){
		proDialogo = new ProgressDialog(Usuario_Comentar_Activity.this);
		proDialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialogo.setMessage("Comentando...");
		proDialogo.show();
	}


	protected void exeHttpAsync() {		
		TaskHttpMethodAsync task =  new TaskHttpMethodAsync();
		task.execute();		
	}
	
	class TaskHttpMethodAsync extends AsyncTask<String, Void,Boolean>{

	    @Override
	    protected Boolean doInBackground(String... params) {
				boolean bRequest = false;
				String sEstablecimientoID = etComentario.getText().toString();
						Log.e("CASO", sEstablecimientoID);
						
				if (dao.insertarComentario(sEstablecimientoID, "10", "Hola mundo")) 
					bRequest = true;
		
				return bRequest;
			}

	    @Override
	    protected void onPreExecute() {
	    	
	    	showDialogo();
	    	
	    	proDialogo.setOnCancelListener(new OnCancelListener() {
	    	
	        @Override
		    public void onCancel(DialogInterface dialog) {
		    	TaskHttpMethodAsync.this.cancel(true);  }
		    });
		    proDialogo.setProgress(0);
		   // pDialog.show();
	    }
	    
	    @Override
		protected void onPostExecute(Boolean result) {		
				super.onPostExecute(result);
				proDialogo.dismiss();
				
				if (result) {
					Toast.makeText(getApplicationContext(),"Bienvenid@_OK "+dao.oJsonStatus.getMessage(), Toast.LENGTH_LONG).show();					
				}else{
					Toast.makeText(getApplicationContext(),"Error: "+dao.oJsonStatus.getMessage()+" Info: "+dao.oJsonStatus.getInfo(),Toast.LENGTH_LONG).show();
				}
			}
	    
	    @Override
	    protected void onCancelled() {
	    Toast.makeText(getApplicationContext(), "Acción cancelada!",
	    Toast.LENGTH_SHORT).show();
	    }
				
	}//End ClassAs
	
	
	private OnClickListener cancel_button_click_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
	
		}
	};
	
	
	
	

}
