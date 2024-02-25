package com.example.dual.formularios;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class formulario_carrera extends AppCompatActivity {
    EditText numeroControl, nombre, paterno, materno, grupo, promedio, motivos;
    Spinner carreraActual, carreraCambio;
    String crud;
    static WebService obj = new WebService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_carrera);

        numeroControl = findViewById(R.id.matricula_cambioCarrera);
        grupo = findViewById(R.id.grupo_cambioCarrera);
        paterno = findViewById(R.id.apellidoPaterno_cambioCarrera);
        materno = findViewById(R.id.apellidoMaterno_cambioCarrera);
        nombre = findViewById(R.id.nombres_cambioCarrera);
        promedio = findViewById(R.id.promedio_cambioCarrera);
        carreraActual = findViewById(R.id.spinner_carreraActual);
        carreraCambio = findViewById(R.id.spinner_carreraCambio);
        motivos = findViewById(R.id.motivos_cambioCarrera);
    }
    public void enviarDatos_cambioCarrera(View view) {
        if (numeroControl.getText().toString().isEmpty()
                || grupo.getText().toString().isEmpty()
                || paterno.getText().toString().isEmpty()
                || materno.getText().toString().isEmpty()
                || nombre.getText().toString().isEmpty()
                || promedio.getText().toString().isEmpty()
                || carreraActual.getSelectedItem().toString().isEmpty()
                || carreraCambio.getSelectedItem().toString().isEmpty()
                || motivos.getText().toString().isEmpty()) {
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
                    paterno.getText().toString(),
                    materno.getText().toString(),
                    fechaActual,
                    horaActual,
                    motivos.getText().toString(),
                    carreraActual.getSelectedItem().toString(),
                    carreraCambio.getSelectedItem().toString(),
                    promedio.getText().toString());


            Toast.makeText(formulario_carrera.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
        }
    }
    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "solicitar":
                    msj = obj.solicitarCambioCarrera(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7],parameter[8], parameter[9],parameter[10], parameter[11]);
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
                    paterno.setText(json_data.getString("apellido_paterno"));
                    materno.setText(json_data.getString("apellido_materno"));
                    promedio.setText(json_data.getString("promedio"));

                    // Establece las selecciones en los Spinners
                    String carreraActualValue = json_data.getString("carreraActual");
                    String carreraCambioValue = json_data.getString("carreraCambio");

                    int posicionCarreraActual = obtenerPosicionEnArray(carreraActual, carreraActualValue);
                    int posicionCarreraCambio = obtenerPosicionEnArray(carreraCambio, carreraCambioValue);

                    carreraActual.setSelection(posicionCarreraActual);
                    carreraCambio.setSelection(posicionCarreraCambio);

                    motivos.setText(json_data.getString("motivos"));

                    Toast.makeText(formulario_carrera.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                numeroControl.setText("");
                grupo.setText("");
                nombre.setText("");
                paterno.setText("");
                materno.setText("");
                grupo.setText("");
                motivos.setText("");
                promedio.setText("");
            }
        }
    }

    private int obtenerPosicionEnArray(Spinner spinner, String valor) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        return adapter.getPosition(valor);
    }
}