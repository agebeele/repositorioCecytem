package com.example.dual.solicitudCredencial;

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

public class credencial_adapter extends RecyclerView.Adapter<credencial_adapter.credencial_adapterViewHolder> {

    private OnItemClickListener listener;
    private List<String> nombreList;
    private List<String> paternoList;
    private List<String> maternoList;
    private List<String> matriculaList;
    private SharedPreferences preferenciaCredencial;

    public credencial_adapter(List<String> nombreList, List<String> paternoList, List<String> maternoList, List<String> matriculaList, Context context) {
        this.nombreList = nombreList;
        this.paternoList = paternoList;
        this.maternoList = maternoList;
        this.matriculaList = matriculaList;
        preferenciaCredencial = context.getSharedPreferences("MyPreferencia_Credencial", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public credencial_adapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_credencial_adapter, parent, false);
        return new credencial_adapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull credencial_adapterViewHolder holder, int position) {
        holder.nombre.setText(nombreList.get(position));
        holder.paterno.setText(paternoList.get(position));
        holder.materno.setText(maternoList.get(position));
        holder.matricula.setText(matriculaList.get(position));

        // Configurar el estado del CheckBox
        boolean tramiteRealizado = preferenciaCredencial.getBoolean("tramiteRealizado_" + position, false);
        holder.realizadoCredencial.setChecked(tramiteRealizado);

        // Guardar el estado del CheckBox en SharedPreferences cuando cambia
        holder.realizadoCredencial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferenciaCredencial.edit();
            editor.putBoolean("tramiteRealizado_" + position, isChecked);
            editor.apply();
        });
    }

    @Override
    public int getItemCount() {
        return nombreList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class credencial_adapterViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, paterno, materno, matricula;
        CheckBox realizadoCredencial;

        public credencial_adapterViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreAlumno);
            paterno = itemView.findViewById(R.id.paternoAlumno);
            materno = itemView.findViewById(R.id.maternoAlumno);
            matricula = itemView.findViewById(R.id.matriculaAlumno);
            realizadoCredencial = itemView.findViewById(R.id.checkBoxTramiteRealizado_Credencial);

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