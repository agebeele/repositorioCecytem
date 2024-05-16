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

public class formulario_social extends AppCompatActivity {
    String crud;
    static WebService obj = new WebService();
    EditText nombreAlumno, promedio , telefonoCasa,telefonoCelular, grupo, correo, nombreD, cct, turno, domicilioD, telefonoD, nivelG, nombreR, cargoR, actividades, horario, nombreP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_social);
        nombreAlumno = (EditText) findViewById(R.id.txtNombre);
        promedio = (EditText) findViewById(R.id.txtpromedio);
        telefonoCasa = (EditText) findViewById(R.id.txtTelefonoCasa);
        telefonoCelular = (EditText) findViewById(R.id.txtTelefonoCelular);
        grupo = (EditText) findViewById(R.id.txtgrupo);
        correo = (EditText) findViewById(R.id.txtCorreo);
        nombreD = (EditText) findViewById(R.id.txtNombreDependencia);
        cct = (EditText) findViewById(R.id.txtCCT);
        turno = (EditText) findViewById(R.id.txtTurno);
        domicilioD = (EditText) findViewById(R.id.txtDomicilioDependencia);
        telefonoD = (EditText) findViewById(R.id.txtTelefonoDependencia);
        nivelG= (EditText) findViewById(R.id.txtNivelGobierno);
        nombreR = (EditText) findViewById(R.id.txtNombreResponsable);
        cargoR = (EditText) findViewById(R.id.txtCargoResponsable);
        actividades = (EditText) findViewById(R.id.txtActividades);
        horario = (EditText) findViewById(R.id.txtHorarioPrestacion);
        nombreP = (EditText) findViewById(R.id.txtNombrePadreTutor);
    }
    public void enviarDatosCredencial(View view) {

        if (nombreAlumno.getText().toString().isEmpty()
                || promedio.getText().toString().isEmpty()
                || telefonoCasa.getText().toString().isEmpty()
                || telefonoCelular.getText().toString().isEmpty()
                || grupo.getText().toString().isEmpty()
                ||correo.getText().toString().isEmpty()
                ||nombreD.getText().toString().isEmpty()
                ||cct.getText().toString().isEmpty()
                ||turno.getText().toString().isEmpty()
                ||domicilioD.getText().toString().isEmpty()
                ||telefonoD.getText().toString().isEmpty()
                ||nivelG.getText().toString().isEmpty()
                ||nombreR.getText().toString().isEmpty()
                ||cargoR.getText().toString().isEmpty()
                ||actividades.getText().toString().isEmpty()
                ||horario.getText().toString().isEmpty()
                ||nombreP.getText().toString().isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "Datos Faltantes", Toast.LENGTH_LONG).show();
        } else {
            crud = "solicitar";
            // Obtener fecha y hora actual por separado
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String fechaActual = dateFormat.format(calendar.getTime());
            String horaActual = timeFormat.format(calendar.getTime());

            new formulario_social.MiAsyncTask().execute(crud,
                    nombreAlumno.getText().toString(),
                    promedio.getText().toString(),
                    telefonoCasa.getText().toString(),
                    telefonoCelular.getText().toString(),
                    grupo.getText().toString(),
                    correo.getText().toString(),
                    nombreD.getText().toString(),
                    cct.getText().toString(),
                    turno.getText().toString(),
                    domicilioD.getText().toString(),
                    telefonoD.getText().toString(),
                    nivelG.getText().toString(),
                    nombreR.getText().toString(),
                    cargoR.getText().toString(),
                    actividades.getText().toString(),
                    horario.getText().toString(),
                    nombreP.getText().toString(),
                    fechaActual,
                    horaActual);

            Toast.makeText(formulario_social.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
        }
    }

    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "solicitar": //Cambiar por el ServicioWeb
                    msj = obj.solicitarServicio(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7]
                            ,parameter[8], parameter[9], parameter[10], parameter[11], parameter[12], parameter[13], parameter[14], parameter[15],
                            parameter[16], parameter[17], parameter[18], parameter[19]);
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
                    nombreAlumno.setText(json_data.getString("nombre"));
                    promedio.setText(json_data.getString("promedio"));
                    telefonoCasa.setText(json_data.getString("telefonocasa"));
                    telefonoCelular.setText(json_data.getString("telefonocelular"));
                    grupo.setText(json_data.getString("grupo"));
                    correo.setText(json_data.getString("correoins"));
                    nombreD.setText(json_data.getString("nombredepe"));
                    cct.setText(json_data.getString("cct"));
                    turno.setText(json_data.getString("turno"));
                    domicilioD.setText(json_data.getString("domiciliode"));
                    telefonoD.setText(json_data.getString("telefonodepe"));
                    nivelG.setText(json_data.getString("nivel"));
                    nombreR.setText(json_data.getString("nombreres"));
                    cargoR.setText(json_data.getString("cargores"));
                    actividades.setText(json_data.getString("actividades"));
                    horario.setText(json_data.getString("horario"));
                    nombreP.setText(json_data.getString("nombrepadre"));

                    Toast.makeText(formulario_social.this, "Usuario registrado.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                nombreAlumno.setText("");
                promedio.setText("");
                telefonoCasa.setText("");
                telefonoCelular.setText("");
                grupo.setText("");
                correo.setText("");
                nombreD.setText("");
                cct.setText("");
                turno.setText("");
                domicilioD.setText("");
                telefonoD.setText("");
                nivelG.setText("");
                nombreR.setText("");
                cargoR.setText("");
                actividades.setText("");
                horario.setText("");
                nombreP.setText("");

            }
        }
    }
}