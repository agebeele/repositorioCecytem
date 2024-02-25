package com.example.dual.solicitudEventos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class eventos_item extends AppCompatActivity {
TextView hora, fecha, matricula, nombre, telefonoCasa, telefonoCelular, grupo, evento;
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

            hora.setText(info_hora);
            fecha.setText(info_fecha);
            matricula.setText(info_matricula);
            nombre.setText(info_nombre);
            telefonoCasa.setText(info_telefonoCa);
            telefonoCasa.setText(info_telefonoCe);
            grupo.setText(info_grupo);
            evento.setText(info_evento);
        }
    }

    }
