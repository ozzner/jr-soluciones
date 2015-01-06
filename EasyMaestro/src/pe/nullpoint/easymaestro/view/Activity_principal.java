package pe.nullpoint.easymaestro.view;


import java.util.ArrayList;
import java.util.List;

import pe.nullpoint.easymaestro.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Julio on 20/11/2014.
 */
public class Activity_principal extends Activity  {

    private Spinner spinner_oficio, spinner_especialidad;
    private Button btnSubmit;

    List<String> list;
    List<String> list_carpintero;
    List<String> list_albañil;
    List<String> list_electricista;
    List<String> list_pintor;

    ArrayAdapter<String> dataAdapter1;
    ArrayAdapter<String> dataAdapter2;
    ArrayAdapter<String> dataAdapter3;
    ArrayAdapter<String> dataAdapter4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Spinner element
        spinner_oficio = (Spinner) findViewById(R.id.spinner_ocup);
        spinner_especialidad = (Spinner) findViewById(R.id.spinner_especialidad);
        btnSubmit = (Button) findViewById(R.id.btn_buscar);

        spinner_oficio.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(spinner_oficio.getSelectedItem().equals("Albañil")) {
                    spinner_especialidad.setAdapter(dataAdapter1);
                }
                if(spinner_oficio.getSelectedItem().equals("Carpintero")) {
                    spinner_especialidad.setAdapter(dataAdapter2);
                }
                if(spinner_oficio.getSelectedItem().equals("Electricista")) {
                    spinner_especialidad.setAdapter(dataAdapter3);
                }
                if(spinner_oficio.getSelectedItem().equals("Pintor")) {
                    spinner_especialidad.setAdapter(dataAdapter4);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                Intent i = new Intent(getApplicationContext(), Activity_mapa.class);
                startActivity(i);
                finish();
            }

        });

        list = new ArrayList<String>();
        list.add("Albañil");
        list.add("Carpintero");
        list.add("Electricista");
        list.add("Pintor");

//        Lista de especialidades de Albañil
        list_albañil = new ArrayList<String>();
        list_albañil.add("Especialidad albañil 1");
        list_albañil.add("Especialidad albañil 2");
        list_albañil.add("Especialidad albañil 3");
        list_albañil.add("Especialidad albañil 4");

//        Lista de especialidades de Carpintero
        list_carpintero = new ArrayList<String>();
        list_carpintero.add("Especialidad carpintero 1");
        list_carpintero.add("Especialidad carpintero 2");
        list_carpintero.add("Especialidad carpintero 3");
        list_carpintero.add("Especialidad carpintero 4");

//        Lista de especialidades de Electricista
        list_electricista = new ArrayList<String>();
        list_electricista.add("Especialidad de Electricista 1");
        list_electricista.add("Especialidad de Electricista 2");
        list_electricista.add("Especialidad de Electricista 3");
        list_electricista.add("Especialidad de Electricista 4");

//        Lista de especialidades de Pintor
        list_pintor = new ArrayList<String>();
        list_pintor.add("Especialidad de Pintor 1");
        list_pintor.add("Especialidad de Pintor 1");
        list_pintor.add("Especialidad de Pintor 1");
        list_pintor.add("Especialidad de Pintor 1");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_albañil);

        dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_carpintero);

        dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_electricista);

        dataAdapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list_pintor);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_oficio.setAdapter(dataAdapter);
//        spinner_especialidad.setAdapter(dataAdapter2);



    }

}
