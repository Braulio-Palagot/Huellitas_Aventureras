package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.MainActivity.db;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.databinding.FragmentAddPetBinding;
import org.equiposeis.huellitasaventureras.databinding.FragmentRegisterBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddPetFragment extends Fragment {

    private FragmentAddPetBinding binding;
    private String petname = "",otherrace="";
    private int race = 0, petage = 0;
    private String[] races = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        races = getResources().getStringArray(R.array.races);

        Mascota mascota = new Mascota(petname,petage,race,otherrace);
        String nombre_mascota = mascota.getNombre_mascota();
        Integer edad_mascota = mascota.getEdad_mascota();
        Integer raza = mascota.getRaza();
        String Otraraza = mascota.getOtraraza();


        binding.txtRace.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, races));

        binding.txtOtherRace.setVisibility(View.GONE);

        //Listener para el cambio de visibilidad de txtOtherrace en condición de selección Otos
        binding.txtRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (binding.txtRace.getText().toString().equals("Otros")) {
                    binding.txtOtherRace.setVisibility(View.VISIBLE);
                } else {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
        binding.bttnPetRegister.setOnClickListener(v -> {

            if (!binding.txtPetName.getText().toString().isEmpty()) {
                petname = binding.txtPetName.getText().toString();
            }
            if (!binding.txtPetAge.getText().toString().isEmpty()) {
                petage = Integer.parseInt(binding.txtPetAge.getText().toString());
            }

            if (binding.txtRace.getText().toString().equals("Husky siberiano")) {
                race = 0;
            } else if (binding.txtRace.getText().toString().equals("Golden retrieve")) {
                race = 1;
            } else if (binding.txtRace.getText().toString().equals("Caniche")) {
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

            if (!petname.isEmpty() && race != 11) {
                //Mandar a la BD todos los datos
                Map<String, Object> Mascota_db = new HashMap<>();
                Mascota_db.put("Nombre", mascota.getNombre_mascota());
                Mascota_db.put("Edad", mascota.getEdad_mascota());
                Mascota_db.put("Raza", mascota.getRaza());

                db.collection("Mascota").document("Registro_Mascota")
                        .set(Mascota_db)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(requireActivity(),"Registro Exitoso", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(),"Fallo en el Registro", Toast.LENGTH_SHORT).show();
                            }
                        });
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_add_pet_to_navigation_profile, null);
            } else if(!petname.isEmpty() && race==11){
                //Mandar a la BD todos los datos cuando race es otro
                if (!binding.txtPetAge.getText().toString().isEmpty()) {
                    otherrace = binding.txtOtherRace.getText().toString();
                }
                Map<String, Object> Mascota_db = new HashMap<>();
                Mascota_db.put("Nombre", mascota.getNombre_mascota());
                Mascota_db.put("Edad", mascota.getEdad_mascota());
                Mascota_db.put("Raza", mascota.getOtraraza());

                db.collection("Mascota").document("Registro_Mascota")
                        .set(Mascota_db)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(requireActivity(),"Registro Exitoso", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(),"Fallo en el Registro", Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Toast.makeText(requireActivity(),"Campos Incompletos", Toast.LENGTH_SHORT).show(); }
        });

        binding.bttnCancelPetRegister.setOnClickListener(v ->
                //Regresar navegación.
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_add_pet_to_navigation_profile, null)
        );


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}