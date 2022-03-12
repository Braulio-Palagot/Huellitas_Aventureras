package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.MainActivity.ADD_PET_TYPE;
import static org.equiposeis.huellitasaventureras.MainActivity.ALREADY_DOWNLOADED;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotaSeleccionada;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotasQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.userQuery;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.equiposeis.huellitasaventureras.AuthActivity;
import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.adapters.PetAdapter;
import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.databinding.FragmentProfileBinding;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private final Function1 onClickListener = (new Function1() {
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
    public static PetAdapter rclrPetsAdapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.rclrPet.setHasFixedSize(false);
        setHasOptionsMenu(true);
        rclrPetsAdapter = new PetAdapter(getActivity(), mascotas, onClickListener);

        binding.txtViewPet.setVisibility(View.GONE);
        binding.rclrPet.setVisibility(View.GONE);
        binding.bttnAddPet.setVisibility(View.GONE);
        binding.txtViewWalker.setVisibility(View.GONE);
        binding.rclrRides.setVisibility(View.GONE);
        binding.rclrPet.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rclrPet.setAdapter(rclrPetsAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Jalar datos de la BD
        DocumentSnapshot userData = userQuery.getResult();
        if (userData.exists()) {
            binding.txtName.setText(userData.get(getResources().getString(R.string.NOMBRE_USUARIO)).toString());
            binding.txtAddress.setText(userData.get(getResources().getString(R.string.DOMICILIO_USUARIO)).toString());

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

        if (!ALREADY_DOWNLOADED) {
            showPets();
            ALREADY_DOWNLOADED = true;
        }

        binding.bttnAddPet.setOnClickListener(v -> {
            ADD_PET_TYPE = "ADD_PET";
            //Navegación hacia el fragment agregar mascota
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_profile_to_navigation_add_pet, null);
        });

        return root;
    }

    private void showPets() {
        for (QueryDocumentSnapshot document : mascotasQuery.getResult()) {
            String fecha = document.get("Fecha").toString();
            int anhoMascota = Integer.parseInt(fecha.substring(fecha.lastIndexOf('/')+1));
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