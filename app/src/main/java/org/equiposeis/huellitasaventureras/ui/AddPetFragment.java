package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.MainActivity.ADD_PET_TYPE;
import static org.equiposeis.huellitasaventureras.MainActivity.db;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotaSeleccionada;
import static org.equiposeis.huellitasaventureras.ui.ProfileFragment.mascotas;
import static org.equiposeis.huellitasaventureras.ui.ProfileFragment.rclrPetsAdapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.databinding.FragmentAddPetBinding;

import java.util.HashMap;
import java.util.Map;

public class AddPetFragment extends Fragment {

    private FragmentAddPetBinding binding;
    private String petname = "",otherrace="",id_persona="",petfecha="",race1="";
    private int race = 0, petage = 0;
    private String[] races = null;
    private FirebaseUser user = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        races = getResources().getStringArray(R.array.races);
        user = auth.getCurrentUser();

        Mascota mascota = new Mascota(id_persona,petname,petage,petfecha,race1,otherrace);

        binding.txtlytOtherRace.setVisibility(View.GONE);
        binding.txtOtherRace.setVisibility(View.GONE);

        if (ADD_PET_TYPE.equals("EDIT_PET")) {
            binding.txtPetName.setText(mascotaSeleccionada.getNombre_mascota());
            binding.txtPetfecha.setText(mascotaSeleccionada.getFecha_mascota());
            binding.txtRace.setText(mascotaSeleccionada.getRaza());
            if (!mascotaSeleccionada.getRaza().equals("Husky siberiano") &&
                    !mascotaSeleccionada.getRaza().equals("Golden retriever") &&
                    !mascotaSeleccionada.getRaza().equals("Caniche") &&
                    !mascotaSeleccionada.getRaza().equals("Pastor alemán") &&
                    !mascotaSeleccionada.getRaza().equals("Yorkshire terrier") &&
                    !mascotaSeleccionada.getRaza().equals("Dálmata") &&
                    !mascotaSeleccionada.getRaza().equals("Bóxer") &&
                    !mascotaSeleccionada.getRaza().equals("Chihuahua") &&
                    !mascotaSeleccionada.getRaza().equals("Bulldog inglés") &&
                    !mascotaSeleccionada.getRaza().equals("Beagle") &&
                    !mascotaSeleccionada.getRaza().equals("Schnauzer")) {
                binding.txtOtherRace.setText(mascotaSeleccionada.getOtraraza());
                binding.txtlytOtherRace.setVisibility(View.VISIBLE);
                binding.txtOtherRace.setVisibility(View.VISIBLE);
            }
        }

        String id_persona = mascota.getId_persona();
        String nombre_mascota = mascota.getNombre_mascota();
        Integer edad_mascota = mascota.getEdad_mascota();
        String raza = mascota.getRaza();
        String Otraraza = mascota.getOtraraza();
        String petfech = mascota.getFecha_mascota();


        binding.txtRace.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, races));

        //Listener para el cambio de visibilidad de txtOtherrace en condición de selección Otos
        binding.txtRace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 11) {
                    binding.txtlytOtherRace.setVisibility(View.VISIBLE);
                    binding.txtOtherRace.setVisibility(View.VISIBLE);
                } else {
                    binding.txtlytOtherRace.setVisibility(View.GONE);
                    binding.txtOtherRace.setVisibility(View.GONE);
                }
            }
        });

        binding.bttnPetRegister.setOnClickListener(v -> {

            if (!binding.txtPetName.getText().toString().isEmpty()) {
                petname = binding.txtPetName.getText().toString();
            }
            if (!binding.txtPetfecha.getText().toString().isEmpty()) {
                petfecha = binding.txtPetfecha.getText().toString();
            }

            if (binding.txtRace.getText().toString().equals("Husky siberiano")) {
                race = 0;
                race1 ="Husky siberiano";
            } else if (binding.txtRace.getText().toString().equals("Golden retriever")) {
                race = 1;
                race1 ="Golden retriever";
            } else if (binding.txtRace.getText().toString().equals("Caniche")) {
                race = 2;
                race1 ="Caniche";
            } else if (binding.txtRace.getText().toString().equals("Pastor alemán")) {
                race = 3;
                race1 ="Pastor alemán";
            } else if (binding.txtRace.getText().toString().equals("Yorkshire terrier")) {
                race = 4;
                race1 ="Yorkshire terrier";
            } else if (binding.txtRace.getText().toString().equals("Dálmata")) {
                race = 5;
                race1 ="Dálmata";
            } else if (binding.txtRace.getText().toString().equals("Bóxer")) {
                race = 6;
                race1 ="Bóxer";
            } else if (binding.txtRace.getText().toString().equals("Chihuahua")) {
                race = 7;
                race1 ="Chihuahua";
            } else if (binding.txtRace.getText().toString().equals("Bulldog inglés")) {
                race = 8;
                race1 ="Bulldog inglés";
            } else if (binding.txtRace.getText().toString().equals("Beagle")) {
                race = 9;
                race1 ="Beagle";
            } else if (binding.txtRace.getText().toString().equals("Schnauzer")) {
                race = 10;
                race1 ="Schnauzer";
            } else {
                race = 11;
            }

            mascota.setId_persona(user.getUid());
            mascota.setNombre_mascota(petname);
            mascota.setFecha_mascota(petfecha);
            mascota.setRaza(race1);
            mascota.setOtraraza(otherrace);

            if (!petname.isEmpty() && race != 11) {
                //Mandar a la BD todos los datos

                Map<String, Object> Mascota_db = new HashMap<>();
                Mascota_db.put("ID_Cliente", mascota.getId_persona());
                Mascota_db.put("Nombre", mascota.getNombre_mascota());
                Mascota_db.put("Fecha", mascota.getFecha_mascota());
                Mascota_db.put("Raza", mascota.getRaza());

                db.collection("Mascota").document(user.getUid()+petname)
                        .set(Mascota_db)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mascotas.add(mascota);
                                Toast.makeText(requireActivity(),"Registro Exitoso", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_add_pet_to_navigation_profile, null);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(),"Fallo en el Registro", Toast.LENGTH_SHORT).show();
                            }
                        });

            } else if(!petname.isEmpty() && race==11){
                //Mandar a la BD todos los datos cuando race es otro
                if (!binding.txtOtherRace.getText().toString().isEmpty()) {
                    otherrace = binding.txtOtherRace.getText().toString();
                    mascota.setOtraraza(otherrace);
                }
                Map<String, Object> Mascota_db = new HashMap<>();
                Mascota_db.put("ID_Cliente", mascota.getId_persona());
                Mascota_db.put("Nombre", mascota.getNombre_mascota());
                Mascota_db.put("Fecha", mascota.getFecha_mascota());
                Mascota_db.put("Raza", mascota.getOtraraza());

                db.collection("Mascota").document(user.getUid()+petname)
                        .set(Mascota_db)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mascotas.add(mascota);
                                Toast.makeText(requireActivity(),"Registro Exitoso", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_add_pet_to_navigation_profile, null);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_options_menu, menu);
        menu.findItem(R.id.delete_pet).setVisible(true);
        menu.findItem(R.id.action_navigation_profile_to_navigation_edit_profile).setVisible(false);
        menu.findItem(R.id.logout).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_pet:
                db.collection("Mascota").document(user.getUid() + mascotaSeleccionada.getNombre_mascota())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                mascotas.remove(mascotaSeleccionada);
                                NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_add_pet_to_navigation_profile);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "Intentelo de nuevo", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}