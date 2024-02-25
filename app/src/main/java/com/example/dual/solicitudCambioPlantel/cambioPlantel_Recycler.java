package com.example.dual.solicitudCambioPlantel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dual.R;
import com.example.dual.WebService;
import com.example.dual.alumnos.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class cambioPlantel_Recycler extends AppCompatActivity {
    String msj;
    WebService obj = new WebService();
    RecyclerView recyclerView;
    cambioPlantel_Adapter cambioPlantelAdapter;
    private List<String> nombreList = new ArrayList<>();
    private List<String> paternoList = new ArrayList<>();
    private List<String> maternoList = new ArrayList<>();
    private List<String> matriculaList = new ArrayList<>();
    private List<String> fechaList = new ArrayList<>();
    private List<String> horaList = new ArrayList<>();
    private List<String> grupoList = new ArrayList<>();
    private List<String> promedioList = new ArrayList<>();
    private List<String> actualList = new ArrayList<>();
    private List<String> cambioList = new ArrayList<>();
    private List<String> motivosList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_plantel_recycler);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_cambioPlantel);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(cambioPlantel_Recycler.this, R.drawable.divider_line);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(cambioPlantel_Recycler.this));

        cambioPlantelAdapter = new cambioPlantel_Adapter(nombreList, paternoList, maternoList, matriculaList, this);

        recyclerView.setAdapter(cambioPlantelAdapter);

        MiAsyncTask miAsyncTask = new MiAsyncTask();
        miAsyncTask.execute();

        cambioPlantelAdapter.setOnItemClickListener(new cambioPlantel_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(getApplicationContext(), cambioPlantel_Item.class);
                i.putExtra("fecha", fechaList.get(position));
                i.putExtra("hora", horaList.get(position));
                i.putExtra("nombre", nombreList.get(position));
                i.putExtra("matricula", matriculaList.get(position));
                i.putExtra("apellido_paterno", paternoList.get(position));
                i.putExtra("apellido_materno", maternoList.get(position));
                i.putExtra("grupo", grupoList.get(position));
                i.putExtra("promedio", promedioList.get(position));
                i.putExtra("plantelActual", actualList.get(position));
                i.putExtra("plantelCambio", cambioList.get(position));
                i.putExtra("motivos", motivosList.get(position));

                startActivity(i);
            }
        });
    }
    class MiAsyncTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... parameters) {
            msj = obj.solicitudesCambioPlantel();
            publishProgress(msj);
            return null;
        }
        @SuppressLint({"NotifyDataSetChanged", "DiscouragedApi"})
        @Override
        protected void onProgressUpdate(String... progress) {
            try {
                JSONArray jsonArray = new JSONArray(progress[0]);
                JSONObject json_data;
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    json_data = jsonArray.getJSONObject(i);

                    String nombre =  json_data.getString("nombre");
                    String paterno =  json_data.getString("apellido_paterno");
                    String materno =  json_data.getString("apellido_materno");
                    String matricula =  json_data.getString("matricula");

                    String fecha = json_data.getString("fecha");
                    String hora = json_data.getString("hora");
                    String grupo =  json_data.getString("grupo");

                    String promedio = json_data.getString("promedio");
                    String plantelActual = json_data.getString("plantelActual");
                    String plantelCambio = json_data.getString("plantelCambio");
                    String motivos = json_data.getString("motivos");

                    nombreList.add(nombre);
                    paternoList.add(paterno);
                    maternoList.add(materno);
                    matriculaList.add(matricula);

                    fechaList.add(fecha);
                    horaList.add(hora);
                    grupoList.add(grupo);

                    promedioList.add(promedio);
                    actualList.add(plantelActual);
                    cambioList.add(plantelCambio);
                    motivosList.add(motivos);
                }
            } catch (JSONException e) {
                Toast.makeText(cambioPlantel_Recycler.this, progress[0], Toast.LENGTH_LONG).show();
            }
            cambioPlantelAdapter.notifyDataSetChanged();
        }
    }
}