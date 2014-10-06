/**
 * 
 */
package com.apprade.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.activity.Usuario_Registro_Activity.TaskHttpMethodAsync;
import com.apprade.adapter.Adapter_ViewPage;
import com.apprade.dao.DAO_Usuario;
import com.apprade.entity.Entity_Ranking;
import com.apprade.helper.Helper_SharedPreferences;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.InitiateMatchResult;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.LinePageIndicator;

/**
 * @author renzo
 *
 */
public class Intro_Activity extends FragmentActivity {
	
	
	
	private ViewPager vp_Intro;
	private LinePageIndicator pi_Intros;
	private  ActionBar actionBar;
	private EditText password,email;	
	private Button btnLogin;
	private DAO_Usuario dao;
	public Entity_Ranking rank;
	private String sEmail="",sPassword="";
	private ProgressDialog proDialogo;
	private ImageButton ib;
	
	
	public Intro_Activity() {
		
		dao= new DAO_Usuario();
		rank = new Entity_Ranking();
		
		}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*PARTE 1*/
		setContentView(R.layout.activity_intro);
		vp_Intro = (ViewPager) findViewById(R.id.vp_intro);
		
		Adapter_ViewPage vpAdapter = new Adapter_ViewPage(getSupportFragmentManager());
		vp_Intro.setAdapter(vpAdapter);
		
		pi_Intros = (LinePageIndicator)findViewById(R.id.indicator);
		pi_Intros.setViewPager(vp_Intro);

		/*PARTE 2*/
		
//		email.setText(getIntent().getStringExtra("correo"));
//		password.setText(getIntent().getStringExtra("password"));

	}
	
	
	
	protected boolean validarCampos(){
		boolean esError=false;
		
		sEmail = email.getText().toString();
		sPassword = password.getText().toString();
		
		if(sEmail.compareTo("")==0){
			email.setError("Debes ingresar un Correo");
			esError=true;
    	}
		
		if(sPassword.compareTo("")==0){
			password.setError("Debes ingresar un Password");
			esError=true;}
    	
		
		return esError;
		
	}
	
	
	protected void showDialogo(){
		proDialogo = new ProgressDialog(Intro_Activity.this);
		proDialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialogo.setMessage("Conectando...");
		proDialogo.show();
	}

	
	protected void llamarMapa() {
		
		Intent mapa = new Intent(getApplicationContext(), App_GPSMapa_Activity.class);
		Log.e("oUserID - login", dao.oUsuario.getUsuarioID()+"");
		mapa.putExtra("user_id",dao.oUsuario.getUsuarioID());
		mapa.putExtra("user",dao.oUsuario.getNombre());
		startActivity(mapa);
		finish();
			
	}
	
	  class TaskHttpMethodAsync extends AsyncTask<String, Void,Boolean>{

		    @Override
		    protected Boolean doInBackground(String... params) {
					boolean bRequest = false;
			
					sEmail = email.getText().toString();
					sPassword = password.getText().toString();
					
					if (dao.loginUsuario(sEmail, sPassword)) 
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
						llamarMapa();
						Helper_SharedPreferences oShared = new Helper_SharedPreferences();
						oShared.getAllLoginDataStored(getApplicationContext());
						
						oShared.storeLogin(dao.oUsuario.getNombre(),dao.oUsuario.getEmail() ,dao.oUsuario.getUsuarioID(),1, getApplicationContext());
						
						
						String sUser = dao.oUsuario.getNombre();
						Toast.makeText(getApplicationContext(),"Bienvenid@_"+sUser, Toast.LENGTH_SHORT).show();

					}else{
						Toast.makeText(getApplicationContext(),dao.oJsonStatus.getMessage()+". "+dao.oJsonStatus.getInfo()+".",Toast.LENGTH_SHORT).show();
					}
				}
		    
		    @Override
		    protected void onCancelled() {
		    Toast.makeText(getApplicationContext(), "Acción cancelada!",
		    Toast.LENGTH_SHORT).show();
		    }
					
		}//End ClassAsync
	
	 @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	     MenuInflater inflater = getMenuInflater();
	     inflater.inflate(R.menu.registro_menu, menu);
	     
	     return true;
	   } 
	
		@Override
		   public boolean onOptionsItemSelected(MenuItem item) {
			final View v;
			  actionBar = getActionBar();

			  switch (item.getItemId()) {
		
			     case R.id.reg_login_action:
//					Intent login = new Intent(getApplicationContext(), Usuario_Login_Activity.class);
//					startActivity(login);
//					finish();
			    	 actionBar.setSubtitle("Login");
			    		 
			    	 AlertDialog.Builder alertDialog = new AlertDialog.Builder(Intro_Activity.this);
			 		LayoutInflater inflater = this.getLayoutInflater();
			 	     v = inflater.inflate(R.layout.dialog_custom_login, null);
			 		alertDialog.setView(v);

		    		
		    		
			 		/* When positive (yes/ok) is clicked */
					alertDialog.setPositiveButton("Iniciar!", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						
						email = (EditText)v.findViewById(R.id.txtEmail);
			    		password= (EditText)v.findViewById(R.id.txtPassword);
			    		
						if (!validarCampos()) 
				    	 new TaskHttpMethodAsync().execute();
						
					}
					});

					/* When negative (No/cancel) button is clicked*/
					alertDialog.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel(); 
					}
					});
					
					alertDialog.show();		
					
				    break;  
			     case R.id.reg_about_action:
				   actionBar.setSubtitle("About app");
			       break;      
			       
			     default:
			       break;
		     }

		     return true;
		   } 	
	

}
