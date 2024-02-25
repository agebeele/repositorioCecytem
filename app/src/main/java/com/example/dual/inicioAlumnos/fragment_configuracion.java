package com.example.dual.inicioAlumnos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.dual.alumnos.Activity_Eventos;
import com.example.dual.R;
import com.example.dual.alumnos.Activity_Login;
import com.example.dual.alumnos.Activity_Nostros;
import com.example.dual.alumnos.Activity_Perfil;

public class fragment_configuracion extends Fragment {

    public Button cerrarSesion = null;
    Button ubicacion, llamadaAyuda, myPerfil, eventos, acercaNostros;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    Intent intent;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_configuracion, container, false);

        cerrarSesion = (Button) v.findViewById(R.id.boton_cerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });

        ubicacion = (Button) v.findViewById(R.id.ubicacionBoton);
        ubicacion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                checkLocationPermission();
            }
        });
        llamadaAyuda = (Button) v.findViewById(R.id.llamadaBoton);
        llamadaAyuda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                llamarTelefono();
            }
        });
        myPerfil = (Button) v.findViewById(R.id.perfilBoton);
        myPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambioPerfil();
            }
        });
        acercaNostros = (Button) v.findViewById(R.id.nosotrosBoton);
        acercaNostros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambioNosotros();
            }
        });
        eventos = (Button)  v.findViewById(R.id.eventosBoton);
        eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambioEventos();
            }
        });

        return v;
    }

    private void cambioPerfil() {
        Intent pantallaPerfil = new Intent(getActivity(), Activity_Perfil.class);
        startActivity(pantallaPerfil);
    }
    private void cambioEventos() {
        Intent pantallaEventos = new Intent(getActivity(), Activity_Eventos.class);
        startActivity(pantallaEventos);
    }
    private void cambioNosotros() {
        Intent pantallaNosotros = new Intent(getActivity(), Activity_Nostros.class);
        startActivity(pantallaNosotros);
    }

    private void llamarTelefono() {
        // Número de teléfono que deseas llamar
        String phoneNumber = "tel:" + "3137065253"; // Reemplaza con el número de teléfono real

        // Crear un intent para realizar la llamada
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));

        // Verificar si hay una aplicación que puede manejar la acción de marcar
        if (dialIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Iniciar la actividad de marcado
            startActivity(dialIntent);
        }
    }

    private void checkLocationPermission() {
        // Verificar si el permiso ya está concedido
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Si el permiso no está concedido, solicitarlo
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

        } else {
            Intent locationIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(locationIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Verificar si la solicitud de permiso es para ubicación y si se concedió
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, realizar acciones relacionadas con la ubicación
            } else {
                // Permiso denegado, puedes mostrar un mensaje al usuario o realizar otras acciones
            }
        }
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