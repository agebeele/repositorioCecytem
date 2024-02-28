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

public class formulario_becas extends AppCompatActivity {
    EditText curp, paterno, materno, nombres, fechaNacimieto, sexo, estadoNacimiento,
            situacionAcademica, semestreActual, correoPersonal, correoInstitucionas,
            telefonoCasa, telefonoAlumno, telefonoPadre, nombreCalle, noExterior,
            noInterior, nombreColonia, municipio, estadoDomicilio, codigoPostal,
            escuelaSecundaria, promedioGeneral;
    String crud;
    static WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_becas);

        curp = findViewById(R.id.txtCurp);
        paterno = findViewById(R.id.txtApellidoPaterno);
        materno = findViewById(R.id.txtApellidoMaterno);
        nombres = findViewById(R.id.txtNombres);
        fechaNacimieto = findViewById(R.id.txtFechaNacimiento);
        sexo = findViewById(R.id.txtSexo);
        estadoNacimiento = findViewById(R.id.txtEstadoNacimiento);
        situacionAcademica = findViewById(R.id.txtSituacionAcademica);
        semestreActual = findViewById(R.id.txtSemestreActual);
        correoPersonal = findViewById(R.id.txtCorreoPersonal);
        correoInstitucionas = findViewById(R.id.txtCorreoInstitucional);
        telefonoCasa = findViewById(R.id.txtTelefonoCasa);
        telefonoAlumno = findViewById(R.id.txtTelefonoCelularAlumno);
        telefonoPadre = findViewById(R.id.txtTelefonoCelularPadre);
        nombreCalle = findViewById(R.id.txtNombreCalle);
        noExterior = findViewById(R.id.txtNoExterior);
        noInterior = findViewById(R.id.txtNoInterior);
        nombreColonia = findViewById(R.id.txtNombreColonia);
        municipio = findViewById(R.id.txtMunicipio);
        estadoDomicilio = findViewById(R.id.txtEstadoDomicilio);
        codigoPostal = findViewById(R.id.txtCodigoPostal);
        escuelaSecundaria = findViewById(R.id.txtEscuelaSecundaria);
        promedioGeneral = findViewById(R.id.txtPromedioSecundaria);
    }
    public void enviarDatosBecas(View view) {
        if (curp.getText().toString().isEmpty()
                || paterno.getText().toString().isEmpty()
                || materno.getText().toString().isEmpty()
                || nombres.getText().toString().isEmpty()
                || fechaNacimieto.getText().toString().isEmpty()
                || sexo.getText().toString().isEmpty()
                || estadoNacimiento.getText().toString().isEmpty()
                || situacionAcademica.getText().toString().isEmpty()
                || semestreActual.getText().toString().isEmpty()
                || correoPersonal.getText().toString().isEmpty()
                || correoInstitucionas.getText().toString().isEmpty()
                || telefonoCasa.getText().toString().isEmpty()
                || telefonoAlumno.getText().toString().isEmpty()
                || telefonoPadre.getText().toString().isEmpty()
                || nombreCalle.getText().toString().isEmpty()
                || noExterior.getText().toString().isEmpty()
                || noInterior.getText().toString().isEmpty()
                || nombreColonia.getText().toString().isEmpty()
                || municipio.getText().toString().isEmpty()
                || estadoDomicilio.getText().toString().isEmpty()
                || codigoPostal.getText().toString().isEmpty()
                || escuelaSecundaria.getText().toString().isEmpty()
                || promedioGeneral.getText().toString().isEmpty()) {
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
                    curp.getText().toString(),
                    nombres.getText().toString(),
                    paterno.getText().toString(),
                    materno.getText().toString(),
                    fechaNacimieto.getText().toString(),
                    sexo.getText().toString(),
                    estadoNacimiento.getText().toString(),
                    situacionAcademica.getText().toString(),
                    semestreActual.getText().toString(),
                    correoPersonal.getText().toString(),
                    correoInstitucionas.getText().toString(),
                    telefonoCasa.getText().toString(),
                    telefonoAlumno.getText().toString(),
                    telefonoPadre.getText().toString(),
                    nombreCalle.getText().toString(),
                    noExterior.getText().toString(),
                    noInterior.getText().toString(),
                    nombreColonia.getText().toString(),
                    municipio.getText().toString(),
                    estadoDomicilio.getText().toString(),
                    codigoPostal.getText().toString(),
                    escuelaSecundaria.getText().toString(),
                    promedioGeneral.getText().toString(),
                    fechaActual,
                    horaActual);


            Toast.makeText(formulario_becas.this, "Solicitud enviada.", Toast.LENGTH_SHORT).show();
        }
    }
    class MiAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "solicitar":
                    msj = obj.solicitarBeca(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7],
                            parameter[8], parameter[9], parameter[10], parameter[11], parameter[12], parameter[13], parameter[14], parameter[15],
                            parameter[16], parameter[17], parameter[18], parameter[19], parameter[20], parameter[21], parameter[22], parameter[23],
                            parameter[24], parameter[25]);
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
                    curp.setText(json_data.getString("curp"));
                    paterno.setText(json_data.getString("paterno"));
                    materno.setText(json_data.getString("materno"));
                    nombres.setText(json_data.getString("nombre"));
                    fechaNacimieto.setText(json_data.getString("fecha_nacimiento"));
                    sexo.setText(json_data.getString("sexo"));
                    estadoNacimiento.setText(json_data.getString("estado_nacimiento"));
                    situacionAcademica.setText(json_data.getString("situacion_academica"));
                    semestreActual.setText(json_data.getString("semestre_actual"));
                    correoPersonal.setText(json_data.getString("correo_personal"));
                    correoInstitucionas.setText(json_data.getString("correo_institucional"));
                    telefonoCasa.setText(json_data.getString("telefono_casa"));
                    telefonoAlumno.setText(json_data.getString("telefono_alumno"));
                    telefonoPadre.setText(json_data.getString("telefono_padre"));
                    nombreCalle.setText(json_data.getString("nombre_calle"));
                    noExterior.setText(json_data.getString("no_exterior"));
                    noInterior.setText(json_data.getString("no_interior"));
                    nombreColonia.setText(json_data.getString("nombre_colonia"));
                    municipio.setText(json_data.getString("municipio"));
                    estadoDomicilio.setText(json_data.getString("estado_domicilio"));
                    codigoPostal.setText(json_data.getString("codigo_postal"));
                    escuelaSecundaria.setText(json_data.getString("escuela_secundaria"));
                    promedioGeneral.setText(json_data.getString("promedio_general"));

                    Toast.makeText(formulario_becas.this, "Usuario registrado.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                curp.setText("");
                paterno.setText("");
                materno.setText("");
                nombres.setText("");
                fechaNacimieto.setText("");
                sexo.setText("");
                estadoNacimiento.setText("");
                situacionAcademica.setText("");
                semestreActual.setText("");
                correoPersonal.setText("");
                correoInstitucionas.setText("");
                telefonoCasa.setText("");
                telefonoAlumno.setText("");
                telefonoPadre.setText("");
                nombreCalle.setText("");
                noExterior.setText("");
                noInterior.setText("");
                nombreColonia.setText("");
                municipio.setText("");
                estadoDomicilio.setText("");
                codigoPostal.setText("");
                escuelaSecundaria.setText("");
                promedioGeneral.setText("");
            }
        }
    }
}