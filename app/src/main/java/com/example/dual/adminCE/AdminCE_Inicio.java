package com.example.dual.adminCE;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dual.R;
import com.example.dual.databinding.ActivityAdminCeInicioBinding;

public class AdminCE_Inicio extends AppCompatActivity {
    ActivityAdminCeInicioBinding bindingCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ce_inicio);

        bindingCE = ActivityAdminCeInicioBinding.inflate(getLayoutInflater());
        setContentView(bindingCE.getRoot());

        // Iniciar transacción de fragmento solo si savedInstanceState es nulo
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(bindingCE.frameLayoutCE.getId(), new FragmentCE_Inicio())
                    .commit();
        }

        bindingCE.bottomNavigationViewCE.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Inicio_CE) {
                replaceFragment(new FragmentCE_Inicio());
            } else if (item.getItemId() == R.id.CE_CE) {
                replaceFragment(new FragmentCE_CE());
            } else if (item.getItemId() == R.id.Config_CE) {
                replaceFragment(new FragmentCE_Config());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layoutCE, fragment);
        fragmentTransaction.commit();
    }
}