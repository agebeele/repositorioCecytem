package com.example.dual.adminVIN;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dual.R;
import com.example.dual.solicitudBecas.becas_recycler;
import com.example.dual.solicitudEventos.eventos_recycler;
import com.example.dual.solicitudServicioSocial.servicio_recycler;

public class FragmentVIN_vinculacion extends Fragment {
    TextView becas, servico, evento;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_v_i_n_vinculacion, container, false);

        becas = view.findViewById(R.id.solicitudBecas);
        servico = view.findViewById(R.id.solicitudServicio);
        evento = view.findViewById(R.id.solicitudEventos);

        becas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_becas = new Intent(getActivity(), becas_recycler.class);
                startActivity(recycler_becas);
            }
        });
        servico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_servicio = new Intent(getActivity(), servicio_recycler.class);
                startActivity(recycler_servicio);
            }
        });
        evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recycler_eventos = new Intent(getActivity(), eventos_recycler.class);
                startActivity(recycler_eventos);
            }
        });
        return view;
    }
}