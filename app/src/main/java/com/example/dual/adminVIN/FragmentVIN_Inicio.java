package com.example.dual.adminVIN;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentVIN_Inicio extends Fragment {

    RecyclerView recyclerView;
    AdapterVIN adapterVIN;
    //private List<item_ce> item_ceList;
    FloatingActionButton agregarItem;
    String msj;
    WebService obj = new WebService();
    private List<String> titloList = new ArrayList<>();
    private List<String> descripcionList = new ArrayList<>();
    private List<String> fechaList = new ArrayList<>();
    private List<String> horaList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_v_i_n__inicio, container, false);

        // Inflate the layout for this fragment

        agregarItem = (FloatingActionButton) v.findViewById(R.id.floatingActionButton_vin);

        //item_ceList = generatePublicacionItems();

        recyclerView = v.findViewById(R.id.recyclerView_inicio_vin);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_line);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapterVIN = new AdapterVIN(titloList, descripcionList, horaList, fechaList, imageList);

        recyclerView.setAdapter(adapterVIN);

        MiAsyncTask miAsyncTask = new MiAsyncTask();
        miAsyncTask.execute();

        agregarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarPublicacion();
            }
        });

        return v;
    }

    private void agregarPublicacion() {
        Intent publicaion = new Intent(getActivity(),agregarItem_VIN.class);
        startActivity(publicaion);
    }
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
            adapterVIN.notifyDataSetChanged();
        }
    }
}