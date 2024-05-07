package com.example.dual.alumnos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;
import com.example.dual.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

public class Activity_Eventos extends AppCompatActivity {
    private DatePicker datePicker;
    private EditText eventTitle;
    private EditText eventDescription;
    private TextView eventTextView;
    private WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        datePicker = findViewById(R.id.datePicker);
        eventTitle = findViewById(R.id.eventTitle);
        eventDescription = findViewById(R.id.eventDescription);
        eventTextView = findViewById(R.id.eventTextView);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarEvento();
            }
        });

        // Configurar el DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                actualizarEventos(year, monthOfYear, dayOfMonth);
            }
        });

        // Actualizar eventos para mostrar los eventos de hoy al iniciar la actividad
        actualizarEventos(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void guardarEvento() {
        String titulo = eventTitle.getText().toString();
        String descripcion = eventDescription.getText().toString();
        String fecha = obtenerFechaDatePicker();

        // Guardar el evento localmente
        guardarEventoLocalmente(titulo, descripcion, fecha);

        // Limpiar campos
        eventTitle.setText("");
        eventDescription.setText("");

        // Actualizar eventos para mostrar el nuevo evento agregado
        actualizarEventos(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
    }

    private void actualizarEventos(int year, int monthOfYear, int dayOfMonth) {
        String fechaSeleccionada = obtenerFecha(year, monthOfYear, dayOfMonth);
        mostrarEventosLocalmente(fechaSeleccionada);
        // Obtener eventos de la base de datos remota
        new ObtenerEventosRemotosTask().execute(fechaSeleccionada);
    }

    private String obtenerFechaDatePicker() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1; // Se suma 1 porque enero es 0
        int year = datePicker.getYear();

        // Formatea la fecha en formato "yyyy-MM-dd"
        return String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
    }

    private String obtenerFecha(int year, int month, int day) {
        return year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day);
    }

    private void guardarEventoLocalmente(String titulo, String descripcion, String fecha) {
        SharedPreferences sharedPreferences = getSharedPreferences("Eventos", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            JSONArray eventosArray;
            if (sharedPreferences.contains("eventos")) {
                eventosArray = new JSONArray(sharedPreferences.getString("eventos", "[]"));
            } else {
                eventosArray = new JSONArray();
            }
            JSONObject evento = new JSONObject();
            evento.put("titulo", titulo);
            evento.put("descripcion", descripcion);
            evento.put("fecha", fecha);
            eventosArray.put(evento);
            editor.putString("eventos", eventosArray.toString());
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void mostrarEventosLocalmente(String fechaSeleccionada) {
        SharedPreferences sharedPreferences = getSharedPreferences("Eventos", MODE_PRIVATE);
        if (sharedPreferences.contains("eventos")) {
            try {
                JSONArray eventosArray = new JSONArray(sharedPreferences.getString("eventos", "[]"));

                // Mostrar solo los eventos que coinciden con la fecha seleccionada
                StringBuilder eventosMostrados = new StringBuilder();

                for (int i = 0; i < eventosArray.length(); i++) {
                    JSONObject evento = eventosArray.getJSONObject(i);

                    // Obtén la fecha del evento
                    String fechaEvento = evento.getString("fecha");

                    // Compara las fechas formateadas
                    if (fechaEvento.equals(fechaSeleccionada)) {
                        // Agregar la información del evento al StringBuilder
                        String titulo = evento.getString("titulo");
                        String descripcion = evento.getString("descripcion");
                        eventosMostrados.append("Título: ").append(titulo).append("\nDescripción: ").append(descripcion).append("\n\n");
                    }
                }

                // Mostrar los eventos en el TextView
                if (eventosMostrados.length() > 0) {
                    eventTextView.setText(eventosMostrados.toString());
                } else {
                    eventTextView.setText("No hay eventos locales para esta fecha.");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            eventTextView.setText("No hay eventos locales para esta fecha.");
        }
    }

    private class ObtenerEventosRemotosTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // params[0]: fecha seleccionada
            return obj.obtenerEventos(params[0]);
        }

        @Override
        protected void onPostExecute(String eventosJson) {
            try {
                // Convierte el string JSON a un objeto JSON
                JSONArray eventosArray = new JSONArray(eventosJson);

                // Muestra los eventos remotos en el TextView
                StringBuilder eventosRemotos = new StringBuilder();

                for (int i = 0; i < eventosArray.length(); i++) {
                    JSONObject evento = eventosArray.getJSONObject(i);

                    // Agregar la información del evento al StringBuilder
                    String titulo = evento.getString("titulo");
                    String descripcion = evento.getString("descripcion");
                    eventosRemotos.append("Título: ").append(titulo).append("\nDescripción: ").append(descripcion).append("\n\n");
                }

                // Mostrar los eventos remotos en el TextView
                if (eventosRemotos.length() > 0) {
                    eventTextView.append("\nEventos Remotos:\n\n" + eventosRemotos.toString());
                } else {
                    eventTextView.append("\nNo hay eventos remotos para esta fecha.");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}