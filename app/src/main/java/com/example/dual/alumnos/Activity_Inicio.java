package com.example.dual.alumnos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dual.R;
import com.example.dual.databinding.ActivityInicioBinding;
import com.example.dual.inicioAlumnos.fragment_configuracion;
import com.example.dual.inicioAlumnos.fragment_control_escolar;
import com.example.dual.inicioAlumnos.fragment_inicio;
import com.example.dual.inicioAlumnos.fragment_vinculacion;

public class Activity_Inicio extends AppCompatActivity {
    ActivityInicioBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Iniciar transacción de fragmento solo si savedInstanceState es nulo
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.frameLayout.getId(), new fragment_inicio())
                    .commit();
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Inicio) {
                replaceFragment(new fragment_inicio());
            } else if (item.getItemId() == R.id.CE) {
                replaceFragment(new fragment_control_escolar());
            } else if (item.getItemId() == R.id.Vinculacion) {
                replaceFragment(new fragment_vinculacion());
            } else if (item.getItemId() == R.id.Config) {
                replaceFragment(new fragment_configuracion());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}