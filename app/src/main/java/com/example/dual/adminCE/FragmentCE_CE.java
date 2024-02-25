package com.example.dual.adminCE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dual.R;
import com.example.dual.solicitudCambioCarrera.cambioCarrera_Recycler;
import com.example.dual.solicitudCambioGrupo.cambioGrupo_Recycler;
import com.example.dual.solicitudCambioPlantel.cambioPlantel_Recycler;
import com.example.dual.solicitudCambioTurno.cambioTurno_Recycler;
import com.example.dual.solicitudConstancia.constancia_recycler;
import com.example.dual.solicitudCredencial.credencial_recycler;
import com.example.dual.solicitudHistorial.historial_recycler;

public class FragmentCE_CE extends Fragment {

    TextView credencial, constancia, historial, cambioGrupo, cambioTurno, cambioCarrera, cambioPlantel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_e__c_e, container, false);

        credencial = view.findViewById(R.id.solicitudCredencial);
        constancia = view.findViewById(R.id.solicitudConstancia);
        historial = view.findViewById(R.id.solicitudHistorial);
        cambioGrupo = view.findViewById(R.id.solicitudGrupo);
        cambioTurno = view.findViewById(R.id.solicitudTurno);
        cambioCarrera = view.findViewById(R.id.solicitudCarrera);
        cambioPlantel = view.findViewById(R.id.solicitudPlantel);

        cambioPlantel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_plantel = new Intent(getActivity(), cambioPlantel_Recycler.class);
                startActivity(recycler_plantel);
            }
        });
        cambioCarrera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_carrera = new Intent(getActivity(), cambioCarrera_Recycler.class);
                startActivity(recycler_carrera);
            }
        });
        cambioTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_turno = new Intent(getActivity(), cambioTurno_Recycler.class);
                startActivity(recycler_turno);
            }
        });
        cambioGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_grupo = new Intent(getActivity(), cambioGrupo_Recycler.class);
                startActivity(recycler_grupo);
            }
        });
        credencial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_credencial = new Intent(getActivity(), credencial_recycler.class);
                startActivity(recycler_credencial);
            }
        });
        constancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_constancia = new Intent(getActivity(), constancia_recycler.class);
                startActivity(recycler_constancia);
            }
        });
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_historial = new Intent(getActivity(), historial_recycler.class);
                startActivity(recycler_historial);
            }
        });
        return view;
    }
}