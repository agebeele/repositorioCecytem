package com.example.dual.adminVIN;

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

public class agregarItem_VIN extends AppCompatActivity {

    EditText titulo_et, descripcion_et;
    String crud;
    static WebService obj = new WebService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_item_vin);

        titulo_et = (EditText) findViewById(R.id.titulo_et);
        descripcion_et = (EditText) findViewById(R.id.descripcion_et);

    }
    public void agregarPublicacion (View view){
        if (titulo_et.getText().toString().isEmpty() || descripcion_et.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "Publicacion";
            // Obtener fecha y hora actual
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String fechaActual = dateFormat.format(calendar.getTime());
            String horaActual = timeFormat.format(calendar.getTime());

            new MiAsyncTask().execute(crud,
                    titulo_et.getText().toString(),
                    descripcion_et.getText().toString(),
                    fechaActual,
                    horaActual);
        }
    }
    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "Publicacion":
                    msj = obj.crearPublicacion(parameter[1], parameter[2], parameter[3], parameter[4]);
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
                // Aquí puedes realizar acciones adicionales si es necesario

                titulo_et.setText(json_data.getString("titulo"));
                descripcion_et.setText(json_data.getString("descripcion"));

                Toast.makeText(agregarItem_VIN.this, "Publicacion creada.", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                titulo_et.setText("");
                descripcion_et.setText("");
                Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_LONG).show();
            }
        }
    }
}