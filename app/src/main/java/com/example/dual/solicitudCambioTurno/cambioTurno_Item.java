package com.example.dual.solicitudCambioTurno;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class cambioTurno_Item extends AppCompatActivity {
    TextView fecha, matricula, nombre, paterno, materno, grupo, turnoActual, turnoCambio, motivos, promedio;

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
        }
    }
}