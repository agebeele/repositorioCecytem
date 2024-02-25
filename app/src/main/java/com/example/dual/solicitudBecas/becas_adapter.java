package com.example.dual.solicitudBecas;

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

public class becas_adapter extends RecyclerView.Adapter<becas_adapter.becas_adapterViewHolder> {

    private OnItemClickListener listener;
    private List<String> curpList ;
    private List<String> paternoList;
    private List<String> maternoList ;
    private List<String> nombreList ;
    private SharedPreferences preferenciaBecas;

    public becas_adapter(List<String> curpList, List<String> nombreList, List<String> paternoList, List<String> maternoList, Context context) {
        this.curpList = curpList;
        this.paternoList = paternoList;
        this.maternoList = maternoList;
        this.nombreList = nombreList;
        preferenciaBecas = context.getSharedPreferences("MyPreferencia_Becas", Context.MODE_PRIVATE);
    }


    @NonNull
    @Override
    public becas_adapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_becas_adapter, parent, false);
        return new becas_adapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull becas_adapterViewHolder holder, int position) {
        holder.nombre.setText(nombreList.get(position));
        holder.paterno.setText(paternoList.get(position));
        holder.materno.setText(maternoList.get(position));
        holder.curp.setText(curpList.get(position));

        // Configurar el estado del CheckBox
        boolean tramiteRealizado = preferenciaBecas.getBoolean("tramiteRealizado_" + position, false);
        holder.realizadoBecas.setChecked(tramiteRealizado);

        // Guardar el estado del CheckBox en SharedPreferences cuando cambia
        holder.realizadoBecas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferenciaBecas.edit();
            editor.putBoolean("tramiteRealizado_" + position, isChecked);
            editor.apply();
        });
    }

    @Override
    public int getItemCount() {
        return curpList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class becas_adapterViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, paterno, materno, curp;
        CheckBox realizadoBecas;

        public becas_adapterViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreAlumno);
            paterno = itemView.findViewById(R.id.paternoAlumno);
            materno = itemView.findViewById(R.id.maternoAlumno);
            curp = itemView.findViewById(R.id.curpAlumno);
            realizadoBecas = itemView.findViewById(R.id.checkBoxTramiteRealizado_Beca);

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
