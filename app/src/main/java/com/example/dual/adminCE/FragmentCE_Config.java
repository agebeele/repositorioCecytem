package com.example.dual.adminCE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.dual.R;
import com.example.dual.alumnos.Activity_Eventos;
import com.example.dual.alumnos.Activity_Login;
import com.example.dual.alumnos.Activity_Nostros;

public class FragmentCE_Config extends Fragment {

    public Button cerrarSesion = null;
    Button ubicacion, llamadaAyuda, myPerfil, eventos, acercaNostros;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_c_e__config, container, false);

        myPerfil = (Button) v.findViewById(R.id.perfilBoton);
        myPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarPantalla_Perfil();
            }
        });

        cerrarSesion = (Button) v.findViewById(R.id.boton_cerrarSesionCE);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });
        eventos = (Button)v.findViewById(R.id.eventosBoton);
        eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarEventos();
            }
        });

        acercaNostros = (Button) v.findViewById(R.id.nosotrosBoton);
        acercaNostros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambioNosotros();
            }
        });

        return v;
    }

    private void cambiarEventos() {
        Intent pantallaEventos = new Intent(getActivity(), Activity_Eventos.class);
        startActivity(pantallaEventos);
    }

    private void cambiarPantalla_Perfil() {
        Intent cambio_perfil = new Intent(getActivity(),adminCE_Perfil.class);
        startActivity(cambio_perfil);
    }

    private void cambioNosotros() {
        Intent pantallaNosotros = new Intent(getActivity(), Activity_Nostros.class);
        startActivity(pantallaNosotros);
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Seguro que quieres cerrar la sesión?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(getActivity(), Activity_Login.class);
                startActivity(intent);
                getActivity().finish();  // Cierra la actividad actual

                Toast.makeText(getActivity(), "Sesion cerrada", Toast.LENGTH_SHORT).show();
            }

        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // El usuario hizo clic en "No", no hacer nada
            }
        });
        builder.show();
    }
}