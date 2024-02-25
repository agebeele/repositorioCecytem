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

public class formulario_historial extends AppCompatActivity {
    TextView numeroControl, grupo, apellidoPaterno, apellidoMaterno, nombre, observaciones;
    String crud;
    static WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_historial);

        numeroControl = findViewById(R.id.matricula_historial);
        grupo = findViewById(R.id.grupo_historial);
        apellidoPaterno = findViewById(R.id.paterno_historial);
        apellidoMaterno = findViewById(R.id.materno_historial);
        nombre = findViewById(R.id.nombre_historial);
        observaciones = findViewById(R.id.observaciones_historial);
    }

    public void enviarDatosHistorial(View view) {

        if (numeroControl.getText().toString().isEmpty()
                || grupo.getText().toString().isEmpty()
                || nombre.getText().toString().isEmpty()
                || observaciones.getText().toString().isEmpty()
                || apellidoPaterno.getText().toString().isEmpty()
                || apellidoMaterno.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "solicitarHistorial";
            // Obtener fecha y hora actual por separado
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String fechaActual = dateFormat.format(calendar.getTime());
            String horaActual = timeFormat.format(calendar.getTime());

            new MiAsyncTask().execute(crud,
                    numeroControl.getText().toString(),
                    grupo.getText().toString(),
                    nombre.getText().toString(),
                    apellidoPaterno.getText().toString(),
                    apellidoMaterno.getText().toString(),
                    fechaActual,
                    horaActual,
                    observaciones.getText().toString());

            Toast.makeText(formulario_historial.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
        }
    }

    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "solicitarHistorial":
                    msj = obj.solicitarHistorial(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7], parameter[8]);
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

                    numeroControl.setText(json_data.getString("matricula"));
                    grupo.setText(json_data.getString("grupo"));
                    nombre.setText(json_data.getString("nombre"));
                    apellidoPaterno.setText(json_data.getString("apellido_paterno"));
                    apellidoMaterno.setText(json_data.getString("apellido_materno"));
                    observaciones.setText(json_data.getString("observaciones"));
                } else {
                    // Manejar el caso en que el JSONArray está vacío
                    numeroControl.setText("");
                    grupo.setText("");
                    nombre.setText("");
                    apellidoPaterno.setText("");
                    apellidoMaterno.setText("");
                    observaciones.setText("");
                }
            } catch (JSONException e) {
                // Manejar errores de parsing JSON
                numeroControl.setText("");
                grupo.setText("");
                nombre.setText("");
                apellidoPaterno.setText("");
                apellidoMaterno.setText("");
                observaciones.setText("");

                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}