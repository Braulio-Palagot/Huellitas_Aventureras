package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentRideDetailsBinding;

public class RideDetailsFragment extends Fragment {

    private FragmentRideDetailsBinding binding;
    private int ride =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentRideDetailsBinding.inflate(inflater,container,false);
        View root=binding.getRoot();

        //Lamar datos de la BD

        //Ocultar datos de solicitud
        if (ride==0){
            binding.bttnAccept.setVisibility(View.GONE);
            binding.bttnReject.setVisibility(View.GONE);
        } else { //ocultar datos de paseo en curso
            binding.bttnAccept.setVisibility(View.VISIBLE);
            binding.bttnReject.setVisibility(View.VISIBLE);
        }

        binding.bttnAccept.setOnClickListener(v ->{
            //Mandar a BD la respuesta de aceptar
            requireActivity().onBackPressed();
        });

        binding.bttnReject.setOnClickListener(v ->{
            //Mandar a BD la respuesta de rechazar
            requireActivity().onBackPressed();
        });

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}