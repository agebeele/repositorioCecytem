package com.example.dual.adminVIN;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dual.R;

import java.util.List;

public class AdapterVIN extends RecyclerView.Adapter<AdapterVIN.AdapterVINViewHolder> {

    private List<String> tituloList;
    private List<String> descripcionList;
    private List<String> fechaList;
    private List<String> horaList;
    private List<String> imageList;

    public AdapterVIN(List<String> tituloList, List<String> descripcionList,List<String> fechaList,List<String> horaList,List<String> imageList) {
        this.tituloList = tituloList; this.descripcionList = descripcionList; this.fechaList = fechaList; this.horaList = horaList;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public AdapterVIN.AdapterVINViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_vin, parent, false);
        return new AdapterVIN.AdapterVINViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVIN.AdapterVINViewHolder holder, int position) {
        holder.imagen.setImageResource(Integer.parseInt(imageList.get(position)));
        holder.titulo.setText(tituloList.get(position));
        holder.descripcion.setText(descripcionList.get(position));
        holder.fecha.setText(fechaList.get(position));
        holder.hora.setText(horaList.get(position));
    }

    @Override
    public int getItemCount() {
        return tituloList.size();
    }
    public static class AdapterVINViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView titulo, descripcion, fecha, hora;

        public AdapterVINViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_publicacion);
            titulo = itemView.findViewById(R.id.titulo_publicacion);
            descripcion = itemView.findViewById(R.id.descripcion_publicacion);
            fecha = itemView.findViewById(R.id.fecha_publicacion);
            hora = itemView.findViewById(R.id.hora_publicacion);
        }
    }
}