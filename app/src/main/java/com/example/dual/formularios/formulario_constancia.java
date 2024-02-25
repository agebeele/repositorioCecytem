package com.example.dual.formularios;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;
import com.example.dual.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class formulario_constancia extends AppCompatActivity {

    TextView matricula, grupo, paterno, materno, nombre, observaciones;
    String crud;
    static WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_constancia);

        matricula = findViewById(R.id.matricula_constancia);
        grupo = findViewById(R.id.grupo_constancia);
        paterno = findViewById(R.id.paterno_constancia);
        materno = findViewById(R.id.materno_constancia);
        nombre = findViewById(R.id.nombre_constancia);
        observaciones = findViewById(R.id.observaciones_constancia);
    }

    public void enviarDatosConstancia(View view) {

        if (matricula.getText().toString().isEmpty()
                || grupo.getText().toString().isEmpty()
                || nombre.getText().toString().isEmpty()
                || observaciones.getText().toString().isEmpty()
                || paterno.getText().toString().isEmpty()
                || materno.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "solicitarConstancia";

            // Obtener fecha y hora actual por separado
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String fechaActual = dateFormat.format(calendar.getTime());
            String horaActual = timeFormat.format(calendar.getTime());

            // Tu llamada al método corregida
            new MiAsyncTask().execute(crud,
                    matricula.getText().toString(),
                    grupo.getText().toString(),
                    nombre.getText().toString(),
                    paterno.getText().toString(),
                    materno.getText().toString(),
                    fechaActual,
                    horaActual,
                    observaciones.getText().toString());

            Toast.makeText(formulario_constancia.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
        }
    }

    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "solicitarConstancia":
                    msj = obj.solicitarConstancia(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7], parameter[8]);
                    publishProgress(msj);
                    break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);

            try {
                JSONArray jArray = new JSONArray(progress[0]);
                if (jArray.length() > 0) {  // Verificar si hay elementos en el JSONArray
                    JSONObject json_data = jArray.getJSONObject(0); // Obtén el primer objeto del JSONArray

                    matricula.setText(json_data.getString("matricula"));
                    grupo.setText(json_data.getString("grupo"));
                    nombre.setText(json_data.getString("nombre"));
                    paterno.setText(json_data.getString("apellido_paterno"));
                    materno.setText(json_data.getString("apellido_materno"));
                    observaciones.setText(json_data.getString("observaciones"));
                } else {
                    // Manejar el caso en que el JSONArray está vacío
                    matricula.setText("");
                    grupo.setText("");
                    nombre.setText("");
                    paterno.setText("");
                    materno.setText("");
                    observaciones.setText("");
                }
            } catch (JSONException e) {
                // Manejar errores de parsing JSON
                matricula.setText("");
                grupo.setText("");
                nombre.setText("");
                paterno.setText("");
                materno.setText("");
                observaciones.setText("");

                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}