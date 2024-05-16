package com.example.dual.solicitudCambioGrupo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class cambioGrupo_Item extends AppCompatActivity {
    TextView fecha, matricula, nombre, paterno, materno, grupo, grupoActual, grupoCambio, motivos, promedio, correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_grupo_item);

        fecha = (TextView) findViewById(R.id.item_fechaGrupo);
        matricula = (TextView) findViewById(R.id.item_matriculaGrupo);
        nombre = (TextView) findViewById(R.id.item_nombreGrupo);
        paterno = (TextView) findViewById(R.id.item_paternoGrupo);
        materno = (TextView) findViewById(R.id.item_maternoGrupo);
        grupo = (TextView) findViewById(R.id.item_grupoGrupo);
        grupoActual = (TextView) findViewById(R.id.item_grupoActual);
        grupoCambio = (TextView) findViewById(R.id.item_grupoCambio);
        promedio = (TextView) findViewById(R.id.item_promedioGrupo);
        motivos = (TextView) findViewById(R.id.item_motivosGrupo);
        correo = (TextView) findViewById(R.id.item_correoGrupo);

        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_fecha = recibirInformacion.getString("fecha");
            String info_matricula = recibirInformacion.getString("matricula");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_paterno = recibirInformacion.getString("apellido_paterno");
            String info_materno = recibirInformacion.getString("apellido_materno");
            String info_grupo = recibirInformacion.getString("grupo");

            String info_actual = recibirInformacion.getString("grupoActual");
            String info_cambio = recibirInformacion.getString("grupoCambio");
            String info_promedio = recibirInformacion.getString("promedio");
            String info_motivos = recibirInformacion.getString("motivos");

            String info_correo = recibirInformacion.getString("correo");

            fecha.setText(info_fecha);
            matricula.setText(info_matricula);
            nombre.setText(info_nombre);
            paterno.setText(info_paterno);
            materno.setText(info_materno);
            grupo.setText(info_grupo);
            grupoActual.setText(info_actual);
            grupoCambio.setText(info_cambio);
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