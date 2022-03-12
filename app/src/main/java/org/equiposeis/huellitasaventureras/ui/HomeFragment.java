package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.MainActivity.userQuery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;
private FirebaseUser user = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        binding.textViewRideInRequest.setVisibility(View.GONE);
        binding.textViewRideInRequest2.setVisibility(View.GONE);
        binding.rclrRideInRequest.setVisibility(View.GONE);
        binding.rclrRideInRequestFinish.setVisibility(View.GONE);
        binding.textViewRideInProgress.setVisibility(View.GONE);
        binding.textViewRideInFinish.setVisibility(View.GONE);
        binding.rclrRide.setVisibility(View.GONE);
        binding.rclrRideInFinish.setVisibility(View.GONE);
        binding.bttnQuestRide.setVisibility(View.GONE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Jalar datos de la BD
        user = auth.getCurrentUser();
        userQuery.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                    //Mostrar datos de Cliente
                try {
                    if (Integer.parseInt(document.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 0) {
                        binding.textViewRideInProgress.setVisibility(View.VISIBLE);
                        binding.textViewRideInFinish.setVisibility(View.VISIBLE);
                        binding.rclrRide.setVisibility(View.VISIBLE);
                        binding.rclrRideInFinish.setVisibility(View.VISIBLE);
                        binding.bttnQuestRide.setVisibility(View.VISIBLE);
                    } else if (Integer.parseInt(document.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 1) {
                        //Mostrar datos de Paseador
                        binding.textViewRideInRequest.setVisibility(View.VISIBLE);
                        binding.textViewRideInRequest2.setVisibility(View.VISIBLE);
                        binding.rclrRideInRequest.setVisibility(View.VISIBLE);
                        binding.rclrRideInRequestFinish.setVisibility(View.VISIBLE);

                    }
                } catch (Exception e) {
                    Log.e("Error:", "Exception: "+e);
                }
                    // Se cargan las mascotas al RecyclerView.
            } else {
                Log.e("USER_NOT_FOUND", "El usuario no se halló: ", task.getException());
            }
        });

        binding.bttnQuestRide.setOnClickListener(v ->
                //Solicitar Paseo
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_navigation_ride_request, null)
        );

    return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}