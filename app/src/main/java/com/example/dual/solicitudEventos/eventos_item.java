package com.example.dual.solicitudEventos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class eventos_item extends AppCompatActivity {
TextView hora, fecha, matricula, nombre, telefonoCasa, telefonoCelular, grupo, evento, correo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_item);
        hora = (TextView) findViewById(R.id.item_hora);
        fecha = (TextView) findViewById(R.id.item_fecha);
        matricula = (TextView) findViewById(R.id.item_matricula);
        nombre = (TextView)  findViewById(R.id.item_nombre);
        telefonoCasa = (TextView) findViewById(R.id.item_telefonocasa);
        telefonoCelular = (TextView) findViewById(R.id.item_telefonocelular);
        grupo = (TextView) findViewById(R.id.item_grupo);
        evento = (TextView)  findViewById(R.id.item_evento);
        correo = (TextView)  findViewById(R.id.item_correoEvento);


        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_hora = recibirInformacion.getString("hora");
            String info_fecha = recibirInformacion.getString("fecha");
            String info_matricula = recibirInformacion.getString("matricula");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_telefonoCe = recibirInformacion.getString("telefonocasa");
            String info_telefonoCa = recibirInformacion.getString("telefonocelular");
            String info_grupo = recibirInformacion.getString("grupo");
            String info_evento = recibirInformacion.getString("evento");
            String info_correo = recibirInformacion.getString("correo");

            hora.setText(info_hora);
            fecha.setText(info_fecha);
            matricula.setText(info_matricula);
            nombre.setText(info_nombre);
            telefonoCasa.setText(info_telefonoCa);
            telefonoCasa.setText(info_telefonoCe);
            grupo.setText(info_grupo);
            evento.setText(info_evento);
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
                "Podrá pasar al día siguiente a recoger la documentación correspondiente en ventanilla de Vinculación.\n" +
                "\n" +
                "Si tiene alguna duda o requiere información adicional, no dude en contactarnos.\n" +
                "\n" +
                "Atentamente,Vinculación.");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {correo.getText().toString()});
        startActivity(intent);
    }
}
