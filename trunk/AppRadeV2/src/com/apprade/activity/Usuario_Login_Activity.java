package com.apprade.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.dao.DAO_Usuario;
import com.apprade.entity.Entity_Ranking;
import com.apprade.helper.Helper_SharedPreferences;
import com.apprade.helper.Helper_SubRoutines;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;




public class Usuario_Login_Activity extends FragmentActivity implements
		ValidationListener, ConnectionCallbacks, OnConnectionFailedListener {

	private Validator validator;
	@TextRule(order = 2, minLength = 3, message = "Ingrese min 3 caracteres.")
	@Required(order = 1, message = "Este campo es requerido.")
	private EditText password, email;
	private Button btnLogin;
	private DAO_Usuario dao;
	public Entity_Ranking rank;
	private String sEmail = "", sPassword = "";
	private ActionBar actionBar;
	private ProgressDialog proDialogo;
	private Helper_SubRoutines oRoutine;
	private static final String TAG_VACIO = "";
	private String personName;
	private String personUserID;
	private String personBirthday;
	private int personGender;
	private String sSex;

	private final String TAG = Usuario_Login_Activity.class.getSimpleName();

	/******** GOOGLE+ ********/
	private GoogleApiClient googleApiClient;
	private SignInButton btnSignIn;
	private ConnectionResult connectionResult;
	private boolean intentInProgress;
	private boolean signInClicked;
	private static final int RC_SIGN_IN = 0;
	private Button btn_temp;



	// private SharedPreferences mPrefs;

	public Usuario_Login_Activity() {
		super();
		oRoutine = new Helper_SubRoutines();
		dao = new DAO_Usuario();
		rank = new Entity_Ranking();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_login);

		/* Inicializing views */
		email = (EditText) findViewById(R.id.txtEmail);
		password = (EditText) findViewById(R.id.txtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
		btn_temp = (Button) findViewById(R.id.btn_logout);

		/* Setting text */
		email.setText(getIntent().getStringExtra("correo"));
		password.setText(getIntent().getStringExtra("password"));

		// Initializing google plus api client
		googleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();


		validator = new Validator(this);
		validator.setValidationListener(this);

		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showMyToast("Login google+");
				googleApiClient.connect();

				if (!googleApiClient.isConnecting()) {
					signInClicked = true;
					resolveSignInError();
				}
			}
		});
		

		btn_temp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// signOutFromGplus();
				showMyToast("Login facebook.");
			}
		});

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				validator.validate();
			}
		});
	}

	public void showMyToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
	}

	protected boolean validarCampos() {
		boolean esError = false;

		sEmail = email.getText().toString();
		sPassword = password.getText().toString();

		if (sEmail.compareTo("") == 0) {
			email.setError("Debes ingresar un Correo");

		} else if (sPassword.compareTo("") == 0) {
			password.setError("Debes ingresar un Password");

		} else {
			esError = true;
		}

		return esError;

	}

	protected void showDialogo() {
		proDialogo = new ProgressDialog(Usuario_Login_Activity.this);
		proDialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialogo.setCancelable(false);
		proDialogo.setMessage("Conectando...");
		proDialogo.show();
	}

	protected void llamarMapa() {

		Intent mapa = new Intent(getApplicationContext(),
				App_GPSMapa_Activity.class);
		Log.e("oUserID - login", dao.oUsuario.getUsuarioID() + "");
		mapa.putExtra("user_id", dao.oUsuario.getUsuarioID());
		mapa.putExtra("user", dao.oUsuario.getNombre());
		startActivity(mapa);
		finish();

	}

	class TaskHttpMethodAsync extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;

			sEmail = email.getText().toString();
			sPassword = password.getText().toString();

			if (dao.loginUsuario(sEmail, sPassword, 1))
				bRequest = true;

			return bRequest;
		}

		@Override
		protected void onPreExecute() {

			showDialogo();

			proDialogo.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					TaskHttpMethodAsync.this.cancel(true);
				}
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
				// oShared.getAllLoginDataStored(getApplicationContext());

				oShared.storeLogin(dao.oUsuario.getNombre(),
						dao.oUsuario.getEmail(), dao.oUsuario.getUsuarioID(),
						1, getApplicationContext());

			} else {
				Toast.makeText(
						getApplicationContext(),
						dao.oJsonStatus.getMessage() + ". "
								+ dao.oJsonStatus.getInfo() + ".",
						Toast.LENGTH_SHORT).show();
				password.setText(TAG_VACIO);
				email.setText(TAG_VACIO);
			}
		}

		@Override
		protected void onCancelled() {
		}

	}// End ClassAsync

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		actionBar = getActionBar();
		switch (item.getItemId()) {

		case R.id.log_login_action:
			actionBar.setSubtitle("Login");
			validator.validate();
			break;

		case R.id.log_about_action:

			actionBar.setSubtitle("Acerca de");
			LoadInfo();

			break;

		case R.id.log_registro_action:

			Intent i = new Intent(getApplicationContext(),
					Usuario_Registro_Activity.class);
			startActivity(i);
			finish();
			break;

		default:
			break;
		}

		return true;
	}

	private void LoadInfo() {
		final View v;
		AlertDialog.Builder adInfo = new AlertDialog.Builder(
				Usuario_Login_Activity.this);

		LayoutInflater layInfo = this.getLayoutInflater();
		v = layInfo.inflate(R.layout.dialog_custom_about, null);
		adInfo.setView(v);

		adInfo.setNeutralButton("Okay", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

			}
		});

		adInfo.show();
	}

	public void tvRegistrar_onClick(View v) {

		Intent i = new Intent(this, Usuario_Registro_Activity.class);
		startActivity(i);
		finish();
	}

	@Override
	public void onValidationSucceeded() {

		if (oRoutine.isOnline(getApplicationContext()))
			new TaskHttpMethodAsync().execute();
		else
			Toast.makeText(getApplicationContext(),
					"Necesita tener conexión a Internet.", Toast.LENGTH_SHORT)
					.show();

	}

	@Override
	public void onValidationFailed(View failedView, Rule<?> failedRule) {

		String message = failedRule.getFailureMessage();

		if (failedView instanceof EditText) {
			failedView.requestFocus();
			((EditText) failedView).setError(message);
		} else {
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}
	}


	
	/******************* FACEBOOK LOGIN *****************/
