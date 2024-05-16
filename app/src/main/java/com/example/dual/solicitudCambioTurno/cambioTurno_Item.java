package com.example.dual.solicitudCambioTurno;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class cambioTurno_Item extends AppCompatActivity {
    TextView fecha, matricula, nombre, paterno, materno, grupo, turnoActual, turnoCambio, motivos, promedio, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_turno_item);

        fecha = (TextView) findViewById(R.id.item_fechaTurno);
        matricula = (TextView) findViewById(R.id.item_matriculaTurno);
        nombre = (TextView) findViewById(R.id.item_nombreTurno);
        paterno = (TextView) findViewById(R.id.item_paternoTurno);
        materno = (TextView) findViewById(R.id.item_maternoTurno);
        grupo = (TextView) findViewById(R.id.item_grupoTurno);
        turnoActual = (TextView) findViewById(R.id.item_TurnoActual);
        turnoCambio = (TextView) findViewById(R.id.item_TurnoCambio);
        promedio = (TextView) findViewById(R.id.item_promedioTurno);
        motivos = (TextView) findViewById(R.id.item_motivosTurno);
        correo = (TextView) findViewById(R.id.item_correoTurno);

        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_fecha = recibirInformacion.getString("fecha");
            String info_matricula = recibirInformacion.getString("matricula");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_paterno = recibirInformacion.getString("apellido_paterno");
            String info_materno = recibirInformacion.getString("apellido_materno");
            String info_grupo = recibirInformacion.getString("grupo");

            String info_actual = recibirInformacion.getString("turnoActual");
            String info_cambio = recibirInformacion.getString("turnoCambio");
            String info_promedio = recibirInformacion.getString("promedio");
            String info_motivos = recibirInformacion.getString("motivos");
            String info_correo = recibirInformacion.getString("correo");

            fecha.setText(info_fecha);
            matricula.setText(info_matricula);
            nombre.setText(info_nombre);
            paterno.setText(info_paterno);
            materno.setText(info_materno);
            grupo.setText(info_grupo);
            turnoActual.setText(info_actual);
            turnoCambio.setText(info_cambio);
            promedio.setText(info_promedio);
            motivos.setText(info_motivos);
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