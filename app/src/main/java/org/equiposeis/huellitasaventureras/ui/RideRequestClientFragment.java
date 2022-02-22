package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentAddPetBinding;
import org.equiposeis.huellitasaventureras.databinding.FragmentRideRequestClientBinding;

public class RideRequestClientFragment extends Fragment {

    private FragmentRideRequestClientBinding binding;
    private int RideQuestClientEmployee,RideQuestClientPets,RideQuestClientTime;

    private final String[] Employees = getResources().getStringArray(R.array.Employees);
    private final String[] Pets = getResources().getStringArray(R.array.Pets);
    private final String[] Times = getResources().getStringArray(R.array.Times);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRideRequestClientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.txtEmployee.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, Employees));
        binding.txtPet.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, Pets));
        binding.txtTime.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, Times));


        binding.bttnClientRequest.setOnClickListener(v -> {


            //Rellenado de datos Empleados Disponibles subidos en la  BD



            //Rellenado de datos de Mascotas registradas subidos en la BD


            if (binding.txtTime.getText().toString().equals("30 Mint")) {
                RideQuestClientTime = 0;
            } else if (binding.txtTime.getText().toString().equals("45 Mint")) {
                RideQuestClientTime = 1;
            } else if (binding.txtTime.getText().toString().equals("60 Mint")) {
                RideQuestClientTime = 2;
            } else if (binding.txtTime.getText().toString().equals("75 Mint")) {
                RideQuestClientTime = 3;
            } else if (binding.txtTime.getText().toString().equals("90 Mint")) {
                RideQuestClientTime = 4;
            } else if (binding.txtTime.getText().toString().equals("105 Mint")) {
                RideQuestClientTime = 5;
            } else if (binding.txtTime.getText().toString().equals("120 Mint")) {
                RideQuestClientTime = 6;
            } else if (binding.txtTime.getText().toString().equals("135 Mint")) {
                RideQuestClientTime = 7;
            } else if (binding.txtTime.getText().toString().equals("150 Mint")) {
                RideQuestClientTime = 8;
            } else if (binding.txtTime.getText().toString().equals("165 Mint")) {
                RideQuestClientTime = 9;
            } else {
                RideQuestClientTime = 10;
            }


            //Metodo para mandar datos a la base de datos.
            NavHostFragment.findNavController(this).navigate(R.id. action_rideRequestClientFragment_to_navigation_home, null);

        });

        binding.bttnCancelClientRequest.setOnClickListener(v ->
            //Regresar navegación.
             NavHostFragment.findNavController(this).navigate(R.id. action_rideRequestClientFragment_to_navigation_home, null)
                );

        return inflater.inflate(R.layout.fragment_ride_request_client, container, false);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}