package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.MainActivity.ADD_PET_TYPE;
import static org.equiposeis.huellitasaventureras.MainActivity.PETS_ALREADY_DOWNLOADED;
import static org.equiposeis.huellitasaventureras.MainActivity.PROFILE_PHOTO_REFERENCE;
import static org.equiposeis.huellitasaventureras.MainActivity.PROFILE_RIDES_ALREADY_DOWNLOADED;
import static org.equiposeis.huellitasaventureras.MainActivity.clientsQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotaSeleccionada;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotasUsuarioQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.paseoSeleccionado;
import static org.equiposeis.huellitasaventureras.MainActivity.user;
import static org.equiposeis.huellitasaventureras.MainActivity.userQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.walksQuery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.equiposeis.huellitasaventureras.AuthActivity;
import org.equiposeis.huellitasaventureras.Glide.GlideApp;
import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.adapters.PetAdapter;
import org.equiposeis.huellitasaventureras.adapters.RidesCompletedAdapter;
import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.dataModels.Paseo;
import org.equiposeis.huellitasaventureras.databinding.FragmentProfileBinding;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private final Function1 onPetClickListener = (new Function1() {
        @Override
        public Object invoke(Object o) {
            this.invoke((Mascota)o);
            return Unit.INSTANCE;
        }

        public void invoke(Mascota mascota) {
            mascotaSeleccionada = mascota;
            ADD_PET_TYPE = "EDIT_PET";
            NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_navigation_profile_to_navigation_add_pet);
        }
    });
    public static ArrayList<Mascota> mascotas = new ArrayList<Mascota>();
    public static ArrayList<Paseo> paseosFinalizados = new ArrayList<Paseo>();
    public static PetAdapter rclrPetsAdapter = null;
    public static RidesCompletedAdapter rclrPaseosFinalizadosAdapter = null;
    private final Function1 onRideClickListener = (new Function1() {
        @Override
        public Object invoke(Object o) {
            this.invoke((Paseo) o);
            return Unit.INSTANCE;
        }

        public void invoke(Paseo paseo) {
            paseoSeleccionado = paseo;
            NavHostFragment.findNavController(ProfileFragment.this).navigate(R.id.action_navigation_profile_to_navigation_ride_details);
        }
    });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.rclrPet.setHasFixedSize(false);
        setHasOptionsMenu(true);
        rclrPetsAdapter = new PetAdapter(getActivity(), mascotas, onPetClickListener);
        rclrPaseosFinalizadosAdapter = new RidesCompletedAdapter(requireContext(), paseosFinalizados, clientsQuery, onRideClickListener);

        binding.txtViewPet.setVisibility(View.GONE);
        binding.rclrPet.setVisibility(View.GONE);
        binding.bttnAddPet.setVisibility(View.GONE);
        binding.txtViewWalker.setVisibility(View.GONE);
        binding.rclrRides.setVisibility(View.GONE);
        binding.rclrPet.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rclrPet.setAdapter(rclrPetsAdapter);
        binding.rclrRides.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rclrRides.setAdapter(rclrPaseosFinalizadosAdapter);

        //Jalar datos de la BD
        userQuery.addOnSuccessListener(documentSnapshot -> {
            DocumentSnapshot userData = userQuery.getResult();
            if (userData.exists()) {
                binding.txtName.setText(userData.get(getResources().getString(R.string.NOMBRE_USUARIO)).toString());
                binding.txtAddress.setText(userData.get(getResources().getString(R.string.DOMICILIO_USUARIO)).toString());
                GlideApp.with(ProfileFragment.this)
                        .load(PROFILE_PHOTO_REFERENCE)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.imgUserPhoto);

                //Ocultar datos de paseador
                if (Integer.parseInt(userData.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 0) {
                    binding.txtViewPet.setVisibility(View.VISIBLE);
                    binding.rclrPet.setVisibility(View.VISIBLE);
                    binding.bttnAddPet.setVisibility(View.VISIBLE);
                } else if (Integer.parseInt(userData.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 1) { //ocultar datos de cliente
                    binding.txtViewWalker.setVisibility(View.VISIBLE);
                    binding.rclrRides.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(requireActivity(), "No es posible cargar los datos del usuario en este momento.", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            if (!PETS_ALREADY_DOWNLOADED) {
                showPets();
                PETS_ALREADY_DOWNLOADED = true;
            }
        } catch (Exception e) {
            Log.e("Error:", e.toString());
        }
        try {
            if (!PROFILE_RIDES_ALREADY_DOWNLOADED) {
                showRides();
            }
        } catch (Exception e) {
            Log.e("Error:", e.toString());
        }

        binding.bttnAddPet.setOnClickListener(v -> {
            ADD_PET_TYPE = "ADD_PET";
            //Navegación hacia el fragment agregar mascota
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_profile_to_navigation_add_pet, null);
        });

        return root;
    }

    private void showRides() {
        walksQuery.addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : walksQuery.getResult()) {
                if (document.get("ID_Paseador").toString().equals(user.getUid())) {
                    Paseo addingWalk = new Paseo(
                            document.get("ID_Usuario").toString(),
                            document.get("ID_Paseador").toString(),
                            document.get("Mascota").toString(),
                            document.get("Duracion_Paseo").toString(),
                            Integer.parseInt(document.get("Estado").toString())
                    );
                    if (addingWalk.getEstado() == 2) {
                        // Finalizado
                        if (!paseosFinalizados.contains(addingWalk)) {
                            paseosFinalizados.add(addingWalk);
                            binding.rclrRides.getAdapter().notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    private void showPets() {
        mascotasUsuarioQuery.addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : mascotasUsuarioQuery.getResult()) {
                String fecha = document.get("Fecha").toString();
                int anhoMascota = Integer.parseInt(fecha.substring(fecha.lastIndexOf('/') + 1));
                int edadMascota = 2022 - anhoMascota;
                Mascota addingPet = new Mascota(
                        document.get("ID_Cliente").toString(),
                        document.get("Nombre").toString(),
                        edadMascota,
                        document.get("Fecha").toString(),
                        document.get("Raza").toString()
                );
                if (!mascotas.contains(addingPet)) {
                    mascotas.add(addingPet);
                    binding.rclrPet.getAdapter().notifyItemInserted(mascotas.indexOf(addingPet));
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_options_menu, menu);
        menu.findItem(R.id.delete_pet).setVisible(false);
        menu.findItem(R.id.action_navigation_profile_to_navigation_edit_profile).setVisible(true);
        menu.findItem(R.id.logout).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_navigation_profile_to_navigation_edit_profile:
                NavHostFragment.findNavController(this).navigate(item.getItemId());
                break;
            case R.id.logout:
                auth.signOut();
                startActivity(new Intent(requireActivity(), AuthActivity.class));
                getActivity().finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}