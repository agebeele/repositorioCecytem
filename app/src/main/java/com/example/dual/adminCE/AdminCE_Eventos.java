package com.example.dual.adminCE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dual.R;
import com.example.dual.WebService;
import com.example.dual.alumnos.Activity_Eventos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

public class AdminCE_Eventos extends AppCompatActivity {
    private DatePicker datePicker;
    private EditText eventTitle;
    private EditText eventDescription;
    private TextView eventTextView;
    private WebService obj = new WebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ce_eventos);

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

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEvento();
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
    }

    private void guardarEvento() {
        String titulo = eventTitle.getText().toString();
        String descripcion = eventDescription.getText().toString();
        String fecha = obtenerFechaDatePicker();

        // Llama al AsyncTask para enviar datos al servidor
        new GuardarEventoTask().execute(titulo, descripcion, fecha);
    }

    private void actualizarEventos(int year, int monthOfYear, int dayOfMonth) {
        String fechaSeleccionada = obtenerFecha(year, monthOfYear, dayOfMonth);

        // Llama al AsyncTask para obtener eventos del servidor
        new ObtenerEventosTask().execute(fechaSeleccionada);
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

    private class GuardarEventoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // params[0]: titulo, params[1]: descripcion, params[2]: fecha
            return obj.agregarEvento(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(String resultado) {
            // Muestra el resultado en un mensaje Toast
            Toast.makeText(AdminCE_Eventos.this, "Evento agregado: " + resultado, Toast.LENGTH_SHORT).show();

            // Vacía los campos del título y la descripción
            eventTitle.setText("");
            eventDescription.setText("");

            // Actualiza los eventos para mostrar el nuevo evento agregado
            actualizarEventos(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        }
    }

    private class ObtenerEventosTask extends AsyncTask<String, Void, String> {
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

                // Muestra solo los eventos que coinciden con la fecha seleccionada
                StringBuilder eventosMostrados = new StringBuilder();

                for (int i = 0; i < eventosArray.length(); i++) {
                    JSONObject evento = eventosArray.getJSONObject(i);

                    // Obtén la fecha del evento
                    String fechaEvento = evento.getString("fecha");

                    // Agrega la información del evento al StringBuilder
                    String titulo = evento.getString("titulo");
                    String descripcion = evento.getString("descripcion");
                    eventosMostrados.append("Título: ").append(titulo).append("\nDescripción: ").append(descripcion).append("\nFecha: ").append(fechaEvento).append("\n\n");
                }

                // Muestra los eventos en el TextView
                if (eventosMostrados.length() > 0) {
                    eventTextView.setText(eventosMostrados.toString());
                } else {
                    eventTextView.setText("No hay eventos para esta fecha.");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void eliminarEvento() {
        // Obtener la fecha seleccionada del DatePicker
        String fecha = obtenerFechaDatePicker();
        // Eliminar el evento de la base de datos remota
        eliminarEventoRemoto(fecha);
    }

    // Método para eliminar el evento de la base de datos remota
    private void eliminarEventoRemoto(String fecha) {
        new EliminarEventoTask().execute(fecha);
    }

    private class EliminarEventoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // params[0]: fecha
            return obj.eliminarEvento(params[0]);
        }

        @Override
        protected void onPostExecute(String resultado) {
            if (resultado != null && !resultado.isEmpty()) {
                // Muestra el resultado en un mensaje Toast
                Toast.makeText(AdminCE_Eventos.this, "Resultado: " + resultado, Toast.LENGTH_SHORT).show();

                // Aquí puedes manejar la respuesta del servidor si es necesario
                if (resultado.equals("200")) {
                    // El evento se eliminó correctamente
                    // Puedes realizar acciones adicionales si es necesario
                } else if (resultado.equals("500")) {
                    // Hubo un error en la eliminación del evento
                    // Puedes manejar este caso según tus necesidades
                } else {
                    // Otro tipo de respuesta del servidor
                    // Puedes manejar este caso según tus necesidades
                }
            } else {
                Toast.makeText(AdminCE_Eventos.this, "No se recibió respuesta del servidor", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
