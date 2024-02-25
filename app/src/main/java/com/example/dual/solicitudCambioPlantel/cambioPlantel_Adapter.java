package com.example.dual.solicitudCambioPlantel;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dual.R;

import java.util.List;

public class cambioPlantel_Adapter extends RecyclerView.Adapter<cambioPlantel_Adapter.cambioPlantelAdapterViewHolder> {
    private OnItemClickListener listener;
    private List<String> nombreList;
    private List<String> paternoList;
    private List<String> maternoList;
    private List<String> matriculaList;
    private SharedPreferences preferencia_cambioPlantel;

    public cambioPlantel_Adapter(List<String> nombreList, List<String> paternoList, List<String> maternoList, List<String> matriculaList, Context context) {
        this.nombreList = nombreList;
        this.paternoList = paternoList;
        this.maternoList = maternoList;
        this.matriculaList = matriculaList;
        preferencia_cambioPlantel = context.getSharedPreferences("MyPreferencia_Carrera", Context.MODE_PRIVATE);
    }
    @NonNull
    @Override
    public cambioPlantel_Adapter.cambioPlantelAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cambio_plantel_adapter, parent, false);
        return new cambioPlantel_Adapter.cambioPlantelAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull cambioPlantel_Adapter.cambioPlantelAdapterViewHolder holder, int position) {
        holder.nombre.setText(nombreList.get(position));
        holder.paterno.setText(paternoList.get(position));
        holder.materno.setText(maternoList.get(position));
        holder.matricula.setText(matriculaList.get(position));

        // Configurar el estado del CheckBox
        boolean tramiteRealizado = preferencia_cambioPlantel.getBoolean("tramiteRealizado_" + position, false);
        holder.realizado_cambioGrupo.setChecked(tramiteRealizado);

        // Guardar el estado del CheckBox en SharedPreferences cuando cambia
        holder.realizado_cambioGrupo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferencia_cambioPlantel.edit();
            editor.putBoolean("tramiteRealizado_" + position, isChecked);
            editor.apply();
        });
    }

    @Override
    public int getItemCount() {
        return nombreList.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class cambioPlantelAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, paterno, materno, matricula;
        CheckBox realizado_cambioGrupo;

        public cambioPlantelAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_plantel);
            paterno = itemView.findViewById(R.id.paterno_plantel);
            materno = itemView.findViewById(R.id.materno_plantel);
            matricula = itemView.findViewById(R.id.matricula_plantel);
            realizado_cambioGrupo = itemView.findViewById(R.id.checkBoxTramiteRealizado_plantel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}