package com.example.dual.alumnos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class MainActivity extends AppCompatActivity {
    // Declaración de la barra de progreso
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establece el diseño de la actividad
        setContentView(R.layout.activity_main);

        // Inicializa la barra de progreso utilizando su ID del diseño
        progressBar = findViewById(R.id.progressBar);

        // Establece el progreso inicial de la barra de progreso (opcional)
        progressBar.setProgress(0);

        // Configura un Handler para retrasar la navegación a otra actividad
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Después de 5 segundos (5000 milisegundos), crea un Intent para navegar a la siguiente actividad
                Intent intent = new Intent(MainActivity.this, Activity_Login.class);

                // Inicia la nueva actividad
                startActivity(intent);

                // Opcional: cierra esta actividad después de la navegación
                finish();
            }
        }, 5000); // 5000 milisegundos (5 segundos)
    }
}