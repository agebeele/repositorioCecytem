package com.example.dual.solicitudEventos;

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

public class eventos_adapter extends RecyclerView.Adapter<eventos_adapter.eventos_adapterViewHolder> {

    private OnItemClickListener listener;
    private List<String> nombreList;
    private List<String> grupoList;
    private List<String> eventoList;
    private List<String> matriculaList;
    private SharedPreferences preferenciaEventos;

    public eventos_adapter(List<String>nombreList, List<String>grupoList, List<String>eventoList, List<String>matriculaList, Context context){
        this.nombreList = nombreList;
        this.grupoList = grupoList;
        this.eventoList = eventoList;
        this.matriculaList = matriculaList;
        preferenciaEventos = context.getSharedPreferences("MyPreferencia_Eventos", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public eventos_adapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_eventos_adapter, parent, false);
        return new eventos_adapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull eventos_adapterViewHolder holder, int position) {
        holder.nombre.setText(nombreList.get(position));
        holder.grupo.setText(grupoList.get(position));
        holder.evento.setText(eventoList.get(position));
        holder.matricula.setText(matriculaList.get(position));

        // Configurar el estado del CheckBox
        boolean tramiteRealizado = preferenciaEventos.getBoolean("tramiteRealizado_" + position, false);
        holder.realizadoEventos.setChecked(tramiteRealizado);

        // Guardar el estado del CheckBox en SharedPreferences cuando cambia
        holder.realizadoEventos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferenciaEventos.edit();
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
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public  class eventos_adapterViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, grupo, evento, matricula;
        CheckBox realizadoEventos;

        public eventos_adapterViewHolder (@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreAlumno);
            grupo = itemView.findViewById(R.id.grupoAlumno);
            evento = itemView.findViewById(R.id.eventoAlumno);
            matricula = itemView.findViewById(R.id.matriculaAlumno);
            realizadoEventos = itemView.findViewById(R.id.checkBoxTramiteRealizado_Eventos);

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