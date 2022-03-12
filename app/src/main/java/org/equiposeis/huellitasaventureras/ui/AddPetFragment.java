package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.MainActivity.ADD_PET_TYPE;
import static org.equiposeis.huellitasaventureras.MainActivity.db;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotaSeleccionada;
import static org.equiposeis.huellitasaventureras.ui.ProfileFragment.mascotas;

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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseUser;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.databinding.FragmentAddPetBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class AddPetFragment extends Fragment {

    private FragmentAddPetBinding binding;
    private String petname = "", otherrace = "", id_persona = "", petfecha = "", race1 = "";
    private int race = 0, petage = 0;
    private String[] races = null;
    private FirebaseUser user = null;
    private Mascota mascota = new Mascota();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        races = getResources().getStringArray(R.array.races);
        user = auth.getCurrentUser();

        binding.txtlytOtherRace.setVisibility(View.GONE);
        binding.txtOtherRace.setVisibility(View.GONE);
        binding.txtPetfecha.setOnClickListener(view -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Fecha de Nacimiento")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();
            datePicker.addOnPositiveButtonClickListener(selection -> binding.txtPetfecha.setText(setDate(selection)));
            datePicker.show(getActivity().getSupportFragmentManager(), "tag");
        });

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
                binding.txtRace.setText("Otro");
                binding.txtOtherRace.setText(mascotaSeleccionada.getRaza());
                binding.txtlytOtherRace.setVisibility(View.VISIBLE);
                binding.txtOtherRace.setVisibility(View.VISIBLE);
            }
        }


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
                race1 = "Husky siberiano";
            } else if (binding.txtRace.getText().toString().equals("Golden retriever")) {
                race = 1;
                race1 = "Golden retriever";
            } else if (binding.txtRace.getText().toString().equals("Caniche")) {
                race = 2;
                race1 = "Caniche";
            } else if (binding.txtRace.getText().toString().equals("Pastor alemán")) {
                race = 3;
                race1 = "Pastor alemán";
            } else if (binding.txtRace.getText().toString().equals("Yorkshire terrier")) {
                race = 4;
                race1 = "Yorkshire terrier";
            } else if (binding.txtRace.getText().toString().equals("Dálmata")) {
                race = 5;
                race1 = "Dálmata";
            } else if (binding.txtRace.getText().toString().equals("Bóxer")) {
                race = 6;
                race1 = "Bóxer";
            } else if (binding.txtRace.getText().toString().equals("Chihuahua")) {
                race = 7;
                race1 = "Chihuahua";
            } else if (binding.txtRace.getText().toString().equals("Bulldog inglés")) {
                race = 8;
                race1 = "Bulldog inglés";
            } else if (binding.txtRace.getText().toString().equals("Beagle")) {
                race = 9;
                race1 = "Beagle";
            } else if (binding.txtRace.getText().toString().equals("Schnauzer")) {
                race = 10;
                race1 = "Schnauzer";
            } else {
                race = 11;
            }

            mascota.setId_usuario(user.getUid());
            mascota.setNombre_mascota(petname);
            mascota.setFecha_mascota(petfecha);
            mascota.setRaza(race1);

            if (!petname.isEmpty() && race != 11) {
                //Mandar a la BD todos los datos

                Map<String, Object> Mascota_db = new HashMap<>();
                Mascota_db.put("ID_Cliente", mascota.getId_usuario());
                Mascota_db.put("Nombre", mascota.getNombre_mascota());
                Mascota_db.put("Fecha", mascota.getFecha_mascota());
                Mascota_db.put("Raza", mascota.getRaza());

                db.collection("Mascota").document(user.getUid() + petname)
                        .set(Mascota_db)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (ADD_PET_TYPE.equals("ADD_PET"))
                                    mascotas.add(mascota);
                                else if (ADD_PET_TYPE.equals("EDIT_PET"))
                                    mascotas.set(mascotas.indexOf(mascotaSeleccionada), mascota);
                                Toast.makeText(requireActivity(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_add_pet_to_navigation_profile, null);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "Fallo en el Registro", Toast.LENGTH_SHORT).show();
                            }
                        });

            } else if (!petname.isEmpty() && race == 11) {
                //Mandar a la BD todos los datos cuando race es otro
                if (!binding.txtOtherRace.getText().toString().isEmpty()) {
                    otherrace = binding.txtOtherRace.getText().toString();
                    mascota.setRaza(otherrace);
                }
                Map<String, Object> Mascota_db = new HashMap<>();
                Mascota_db.put("ID_Cliente", mascota.getId_usuario());
                Mascota_db.put("Nombre", mascota.getNombre_mascota());
                Mascota_db.put("Fecha", mascota.getFecha_mascota());
                Mascota_db.put("Raza", mascota.getRaza());

                db.collection("Mascota").document(user.getUid() + petname)
                        .set(Mascota_db)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (ADD_PET_TYPE.equals("ADD_PET"))
                                    mascotas.add(mascota);
                                else if (ADD_PET_TYPE.equals("EDIT_PET"))
                                    mascotas.set(mascotas.indexOf(mascotaSeleccionada), mascota);
                                Toast.makeText(requireActivity(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_add_pet_to_navigation_profile, null);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "Fallo en el Registro", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(requireActivity(), "Campos Incompletos", Toast.LENGTH_SHORT).show();
            }
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
        if (ADD_PET_TYPE.equals("EDIT_PET"))
            menu.findItem(R.id.delete_pet).setVisible(true);
        else
            menu.findItem(R.id.optionsMenu).setVisible(false);
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

    private String setDate(Long dateTimeStamp) {
        int offsetFromUTC = TimeZone.getDefault().getOffset(new Date().getTime()) * -1;
        DateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault());

        return (dateFormat.format((dateTimeStamp + offsetFromUTC)));
    }
}