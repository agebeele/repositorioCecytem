package com.example.dual.inicioAlumnos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dual.R;
import com.example.dual.WebService;
import com.example.dual.chatBotCE.chatBot_inicio;
import com.example.dual.formularios.formulario_carrera;
import com.example.dual.formularios.formulario_constancia;
import com.example.dual.formularios.formulario_credencial;
import com.example.dual.formularios.formulario_grupo;
import com.example.dual.formularios.formulario_historial;
import com.example.dual.formularios.formulario_plantel;
import com.example.dual.formularios.formulario_turno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class fragment_control_escolar extends Fragment {

    TextView datos_controlEscolar, nombreBievenida;
    Button tramite;
    private FloatingActionButton floatingActionButton;
    Intent intent;
    WebService obj = new WebService();
    Spinner spinner;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_control_escolar, container, false);

        // Inicializar Spinner y TextView
        spinner = v.findViewById(R.id.spinner_list);
        textView = v.findViewById(R.id.texto_spinner);

        floatingActionButton = v.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirChat();
            }
        });

        tramite = (Button) v.findViewById(R.id.boton_tramite);
        tramite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedItem = (String) spinner.getSelectedItem();
                switch (selectedItem) {
                    case "Credencial":
                        Intent intent_credencial = new Intent(getActivity(), formulario_credencial.class);
                        startActivity(intent_credencial);
                        break;
                    case "Constancia":
                        Intent intent_constancia = new Intent(getActivity(), formulario_constancia.class);
                        startActivity(intent_constancia);
                        break;
                    case "Historial Academico":
                        Intent intent_historial = new Intent(getActivity(), formulario_historial.class);
                        startActivity(intent_historial);
                        break;
                    case "Cambio de turno":
                        Intent intent_turno = new Intent(getActivity(), formulario_turno.class);
                        startActivity(intent_turno);
                        break;
                    case "Cambio de carrera":
                        Intent intent_carrera = new Intent(getActivity(), formulario_carrera.class);
                        startActivity(intent_carrera);
                        break;
                    case "Cambio de grupo":
                        Intent intent_grupo = new Intent(getActivity(), formulario_grupo.class);
                        startActivity(intent_grupo);
                        break;
                    case "Cambio de plantel":
                        Intent intent_plantel = new Intent(getActivity(), formulario_plantel.class);
                        startActivity(intent_plantel);
                        break;
                    default:
                        break;
                }
            }
        });

        datos_controlEscolar = (TextView) v.findViewById(R.id.datosCE);
        datos_controlEscolar.setText("En Control escolar es un portal para que el estudiante pueda realizar distintos trámites, por ejemplo:" +
                "\n - Trámite o reposición de una credencial." +
                "\n - Trámite de una Constancia de estudios." +
                "\n - Solicitudes para cambio de turno, grupo, carrera o plantel, es a fin de semestre." +
                "\n - Revalidación de materias." +
                "\n - Información para la titulación y/o cédula profesional");

        // Obtén la fecha actual
        Calendar fechaActual = Calendar.getInstance();

        // Establece las fechas de inicio y fin del período vacacional por semestre
        Calendar fechaInicioVacaciones = Calendar.getInstance();
        fechaInicioVacaciones.set(Calendar.YEAR, 2024); // Cambia el año según tu necesidad
        fechaInicioVacaciones.set(Calendar.MONTH, Calendar.JULY); // Cambia el mes según tu necesidad
        fechaInicioVacaciones.set(Calendar.DAY_OF_MONTH, 1); // Cambia el día según tu necesidad

        Calendar fechaFinVacaciones = Calendar.getInstance();
        fechaFinVacaciones.set(Calendar.YEAR, 2024); // Cambia el año según tu necesidad
        fechaFinVacaciones.set(Calendar.MONTH, Calendar.SEPTEMBER); // Cambia el mes según tu necesidad
        fechaFinVacaciones.set(Calendar.DAY_OF_MONTH, 31); // Cambia el día según tu necesidad

        // Establece las fechas de inicio y fin del período vacacional por semestre
        fechaInicioVacaciones.set(Calendar.YEAR, 2024); // Cambia el año según tu necesidad
        fechaInicioVacaciones.set(Calendar.MONTH, Calendar.JANUARY); // Cambia el mes según tu necesidad
        fechaInicioVacaciones.set(Calendar.DAY_OF_MONTH, 13); // Cambia el día según tu necesidad

        fechaFinVacaciones.set(Calendar.YEAR, 2024); // Cambia el año según tu necesidad
        fechaFinVacaciones.set(Calendar.MONTH, Calendar.DECEMBER); // Cambia el mes según tu necesidad
        fechaFinVacaciones.set(Calendar.DAY_OF_MONTH, 31); // Cambia el día según tu necesidad

// Obtén el array de opciones desde los recursos
        String[] opciones = getResources().getStringArray(R.array.spinner_ce);

