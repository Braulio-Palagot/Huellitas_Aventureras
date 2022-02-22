package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;
private int user =0;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        //Tomar los datos de la BD

        if (user==0){//ocultaer datos empleado
                  //Ocultar los recyclerview

        } else { //ocultar datos de cliente
            binding.bttnQuestRide.setVisibility(View.GONE);
                //Publicidad e imagenes
        }

        binding.bttnQuestRide.setOnClickListener(v ->
                //Solicitar Paseo
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_navigation_ride_request, null)
        );

    return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}