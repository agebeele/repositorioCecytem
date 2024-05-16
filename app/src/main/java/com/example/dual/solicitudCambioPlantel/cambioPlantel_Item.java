package com.example.dual.solicitudCambioPlantel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class cambioPlantel_Item extends AppCompatActivity {
    TextView fecha, matricula, nombre, paterno, materno, grupo, plantelActual, platelCambio, motivos, promedio, correo;

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
        correo = (TextView) findViewById(R.id.item_correoPlantel);

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
            String info_correo = recibirInformacion.getString("correo");

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