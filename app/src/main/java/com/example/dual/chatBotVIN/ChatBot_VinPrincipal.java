package com.example.dual.chatBotVIN;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dual.R;

public class ChatBot_VinPrincipal extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatBot_VINAdapter chatBot_vinAdapter;
    private Spinner questionSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot_vin_principal);

        recyclerView = findViewById(R.id.recyclerViewVIN);
        /*FloatingActionButton fab = findViewById(R.id.fab);*/
        questionSpinner = findViewById(R.id.simple_spinner_itemVIN);

        chatBot_vinAdapter = new ChatBot_VINAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatBot_vinAdapter);

        // Configurar el adaptador del Spinner con preguntas
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.questions,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionSpinner.setAdapter(adapter);

        // Manejar la selección del Spinner con switch
        questionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtener la opción seleccionada (se suma 1 porque los índices en el Spinner comienzan desde 0)
                int selectedOption = position + 1;

                // Obtener respuesta personalizada según la opción seleccionada
                String botResponse = getCustomResponse(selectedOption);

                // Añadir mensaje del bot a la lista
                chatBot_vinAdapter.addMessage(new ChatBot_VINMessage("ChatBot", botResponse));

                // Desplazar hacia abajo para mostrar el último mensaje
                recyclerView.smoothScrollToPosition(chatBot_vinAdapter.getItemCount() - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No hacer nada en este caso
            }
        });

            /*fab.setOnClickListener(view -> {
                // No hay entrada de texto para enviar, ya que has eliminado el EditText
            });*/
    }

    private String getCustomResponse(int selectedOption) {
        // Agrega lógica para devolver respuestas personalizadas según la opción seleccionada
        switch (selectedOption) {
            case 1:
                return "Bienvenido elige una pregunta";
            case 2:
                return "El Servicio Social lo deberas iniciar cuando este en 3 Semestre, el periodo del Servicio Social es de 6 meses, los cuales se llevan en los meses de Marzo-Septiembre. ";
            // Agrega más casos según sea necesario
            case 3:
                return "En el Servicio Social deberas hacer 480 horas durante los 6 Meses";
            case 4:
                return "Si no realizas el Servicio Social perderas la oportunidad de poder titularte, hasta que realices este proceso";
            case 5:
                return "La carta de aceptacion es aquella que te entregan en la Dependecia o Empresa donde realices el Serivicio, esta carta la deberas entregar en la oficina de Vinculación";
            case 6:
                return "Donde puedes realizar tu Servicio Social puede ser en Dependecias de Gobierno de Coacalco es lo mas recomendable, en Escuelas publicas dentro del municipio de Coacalco, si tienes otras opciones puedes preguntar si tu opcion es viable en la oficina de Vinculacion";
            case 7:
                return "El Servicio Social no se puede liberar, deberas realizar el proceso y cumplir con los 6 meses como se indica";
            case 8:
                return "El informe es el documento o el seguimiento de tus horas y las actividades que estes realizando en tu Servicio";
            case 9:
                return "Para Tramitar tu beca deberas estar al pendiente de la fecha de entega de documentos para llevar acabo tu registro";
            case 10:
                return "Para saber el dia de tu deposito deberas consultarlo en el Buscador de Becas 'buscador.becasbenitojuarez.gob.mx/consulta/' e ingresa tu CURP y verifica si esta activa en caso de estar activa en la parte de abajo aparecera el dia que podras retirar, para mas informacion visita la oficina de Vinculacion";
            case 11:
                return "Para la entrega de tu tarjeta llegara al plantel, en caso de no recoger tu tarjeta deberas ir al Municipio de Ecatepec a recogerla, para mas informacion visita la oficina de Vinculacion";
            default:
                return "Respuesta predeterminada para opciones no manejadas";
        }
    }
}