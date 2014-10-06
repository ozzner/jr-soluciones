package com.sigetdriver.view.activity;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.sigetdriver.R;
import com.sigetdriver.entities.PuntoBean;
import com.sigetdriver.entities.ServicioBean;
import com.sigetdriver.util.AppController;
import com.sigetdriver.view.adapter.ServicioAdapter;

public class Servicio2Activity extends Activity {
	
	private static final String TAG = Servicio2Activity.class.getSimpleName();

	
	
	// Movies json url
	private static final String url = "http://api.androidhive.info/json/movies.json";
//	private static final String url = "http://54.69.22.121:8080/taxis/rest/ordenServicio/96";
	private ProgressDialog pDialog;
	private List<ServicioBean> serviceslist = new ArrayList<ServicioBean>();
	private ListView listView;
	private ServicioAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.listaServicios);
		adapter = new ServicioAdapter(this, 0, serviceslist);
		listView.setAdapter(adapter);

		pDialog = new ProgressDialog(this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();

		// changing action bar color
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1b1b1b")));

		// Creating volley request obj
		JsonArrayRequest movieReq = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						hidePDialog();

						// Parsing json
						
							try {

								JSONObject obj = response.getJSONObject(1);
								
								ServicioBean lista = new ServicioBean();
//								movie.setTitle(obj.getString("title"));
//								movie.setThumbnailUrl(obj.getString("image"));
//								movie.setRating(((Number) obj.get("rating"))
//										.doubleValue());
//								movie.setYear(obj.getInt("releaseYear"));
								lista.setIdServicio(obj.getString("title"));
								lista.setTipoServicio(obj.getString("image"));
								
								// Genre is json array
								JSONArray genreArry = obj.getJSONArray("puntos");
								
								ArrayList<PuntoBean> genre = new ArrayList<PuntoBean>();
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((PuntoBean) genreArry.get(j));
								}
								lista.setPuntos(genre);

								// adding movie to movies array
								serviceslist.add(lista);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						

						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hidePDialog();

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(movieReq);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hidePDialog();
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	

}
