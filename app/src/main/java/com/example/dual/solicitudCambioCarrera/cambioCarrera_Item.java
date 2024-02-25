package com.example.dual.solicitudCambioCarrera;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class cambioCarrera_Item extends AppCompatActivity {
    TextView fecha, matricula, nombre, paterno, materno, grupo, carreraActual, carreraCambio, motivos, promedio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_carrera_item);

        fecha = (TextView) findViewById(R.id.item_fechaCarrera);
        matricula = (TextView) findViewById(R.id.item_matriculaCarrera);
        nombre = (TextView) findViewById(R.id.item_nombreCarrera);
        paterno = (TextView) findViewById(R.id.item_paternoCarrera);
        materno = (TextView) findViewById(R.id.item_maternoCarrera);
        grupo = (TextView) findViewById(R.id.item_grupoCarrera);
        carreraActual = (TextView) findViewById(R.id.item_carreraActual);
        carreraCambio = (TextView) findViewById(R.id.item_carreraCambio);
        promedio = (TextView) findViewById(R.id.item_promedioCarrera);
        motivos = (TextView) findViewById(R.id.item_motivosCarrera);

        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_fecha = recibirInformacion.getString("fecha");
            String info_matricula = recibirInformacion.getString("matricula");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_paterno = recibirInformacion.getString("apellido_paterno");
            String info_materno = recibirInformacion.getString("apellido_materno");
            String info_grupo = recibirInformacion.getString("grupo");

            String info_actual = recibirInformacion.getString("carreraActual");
            String info_cambio = recibirInformacion.getString("carreraCambio");
            String info_promedio = recibirInformacion.getString("promedio");
            String info_motivos = recibirInformacion.getString("motivos");

            fecha.setText(info_fecha);
            matricula.setText(info_matricula);
            nombre.setText(info_nombre);
            paterno.setText(info_paterno);
            materno.setText(info_materno);
            grupo.setText(info_grupo);
            carreraActual.setText(info_actual);
            carreraCambio.setText(info_cambio);
            promedio.setText(info_promedio);
            motivos.setText(info_motivos);
        }
    }
}