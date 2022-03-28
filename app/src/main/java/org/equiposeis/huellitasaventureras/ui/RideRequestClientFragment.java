package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.MainActivity.db;
import static org.equiposeis.huellitasaventureras.MainActivity.employeesQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotasUsuarioQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Paseo;
import org.equiposeis.huellitasaventureras.databinding.FragmentRideRequestClientBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RideRequestClientFragment extends Fragment {

    private FragmentRideRequestClientBinding binding;

    private ArrayList<String> employees = new ArrayList<>();
    private ArrayList<String> Pets = new ArrayList<>();
    private String[] Times = null;
    private String time = "";
    private String mascota = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRideRequestClientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Times = getResources().getStringArray(R.array.Times);

        Paseo paseo = new Paseo();

        binding.txtEmployee.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, employees));
        binding.txtPet.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, Pets));
        binding.txtTime.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, Times));

//Rellenado de datos Empleados Disponibles subidos en la  BD
        mascotasUsuarioQuery.addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : mascotasUsuarioQuery.getResult()) {
                String nombre = document.get("Nombre").toString();
                Pets.add(nombre);
            }
            binding.txtPet.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, Pets));
        });


        //Rellenado de datos de Mascotas registradas subidos en la BD
        employeesQuery.addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : employeesQuery.getResult()) {
                String nombre = document.get("Nombre").toString();
                employees.add(nombre);
            }
            binding.txtEmployee.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, employees));
        });


        binding.bttnClientRequest.setOnClickListener(v -> {

            if (!binding.txtTime.getText().toString().isEmpty()) {
                time = binding.txtTime.getText().toString();
            }
            if (!binding.txtPet.getText().toString().isEmpty()) {
                mascota = binding.txtPet.getText().toString();
            }

            for (DocumentSnapshot document : employeesQuery.getResult()) {
                if (document.get("Nombre").toString().equals(binding.txtEmployee.getText().toString())) {
                    paseo.setId_paseador(document.get("ID_Usuario").toString());
                    break;
                }
            }
            paseo.setId_usuario(user.getUid());
            paseo.setMascota(mascota);
            paseo.setDuracionPaseo(time);
            paseo.setEstado(0);

            //Metodo para mandar datos a la base de datos.
            Map<String, Object> Paseo_db = new HashMap<>();
            Paseo_db.put("ID_Usuario", paseo.getId_usuario());
            Paseo_db.put("Mascota", paseo.getMascota());
            Paseo_db.put("ID_Paseador", paseo.getId_paseador());
            Paseo_db.put("Duracion_Paseo", paseo.getDuracionPaseo());
            Paseo_db.put("Estado", paseo.getEstado());

            db.collection("Paseos").document(user.getUid() + mascota)
                    .set(Paseo_db)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(requireActivity(), "Registro de paseo exitoso", Toast.LENGTH_SHORT).show();

                        NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_ride_request_to_navigation_home, null);
                    })
                    .addOnFailureListener(e -> Toast.makeText(requireActivity(), "No se pudo realizar el registro del paseo", Toast.LENGTH_SHORT).show());
        });

        binding.bttnCancelClientRequest.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_ride_request_to_navigation_home, null);
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}