// Verifica si la fecha actual está dentro del período vacacional
        if (fechaActual.after(fechaInicioVacaciones) && fechaActual.before(fechaFinVacaciones)) {
            // Período vacacional, permitir todas las opciones en el Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireActivity(), android.R.layout.simple_spinner_item, opciones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } else {
            // Fuera del período vacacional, filtra opciones específicas en el Spinner
            List<String> opcionesFiltradas = new ArrayList<>(Arrays.asList(opciones));
            opcionesFiltradas.remove("Cambio de turno");
            opcionesFiltradas.remove("Cambio de carrera");
            opcionesFiltradas.remove("Cambio de grupo");
            opcionesFiltradas.remove("Cambio de plantel");

            ArrayAdapter<String> adapterFiltrado = new ArrayAdapter<>(
                    requireActivity(), android.R.layout.simple_spinner_item, opcionesFiltradas);
            adapterFiltrado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapterFiltrado);
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int position, long id) {
                // Obtiene el item actual seleccionado
                String selectedItem = (String) adapterView.getItemAtPosition(position);

                switch (selectedItem) {
                    case "Credencial":
                        textView.setText("Para reponer una credencial son necesarios los siguientes datos:" +
                                "\n - Nombre completo" +
                                "\n - Grupo" +
                                "\n - Matrícula" +
                                "\n - Turno");
                        break;
                    case "Constancia":
                        textView.setText("Para solicitar una constancia son necesarios los siguientes datos:" +
                                "\n - Nombre completo" +
                                "\n - Grupo" +
                                "\n - Matrícula" +
                                "\n - Carrera" +
                                "\n Estas también se pueden solicitar con:" +
                                "\n - Fotografía" +
                                "\n - Firma" +
                                "\n - Historial académico");
                        break;
                    case "Historial Academico":
                        textView.setText("Para solicitar un Historial Académico, puede ser desde el DEO, sin embargo, para pedir este documento son necesarios los siguientes datos" +
                                "\n - Nombre completo" +
                                "\n - Grupo" +
                                "\n - Matrícula" +
                                "\n - Turno");
                        break;
                    case "Cambio de turno":
                        textView.setText("Para solicitar un cambio de turno, deberás llenar el siguiente formato, el cual cuenta con los siguientes apartados:" +
                                "\n - Nombre completo" +
                                "\n - Grupo" +
                                "\n - Matrícula" +
                                "\n - Carrera" +
                                "\n - Motivo por el cual quieres hacer tu cambio" +
                                "\n - Promedio" +
                                "\n - Nombre o firma del alumno/a" +
                                "\n  Historial académico");
                        break;
                    case "Cambio de carrera":
                        textView.setText("Para solicitar un cambio de carrera es necesario acreditar todas tus materias y mantener un promedio de 8.5, sin embargo, debes de llenar el siguiente formato el cual lleva:" +
                                "\n - Nombre completo" +
                                "\n - Grupo" +
                                "\n - Especialidad que cursa" +
                                "\n - Especialidad a la cual se quiere cambiar" +
                                "\n - Turno" +
                                "\n - Motivo por el cual quieres hacer tu cambio" +
                                "\n - Promedio" +
                                "\n - Nombre o firma del alumno/a");
                        break;
                    case "Cambio de grupo":
                        textView.setText("Para solicitar un cambio de grupo es necesario acreditar todas tus materias y mantener un promedio de 8.5 para asegurar tu cambio, sin embargo, debes de llenar el siguiente formato el cual lleva:" +
                                "\n - Nombre completo" +
                                "\n - Grupo" +
                                "\n - Carrera" +
                                "\n - Turno" +
                                "\n - Promedio" +
                                "\n - Motivo por el cual quieres hacer tu cambio" +
                                "\n - Nombre o firma del alumno/a");
                        break;
                    case "Cambio de plantel":
                        textView.setText("Para solicitar un cambio de plantel es necesario acreditar todas tus materias y mantener un promedio de 8.5 para asegurar tu cambio, sin embargo, debes de llenar el siguiente formato el cual lleva:" +
                                "\n - Nombre completo" +
                                "\n - Grupo" +
                                "\n - Carrera" +
                                "\n - Plantel y especialidad a el cual te cambiarás" +
                                "\n - Turno" +
                                "\n - Promedio" +
                                "\n - Motivo por el cual quieres hacer tu cambio" +
                                "\n - Nombre o firma del alumno/a");
                        break;
                    default:
                        textView.setText("Selecciona una opción");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Maneja la situación en la que no se ha seleccionado nada
                textView.setText("Selecciona una opción");
            }
        });

        nombreBievenida = (TextView) v.findViewById(R.id.bienvenida_txt1);

        // Recuperar la matrícula (nombre de usuario) guardada en SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String nombreUsuario = preferences.getString("matricula", "");

        if (!nombreUsuario.isEmpty()) {
            // Ejecutar AsyncTask para obtener los datos del usuario
            MiAsyncTask miAsyncTask = new MiAsyncTask();
            miAsyncTask.execute("datosUser", nombreUsuario);
        }

        return v;
    }

    class MiAsyncTask extends AsyncTask<String, String, String> {
        private SharedPreferences preferences;

        @Override
        protected String doInBackground(String... parameter) {
            String msj = null;
            switch (parameter[0]) {
                case "datosUser":
                    // Utilizar el nombre de usuario obtenido de SharedPreferences
                    msj = obj.datosUsuario(parameter[1]);
                    publishProgress(msj);
                    break;
                default:
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Recuperar las SharedPreferences en onPreExecute
            preferences = getActivity().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            Log.d("JSON_DEBUG", progress[0]);

            try {
                JSONObject jsonObject = new JSONObject(progress[0]);
                boolean success = jsonObject.getBoolean("success");

                if (success) {
                    // Utilizar el nombre de usuario obtenido del JSON
                    JSONArray data = jsonObject.getJSONArray("data");
                    JSONObject usuario = data.getJSONObject(0);
                    String nombreUsuario = usuario.getString("nombre");

                    // Concatenar el mensaje de bienvenida con el nombre del usuario
                    String mensajeBienvenida = "Bienvenido " + nombreUsuario;

                    // Asignar el mensaje concatenado al TextView
                    nombreBievenida.setText(mensajeBienvenida);

                } else {
                    String mensaje = jsonObject.getString("message");
                    Toast.makeText(requireActivity(), mensaje, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                nombreBievenida.setText("");
                Toast.makeText(requireActivity(), "Error al obtener los datos del usuario", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void abrirChat() {
        intent = new Intent(getActivity(), chatBot_inicio.class);
        startActivity(intent);
    }
}