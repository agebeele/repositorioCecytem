package com.example.dual.adminCE;

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

public class adminCE_Perfil extends AppCompatActivity {

    TextView nombreGeneral, matriculaGeneral;
    TextView nombre, apellido_paterno, apellido_materno;
    WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ce_perfil);

        nombreGeneral = findViewById(R.id.nombre_ce);
        matriculaGeneral = findViewById(R.id.matricula_ce);
        nombre = findViewById(R.id.infoNombre_ce);
        apellido_paterno = findViewById(R.id.infoPaterno_ce);
        apellido_materno = findViewById(R.id.infoMaterno_ce);

        // Recuperar la matrícula guardada en SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String matricula = preferences.getString("matricula", "");

        if (!matricula.isEmpty()) {
            MiAsyncTask datosUserTask = new MiAsyncTask();
            datosUserTask.execute(matricula);
        }
    }

    class MiAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... parameter) {
            return obj.datosAdminCE(parameter[0]);
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

                        // Maneja los datos del usuario
                        JSONObject usuarioData = usuario.getJSONObject(0); // Obtén el primer objeto del JSONArray
                        matriculaGeneral.setText(usuarioData.getString("matricula"));
                        nombreGeneral.setText(usuarioData.getString("nombre"));
                        nombre.setText(usuarioData.getString("nombre"));
                        apellido_paterno.setText(usuarioData.getString("apellido_paterno"));
                        apellido_materno.setText(usuarioData.getString("apellido_materno"));

                    } else {
                        // Maneja el caso de error o usuario no encontrado
                        String mensaje = jsonObject.getString("message");

                        matriculaGeneral.setText("");
                        nombreGeneral.setText("");
                        nombre.setText("");
                        apellido_paterno.setText("");
                        apellido_materno.setText("");

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