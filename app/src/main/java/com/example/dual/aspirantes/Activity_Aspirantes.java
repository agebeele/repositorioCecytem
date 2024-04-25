package com.example.dual.aspirantes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dual.R;

public class Activity_Aspirantes extends AppCompatActivity {
    //Declaración de los TextView
    TextView informacion, requisitos, datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspirantes);
    //Inicializacion de el arreglo de las imagenes con sus ID.
        int[] images = {R.drawable.coacalco_imagen1, R.drawable.cafeteria, R.drawable.canchas,
                R.drawable.edificioab, R.drawable.edificioc, R.drawable.edificiod,
                R.drawable.edificiofe, R.drawable.explanada, R.drawable.futboll,
                R.drawable.gym};

        informacion = (TextView) findViewById(R.id.txt_informacion);
        datos = (TextView) findViewById(R.id.txt_datos);
        requisitos = (TextView) findViewById(R.id.txt_requisitos);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        ImageAdapter imageAdapter = new ImageAdapter(images);
        viewPager.setAdapter(imageAdapter);

        datos.setText("La convocatoria       \n" +
                "Se pública el 24 de enero del 2024 en la siguiente página \n" +
                "Pre-Registro    \n" +
                "El 24 de enero al 23 de febrero pre-registro de aspirantes vía internet 103 municipios\n" +
                "Registro    \n" +
                "El 4 al 15 de marzo registro de aspirantes 103 municipios\n" +
                "Examen    \n" +
                "Se aplicará el 23 de junio en el horario de 9:00 hrs, sede y grupo que le corresponda.\n" +
                "Resultados    \n" +
                "16 de julio, publicación de resultados 103 municipios\n" +
                "Inscripción       \n" +
                "La inscripción se llevará a cabo  en las fechas indicadas por el Colegio, difundidas el día de la publicación de resultados." +
                "\n");

        informacion.setText("En el CECyTEM Plantel Coacalco contamos con las siguientes instalaciones:" +
                "\n"+
                "\n - Edificios: 7" +
                "\n - Aulas: 20" +
                "\n - Laboratorios: 2" +
                "\n - Talleres y salas de cómputo: 4" +
                "\n - Áreas deportivas: 4" +
                "\n - Bibliotecas: 1 (6731 volúmenes)" +
                "\n - Cafeterías: 1");

        requisitos.setText("La Inscripción es el registro de los aspirantes que se hace por única vez con el fin de iniciar el historial académico. Para la inscripcion" +
                "es necesario lo siguiente:" +
                "\n"+
                "\n - Aparecer (sin excepción) en la lista de aspirantes aceptados (Concurso COMIPEMS) y no haber causado baja definitiva en años anteriores." +
                "\n - Copia de comprobante de COMIPEMS (Original y copia)." +
                "\n - Certificado de educación secundaria (original y copia)." +
                "\n - Acta de nacimiento (original y copia)." +
                "\n - Certificado de salud expedido por alguna institución oficial (IMSS, ISSSTE, ISEM, etc.)." +
                "\n - Copia de la C.U.R.P. (Copia ampliación a 200%).  " +
                "\n - 5 fotografías tamaño infantil (recientes e iguales)." +
                "\n - Comprobante de domicilio (Copia)." +
                "\n - Identificación Oficial del Padre o Tutor INE (copia)." +
                "\n - Solicitud de inscripción con fotografía (llenada a computadora o letra de molde). (Descargar solicitud de inscripción)" +
                "\n - Cubrir cuota de inscripción semestral.");
    }
    public void recorridoGeorreferenciado (View view) {
        Intent iniciar = new Intent(Activity_Aspirantes.this, Activity_RecorridoGeorreferenciado.class);
        startActivity(iniciar);
    }
}