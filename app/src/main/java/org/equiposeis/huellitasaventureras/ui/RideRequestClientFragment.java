package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentAddPetBinding;
import org.equiposeis.huellitasaventureras.databinding.FragmentRideRequestClientBinding;

public class RideRequestClientFragment extends Fragment {

    private FragmentRideRequestClientBinding binding;
    private int RideQuestClientEmpleado,RideQuestClientMascota,RideQuestClientTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRideRequestClientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        binding.bttnClientRequest.setOnClickListener(v -> {
            RideQuestClientEmpleado = binding.spnrEmployee.getSelectedItemPosition();
            RideQuestClientMascota = binding.spnrPet.getSelectedItemPosition();
            RideQuestClientTime = binding.spnrTime.getSelectedItemPosition();

            //Mandar datos a la base de datos.
        });

        binding.bttnCancelClientRequest.setOnClickListener(v -> getActivity().onBackPressed(

                //Regresar navegación.

        ));

        return inflater.inflate(R.layout.fragment_ride_request_client, container, false);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}