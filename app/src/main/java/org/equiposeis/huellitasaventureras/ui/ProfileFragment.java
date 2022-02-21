package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import org.equiposeis.huellitasaventureras.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

private FragmentProfileBinding binding;
private int user =0;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentProfileBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    //Jalar datos de la BD
        //Ocultar datos de paseador
if (user==0){
    binding.txtViewPet.setVisibility(View.VISIBLE);
    binding.rclrPet.setVisibility(View.VISIBLE);
    binding.bttnAddPet.setVisibility(View.VISIBLE);
    binding.txtViewWalker.setVisibility(View.GONE);
    binding.rclrRides.setVisibility(View.GONE);
} else { //ocultar datos de cliente
    binding.txtViewPet.setVisibility(View.GONE);
    binding.rclrPet.setVisibility(View.GONE);
    binding.bttnAddPet.setVisibility(View.GONE);
    binding.txtViewWalker.setVisibility(View.VISIBLE);
    binding.rclrRides.setVisibility(View.VISIBLE);
}

    binding.bttnAddPet.setOnClickListener( v -> {
        //Navegación hacia el fragment agregar mascota

    }
);

    return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}