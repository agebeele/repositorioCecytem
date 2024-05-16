package com.example.dual.solicitudCredencial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dual.alumnos.DividerItemDecoration;
import com.example.dual.R;
import com.example.dual.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class credencial_recycler extends AppCompatActivity {

    String msj;
    WebService obj = new WebService();
    RecyclerView recyclerView;
    credencial_adapter credencial_adapter;
    private List<String> nombreList = new ArrayList<>();
    private List<String> paternoList = new ArrayList<>();
    private List<String> maternoList = new ArrayList<>();
    private List<String> matriculaList = new ArrayList<>();
    private List<String> fechaList = new ArrayList<>();
    private List<String> horaList = new ArrayList<>();
    private List<String> grupoList = new ArrayList<>();
    private List<String> correoList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_credencial);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistorial);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(credencial_recycler.this, R.drawable.divider_line);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(credencial_recycler.this));

        credencial_adapter = new credencial_adapter(nombreList, paternoList, maternoList, matriculaList, this);

        recyclerView.setAdapter(credencial_adapter);

        MiAsyncTask miAsyncTask = new MiAsyncTask();
        miAsyncTask.execute();

        credencial_adapter.setOnItemClickListener(new credencial_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(getApplicationContext(), credencial_item.class);
                i.putExtra("fecha", fechaList.get(position));
                i.putExtra("hora", horaList.get(position));
                i.putExtra("nombre", nombreList.get(position));
                i.putExtra("matricula", matriculaList.get(position));
                i.putExtra("apellido_paterno", paternoList.get(position));
                i.putExtra("apellido_materno", maternoList.get(position));
                i.putExtra("grupo", grupoList.get(position));
                i.putExtra("correo", correoList.get(position));

                startActivity(i);
            }
        });
    }
    class MiAsyncTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... parameters) {
            msj = obj.solicitudesCredencial();
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
                    String correo =  json_data.getString("correo");

                    nombreList.add(nombre);
                    paternoList.add(paterno);
                    maternoList.add(materno);
                    matriculaList.add(matricula);

                    fechaList.add(fecha);
                    horaList.add(hora);
                    grupoList.add(grupo);
                    correoList.add(correo);
                }
            } catch (JSONException e) {
                Toast.makeText(credencial_recycler.this, progress[0], Toast.LENGTH_LONG).show();
            }
            credencial_adapter.notifyDataSetChanged();
        }
    }
}