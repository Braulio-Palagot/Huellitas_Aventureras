package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.MainActivity.db;
import static org.equiposeis.huellitasaventureras.MainActivity.employeesQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotaSeleccionada;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotasQuery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Paseo;
import org.equiposeis.huellitasaventureras.databinding.FragmentRideRequestClientBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RideRequestClientFragment extends Fragment {

    private FragmentRideRequestClientBinding binding;
    private int RideQuestClientEmployee,RideQuestClientPets,RideQuestClientTime;

    private ArrayList<String> employees = null;
    private ArrayList<String> Pets = null;
    private String[] Times = null;
    private FirebaseUser ride=null;
    private String Times1 = "";
    private FirebaseUser user= null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRideRequestClientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user=auth.getCurrentUser();
        Times = getResources().getStringArray(R.array.Times);
        ride=auth.getCurrentUser();

        Paseo paseo =new Paseo("","","");
        String id_paseo=paseo.getId_paseo();
        String duracionPaseo=paseo.getDuracionPaseo();
        String times1=paseo.getTime();

        binding.txtEmployee.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, employees));
        binding.txtPet.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, Pets));
        binding.txtTime.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, Times));

//Rellenado de datos Empleados Disponibles subidos en la  BD
        employeesQuery.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : employeesQuery.getResult()){
                    String nombre= document.get("Nombre").toString();
                    Pets.add(nombre);
                }
                binding.txtPet.setAdapter(new ArrayAdapter(requireActivity(),R.layout.dropdown_item,Pets));
            }
        });


        //Rellenado de datos de Mascotas registradas subidos en la BD
        mascotasQuery.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : mascotasQuery.getResult()){
                    String nombre= document.get("Nombre").toString();
                    employees.add(nombre);
                }
                binding.txtEmployee.setAdapter(new ArrayAdapter(requireActivity(),R.layout.dropdown_item,employees));
            }
        });



        binding.bttnClientRequest.setOnClickListener(v -> {

            if (binding.txtTime.getText().toString().equals("30 Mint")) {
                RideQuestClientTime = 0;
                Times1="30 Mint";
            } else if (binding.txtTime.getText().toString().equals("45 Mint")) {
                RideQuestClientTime = 1;
                Times1="45 Mint";
            } else if (binding.txtTime.getText().toString().equals("60 Mint")) {
                RideQuestClientTime = 2;
                Times1="60 Mint";
            } else if (binding.txtTime.getText().toString().equals("75 Mint")) {
                RideQuestClientTime = 3;
                Times1="75 Mint";
            } else if (binding.txtTime.getText().toString().equals("90 Mint")) {
                RideQuestClientTime = 4;
                Times1="90 Mint";
            } else if (binding.txtTime.getText().toString().equals("105 Mint")) {
                RideQuestClientTime = 5;
                Times1="105 Mint";
            } else if (binding.txtTime.getText().toString().equals("120 Mint")) {
                RideQuestClientTime = 6;
                Times1="120 Mint";
            } else if (binding.txtTime.getText().toString().equals("135 Mint")) {
                RideQuestClientTime = 7;
                Times1="135 Mint";
            } else if (binding.txtTime.getText().toString().equals("150 Mint")) {
                RideQuestClientTime = 8;
                Times1="150 Mint";
            } else if (binding.txtTime.getText().toString().equals("165 Mint")) {
                RideQuestClientTime = 9;
                Times1="165 Mint";
            } else {
                RideQuestClientTime = 10;
                Times1="180 Mint";
            }



            paseo.setId_paseo(ride.getUid());
            paseo.setTime(times1);

            //Metodo para mandar datos a la base de datos.

            Map<String, Object> Paseo_db=new HashMap<>();
            Paseo_db.put("id_Paseo", paseo.getId_paseo());
            //Paseo_db.put("Mascota", );
            Paseo_db.put("ID_Cliente", user.getUid());
            Paseo_db.put("Duracion Paseo", paseo.getTime());


            db.collection("Solicitud De Paseo").document(user.getUid())
                    .set(Paseo_db)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(requireActivity(),"Registro de paseo exitoso", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_add_pet_to_navigation_profile, null);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireActivity(),"No se pudo realizar el registro del paseo", Toast.LENGTH_SHORT).show();
                        }
                    });
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_ride_request_to_navigation_home, null);

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