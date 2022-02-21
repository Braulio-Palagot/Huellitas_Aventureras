package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

        binding.bttnQuestRide.setOnClickListener(v -> getActivity().onBackPressed(
                //Navegación hacia el Fragment Solicitar paseo.
        ));



    return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}