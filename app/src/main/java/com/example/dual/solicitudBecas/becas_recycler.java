package com.example.dual.solicitudBecas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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

public class becas_recycler extends AppCompatActivity {
    String msj;
    WebService obj = new WebService();
    RecyclerView recyclerView;
    becas_adapter becasAdapter;
    private List<String> idList = new ArrayList<>();
    private List<String> curpList = new ArrayList<>();
    private List<String> paternoList = new ArrayList<>();
    private List<String> maternoList = new ArrayList<>();
    private List<String> nombreList = new ArrayList<>();
    private List<String> fechaNacimientoList = new ArrayList<>();
    private List<String> fechaList = new ArrayList<>();
    private List<String> horaList = new ArrayList<>();
    private List<String> sexoList = new ArrayList<>();
    private List<String> EstadoNList = new ArrayList<>();
    private List<String> SituacionAList = new ArrayList<>();
    private List<String> SemestreList = new ArrayList<>();
    private List<String> CorreoPList = new ArrayList<>();
    private List<String> CorreoInsList = new ArrayList<>();
    private List<String> TelefonoCasaList = new ArrayList<>();
    private List<String> TelefonoAlumnoList = new ArrayList<>();
    private List<String> TelefonoPadreList = new ArrayList<>();
    private List<String> DomicilioList = new ArrayList<>();
    private List<String> NoExtList = new ArrayList<>();
    private List<String> NoIntList = new ArrayList<>();
    private List<String> NombreColoniaList = new ArrayList<>();
    private List<String> MunicipioList = new ArrayList<>();
    private List<String> EstadoDList = new ArrayList<>();
    private List<String> CodigoPList = new ArrayList<>();
    private List<String> EscuelaPList = new ArrayList<>();
    private List<String> PromedioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_becas_recycler);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewBecas);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(becas_recycler.this, R.drawable.divider_line);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(becas_recycler.this));

        becasAdapter = new becas_adapter(curpList, paternoList, maternoList, nombreList, this);
        recyclerView.setAdapter(becasAdapter);

        MiAsyncTask miAsyncTask = new MiAsyncTask();
        miAsyncTask.execute();

        becasAdapter.setOnItemClickListener(new becas_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(getApplicationContext(), beca_item.class);
                i.putExtra("id", idList.get(position));
                i.putExtra("fecha", fechaList.get(position));
                i.putExtra("hora", horaList.get(position));
                i.putExtra("curp", curpList.get(position));
                i.putExtra("apellido_paterno", paternoList.get(position));
                i.putExtra("apellido_materno", maternoList.get(position));
                i.putExtra("nombre", nombreList.get(position));
                i.putExtra("fecha_nacimiento", fechaNacimientoList.get(position));
                i.putExtra("sexo", sexoList.get(position));
                i.putExtra("estado_nacimiento", EstadoNList.get(position));
                i.putExtra("situacion_academica", SituacionAList.get(position));
                i.putExtra("semestre", SemestreList.get(position));
                i.putExtra("correo_personal", CorreoPList.get(position));
                i.putExtra("correo_institucional", CorreoInsList.get(position));
                i.putExtra("telefono_casa", TelefonoCasaList.get(position));
                i.putExtra("telefono_alumno", TelefonoAlumnoList.get(position));
                i.putExtra("telefono_padre", TelefonoPadreList.get(position));
                i.putExtra("domicilio", DomicilioList.get(position));
                i.putExtra("no_exterior", NoExtList.get(position));
                i.putExtra("no_interior", NoIntList.get(position));
                i.putExtra("nombre_colonia", NombreColoniaList.get(position));
                i.putExtra("municipio", MunicipioList.get(position));
                i.putExtra("estado_domicilio", EstadoDList.get(position));
                i.putExtra("codigo_postal", CodigoPList.get(position));
                i.putExtra("escuela_procedencia", EscuelaPList.get(position));
                i.putExtra("promedio", PromedioList.get(position));
                startActivity(i);
            }
        });
    }
    class MiAsyncTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... parameters) {
            msj = obj.solicitudesBecas();//Modificar en el WebService
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

                    String id = json_data.getString("id");
                    String nombre = json_data.getString("nombre");
                    String paterno = json_data.getString("apellido_paterno");
                    String materno =  json_data.getString("apellido_materno");
                    String curp =  json_data.getString("curp");
                    String fecha = json_data.getString("fecha");
                    String hora = json_data.getString("hora");
                    String fechaN = json_data.getString("fecha_nacimiento");
                    String sexo = json_data.getString("sexo");
                    String estadon= json_data.getString("estado_nacimiento");
                    String situacion= json_data.getString("situacion_academica");
                    String semestre= json_data.getString("semestre");
                    String correop = json_data.getString("correo_personal");
                    String correoins = json_data.getString("correo_institucional");
                    String telefonoc = json_data.getString("telefono_casa");
                    String telefonoa = json_data.getString("telefono_alumno");
                    String telefonop = json_data.getString("telefono_padre");
                    String domicilio = json_data.getString("domicilio");
                    String noe = json_data.getString("no_exterior");
                    String noi = json_data.getString("no_interior");
                    String noc = json_data.getString("nombre_colonia");
                    String municipio = json_data.getString("municipio");
                    String estadod = json_data.getString("estado_domicilio");
                    String codigop = json_data.getString("codigo_postal");
                    String escuelap = json_data.getString("escuela_procedencia");
                    String   promedio = json_data.getString("promedio");

                    Log.d("WebService", "curp: " + curp);

                    idList.add(id);
                    curpList.add(curp);
                    nombreList.add(nombre);
                    paternoList.add(paterno);
                    maternoList.add(materno);
                    fechaList.add(fecha);
                    horaList.add(hora);
                    fechaNacimientoList.add(fechaN);
                    sexoList.add(sexo);
                    EstadoNList.add(estadon);
                    SituacionAList.add(situacion);
                    SemestreList.add(semestre);
                    CorreoPList.add(correop);
                    CorreoInsList.add(correoins);
                    TelefonoCasaList.add(telefonoc);
                    TelefonoAlumnoList.add(telefonoa);
                    TelefonoPadreList.add(telefonop);
                    DomicilioList.add(domicilio);
                    NoExtList.add(noe);
                    NoIntList.add(noi);
                    NombreColoniaList.add(noc);
                    MunicipioList.add(municipio);
                    EstadoDList.add(estadod);
                    CodigoPList.add(codigop);
                    EscuelaPList.add(escuelap);
                    PromedioList.add(promedio);

                }
            } catch (JSONException e) {
                Log.e("WebService", "Error al procesar los datos JSON", e);
            }
            becasAdapter.notifyDataSetChanged();
        }
    }
}