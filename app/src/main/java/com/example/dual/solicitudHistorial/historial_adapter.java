package com.example.dual.solicitudHistorial;

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
import com.example.dual.solicitudConstancia.constancia_adapter;

import java.util.List;

public class historial_adapter extends RecyclerView.Adapter<historial_adapter.historial_adapterViewHolder>{

    private constancia_adapter.OnItemClickListener listener;
    private List<String> nombreList;
    private List<String> paternoList;
    private List<String> maternoList;
    private List<String> matriculaList;
    private SharedPreferences preferenciaHistorial;

    public historial_adapter(List<String>nombreList, List<String>paternoList, List<String>maternoList, List<String>matriculaList, Context context){
        this.nombreList = nombreList;
        this.paternoList = paternoList;
        this.maternoList = maternoList;
        this.matriculaList = matriculaList;
        preferenciaHistorial = context.getSharedPreferences("MyPreferencia_Historial", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public historial_adapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_historial_adapter, parent, false);
        return new historial_adapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull historial_adapter.historial_adapterViewHolder holder, int position) {
        holder.nombre.setText(nombreList.get(position));
        holder.paterno.setText(paternoList.get(position));
        holder.materno.setText(maternoList.get(position));
        holder.matricula.setText(matriculaList.get(position));

        // Configurar el estado del CheckBox
        boolean tramiteRealizado = preferenciaHistorial.getBoolean("tramiteRealizado_" + position, false);
        holder.realizadoHistorial.setChecked(tramiteRealizado);

        // Guardar el estado del CheckBox en SharedPreferences cuando cambia
        holder.realizadoHistorial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferenciaHistorial.edit();
            editor.putBoolean("tramiteRealizado_" + position, isChecked);
            editor.apply();
        });
    }
    @Override
    public int getItemCount()  {
        return nombreList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(constancia_adapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    public  class historial_adapterViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, paterno, materno, matricula;
        CheckBox realizadoHistorial;

        public historial_adapterViewHolder (@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreAlumno);
            paterno = itemView.findViewById(R.id.paternoAlumno);
            materno = itemView.findViewById(R.id.maternoAlumno);
            matricula = itemView.findViewById(R.id.matriculaAlumno);
            realizadoHistorial = itemView.findViewById(R.id.checkBoxTramiteRealizado_Historial);

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