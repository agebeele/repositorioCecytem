package com.example.dual.solicitudConstancia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class constancia_item extends AppCompatActivity {

    TextView hora, fecha, matricula, nombre, paterno, materno, grupo, observaciones, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constancia_item);

        hora = (TextView) findViewById(R.id.item_hora);
        fecha = (TextView) findViewById(R.id.item_fecha);
        matricula = (TextView) findViewById(R.id.item_matricula);
        nombre = (TextView) findViewById(R.id.item_nombre);
        paterno = (TextView) findViewById(R.id.item_apellidoPaterno);
        materno = (TextView) findViewById(R.id.item_apellidoMaterno);
        grupo = (TextView) findViewById(R.id.item_grupo);
        observaciones = (TextView) findViewById(R.id.item_observaciones);
        correo = (TextView) findViewById(R.id.item_correoConstancia);

        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_hora = recibirInformacion.getString("hora");
            String info_fecha = recibirInformacion.getString("fecha");
            String info_matricula = recibirInformacion.getString("matricula");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_paterno = recibirInformacion.getString("apellido_paterno");
            String info_materno = recibirInformacion.getString("apellido_materno");
            String info_grupo = recibirInformacion.getString("grupo");
            String info_observaciones = recibirInformacion.getString("observaciones");
            String info_correo = recibirInformacion.getString("correo");

            hora.setText(info_hora);
            fecha.setText(info_fecha);
            matricula.setText(info_matricula);
            nombre.setText(info_nombre);
            paterno.setText(info_paterno);
            materno.setText(info_materno);
            grupo.setText(info_grupo);
            observaciones.setText(info_observaciones);
            correo.setText(info_correo);
        }
    }
    public void Correo(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Notificación de trámite en proceso");
        intent.putExtra(Intent.EXTRA_TEXT, "Estimado/a Alumno/a,\n" +
                "\n" +
                "Nos complace informarle que su trámite ya se encuentra en proceso. Le agradecemos su paciencia y colaboración durante este período.\n" +
                "\n" +
                "Podrá pasar al día siguiente a recoger la documentación correspondiente en ventanilla de Control Escolar.\n" +
                "\n" +
                "Si tiene alguna duda o requiere información adicional, no dude en contactarnos.\n" +
                "\n" +
                "Atentamente,Vinculación.");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {correo.getText().toString()});
        startActivity(intent);
    }
}