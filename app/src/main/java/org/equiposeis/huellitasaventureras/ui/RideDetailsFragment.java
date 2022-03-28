package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.MainActivity.clientsQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.db;
import static org.equiposeis.huellitasaventureras.MainActivity.mascotasQuery;
import static org.equiposeis.huellitasaventureras.MainActivity.paseoSeleccionado;
import static org.equiposeis.huellitasaventureras.ui.HomeFragment.paseosEnCurso;
import static org.equiposeis.huellitasaventureras.ui.HomeFragment.paseosPendientes;
import static org.equiposeis.huellitasaventureras.ui.ProfileFragment.paseosFinalizados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.firestore.DocumentSnapshot;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.dataModels.UsuarioCliente;
import org.equiposeis.huellitasaventureras.databinding.FragmentRideDetailsBinding;

import java.util.HashMap;
import java.util.Map;

public class RideDetailsFragment extends Fragment {

    private FragmentRideDetailsBinding binding;
    private UsuarioCliente cliente = null;
    private Mascota mascota = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentRideDetailsBinding.inflate(inflater,container,false);
        View root=binding.getRoot();

        // Rellenar datos del Paseo Seleccionado:
        for (DocumentSnapshot document : clientsQuery.getResult()) {
            if (document.get("ID_Usuario").toString().equals(paseoSeleccionado.getId_usuario())) {
                cliente = new UsuarioCliente(
                        Integer.parseInt(document.get("Mascotas_Alta").toString()),
                        document.get("Foto_Usuario").toString(),
                        document.get("ID_Usuario").toString(),
                        document.get("Nombre").toString(),
                        document.get("Genero").toString(),
                        Integer.parseInt(document.get("Edad").toString()),
                        Long.parseLong(document.get("Numero_Telefonico").toString()),
                        document.get("Domicilio").toString(),
                        document.get("Correo_Electronico").toString(),
                        document.get("Tipo_Usuario").toString()
                );
                binding.txtName.setText(cliente.getNombre());
                binding.txtAddress.setText(cliente.getDomicilio());
                break;
            }
        }

        for (DocumentSnapshot document : mascotasQuery.getResult()) {
            if (document.get("ID_Cliente").toString().equals(paseoSeleccionado.getId_usuario()) &&
                    document.get("Nombre").toString().equals(paseoSeleccionado.getMascota())) {
                String fecha = document.get("Fecha").toString();
                int anhoMascota = Integer.parseInt(fecha.substring(fecha.lastIndexOf('/') + 1));
                int edadMascota = 2022 - anhoMascota;
                mascota = new Mascota(
                        document.get("ID_Cliente").toString(),
                        document.get("Nombre").toString(),
                        edadMascota,
                        document.get("Fecha").toString(),
                        document.get("Raza").toString()
                );
                binding.txtPetName.setText(mascota.getNombre_mascota());
                binding.txtPetfecha.setText(mascota.getFecha_mascota());
                binding.txtRace.setText(mascota.getRaza());
                break;
            }
        }


        binding.txtTime.setText(paseoSeleccionado.getDuracionPaseo());

        //Ocultar datos de solicitud
        if (paseoSeleccionado.getEstado() == 0) {
            binding.bttnAccept.setVisibility(View.VISIBLE);
            binding.bttnReject.setVisibility(View.VISIBLE);
            binding.bttnFinish.setVisibility(View.GONE);
        } else if (paseoSeleccionado.getEstado() == 1){ //ocultar datos de paseo en curso
            binding.bttnAccept.setVisibility(View.GONE);
            binding.bttnReject.setVisibility(View.GONE);
            binding.bttnFinish.setVisibility(View.VISIBLE);
        } else {
            binding.bttnAccept.setVisibility(View.GONE);
            binding.bttnReject.setVisibility(View.GONE);
            binding.bttnFinish.setVisibility(View.GONE);
        }

        binding.bttnAccept.setOnClickListener(v ->{
            //Mandar a BD la respuesta de aceptar
            String documentPath = paseoSeleccionado.getId_usuario() + paseoSeleccionado.getMascota();
            Map<String, Object> updateRideStatus = new HashMap<>();
            updateRideStatus.put("Estado", 1);
            db.collection("Paseos").document(documentPath).update(updateRideStatus);
            paseosPendientes.remove(paseoSeleccionado);
            paseoSeleccionado.setEstado(1);
            paseosEnCurso.add(paseoSeleccionado);
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_ride_details_to_navigation_home, null);
        });

        binding.bttnReject.setOnClickListener(v ->{
            //Mandar a BD la respuesta de rechaza
            String documentPath = paseoSeleccionado.getId_usuario() + paseoSeleccionado.getMascota();
            db.collection("Paseos").document(documentPath).delete();
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_profile_to_navigation_add_pet, null);
        });

        binding.bttnFinish.setOnClickListener(view -> {
            //Mandar a BD la finalización
            String documentPath = paseoSeleccionado.getId_usuario() + paseoSeleccionado.getMascota();
            Map<String, Object> updateRideStatus = new HashMap<>();
            updateRideStatus.put("Estado", 2);
            db.collection("Paseos").document(documentPath).update(updateRideStatus);
            paseosEnCurso.remove(paseoSeleccionado);
            paseoSeleccionado.setEstado(2);
            paseosFinalizados.add(paseoSeleccionado);
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_ride_details_to_navigation_home, null);
        });

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}