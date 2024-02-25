package com.example.dual.chatBotVIN;

import android.content.Context;

import com.example.dual.R;

import java.util.HashMap;
import java.util.Map;

public class ChatBot_Vin {
    private Map<Integer, Integer> questionResponses;

    public ChatBot_Vin() {
        questionResponses = new HashMap<>();
        // Asigna índices únicos a cada pregunta
        questionResponses.put(1, R.string.tiempo_question_1);
        questionResponses.put(2, R.string.tiempo_question_2);
        questionResponses.put(3, R.string.tiempo_question_2);
        questionResponses.put(4, R.string.tiempo_question_2);
        questionResponses.put(5, R.string.tiempo_question_2);
        questionResponses.put(6, R.string.tiempo_question_2);
        // Agrega más preguntas según sea necesario
    }

    public String getResponse(Context context, int selectedQuestionIndex) {
        String response = "No entiendo tu mensaje. ¿Puedes ser más específico?";

        // Verifica si se seleccionó una pregunta válida
        if (questionResponses.containsKey(selectedQuestionIndex)) {
            int questionResourceId = questionResponses.get(selectedQuestionIndex);
            response = context.getString(questionResourceId);
        }

        return response;
    }
}
