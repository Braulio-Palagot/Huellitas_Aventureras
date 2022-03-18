package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.MainActivity.clientsQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotasQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.paseoSeleccionado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.dataModels.UsuarioCliente;
import org.equiposeis.huellitasaventureras.databinding.FragmentRideDetailsBinding;

public class RideDetailsFragment extends Fragment {

    private FragmentRideDetailsBinding binding;
    private int ride =0;
    private UsuarioCliente cliente = null;
    private Mascota mascota = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentRideDetailsBinding.inflate(inflater,container,false);
        View root=binding.getRoot();

        // Rellenar datos del Paseo Seleccionado:
        clientsQuery.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : clientsQuery.getResult()) {
                    if (document.get("ID_Usuario").toString().equals(paseoSeleccionado.getId_usuario())) {
                        cliente = new UsuarioCliente(
                                Integer.parseInt(document.get("Mascotas_Alta").toString()),
                                document.get("Foto_Usuario").toString(),
                                document.get("ID_Usuario").toString(),
                                document.get("Nombre").toString(),
                                Integer.parseInt(document.get("Genero").toString()),
                                Integer.parseInt(document.get("Edad").toString()),
                                Integer.parseInt(document.get("Numero_Telefonico").toString()),
                                document.get("Domicilio").toString(),
                                document.get("Correo_Electronico").toString(),
                                Integer.parseInt(document.get("Tipo_Usuario").toString())
                        );
                        break;
                    }
                }
            }
        });

        mascotasQuery.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : mascotasQuery.getResult()) {
                    if (document.get("ID_Usuario").toString().equals(paseoSeleccionado.getId_usuario()) &&
                            document.get("Mascota").toString().equals(paseoSeleccionado.getMascota())) {
                        String fecha = document.get("Fecha").toString();
                        int anhoMascota = Integer.parseInt(fecha.substring(fecha.lastIndexOf('/')+1));
                        int edadMascota = 2022 - anhoMascota;
                        mascota = new Mascota(
                                document.get("ID_Cliente").toString(),
                                document.get("Nombre").toString(),
                                edadMascota,
                                document.get("Fecha").toString(),
                                document.get("Raza").toString()
                        );
                        break;
                    }
                }
            }
        });

        binding.txtName.setText(cliente.getNombre());
        binding.txtAddress.setText(cliente.getDomicilio());
        binding.txtPetName.setText(mascota.getNombre_mascota());
        binding.txtPetfecha.setText(mascota.getEdad_mascota());
        binding.txtRace.setText(mascota.getRaza());
        binding.txtTime.setText(paseoSeleccionado.getDuracionPaseo());

        //Ocultar datos de solicitud
        if (ride==0){
            binding.bttnAccept.setVisibility(View.GONE);
            binding.bttnReject.setVisibility(View.GONE);
        } else { //ocultar datos de paseo en curso
            binding.bttnAccept.setVisibility(View.VISIBLE);
            binding.bttnReject.setVisibility(View.VISIBLE);
        }

        binding.bttnAccept.setOnClickListener(v ->{
            //Mandar a BD la respuesta de aceptar
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_profile_to_navigation_add_pet, null);
        });

        binding.bttnReject.setOnClickListener(v ->{
            //Mandar a BD la respuesta de rechaza
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_profile_to_navigation_add_pet, null);

        });

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}