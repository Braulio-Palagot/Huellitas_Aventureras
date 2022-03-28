package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.MainActivity.HOME_RIDES_ALREADY_DOWNLOADED;
import static org.equiposeis.huellitasaventureras.MainActivity.clientsQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.employeesQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.paseoSeleccionado;
import static org.equiposeis.huellitasaventureras.MainActivity.userQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.walksQuery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.adapters.RidesInProgressAdapter;
import org.equiposeis.huellitasaventureras.adapters.RidesRequestedAdapter;
import org.equiposeis.huellitasaventureras.dataModels.Paseo;
import org.equiposeis.huellitasaventureras.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseUser user = null;
    private final Function1 onClickListener = (new Function1() {
        @Override
        public Object invoke(Object o) {
            this.invoke((Paseo) o);
            return Unit.INSTANCE;
        }

        public void invoke(Paseo paseo) {
            paseoSeleccionado = paseo;
            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_navigation_home_to_navigation_ride_details);
        }
    });
    public static ArrayList<Paseo> paseosPendientes = new ArrayList<>();
    public static ArrayList<Paseo> paseosEnCurso = new ArrayList<>();
    public static RidesRequestedAdapter rclrPaseosPendientesAdapter = null;
    public static RidesInProgressAdapter rclrPaseosEnCursoAdapter = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        userQuery.addOnSuccessListener(documentSnapshot -> {
            if (Integer.parseInt(userQuery.getResult().get("Tipo_Usuario").toString()) == 0) {
                rclrPaseosEnCursoAdapter = new RidesInProgressAdapter(requireContext(), paseosEnCurso, employeesQuery, onClickListener);
            } else {
                rclrPaseosPendientesAdapter = new RidesRequestedAdapter(requireContext(), paseosPendientes, clientsQuery, onClickListener);
                rclrPaseosEnCursoAdapter = new RidesInProgressAdapter(requireContext(), paseosEnCurso, clientsQuery, onClickListener);
            }

            try {
                binding.rclrRidesRequested.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                binding.rclrRidesRequested.setAdapter(rclrPaseosPendientesAdapter);
                binding.rclrRidesInProgress.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                binding.rclrRidesInProgress.setAdapter(rclrPaseosEnCursoAdapter);
                if (!HOME_RIDES_ALREADY_DOWNLOADED) {
                    showWalks();
                    HOME_RIDES_ALREADY_DOWNLOADED = true;
                }
            } catch (Exception e) {
                Log.e("Error:", e.toString());
            }
        });

        //Jalar datos de la BD
        user = auth.getCurrentUser();
        userQuery.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                //Mostrar datos de Cliente
                try {
                    if (Integer.parseInt(document.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 0) {
                        binding.textViewRideInProgress.setVisibility(View.VISIBLE);
                        binding.rclrRidesInProgress.setVisibility(View.VISIBLE);
                        binding.bttnQuestRide.setVisibility(View.VISIBLE);
                    } else if (Integer.parseInt(document.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 1) {
                        //Mostrar datos de Paseador
                        binding.textViewRideInRequest.setVisibility(View.VISIBLE);
                        binding.rclrRidesRequested.setVisibility(View.VISIBLE);

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

    private void showWalks() {
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
                    if (addingWalk.getEstado() == 0) {
                        // Pendiente
                        if (!paseosPendientes.contains(addingWalk)) {
                            paseosPendientes.add(addingWalk);
                            binding.rclrRidesRequested.getAdapter().notifyDataSetChanged();
                        }
                    } else if (addingWalk.getEstado() == 1) {
                        // En curso
                        if (!paseosEnCurso.contains(addingWalk)) {
                            paseosEnCurso.add(addingWalk);
                            binding.rclrRidesInProgress.getAdapter().notifyDataSetChanged();
                        }
                    }
                } else if (document.get("ID_Usuario").toString().equals(user.getUid())) {
                    Paseo addingWalk = new Paseo(
                            document.get("ID_Usuario").toString(),
                            document.get("ID_Paseador").toString(),
                            document.get("Mascota").toString(),
                            document.get("Duracion_Paseo").toString(),
                            Integer.parseInt(document.get("Estado").toString())
                    );
                    if (Integer.parseInt(document.get("Estado").toString()) == 1) {
                        // En curso
                        if (!paseosEnCurso.contains(addingWalk)) {
                            paseosEnCurso.add(addingWalk);
                            binding.rclrRidesInProgress.getAdapter().notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}