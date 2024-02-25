package com.example.dual.solicitudServicioSocial;

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

public class servicio_recycler extends AppCompatActivity {
    String msj;
    WebService obj = new WebService();
    RecyclerView recyclerView;
    servicio_adapter ServicioAdapter;
    private List<String> fechaList = new ArrayList<>();
    private List<String> horaList = new ArrayList<>();
    private List<String> nombreList = new ArrayList<>();
    private List<String> promedioList = new ArrayList<>();
    private List<String> TelefonoCasaList = new ArrayList<>();
    private List<String> TelefonoCelularList = new ArrayList<>();
    private List<String> GrupoList = new ArrayList<>();
    private List<String> CorreoInsList = new ArrayList<>();
    private List<String> NombreDependenciaList = new ArrayList<>();
    private List<String> CCTList = new ArrayList<>();
    private List<String> TurnoList = new ArrayList<>();
    private List<String> DomicilioDList = new ArrayList<>();
    private List<String> TelefonoDList = new ArrayList<>();
    private List<String> NivelPList = new ArrayList<>();
    private List<String> NombreRList = new ArrayList<>();
    private List<String> CargoRList = new ArrayList<>();
    private List<String> ActividadesList = new ArrayList<>();
    private List<String> HorarioList = new ArrayList<>();
    private List<String> NombrePList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewServicio);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(servicio_recycler.this, R.drawable.divider_line);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(servicio_recycler.this));

        ServicioAdapter = new servicio_adapter(nombreList,GrupoList, NombreDependenciaList, TurnoList, this);

        recyclerView.setAdapter(ServicioAdapter);

        MiAsyncTask miAsyncTask = new MiAsyncTask();
        miAsyncTask.execute();

        ServicioAdapter.setOnItemClickListener(new servicio_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(getApplicationContext(), servicio_item.class);
                i.putExtra("hora", horaList.get(position));
                i.putExtra("fecha", fechaList.get(position));
                i.putExtra("nombre", nombreList.get(position));
                i.putExtra("promedio", promedioList.get(position));
                i.putExtra("telefonocasa", TelefonoCasaList.get(position));
                i.putExtra("telefonocelular", TelefonoCelularList.get(position));
                i.putExtra("grupo", GrupoList.get(position));
                i.putExtra("correoins",CorreoInsList.get(position));
                i.putExtra("nombredepe", NombreDependenciaList.get(position));
                i.putExtra("cct", CCTList.get(position));
                i.putExtra("turno", TurnoList.get(position));
                i.putExtra("domiciliode", DomicilioDList.get(position));
                i.putExtra("telefonodepe", TelefonoDList.get(position));
                i.putExtra("nivel", NivelPList.get(position));
                i.putExtra("nombreres", NombreRList.get(position));
                i.putExtra("cargores", CargoRList.get(position));
                i.putExtra("actividades", ActividadesList.get(position));
                i.putExtra("horario", HorarioList.get(position));
                i.putExtra("nombrepadre", NombrePList.get(position));
                startActivity(i);
            }
        });
    }
    class MiAsyncTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... parameters) {
            msj = obj.solicitudesServicio();//modificar el webservice
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
                    String fecha = json_data.getString("fecha");
                    String hora = json_data.getString("hora");
                    String nombre = json_data.getString("nombre");
                    String promedio = json_data.getString("promedio");
                    String telefonocasa = json_data.getString("telefonocasa");
                    String telefonocelular = json_data.getString("telefonocelular");
                    String grupo = json_data.getString("grupo");
                    String correoins = json_data.getString("correoins");
                    String nombredependencia = json_data.getString("nombredepe");
                    String cct = json_data.getString("cct");
                    String turno = json_data.getString("turno");
                    String domiciliod = json_data.getString("domiciliode");
                    String telefonod = json_data.getString("telefonodepe");
                    String nivel = json_data.getString("nivel");
                    String nombrer = json_data.getString("nombreres");
                    String cargor = json_data.getString("cargores");
                    String actividades = json_data.getString("actividades");
                    String horario = json_data.getString("horario");
                    String nombrep = json_data.getString("nombrepadre");

                    Log.d("WebService", "Nombre: " + nombre);

                    horaList.add(hora);
                    fechaList.add(fecha);
                    nombreList.add(nombre);
                    promedioList.add(promedio);
                    TelefonoCasaList.add(telefonocasa);
                    TelefonoCelularList.add(telefonocelular);
                    GrupoList.add(grupo);
                    CorreoInsList.add(correoins);
                    NombreDependenciaList.add(nombredependencia);
                    CCTList.add(cct);
                    TurnoList.add(turno);
                    DomicilioDList.add(domiciliod);
                    TelefonoDList.add(telefonod);
                    NivelPList.add(nivel);
                    NombreRList.add(nombrer);
                    CargoRList.add(cargor);
                    ActividadesList.add(actividades);
                    HorarioList.add(horario);
                    NombrePList.add(nombrep);
                }
            } catch (JSONException e) {
                Toast.makeText(servicio_recycler.this, progress[0], Toast.LENGTH_LONG).show();
            }
            ServicioAdapter.notifyDataSetChanged();
        }
    }
}