package com.example.dual.adminVIN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AdminVin_Login extends AppCompatActivity {

    String crud;
    WebService obj = new WebService();
    EditText usuario, contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_vin_login);

        usuario = (EditText) findViewById(R.id.admin_matricula);
        String dato_usuario = usuario.getText().toString();
        if (dato_usuario.isEmpty()) {
            // Mostrar un mensaje de error o realizar alguna acción
            usuario.setError("El usuario no puede estar vacío");
        } else {
            // El nombre es válido, realiza otras acciones si es necesario
        }

        contra = (EditText) findViewById(R.id.admin_contra);
        String dato_contra = contra.getText().toString();
        if (dato_contra.isEmpty()) {
            // Mostrar un mensaje de error o realizar alguna acción
            contra.setError("La contraseña no puede estar vacía");
        } else {
            // El nombre es válido, realiza otras acciones si es necesario
        }
    }
    public void regresarLogin (View view){
        onBackPressed();
    }
    public void ingresarVIN (View view){
        if (usuario.getText().toString().isEmpty() || contra.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "log";
            new MiAsyncTask().execute(crud, usuario.getText().toString(), contra.getText().toString());
        }
    }
    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "log":
                    msj = obj.login_adminVIN(parameter[1], parameter[2]);
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

                // Obtener la matrícula del JSON
                String matricula = json_data.getString("matricula");

                // Resto del código para redireccionar a la próxima actividad
                usuario.setText(matricula);
                contra.setText(json_data.getString("curp"));
                Toast.makeText(AdminVin_Login.this, "Bienvenido admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminVin_Login.this, AdminVIN_Inicio.class);
                startActivity(intent);
                finish();

                // Guardar la matrícula en SharedPreferences al iniciar sesión
                SharedPreferences preferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("matricula", matricula);
                editor.apply();

            } catch (JSONException e) {
                usuario.setText("");
                contra.setText("");
                Toast.makeText(getApplicationContext(), progress[0], Toast.LENGTH_LONG).show();
            }
        }
    }
}