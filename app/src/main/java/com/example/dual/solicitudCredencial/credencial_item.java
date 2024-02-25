package com.example.dual.solicitudCredencial;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class credencial_item extends AppCompatActivity {

    TextView hora, fecha, matricula, nombre, paterno, materno, grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credencial_item);

        hora = (TextView) findViewById(R.id.item_hora);
        fecha = (TextView) findViewById(R.id.item_fecha);
        matricula = (TextView) findViewById(R.id.item_matricula);
        nombre = (TextView) findViewById(R.id.item_nombre);
        paterno = (TextView) findViewById(R.id.item_apellidoPaterno);
        materno = (TextView) findViewById(R.id.item_apellidoMaterno);
        grupo = (TextView) findViewById(R.id.item_grupo);

        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_hora = recibirInformacion.getString("hora");
            String info_fecha = recibirInformacion.getString("fecha");
            String info_matricula = recibirInformacion.getString("matricula");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_paterno = recibirInformacion.getString("apellido_paterno");
            String info_materno = recibirInformacion.getString("apellido_materno");
            String info_grupo = recibirInformacion.getString("grupo");

            hora.setText(info_hora);
            fecha.setText(info_fecha);
            matricula.setText(info_matricula);
            nombre.setText(info_nombre);
            paterno.setText(info_paterno);
            materno.setText(info_materno);
            grupo.setText(info_grupo);
        }
    }
}