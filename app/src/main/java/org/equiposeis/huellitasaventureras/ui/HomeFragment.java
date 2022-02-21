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

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        //Condición en base si es Cliente o usuario se no seran visibles algunos campos

        //Bttn para solicitar Paseo
        binding.bttnQuestRide.setOnClickListener(v -> getActivity().onBackPressed(
                //Navegación hacia solicitar paseo.
        ));

        //Registros de solicitudes en curso y aceptados de la BD de forma tabular

    return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}