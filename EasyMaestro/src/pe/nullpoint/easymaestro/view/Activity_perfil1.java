package pe.nullpoint.easymaestro.view;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.internal.es;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pe.nullpoint.easymaestro.R;
import pe.nullpoint.easymaestro.controller.Controller_Comentario;
import pe.nullpoint.easymaestro.controller.Controller_Tecnico;
import pe.nullpoint.easymaestro.model.Model_Comentarios;
import pe.nullpoint.easymaestro.model.Model_Tecnico;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AnalogClock;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_perfil1 extends Activity {
	private final String TAG = Activity_perfil1.class.getSimpleName();

	private TextView tvCelular;
	private TextView tvNombre;
	private TextView tvEspecialidad;
	private RatingBar rtbCalificar;
	private ListView lsvComentarios;
	private ArrayAdapter<String> adapter;
	private ArrayList<Model_Comentarios> lista;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil_1);
		
		String nombre,direccion,comentario,profesion,dni;
		int celular,id;
		float calificacion;
		
		tvCelular = (TextView)findViewById(R.id.tv_celular);
		tvEspecialidad = (TextView)findViewById(R.id.tv_especialidades);
		tvNombre = (TextView)findViewById(R.id.tv_nombre);
		rtbCalificar = (RatingBar)findViewById(R.id.ratingBar1);
		lsvComentarios = (ListView)findViewById(R.id.lsv_comentarios);
		
		
		try {

			Bundle oBundle = getIntent().getExtras();
			id = oBundle.getInt("tecnicoID");
			celular = oBundle.getInt("celular");
			calificacion = oBundle.getFloat("calificacion");
			nombre = oBundle.getString("nombre");
			direccion = oBundle.getString("direccion");
			profesion = oBundle.getString("profesion");
			dni = oBundle.getString("dni");
			
			Log.e(TAG, "Datos->"+id + " - " + celular + " - "
					+ " " + calificacion + " - " + nombre + " - " 
					+ direccion+ " - " +profesion+ " - " + dni);
			
			rtbCalificar.setNumStars(5);
			rtbCalificar.setRating((float) calificacion);
			tvCelular.setText(String.valueOf(celular));
			tvEspecialidad.setText(profesion);
			tvNombre.setText(nombre);
			lsvComentarios = (ListView)findViewById(R.id.lsv_comentarios);
			
			cargarListaComentarios(id);
			
			
			
			
		} catch (Exception e) {
			Log.e(TAG,  "hay errorrr" + e.getMessage());
		}
		
			

		
	}
	
	
	private void cargarListaComentarios(final int tecnicoID){
		
		new Thread(new Runnable() {

			@Override
			public void run() {

				Controller_Comentario controller = new Controller_Comentario();
				 final List<String> listaCommentarios = new ArrayList<String>();
				
				lista = controller.getComentariosByTecnicoID(tecnicoID);
				
				for(int x=0;x<lista.size();x++){
					listaCommentarios.add(lista.get(x).getMensaje());
				}
				
				runOnUiThread(new Runnable() {
					
					@SuppressWarnings({ "rawtypes", "unchecked" })
					@Override
					public void run() {
						try {
							adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaCommentarios);
							lsvComentarios.setAdapter(adapter);
							
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "Error al cargar lista", Toast.LENGTH_SHORT).show();
						}
						
					}
				});
			}

		}).start();
	}
	

	
//	private void popularLista(List<String> lista){
//		adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);	
//	}

}
