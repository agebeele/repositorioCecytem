package com.example.dual.alumnos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;
import com.example.dual.WebService;
import com.example.dual.adminCE.AdminCE_Login;
import com.example.dual.adminVIN.AdminVin_Login;
import com.example.dual.aspirantes.Activity_Aspirantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Login extends AppCompatActivity {
    String crud;
    WebService obj = new WebService();
    TextView sitioOfical;
    EditText usuario, contra;
    Button mostrarAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (EditText) findViewById(R.id.txt_matricula);
        String dato_usuario = usuario.getText().toString();
        if (dato_usuario.isEmpty()) {
            // Mostrar un mensaje de error o realizar alguna acción
            usuario.setError("El usuario no puede estar vacío");
        } else {
            // El nombre es válido, realiza otras acciones si es necesario
        }

        contra = (EditText) findViewById(R.id.txt_contra);
        String dato_contra = contra.getText().toString();
        if (dato_contra.isEmpty()) {
            // Mostrar un mensaje de error o realizar alguna acción
            contra.setError("La contraseña no puede estar vacía");
        } else {
            // El nombre es válido, realiza otras acciones si es necesario
        }

        sitioOfical = (TextView) findViewById(R.id.paginaOficial);

        sitioOfical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirPaginaWeb();
            }
        });

        mostrarAdmin = (Button) findViewById(R.id.adminBoton);
        mostrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAdminSelectionDialog();
            }
        });
    }

    private void abrirPaginaWeb() {
        String url = "https://cecytem.mx/deo/gui/LogIn.jsp";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void showAdminSelectionDialog() {
        final CharSequence[] adminOptions = {"Administrador Control Escolar", "Administrador Vinculacion"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona el tipo de administrador");
        builder.setItems(adminOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // El usuario ha seleccionado un tipo de administrador
                String selectedAdmin = adminOptions[which].toString();

                // Puedes realizar acciones dependiendo de la selección
                if (selectedAdmin.equals("Administrador Control Escolar")) {
                    // Iniciar sesión para Admin A
                    Toast.makeText(Activity_Login.this, "Iniciar sesión como Administrador Control Escolar", Toast.LENGTH_SHORT).show();
                    // Aquí puedes abrir la actividad de inicio de sesión para Admin A
                    Intent adminCE = new Intent(Activity_Login.this, AdminCE_Login.class);
                    startActivity(adminCE);
                } else if (selectedAdmin.equals("Administrador Vinculacion")) {
                    // Iniciar sesión para Admin B
                    Toast.makeText(Activity_Login.this, "Iniciar sesión como Administrador Vinculacion", Toast.LENGTH_SHORT).show();
                    // Aquí puedes abrir la actividad de inicio de sesión para Admin B
                    Intent adminVIN = new Intent(Activity_Login.this, AdminVin_Login.class);
                    startActivity(adminVIN);
                }
            }
        });

        builder.show();
    }

    public void login(View view) {
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
                    msj = obj.login(parameter[1], parameter[2]);
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
                Toast.makeText(Activity_Login.this, "Bienvenido...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity_Login.this, Activity_Inicio.class);
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
    public void registrarUsuario(View view) {
        Intent registrar = new Intent(Activity_Login.this, Activity_Registro.class);
        startActivity(registrar);
    }
    public void aspiranteCambio(View view) {
        Intent iniciar = new Intent(Activity_Login.this, Activity_Aspirantes.class);
        startActivity(iniciar);
        Toast.makeText(Activity_Login.this, "Bienvenido/a aspirante",Toast.LENGTH_SHORT).show();
    }
}