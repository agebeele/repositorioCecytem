package com.example.dual.solicitudServicioSocial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class servicio_item extends AppCompatActivity {
TextView fecha,hora,nombre, promedio, telefonocasa, telefonocelular, grupo, correoins, nombredependencia, cct, turno, domiciliod, telefonod,nivel,nombreR, cargoR, actividades,horario, nombreP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_item);
        hora = (TextView) findViewById(R.id.item_hora);
        fecha = (TextView) findViewById(R.id.item_fecha);
        nombre = (TextView) findViewById(R.id.item_nombre);
        promedio = (TextView) findViewById(R.id.item_promedio);
        telefonocasa = (TextView) findViewById(R.id.item_telefonocasa);
        telefonocelular = (TextView) findViewById(R.id.item_telefonocelular);
        grupo = (TextView) findViewById(R.id.item_grupo);
        correoins = (TextView) findViewById(R.id.item_correoins);
        nombredependencia = (TextView) findViewById(R.id.item_nombredependencia);
        cct = (TextView) findViewById(R.id.item_CCT);
        turno = (TextView) findViewById(R.id.item_turno);
        domiciliod = (TextView) findViewById(R.id.item_domiciliod);
        telefonod = (TextView) findViewById(R.id.item_telefonod);
        nivel = (TextView) findViewById(R.id.item_nivel);
        nombreR = (TextView) findViewById(R.id.item_nombrer);
        cargoR = (TextView) findViewById(R.id.item_cargor);
        actividades = (TextView) findViewById(R.id.item_actividades);
        horario = (TextView) findViewById(R.id.item_horarioprestacion);
        nombreP = (TextView) findViewById(R.id.item_nombrepadretutor);

        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_hora = recibirInformacion.getString("hora");
            String info_fecha = recibirInformacion.getString("fecha");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_promedio = recibirInformacion.getString("promedio");
            String info_telefonocasa = recibirInformacion.getString("telefonocasa");
            String info_telefonocelular = recibirInformacion.getString("telefonocelular");
            String info_grupo = recibirInformacion.getString("grupo");
            String info_correoins = recibirInformacion.getString("correoins");
            String info_nombredependencia = recibirInformacion.getString("nombredepe");
            String info_cct = recibirInformacion.getString("cct");
            String info_turno = recibirInformacion.getString("turno");
            String info_domiciliod = recibirInformacion.getString("domiciliode");
            String info_telefonod = recibirInformacion.getString("telefonodepe");
            String info_nivel = recibirInformacion.getString("nivel");
            String info_nombrer = recibirInformacion.getString("nombreres");
            String info_cargor = recibirInformacion.getString("cargores");
            String info_actividades = recibirInformacion.getString("actividades");
            String info_horario = recibirInformacion.getString("horario");
            String info_nombrep = recibirInformacion.getString("nombrepadre");

            hora.setText(info_hora);
            fecha.setText(info_fecha);
            nombre.setText(info_nombre);
            promedio.setText(info_promedio);
            telefonocasa.setText(info_telefonocasa);
            telefonocelular.setText(info_telefonocelular);
            grupo.setText(info_grupo);
            correoins.setText(info_correoins);
            nombredependencia.setText(info_nombredependencia);
            cct.setText(info_cct);
            turno.setText(info_turno);
            domiciliod.setText(info_domiciliod);
            telefonod.setText(info_telefonod);
            nivel.setText(info_nivel);
            nombreR.setText(info_nombrer);
            cargoR.setText(info_cargor);
            actividades.setText(info_actividades);
            horario.setText(info_horario);
            nombreP.setText(info_nombrep);
        }
    }
    public void enviarCorreo (View view) {
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

        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {correoins.getText().toString()});
        startActivity(intent);
    }
}