//	public void loginToFacebook() {
//
//	}
//	
	
//	   private Session.StatusCallback statusCallback = new Session.StatusCallback() {
//		           @Override
//		           public void call(Session session, SessionState state,
//		                   Exception exception) {
//		               if (state.isOpened()) {
//		                   Log.d("MainActivity", "Facebook session opened.");
//		               } else if (state.isClosed()) {
//		                   Log.d("MainActivity", "Facebook session closed.");
//		               }
//		           }
//		       };

	
	
	
	
	
	
	
	
	
	/******************* GOOGLE+ LOGIN *****************/


	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!intentInProgress) {

			connectionResult = result;

			if (signInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
	}
	
	
	private void resolveSignInError() {

		if (connectionResult.hasResolution()) {
			try {
				intentInProgress = true;
				connectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				intentInProgress = false;
				googleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		signInClicked = false;
		showMyToast("Usuario conectado.");

		try {
			if (Plus.PeopleApi.getCurrentPerson(googleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(googleApiClient);
				personName = currentPerson.getDisplayName();
				sPassword = currentPerson.getId();
				personGender = currentPerson.getGender();
				personUserID = currentPerson.getId();
				personBirthday = currentPerson.getBirthday();
				sEmail = Plus.AccountApi.getAccountName(googleApiClient);

				if (personGender == 0) {
					sSex = "M";
				} else {
					sSex = "F";
				}

				if (personBirthday == null) {
					personBirthday = oRoutine
							.getCurrentTime(Helper_SubRoutines.TAG_FORMAT_DATE_MM);
				}

				Log.e(TAG, "Name: " + personName + ", email: " + sEmail
						+ ", cumpleaños " + personBirthday + ", gender: "
						+ personGender);

				new googlePlusRegisterAsync().execute();

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X

			} else {
				showMyToast("No hay información.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		googleApiClient.connect();
	}

	public void signOutFromGplus() {
		if (googleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(googleApiClient);
			googleApiClient.disconnect();
			googleApiClient.connect();
		}
	}

	protected void onStart() {
		super.onStart();
		// googleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		if (googleApiClient.isConnected()) {
			googleApiClient.disconnect();
		}
	}

	/* facebook */
//	   @Override
//       public void onResume() {
//           super.onResume();
//           uiHelper.onResume();
//       }
//    
//       @Override
//       public void onPause() {
//           super.onPause();
//           uiHelper.onPause();
//       }
//    
//       @Override
//       public void onDestroy() {
//           super.onDestroy();
//           uiHelper.onDestroy();
//       }

       
   	/* facebook and google+ */

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		
//		uiHelper.onActivityResult(requestCode, responseCode, intent);

		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				signInClicked = false;
			}

			intentInProgress = false;

			if (!googleApiClient.isConnecting()) {
				googleApiClient.connect();
			}
		}
	}
	
	
	/* facebook*/
	
	@Override
	    public void onSaveInstanceState(Bundle savedState) {
	        super.onSaveInstanceState(savedState);
//	        uiHelper.onSaveInstanceState(savedState);
	    }


	/******************* AsyncTask *****************/

	class googlePlusRegisterAsync extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;

			if (!dao.loginUsuario(sEmail, sPassword, 2)) {
				bRequest = dao.registarUsuario(sEmail, sSex, personName,
						sPassword, personBirthday);
			} else {
				bRequest = true;
			}

			return bRequest;
		}

		@Override
		protected void onPreExecute() {
			showDialogo();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialogo.dismiss();

			if (result) {
				Helper_SharedPreferences oShared = new Helper_SharedPreferences();
				// oShared.getAllLoginDataStored(getApplicationContext());

				oShared.storeLogin(dao.oUsuario.getNombre(),
						dao.oUsuario.getEmail(), dao.oUsuario.getUsuarioID(),
						1, getApplicationContext());
				llamarMapa();
			} else {
				showMyToast("Error. " + dao.oJsonStatus.getInfo());
			}

		}

	}



}
