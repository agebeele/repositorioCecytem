package com.example.dual.chatBotCE;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dual.R;

public class chatBot_inicio extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private ChatBot chatBot;
    private Spinner questionSpinnerCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot_inicio);

        recyclerView = findViewById(R.id.recyclerViewCE);
        //FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        questionSpinnerCE = findViewById(R.id.simple_spinner_itemCE);

        chatAdapter = new ChatAdapter();
        //chatBot = new ChatBot();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        // Configurar el adaptador del Spinner con preguntas
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.questionsCE,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionSpinnerCE.setAdapter(adapter);

        // Manejar la selección del Spinner con switch
        questionSpinnerCE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtener la opción seleccionada (se suma 1 porque los índices en el Spinner comienzan desde 0)
                int selectedOption = position + 1;

                // Obtener respuesta personalizada según la opción seleccionada
                String botResponse = getCustomResponse(selectedOption);

                // Añadir mensaje del bot a la lista
                chatAdapter.addMessage(new ChatMessage("ChatBot", botResponse));

                // Desplazar hacia abajo para mostrar el último mensaje
                recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No hacer nada en este caso
            }
        });

        //ChatAdapter chatAdapter = new ChatAdapter(this); // 'this' es el contexto de la actividad
        //fab.setOnClickListener(view -> sendMessage());
    }

    private String getCustomResponse(int selectedOption) {
        // Agrega lógica para devolver respuestas personalizadas según la opción seleccionada
        switch (selectedOption) {
            case 1:
                return "Bienvenido elige una pregunta";
            case 2:
                return "El costo para una reposición de credencial es el siguiente:" +
                        "\n Credencial para alumno de 1er y 2do Semestre: $10" +
                        "\n Credencial para alumno de 3er, 4to, 5to y 6to Semestre es de: $64";
            case 3:
                return "Para solicitar una Constancia de estudios puedes hacerlo llenando el formulario dentro de la aplicación o bien desde ventanilla en el departamento de Control Escolar, solo con los siguientes datos:" +
                        "\n Nombre Completo" +
                        "\n Ultimos 3 digitos de tu Matricula" +
                        "\n Grupo" +
                        "\n Turno" +
                        "\n Y si quieres alguna especificación como: Firma (Tinta azul o negra), sello de la institución, fotografia, con historial academtico, entre otros." +
                        "\n" +
                        "\n Estos se agregan en el apartado de observaciones.";

            case 4:
                return "Para darte de alta en el seguro, necesitamos que primero saque tu NSS, lo puedes adquirir desde la pagina del imss." +
                        "\n" +
                        "\n Una vez que tengas tu PDF, debes de imprimirlo y presentarlo en Control Escolar, en la parte superior colocaras tu grupo actual" +
                        "y en la parte trasera dos numeros telefonicos a los cuales te marcaremos." +
                        "\n" +
                        "\n De prefrerencia traer tu documento en Lunes o Martes, ya que se envian los viernes y la siguiente semana te marcaremos para que " +
                        "te acerques a tu clinica mas sercana para tramitar tu Carnet.";
            case 5:
                return "Para solicitar un cambio de turno primero debes de llenar la solicitud, esta se habilitara en los periodos de vacaciones o" +
                        "bien vas a Control Escolar y solicitas el cambio, se te dara un gormato el cual deberas de llenar. Una vez " +
                        "llenada y enviada solo deberas de esperar a que se publiquen los resultados y veras si fuiste aceptado/a.";
            case 6:
                return "Para solicitar un cambio de grupo primero debes de llenar la solicitud, esta se habilitara en los periodos de vacaciones o" +
                        "bien vas a Control Escolar y solicitas el cambio, se te dara un gormato el cual deberas de llenar. Una vez " +
                        "llenada y enviada solo deberas de esperar a que se publiquen los resultados y veras si fuiste aceptado/a.";
            case 7:
                return "Para solicitar un cambio de carrera primero debes de llenar la solicitud, esta se habilitara en los periodos de vacaciones o" +
                        "bien vas a Control Escolar y solicitas el cambio, se te dara un gormato el cual deberas de llenar. Una vez " +
                        "llenada y enviada solo deberas de esperar a que se publiquen los resultados y veras si fuiste aceptado/a.";
            case 8:
                return "Para solicitar un cambio de plantel primero debes de llenar la solicitud, esta se habilitara en los periodos de vacaciones o" +
                        "bien vas a Control Escolar y solicitas el cambio, se te dara un gormato el cual deberas de llenar. Una vez " +
                        "llenada y enviada solo deberas de esperar a que se publiquen los resultados y veras si fuiste aceptado/a.";
            case 9:
                return "Si tienes un recurse debes de considerar distintos puntos:" +
                        "\n - Si eres de primer semestre, no puedes tener recurse, de ser asi seras baja." +
                        "\n - Solo puedes tener recurse de segundo semestre en adelante." +
                        "\n - Ejemplo: Si repruebas una materia en tercer semestre, puedes reinscribirte a cuarto sin problemas, en cambio," +
                        "en quinto podras meter el recurse de la materia que reprobaste." +
                        "\n" +
                        "\n Acuede con tu orientador, el te mencionara los pasos a seguir para tu recurse.";
            case 10:
                return "El listado de los precios son los siguientes:" +
                        "\n" +
                        "\n Examen Extraordinario 1: $143" +
                        "\n Examen Extraordinario 2: $143" +
                        "\n Examen Extraordinario 3 (Titulo de Insuficiencia): $154" +
                        "\n" +
                        "\n Nota: Solo puedes juntar 19 examenes de extraordinario en tu estancia en el plantel, si llegas a 20 ya seras " +
                        "baja definitiva.";
            default:
                return "Respuesta predeterminada para opciones no manejadas";
        }
    }
}