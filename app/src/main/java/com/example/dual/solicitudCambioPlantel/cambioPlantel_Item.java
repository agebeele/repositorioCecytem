package com.example.dual.solicitudCambioPlantel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class cambioPlantel_Item extends AppCompatActivity {
    TextView fecha, matricula, nombre, paterno, materno, grupo, plantelActual, platelCambio, motivos, promedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_plantel_item);

        fecha = (TextView) findViewById(R.id.item_fechaPlantel);
        matricula = (TextView) findViewById(R.id.item_matriculaPlantel);
        nombre = (TextView) findViewById(R.id.item_nombrePlantel);
        paterno = (TextView) findViewById(R.id.item_paternoPlantel);
        materno = (TextView) findViewById(R.id.item_maternoPlantel);
        grupo = (TextView) findViewById(R.id.item_grupoPlantel);
        plantelActual = (TextView) findViewById(R.id.item_PlantelActual);
        platelCambio = (TextView) findViewById(R.id.item_PlantelCambio);
        promedio = (TextView) findViewById(R.id.item_promedioPlantel);
        motivos = (TextView) findViewById(R.id.item_motivosPlantel);

        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_fecha = recibirInformacion.getString("fecha");
            String info_matricula = recibirInformacion.getString("matricula");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_paterno = recibirInformacion.getString("apellido_paterno");
            String info_materno = recibirInformacion.getString("apellido_materno");
            String info_grupo = recibirInformacion.getString("grupo");

            String info_actual = recibirInformacion.getString("plantelActual");
            String info_cambio = recibirInformacion.getString("plantelCambio");
            String info_promedio = recibirInformacion.getString("promedio");
            String info_motivos = recibirInformacion.getString("motivos");

            fecha.setText(info_fecha);
            matricula.setText(info_matricula);
            nombre.setText(info_nombre);
            paterno.setText(info_paterno);
            materno.setText(info_materno);
            grupo.setText(info_grupo);
            plantelActual.setText(info_actual);
            platelCambio.setText(info_cambio);
            promedio.setText(info_promedio);
            motivos.setText(info_motivos);
        }
    }
}