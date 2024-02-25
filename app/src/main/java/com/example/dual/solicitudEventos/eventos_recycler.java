package com.example.dual.solicitudEventos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class eventos_recycler extends AppCompatActivity {
    String msj;
    WebService obj = new WebService();
    RecyclerView recyclerView;
    eventos_adapter eventosAdapter;
    private List<String> fechaList = new ArrayList<>();
    private List<String> horaList = new ArrayList<>();
    private List<String> matriculaList = new ArrayList<>();
    private List<String> nombreList = new ArrayList<>();
    private List<String> telefonoCasaList = new ArrayList<>();
    private List<String> telefonoCelularList = new ArrayList<>();
    private List<String> grupoList = new ArrayList<>();
    private List<String> eventoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEventos);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(eventos_recycler.this, R.drawable.divider_line);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(eventos_recycler.this));

        eventosAdapter = new eventos_adapter(matriculaList,nombreList, grupoList, eventoList, this);

        recyclerView.setAdapter(eventosAdapter);

        MiAsyncTask miAsyncTask = new MiAsyncTask();
        miAsyncTask.execute();

        eventosAdapter.setOnItemClickListener(new eventos_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(getApplicationContext(), eventos_item.class);
                i.putExtra("fecha", fechaList.get(position));
                i.putExtra("hora", horaList.get(position));
                i.putExtra("matricula", nombreList.get(position));
                i.putExtra("nombre", nombreList.get(position));
                i.putExtra("telefonocasa", telefonoCasaList.get(position));
                i.putExtra("telefonocelular", telefonoCelularList.get(position));
                i.putExtra("grupo", grupoList.get(position));
                i.putExtra("evento", eventoList.get(position));

                startActivity(i);
            }
        });
    }
    class MiAsyncTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... parameters) {
            msj = obj.solicitudesEventos();//Cambiar por el ServicioWeb
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

                    String nombre = json_data.getString("nombre");
                    String telefonocasa = json_data.getString("telefonocasa");
                    String telefonocelular =  json_data.getString("telefonocelular");
                    String matricula =  json_data.getString("matricula");
                    String fecha = json_data.getString("fecha");
                    String hora = json_data.getString("hora");
                    String grupo = json_data.getString("grupo");
                    String evento =  json_data.getString("evento");

                    Log.d("WebService", "evento: " + evento);

                    eventoList.add(evento);
                    nombreList.add(nombre);
                    telefonoCasaList.add(telefonocasa);
                    telefonoCelularList.add(telefonocelular);
                    matriculaList.add(matricula);
                    fechaList.add(fecha);
                    horaList.add(hora);
                    grupoList.add(grupo);
                }
            } catch (JSONException e) {
                Toast.makeText(eventos_recycler.this, progress[0], Toast.LENGTH_LONG).show();
            }
            eventosAdapter.notifyDataSetChanged();
        }
    }
}