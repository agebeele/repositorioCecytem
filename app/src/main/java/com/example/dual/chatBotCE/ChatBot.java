package com.example.dual.chatBotCE;

import android.content.Context;

import com.example.dual.R;

import java.util.HashMap;
import java.util.Map;

public class ChatBot {
        private Map<Integer, Integer> respuestasCE;

    public ChatBot() {
        respuestasCE = new HashMap<>();
        // Asigna índices únicos a cada pregunta
        respuestasCE.put(1, R.string.tiempo_question_3);
        respuestasCE.put(2, R.string.tiempo_question_4);
        respuestasCE.put(3, R.string.tiempo_question_2);
        respuestasCE.put(4, R.string.tiempo_question_2);
        respuestasCE.put(5, R.string.tiempo_question_2);
        respuestasCE.put(6, R.string.tiempo_question_2);
        // Agrega más preguntas según sea necesario
    }
    public String getResponse(Context context, int selectedQuestionIndex) {
        String response = "No entiendo tu mensaje. ¿Puedes ser más específico?";

        // Verifica si se seleccionó una pregunta válida
        if (respuestasCE.containsKey(selectedQuestionIndex)) {
            int questionResourceId = respuestasCE.get(selectedQuestionIndex);
            response = context.getString(questionResourceId);
        }

        return response;
    }
}
