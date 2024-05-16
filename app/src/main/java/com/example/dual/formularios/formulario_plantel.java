package com.example.dual.formularios;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class formulario_plantel extends AppCompatActivity {

    EditText numeroControl, nombre, paterno, materno, grupo, promedio, motivos, correo;
    TextView carreraActual;
    Spinner carreraCambio;
    String crud;
    static WebService obj = new WebService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_plantel);

        numeroControl = findViewById(R.id.matricula_cambioPlantel);
        grupo = findViewById(R.id.grupo_cambioPlantel);
        paterno = findViewById(R.id.apellidoPaterno_cambioPlantel);
        materno = findViewById(R.id.apellidoMaterno_cambioPlantel);
        nombre = findViewById(R.id.nombres_cambioPlantel);
        promedio = findViewById(R.id.promedio_cambioPlantel);
        carreraActual = findViewById(R.id.PlantelActual);
        carreraCambio = findViewById(R.id.spinnerPlantel_carreraCambio);
        motivos = findViewById(R.id.motivos_cambioPlantel);
        correo = findViewById(R.id.correo_cambioPlantel);
    }
    public void enviarDatos_cambioPlantel(View view) {
        if (numeroControl.getText().toString().isEmpty()
                || grupo.getText().toString().isEmpty()
                || paterno.getText().toString().isEmpty()
                || materno.getText().toString().isEmpty()
                || nombre.getText().toString().isEmpty()
                || promedio.getText().toString().isEmpty()
                || carreraActual.getText().toString().isEmpty()
                || carreraCambio.getSelectedItem().toString().isEmpty()
                || motivos.getText().toString().isEmpty()
                || correo.getText().toString().isEmpty()) {
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
                    carreraActual.getText().toString(),
                    carreraCambio.getSelectedItem().toString(),
                    promedio.getText().toString(),
                    correo.getText().toString());

            Toast.makeText(formulario_plantel.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
        }
    }
    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "solicitar":
                    msj = obj.solicitarCambioPlantel(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7],parameter[8], parameter[9],parameter[10], parameter[11], parameter[12]);
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
                    carreraActual.setText(json_data.getString("plantelActual"));

                    // Establece las selecciones en los Spinners
                    String carreraCambioValue = json_data.getString("plantelCambio");
                    int posicionCarreraCambio = obtenerPosicionEnArray(carreraCambio, carreraCambioValue);
                    carreraCambio.setSelection(posicionCarreraCambio);

                    motivos.setText(json_data.getString("motivos"));
                    correo.setText(json_data.getString("correo"));

                    Toast.makeText(formulario_plantel.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
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
                correo.setText("");
            }
        }
    }

    private int obtenerPosicionEnArray(Spinner spinner, String valor) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        return adapter.getPosition(valor);
    }
}