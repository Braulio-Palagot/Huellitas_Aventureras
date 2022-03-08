package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseUser user = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);

        binding.txtViewPet.setVisibility(View.GONE);
        binding.rclrPet.setVisibility(View.GONE);
        binding.bttnAddPet.setVisibility(View.GONE);
        binding.txtViewWalker.setVisibility(View.GONE);
        binding.rclrRides.setVisibility(View.GONE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Jalar datos de la BD
        user = auth.getCurrentUser();
        db.collection(getResources().getString(R.string.USUARIOS_TABLE)).document(user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    binding.txtName.setText(document.get(getResources().getString(R.string.NOMBRE_USUARIO)).toString());
                    binding.txtAddress.setText(document.get(getResources().getString(R.string.DOMICILIO_USUARIO)).toString());

                    //Ocultar datos de paseador
                    if (Integer.parseInt(document.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 0) {
                        binding.txtViewPet.setVisibility(View.VISIBLE);
                        binding.rclrPet.setVisibility(View.VISIBLE);
                        binding.bttnAddPet.setVisibility(View.VISIBLE);
                    } else if (Integer.parseInt(document.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 1) { //ocultar datos de cliente
                        binding.txtViewWalker.setVisibility(View.VISIBLE);
                        binding.rclrRides.setVisibility(View.VISIBLE);
                    }
                    // Se cargan las mascotas al RecyclerView.
                } else {
                    Toast.makeText(requireActivity(), "No es posible cargar los datos del usuario en este momento.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("USER_NOT_FOUND", "El usuario no se halló: ", task.getException());
            }
        });

        binding.bttnAddPet.setOnClickListener(v -> {
            //Navegación hacia el fragment agregar mascota
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_profile_to_navigation_add_pet, null);
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavHostFragment.findNavController(this).navigate(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}