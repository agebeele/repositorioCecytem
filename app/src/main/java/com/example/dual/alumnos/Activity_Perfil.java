package com.example.dual.alumnos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;
import com.example.dual.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Perfil extends AppCompatActivity {
    TextView nombreGeneral, matriculaGeneral;
    TextView nombre, apellido_paterno, apellido_materno;
    TextView municipio, colonia, calle, numeroExterior, numeroInterior, codigoPostal, telefonoCasa;
    WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombreGeneral = findViewById(R.id.nombre_user);
        matriculaGeneral = findViewById(R.id.matricula_user);
        nombre = findViewById(R.id.infoNombre_user);
        apellido_paterno = findViewById(R.id.infoPaterno_user);
        apellido_materno = findViewById(R.id.infoMaterno_user);

        municipio = findViewById(R.id.txt_municipio);
        colonia = findViewById(R.id.txt_colonia);
        calle = findViewById(R.id.txt_calle);
        numeroExterior = findViewById(R.id.txt_exterior);
        numeroInterior = findViewById(R.id.txt_interior);
        codigoPostal = findViewById(R.id.txt_codigoPostal);
        telefonoCasa = findViewById(R.id.txt_telefonoCasa);

        // Recuperar la matrícula guardada en SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String matricula = preferences.getString("matricula", "");

        if (!matricula.isEmpty()) {
            MiAsyncTask datosUserTask = new MiAsyncTask();
            datosUserTask.setTaskType("datosUser");
            datosUserTask.execute(matricula);

            MiAsyncTask datosDomicilioTask = new MiAsyncTask();
            datosDomicilioTask.setTaskType("datosDomicilio");
            datosDomicilioTask.execute(matricula);

        }
    }

    class MiAsyncTask extends AsyncTask<String, String, String> {
        private String taskType; // Variable para almacenar el tipo de tarea

        // Setter para asignar el tipo de tarea
        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        @Override
        protected String doInBackground(String... parameter) {
            String msj = null;
            switch (taskType) {
                case "datosUser":
                    msj = obj.datosUsuario(parameter[0]);
                    break;
                case "datosDomicilio":
                    msj = obj.datosDomicilio(parameter[0]);
                    break;
                default:
            }
            return msj;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                // Aquí maneja los resultados según corresponda
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        JSONArray usuario = jsonObject.getJSONArray("data");

                        if ("datosUser".equals(taskType)) {
                            // Maneja los datos del usuario
                            JSONObject usuarioData = usuario.getJSONObject(0); // Obtén el primer objeto del JSONArray
                            matriculaGeneral.setText(usuarioData.getString("matricula"));
                            nombreGeneral.setText(usuarioData.getString("nombre"));
                            nombre.setText(usuarioData.getString("nombre"));
                            apellido_paterno.setText(usuarioData.getString("apellido_paterno"));
                            apellido_materno.setText(usuarioData.getString("apellido_materno"));

                        } else if ("datosDomicilio".equals(taskType)) {
                            // Maneja los datos del domicilio
                            JSONObject domicilioData = usuario.getJSONObject(0); // Obtén el primer objeto del JSONArray
                            municipio.setText(domicilioData.getString("municipio"));
                            colonia.setText(domicilioData.getString("colonia"));
                            calle.setText(domicilioData.getString("calle"));
                            numeroExterior.setText(domicilioData.getString("numeroExterior"));
                            numeroInterior.setText(domicilioData.getString("numeroInterior"));
                            codigoPostal.setText(domicilioData.getString("codigoPostal"));
                            telefonoCasa.setText(domicilioData.getString("telefonoCasa"));
                        }
                    } else {
                        // Maneja el caso de error o usuario no encontrado
                        String mensaje = jsonObject.getString("message");

                        matriculaGeneral.setText("");
                        nombreGeneral.setText("");
                        nombre.setText("");
                        apellido_paterno.setText("");
                        apellido_materno.setText("");

                        municipio.setText("");
                        colonia.setText("");
                        calle.setText("");
                        numeroExterior.setText("");
                        numeroInterior.setText("");
                        codigoPostal.setText("");
                        telefonoCasa.setText("");

                        // Mostrar mensaje de error en tu activity
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Maneja errores de parsing JSON
                }
            }
        }
    }
}