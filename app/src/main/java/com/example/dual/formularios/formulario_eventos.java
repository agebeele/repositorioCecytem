package com.example.dual.formularios;

import android.annotation.SuppressLint;
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

public class formulario_eventos extends AppCompatActivity {
    String crud;
    static WebService obj = new WebService();

    EditText matricula,nombre, telefonoCasa, TelefonoCelular, Grupo, evento;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_eventos);

        matricula = findViewById(R.id.txtmatricula);
        nombre = findViewById(R.id.txtNombre);
        telefonoCasa = findViewById(R.id.txtTelefonoCasa);
        TelefonoCelular = findViewById(R.id.txtTelefonoCelular);
        Grupo =findViewById(R.id.txtgrupo);
        evento = findViewById(R.id.txtEventos);
    }
    public void enviarDatosCredencial(View view) {
        if (matricula.getText().toString().isEmpty()
                || nombre.getText().toString().isEmpty()
                || telefonoCasa.getText().toString().isEmpty()
                || TelefonoCelular.getText().toString().isEmpty()
                || Grupo.getText().toString().isEmpty()
                || evento.getText().toString().isEmpty()) {
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
                    fechaActual,
                    horaActual,
                    matricula.getText().toString(),
                    nombre.getText().toString(),
                    telefonoCasa.getText().toString(),
                    TelefonoCelular.getText().toString(),
                    Grupo.getText().toString(),
                    evento.getText().toString());

            Toast.makeText(formulario_eventos.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
        }
    }

    class MiAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "solicitar":
                    msj = obj.solicitarEvento(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7], parameter[8]);
                    break;
            }
            return msj;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jArray = new JSONArray(result);
                JSONObject json_data = null;
                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    matricula.setText(json_data.getString("matricula"));
                    nombre.setText(json_data.getString("nombre"));
                    telefonoCasa.setText(json_data.getString("telefonocasa"));
                    TelefonoCelular.setText(json_data.getString("telefonocelular"));
                    Grupo.setText(json_data.getString("grupo"));
                    evento.setText(json_data.getString("evento"));
                    Toast.makeText(formulario_eventos.this, "Usuario registrado.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                matricula.setText("");
                nombre.setText("");
                telefonoCasa.setText("");
                TelefonoCelular.setText("");
                Grupo.setText("");
                evento.setText("");
            }
        }
    }
}