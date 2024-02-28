package com.example.dual.alumnos;

import android.content.Intent;
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

public class Activity_Registro extends AppCompatActivity {
    private EditText nombre, paterno, materno, matricula, curp;
    Spinner carreraActual;
    String crud;
    static WebService obj = new WebService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = (EditText) findViewById(R.id.nombre_et);
        paterno = (EditText) findViewById(R.id.paterno_et);
        materno = (EditText) findViewById(R.id.materno_et);
        matricula = (EditText) findViewById(R.id.matricula_et);
        curp = (EditText) findViewById(R.id.curp_et);
        carreraActual = (Spinner) findViewById(R.id.carrera_actual);
    }
    public void registrarUsuario (View view){
        if (matricula.getText().toString().isEmpty()
                || curp.getText().toString().isEmpty()
                || nombre.getText().toString().isEmpty()
                || paterno.getText().toString().isEmpty()
                || materno.getText().toString().isEmpty()
                || carreraActual.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "registro";
            new MiAsyncTask().execute(crud,
                    matricula.getText().toString(),
                    curp.getText().toString(),
                    nombre.getText().toString(),
                    paterno.getText().toString(),
                    materno.getText().toString(),
                    carreraActual.getSelectedItem().toString());
        }
    }
    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "registro":
                    msj = obj.registarUsuario(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6]);
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
                    json_data = jArray.getJSONObject(i);
                }
                matricula.setText(json_data.getString("matricula"));
                curp.setText(json_data.getString("curp"));
                nombre.setText(json_data.getString("nombre"));
                paterno.setText(json_data.getString("apellido_paterno"));
                materno.setText(json_data.getString("apellido_materno"));

                String carreraActualValue = json_data.getString("carreraActual");
                int posicionCarreraActual = obtenerPosicionEnArray(carreraActual, carreraActualValue);
                carreraActual.setSelection(posicionCarreraActual);


                Toast.makeText(Activity_Registro.this, "Usuario registrado.", Toast.LENGTH_SHORT).show();

                // Inicio de sesión exitoso, cambiar a la siguiente actividad
                Intent intent = new Intent(Activity_Registro.this, Activity_Login.class); // Cambia HomeActivity por el nombre de tu actividad de inicio
                startActivity(intent);
                finish(); // Opcional, si deseas finalizar la actividad actual

            } catch (JSONException e) {
                matricula.setText("");
                curp.setText("");
                nombre.setText("");
                paterno.setText("");
                materno.setText("");

                Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_LONG).show();
            }
        }
    }

    private int obtenerPosicionEnArray(Spinner spinner, String valor) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        return adapter.getPosition(valor);
    }
}