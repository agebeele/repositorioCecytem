package com.example.dual.alumnos;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Asegúrate de inflar el diseño de tu actividad

        // Obtén la referencia de la ProgressBar desde el diseño
        ProgressBar progressBar = findViewById(R.id.progressBar);

        // Crea un objeto ObjectAnimator para animar el progreso de la ProgressBar
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);

        // Establece la duración de la animación en milisegundos (en este caso, 5000 ms o 5 segundos)
        progressAnimator.setDuration(5000);

        // Inicia la animación
        progressAnimator.start();

        // Configura un Handler para iniciar la nueva actividad después de 5 segundos
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
