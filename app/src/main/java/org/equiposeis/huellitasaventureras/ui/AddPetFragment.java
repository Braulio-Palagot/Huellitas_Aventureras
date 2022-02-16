package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentAddPetBinding;
import org.equiposeis.huellitasaventureras.databinding.FragmentRegisterBinding;

public class AddPetFragment extends Fragment {

    private FragmentAddPetBinding binding;
    private String petname;
    private int race,otherrace, petage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.txtOtherRace.setVisibility(View.GONE);

        //Inicio del Listen Spinner
       binding.spnrRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               //Condición del evento
             if(binding.spnrRace.getSelectedItemPosition()==12)
              binding.txtOtherRace.setVisibility(View.VISIBLE);
           }
           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
        //Fin del Listen Spinner

        binding.bttnPetRegister.setOnClickListener(v -> {
            petname = binding.txtPetName.getText().toString();
            petage = Integer.parseInt(binding.txtPetAge.getText().toString());
            race = binding.spnrRace.getSelectedItemPosition();
            otherrace = Integer.parseInt(binding.txtOtherRace.getText().toString());


            //Mandar datos a la base de datos.
        });

        binding.bttnCancelPetRegister.setOnClickListener(v -> getActivity().onBackPressed(

                //Regresar navegación.

        ));

        return inflater.inflate(R.layout.fragment_add_pet, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}