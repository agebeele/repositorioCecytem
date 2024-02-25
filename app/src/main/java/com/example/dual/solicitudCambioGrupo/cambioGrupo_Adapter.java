package com.example.dual.solicitudCambioGrupo;

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

public class cambioGrupo_Adapter extends RecyclerView.Adapter<cambioGrupo_Adapter.cambioGrupoAdapterViewHolder> {
    private OnItemClickListener listener;
    private List<String> nombreList;
    private List<String> paternoList;
    private List<String> maternoList;
    private List<String> matriculaList;
    private SharedPreferences preferencia_cambioGrupo;
    public cambioGrupo_Adapter(List<String> nombreList, List<String> paternoList, List<String> maternoList, List<String> matriculaList, Context context) {
        this.nombreList = nombreList;
        this.paternoList = paternoList;
        this.maternoList = maternoList;
        this.matriculaList = matriculaList;
        preferencia_cambioGrupo = context.getSharedPreferences("MyPreferencia_Carrera", Context.MODE_PRIVATE);
    }
    @NonNull
    @Override
    public cambioGrupoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cambio_grupo_adapter, parent, false);
        return new cambioGrupoAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull cambioGrupoAdapterViewHolder holder, int position) {
        holder.nombre.setText(nombreList.get(position));
        holder.paterno.setText(paternoList.get(position));
        holder.materno.setText(maternoList.get(position));
        holder.matricula.setText(matriculaList.get(position));

        // Configurar el estado del CheckBox
        boolean tramiteRealizado = preferencia_cambioGrupo.getBoolean("tramiteRealizado_" + position, false);
        holder.realizado_cambioGrupo.setChecked(tramiteRealizado);

        // Guardar el estado del CheckBox en SharedPreferences cuando cambia
        holder.realizado_cambioGrupo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferencia_cambioGrupo.edit();
            editor.putBoolean("tramiteRealizado_" + position, isChecked);
            editor.apply();
        });
    }

    @Override
    public int getItemCount()  {
        return nombreList.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class cambioGrupoAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, paterno, materno, matricula;
        CheckBox realizado_cambioGrupo;

        public cambioGrupoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_grupo);
            paterno = itemView.findViewById(R.id.paterno_grupo);
            materno = itemView.findViewById(R.id.materno_grupo);
            matricula = itemView.findViewById(R.id.matricula_grupo);
            realizado_cambioGrupo = itemView.findViewById(R.id.checkBoxTramiteRealizado_grupo);

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