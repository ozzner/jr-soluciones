/**
 * 
 */
package com.apprade.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apprade.R;
import com.apprade.dao.DAO_Comentario;
import com.apprade.entity.Entity_Comentario;
import com.apprade.entity.Entity_Usuario;
import com.apprade.helper.Helper_SubRoutines;

/**
 * @author Julio
 *
 */
public class Usuario_Comentar_Activity extends Activity {

	private Button btnCancel;
	private ImageView ivEnviarComentario, ivEstado;
	private EditText etComentario;
	private DAO_Comentario dao;
	private ProgressDialog proDialogo, proDialog2;
	private String sNombre, sDireccion, sCola;
	private TextView tvNombreEsta, tvDistrito;
	private int iUsuarioID, iIdEstable;
	private Helper_SubRoutines oRoutine;
	private List<Entity_Comentario> oListaComment = new ArrayList<Entity_Comentario>();

	/**
	 * 
	 */
	public Usuario_Comentar_Activity() {
		super();
		dao = new DAO_Comentario();
		oRoutine = new Helper_SubRoutines();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_calificaciones);

		btnCancel = (Button) findViewById(R.id.btn_cancel_comen);
		ivEstado = (ImageView) findViewById(R.id.iv_estado);
		ivEnviarComentario = (ImageView) findViewById(R.id.iv_send);
		etComentario = (EditText) findViewById(R.id.et_comentario);
		tvDistrito = (TextView) findViewById(R.id.txt_distrito);
		tvNombreEsta = (TextView) findViewById(R.id.txt_nom_establecimiento);

		Bundle oBundle = getIntent().getExtras();

		iUsuarioID = oBundle.getInt("usuarioID");
		iIdEstable = oBundle.getInt("establecimientoID");
		sNombre = oBundle.getString("nomEstablecimiento");
		sDireccion = oBundle.getString("direccion");
		sCola = oBundle.getString("cola");

		switch (sCola) {

		case "Alta cola":
			ivEstado.setImageResource(R.drawable.img4);
			break;

		case "Cola moderada":
			ivEstado.setImageResource(R.drawable.img3);
			break;

		case "Poca cola":
			ivEstado.setImageResource(R.drawable.img2);
			break;

		case "No hay cola":
			ivEstado.setImageResource(R.drawable.img1);
			break;

		default:
			ivEstado.setImageResource(R.drawable.img1);
			break;
		}

		tvDistrito.setText(sDireccion);
		tvNombreEsta.setText(sNombre.toUpperCase());

		btnCancel
				.setOnClickListener((android.view.View.OnClickListener) cancel_button_click_listener);

		exeHttpAsync2();

