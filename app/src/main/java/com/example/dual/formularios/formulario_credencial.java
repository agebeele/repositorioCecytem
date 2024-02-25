package com.example.dual.formularios;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class formulario_credencial extends AppCompatActivity {

    EditText numeroControl, grupo, apellidoPaterno, apellidoMaterno, nombre;
    String crud;
    static WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_credencial);

        numeroControl = findViewById(R.id.ingresa_numeroControl);
        grupo = findViewById(R.id.txtgrupo);
        apellidoPaterno = findViewById(R.id.apellidoPaterno);
        apellidoMaterno = findViewById(R.id.apellidoMaterno);
        nombre = findViewById(R.id.nombres_s);
    }

    public void enviarDatosCredencial(View view) {
        if (numeroControl.getText().toString().isEmpty()
                || grupo.getText().toString().isEmpty()
                || nombre.getText().toString().isEmpty()
                || apellidoPaterno.getText().toString().isEmpty()
                || apellidoMaterno.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "solicitar";
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
                    horaActual);

            Toast.makeText(formulario_credencial.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
        }
    }

    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "solicitar":
                    msj = obj.solicitarCredencial(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7]);
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
                JSONObject json_data = null;
                for (int i = 0; i < jArray.length(); i++) {
                    numeroControl.setText(json_data.getString("matricula"));
                    grupo.setText(json_data.getString("grupo"));
                    nombre.setText(json_data.getString("nombre"));
                    apellidoPaterno.setText(json_data.getString("apellido_paterno"));
                    apellidoMaterno.setText(json_data.getString("apellido_materno"));

                    Toast.makeText(formulario_credencial.this, "Usuario registrado.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                numeroControl.setText("");
                grupo.setText("");
                nombre.setText("");
                apellidoPaterno.setText("");
                apellidoMaterno.setText("");

                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
