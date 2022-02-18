package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentAddPetBinding;
import org.equiposeis.huellitasaventureras.databinding.FragmentRegisterBinding;

import java.util.Objects;

public class AddPetFragment extends Fragment {

    private FragmentAddPetBinding binding;
    private String petname;
    private int race,otherrace, petage;
    private final String[] races = getResources().getStringArray(R.array.races);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.txtRace.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, races));

        binding.txtOtherRace.setVisibility(View.GONE);

        //Listener para el cambio de visibilidad de txtOtherrace en condición de selección Otos
        binding.txtRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(binding.txtRace.getText().toString().equals("Otros")) {
                    binding.txtOtherRace.setVisibility(View.VISIBLE);
                } else { }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
        binding.bttnPetRegister.setOnClickListener(v -> {

            petname = Objects.requireNonNull(binding.txtPetName.getText()).toString();
            petage = Integer.parseInt(Objects.requireNonNull(binding.txtPetAge.getText()).toString());

            if (binding.txtRace.getText().toString().equals("Masculino")) {
                race = 0;
            } else if (binding.txtRace.getText().toString().equals("Husky siberiano")) {
                race = 1;
            } else if (binding.txtRace.getText().toString().equals("Golden retrieve")) {
                race = 2;
            } else if (binding.txtRace.getText().toString().equals("Pastor alemán")) {
                race = 3;
            } else if (binding.txtRace.getText().toString().equals("Yorkshire terrier")) {
                race = 4;
            } else if (binding.txtRace.getText().toString().equals("Dálmata")) {
                race = 5;
            } else if (binding.txtRace.getText().toString().equals("Bóxer")) {
                race = 6;
            } else if (binding.txtRace.getText().toString().equals("Chihuahua")) {
                race = 7;
            } else if (binding.txtRace.getText().toString().equals("Bulldog inglés")) {
                race = 8;
            } else if (binding.txtRace.getText().toString().equals("Beagle")) {
                race = 9;
            } else if (binding.txtRace.getText().toString().equals("Schnauzer")) {
                race = 10;
            } else {
                race = 11;
            }

            if (!petname.isEmpty() && race!=11) {
                    //Mandar datos a la base de datos.
                    requireActivity().onBackPressed();
                } else {
                    Toast.makeText(requireActivity(), R.string.not_loged, Toast.LENGTH_SHORT).show();
                }

        });

        binding.bttnCancelPetRegister.setOnClickListener(v -> getActivity().onBackPressed(
                //Regresar navegación.
        ));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}