		ivEnviarComentario.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (!validarComentario()) 
					exeHttpAsync();
				else{
					oRoutine.showToast(getApplicationContext(), "Escriba un comentario");
				}
			}
		});

		ivEstado
				.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						if (!validarComentario()) 
							exeHttpAsync();
						else{
							oRoutine.showToast(getApplicationContext(), "Escriba un comentario");
						}
						return false;
					}
				});

	}

	protected void showDialogo() {
		proDialogo = new ProgressDialog(Usuario_Comentar_Activity.this);
		proDialogo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		proDialogo.setMessage("Comentando...");
		proDialogo.show();
	}

	protected void exeHttpAsync() {

		AsyncTaskEnviarComentario task = new AsyncTaskEnviarComentario();
		task.execute();
	}

	class AsyncTaskEnviarComentario extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;
			String sComentario = etComentario.getText().toString();

			if (dao.insertarComentario(String.valueOf(iIdEstable), String.valueOf(iUsuarioID),
					sComentario))
				bRequest = true;

			return bRequest;
		}

		@Override
		protected void onPreExecute() {
			showDialogo();

			proDialogo.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					AsyncTaskEnviarComentario.this.cancel(true);
				}
			});
			proDialogo.setProgress(0);

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialogo.dismiss();

			if (result) {
				Toast.makeText(getApplicationContext(),
						dao.oJsonStatus.getMessage(), Toast.LENGTH_LONG).show();
				refreshComment();
				etComentario.setText("");
			} else {
				Toast.makeText(
						getApplicationContext(),
						dao.oJsonStatus.getMessage() + " Info: "
								+ dao.oJsonStatus.getInfo(), Toast.LENGTH_LONG)
						.show();
			}
		}

		@Override
		protected void onCancelled() {
			Toast.makeText(getApplicationContext(), "¡Proceso ocultado!",
					Toast.LENGTH_SHORT).show();
		}

	}// End ClassAs

	/* ASYNC TASK 2 */

	private void exeHttpAsync2() {
		TaskHttpMethodAsyncCargarComentarios task = new TaskHttpMethodAsyncCargarComentarios();
		task.execute();
	}

	class TaskHttpMethodAsyncCargarComentarios extends
			AsyncTask<String, Void, Boolean> {

		List<Entity_Comentario> lista_comentarios = new ArrayList<Entity_Comentario>();

		@Override
		protected Boolean doInBackground(String... params) {
			boolean bRequest = false;

			lista_comentarios = dao.listarTodoComentarioPorID(iIdEstable + "");

			bRequest = dao.oJsonStatus.getError_status();

			if (!bRequest) {
				oListaComment = lista_comentarios;
			}

			return bRequest;
		}

		@Override
		protected void onPreExecute() {

			showDialogo();

			proDialog2.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					TaskHttpMethodAsyncCargarComentarios.this.cancel(true);
					Toast.makeText(getApplicationContext(),
							"Se ha ocultado el proceso", Toast.LENGTH_SHORT)
							.show();
				}
			});
			proDialog2.setProgress(0);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			proDialog2.dismiss();

			if (!result) {
				populateListView();// Llena el listView
				Toast.makeText(getApplicationContext(), "¡Listo!",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Puede ser el 1ro en comentar! ", Toast.LENGTH_SHORT)
						.show();
			}
		}

		private void showDialogo() {

			proDialog2 = new ProgressDialog(Usuario_Comentar_Activity.this);
			proDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			proDialog2.setMessage("Cargando...");
			proDialog2.show();

		}
	}

	
	
	private boolean validarComentario(){
		boolean bResult= false;
		String sComentario = etComentario.getText().toString();
		
		if (sComentario.isEmpty() || sComentario==null) {
			bResult = true;
		}
		
		return bResult;
	}
	
	
	
	private void refreshComment() {

		new Thread(new Runnable() {
			boolean bRequest;

			public void run() {

				List<Entity_Comentario> lista_comentarios = new ArrayList<Entity_Comentario>();
				lista_comentarios = dao.listarTodoComentarioPorID(iIdEstable
						+ "");

				bRequest = dao.oJsonStatus.getError_status();

				if (!bRequest) {
					oListaComment = lista_comentarios;
				}

				runOnUiThread(new Runnable() {
					public void run() {
						populateListView();
					}
				});
			}
		}).start();

	}

	private void populateListView() {
		ArrayAdapter<Entity_Comentario> adapter = new CustomAdapter();
		ListView list = (ListView) findViewById(R.id.lv_comentarios);
		list.setAdapter(adapter);
	}

	private class CustomAdapter extends ArrayAdapter<Entity_Comentario> {

		public CustomAdapter() {
			super(Usuario_Comentar_Activity.this, R.layout.adapter_list_row,
					oListaComment);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(
						R.layout.adapter_list_row, parent, false);
			}

			Entity_Comentario currentComment = oListaComment.get(position);
			TextView tvUsuario = (TextView) itemView
					.findViewById(R.id.row_usuario);

			Entity_Usuario oUser = new Entity_Usuario();

			oUser = currentComment.getUsuario().get(position);
			String sNombre = oUser.getNombre();
			tvUsuario.setText(sNombre);

			TextView tvComment = (TextView) itemView
					.findViewById(R.id.row_comentario);
			tvComment.setText("" + currentComment.getMensaje());

			// Condition:
			TextView tvFecha = (TextView) itemView.findViewById(R.id.row_date1);
			TextView tvFecha2 = (TextView) itemView
					.findViewById(R.id.row_date2);
			tvFecha.setText(oRoutine.customDateConverter(
					currentComment.getFecha(), "yyyy-MMM-dd"));
			tvFecha2.setText(oRoutine.customDateConverter(
					currentComment.getFecha(),
					Helper_SubRoutines.TAG_FORMAT_TIME));
			return itemView;
		}
	}

	private OnClickListener cancel_button_click_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};

}
