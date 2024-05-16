package com.example.dual.solicitudBecas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dual.R;

public class beca_item extends AppCompatActivity {
    TextView hora,fecha,curp, apellidoP, apellidoM, nombre, fechaNaciemiento, sexo, EstadoNacimiento, SituacionAcademica, Semestre, CorreoP, CorreoIns, TelefonoCasa, TelefonoCelularAlumno, TelefonoCelularPadre, Domicilio, NoExterior, NoInterior,NombreColonia, Municipio, EstadoDomicilio,CodigoPostal, EscuelaProcedencia, PromediodeSecundaria;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beca_item);
        hora = (TextView) findViewById(R.id.item_hora);
        fecha = (TextView) findViewById(R.id.item_fecha);
        curp = (TextView) findViewById(R.id.item_curp);
        apellidoP = (TextView) findViewById(R.id.item_apellidoPaterno);
        apellidoM = (TextView) findViewById(R.id.item_apellidoMaterno);
        nombre = (TextView) findViewById(R.id.item_nombre);
        fechaNaciemiento = (TextView)findViewById(R.id.item_naciemiento);
        sexo =(TextView) findViewById(R.id.item_sexo);
        EstadoNacimiento = (TextView) findViewById(R.id.item_EstadoN);
        SituacionAcademica = (TextView) findViewById(R.id.item_Academica);
        Semestre = (TextView) findViewById(R.id.item_semestre);
        CorreoP = (TextView) findViewById(R.id.item_correop);
        CorreoIns  = (TextView) findViewById(R.id.item_correoins);
        TelefonoCasa= (TextView) findViewById(R.id.item_telefonocasa);
         TelefonoCelularAlumno= (TextView) findViewById(R.id.item_telefonoalumno);
         TelefonoCelularPadre= (TextView) findViewById(R.id.item_telefonopadre);
         Domicilio= (TextView) findViewById(R.id.item_domicilio);
         NoExterior= (TextView) findViewById(R.id.item_noex);
         NoInterior= (TextView) findViewById(R.id.item_nointerior);
         NombreColonia= (TextView) findViewById(R.id.item_colonia);
         Municipio= (TextView) findViewById(R.id.item_municipio);
         EstadoDomicilio= (TextView) findViewById(R.id.item_estadod);
         CodigoPostal= (TextView) findViewById(R.id.item_codigop);
         EscuelaProcedencia= (TextView) findViewById(R.id.item_escuelap);
         PromediodeSecundaria= (TextView) findViewById(R.id.item_promedio);

        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion !=null){
            String info_hora = recibirInformacion.getString("hora");
            String info_fecha = recibirInformacion.getString("fecha");
            String info_curp = recibirInformacion.getString("curp");
            String info_paterno = recibirInformacion.getString("apellido_paterno");
            String info_materno = recibirInformacion.getString("apellido_materno");
            String info_nombre = recibirInformacion.getString("nombre");
            String info_fechan = recibirInformacion.getString("fecha_nacimiento");
            String info_sexo = recibirInformacion.getString("sexo");
            String info_estadon = recibirInformacion.getString("estado_nacimiento");
            String info_situacion = recibirInformacion.getString("situacion_academica");
            String info_semestre = recibirInformacion.getString("semestre");
            String info_correop = recibirInformacion.getString("correo_personal");
            String info_correoins = recibirInformacion.getString("correo_institucional");
            String info_telefonoc = recibirInformacion.getString("telefono_casa");
            String info_telefonoa = recibirInformacion.getString("telefono_alumno");
            String info_telefonop = recibirInformacion.getString("telefono_padre");
            String info_domicilio = recibirInformacion.getString("domicilio");
            String info_noe = recibirInformacion.getString("no_exterior");
            String info_noi = recibirInformacion.getString("no_interior");
            String info_noc = recibirInformacion.getString("nombre_colonia");
            String info_municipio = recibirInformacion.getString("municipio");
            String info_estadod = recibirInformacion.getString("estado_domicilio");
            String info_codigop = recibirInformacion.getString("codigo_postal");
            String info_escuelap = recibirInformacion.getString("escuela_procedencia");
            String info_promedio = recibirInformacion.getString("promedio");

            hora.setText(info_hora);
            fecha.setText(info_fecha);
            curp.setText(info_curp);
            apellidoP.setText(info_paterno);
            apellidoM.setText(info_materno);
            nombre.setText(info_nombre);
            fechaNaciemiento.setText(info_fechan);
            sexo.setText(info_sexo);
            EstadoNacimiento.setText(info_estadon);
            SituacionAcademica.setText(info_situacion);
            Semestre.setText(info_semestre);
            CorreoP.setText(info_correop);
            CorreoIns.setText(info_correoins);
            TelefonoCasa.setText(info_telefonoc);
            TelefonoCelularAlumno.setText(info_telefonoa);
            TelefonoCelularPadre.setText(info_telefonop);
            Domicilio.setText(info_domicilio);
            NoExterior.setText(info_noe);
            NoInterior.setText(info_noi);
            NombreColonia.setText(info_noc);
            Municipio.setText(info_municipio);
            EstadoDomicilio.setText(info_estadod);
            CodigoPostal.setText(info_codigop);
            EscuelaProcedencia.setText(info_escuelap);
            PromediodeSecundaria.setText(info_promedio);
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
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {String.valueOf(CorreoP)});
        startActivity(intent);
    }
}