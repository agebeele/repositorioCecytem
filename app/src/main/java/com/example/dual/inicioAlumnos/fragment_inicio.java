package com.example.dual.inicioAlumnos;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dual.alumnos.DividerItemDecoration;
import com.example.dual.R;
import com.example.dual.WebService;
import com.example.dual.alumnos.PublicacionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragment_inicio extends Fragment {

    RecyclerView recyclerView;
    PublicacionAdapter myAdapter;
    //private List<item_publicacion> itemPublicacionList;
    String msj;
    WebService obj = new WebService();
    private List<String> titloList = new ArrayList<>();
    private List<String> descripcionList = new ArrayList<>();
    private List<String> fechaList = new ArrayList<>();
    private List<String> horaList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inicio, container, false);

        //itemPublicacionList = generatePublicacionItems();

        recyclerView = v.findViewById(R.id.recyclerView_inicio);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_line);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myAdapter = new PublicacionAdapter(titloList, descripcionList, horaList, fechaList, imageList);

        recyclerView.setAdapter(myAdapter);

        MiAsyncTask miAsyncTask = new MiAsyncTask();
        miAsyncTask.execute();

        return v;
    }

    /*private List<item_publicacion> generatePublicacionItems(){
        List<item_publicacion> itemPublicacions = new ArrayList<>();
        itemPublicacions.add(new item_publicacion(R.drawable.cecytem,"Consejo tecnico","El dia de mañana se llevara a cabo", "08/11/2023", "10:12"));
        itemPublicacions.add(new item_publicacion(R.drawable.cecytem,"Junta de Padres","Firma de boletas", "14/12/2023", "03:12"));
        itemPublicacions.add(new item_publicacion(R.drawable.cecytem,"No hay clases","Por el cumpleaños de Brandon", "10/11/2023", "08:30"));
        itemPublicacions.add(new item_publicacion(R.drawable.cecytem,"Consejo tecnico","El dia de mañana se llevara a cabo", "08/11/2023", "10:12"));
        itemPublicacions.add(new item_publicacion(R.drawable.cecytem,"Junta de Padres","Firma de boletas", "14/12/2023", "03:12"));
        itemPublicacions.add(new item_publicacion(R.drawable.cecytem,"No hay clases","Por el cumpleaños de Brandon", "10/11/2023", "08:30"));
        itemPublicacions.add(new item_publicacion(R.drawable.cecytem,"Consejo tecnico","El dia de mañana se llevara a cabo", "08/11/2023", "10:12"));
        itemPublicacions.add(new item_publicacion(R.drawable.cecytem,"Junta de Padres","Firma de boletas", "14/12/2023", "03:12"));
        itemPublicacions.add(new item_publicacion(R.drawable.cecytem,"No hay clases","Por el cumpleaños de Brandon", "10/11/2023", "08:30"));

        return itemPublicacions;
    }
    */
    class MiAsyncTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... parameters) {
            msj = obj.muroPublicaciones();
            publishProgress(msj);
            return null;
        }
        @SuppressLint({"NotifyDataSetChanged", "DiscouragedApi"})
        @Override
        protected void onProgressUpdate(String... progress) {
            try {
                JSONArray jsonArray = new JSONArray(progress[0]);
                JSONObject json_data;
                int length = jsonArray.length();
                for (int i = 0; i < length; i++) {
                    json_data = jsonArray.getJSONObject(i);

                    titloList.add(json_data.getString("titulo"));
                    descripcionList.add(json_data.getString("descripcion"));
                    fechaList.add(json_data.getString("fecha"));
                    horaList.add(json_data.getString("hora"));

                    // Agrega una nueva imagen para cada elemento
                    imageList.add(String.valueOf(R.drawable.cecytem));
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), progress[0], Toast.LENGTH_LONG).show();
            }
            myAdapter.notifyDataSetChanged();
        }
    }
}