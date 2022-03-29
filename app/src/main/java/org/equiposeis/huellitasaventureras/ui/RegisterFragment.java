package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.BASE_USER_PHOTO;
import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.AuthActivity.db;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.UsuarioCliente;
import org.equiposeis.huellitasaventureras.dataModels.UsuarioPaseador;
import org.equiposeis.huellitasaventureras.databinding.FragmentRegisterBinding;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    // Creación de variables para mandar a la BD:
    private String name = "", addres = "", mail = "", passOne = "", passTwo = "", gender = "", userType = "";
    private int age = 0;
    private long phone = 0;
    // Se crean los arrays de elemntos de los DropDown:
    private String[] genders = null;
    private String[] user_types = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Se crea el HashMap que se subirá a la Base de Datos:
        Map<String, Object> usuario = new HashMap<>();

        // Se cargan los elementos de los arrays. Si hay que bajar de la BD, se hace aquí:
        genders = getResources().getStringArray(R.array.genders);
        user_types = getResources().getStringArray(R.array.user_types);

        // Se llenan los DropDown con los arrays creados:
        binding.txtGender.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, genders));
        binding.txtUserType.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, user_types));

        // Se crea el click listener para registrarse:
        binding.bttnRegister.setOnClickListener(v -> {
            // Se toman los valores string de los campos de texto, confirmando que no sean nulos:
            if (!binding.txtName.getText().toString().isEmpty()) {
                name = binding.txtName.getText().toString().trim();
            }
            if (!binding.txtAddress.getText().toString().isEmpty()) {
                addres = binding.txtAddress.getText().toString().trim();
            }
            if (!binding.txtMail.getText().toString().isEmpty()) {
                mail = binding.txtMail.getText().toString().trim();
            }
            if (!binding.txtPassword.getText().toString().isEmpty()) {
                passOne = binding.txtPassword.getText().toString();
            }
            if (!binding.txtRepeatPassword.getText().toString().isEmpty()) {
                passTwo = binding.txtRepeatPassword.getText().toString();
            }

            // Se toman los valores numéricos de de edad y teléfono:
            if (!binding.txtAge.getText().toString().isEmpty()) {
                age = Integer.parseInt(binding.txtAge.getText().toString().trim());
            }
            if (!binding.txtPhone.getText().toString().isEmpty()) {
                phone = Long.parseLong(binding.txtPhone.getText().toString().trim());
            }
            if (!binding.txtGender.getText().toString().isEmpty()) {
                gender = binding.txtGender.getText().toString();
            }
            if (!binding.txtUserType.getText().toString().isEmpty()) {
                userType = binding.txtUserType.getText().toString();
            }

            // Se confirma que el correo no esté vacío y que las contraseñas coincidan para
            // realizar el registro:
            if (!mail.isEmpty()) {
                if (passOne.equals(passTwo)) {
                    //Registrar usuario con Firebase Authentication:
                    auth.createUserWithEmailAndPassword(mail, passOne).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (userType.equals("Cliente")) {
                                UsuarioCliente cliente = new UsuarioCliente(
                                        0,
                                        BASE_USER_PHOTO,
                                        auth.getCurrentUser().getUid(),
                                        name,
                                        gender,
                                        age,
                                        phone,
                                        addres,
                                        mail,
                                        userType
                                );
                                usuario.put(getResources().getString(R.string.ID_USUARIO), cliente.getId_usuaio());
                                usuario.put(getResources().getString(R.string.MASCOTAS_USUARIO), cliente.getMascotas_alta());
                                usuario.put(getResources().getString(R.string.NOMBRE_USUARIO), cliente.getNombre());
                                usuario.put(getResources().getString(R.string.GENERO_USUARIO), cliente.getGenero());
                                usuario.put(getResources().getString(R.string.EDAD_USUARIO), cliente.getEdad());
                                usuario.put(getResources().getString(R.string.TELEFONO_USUARIO), cliente.getNumero_telefonico());
                                usuario.put(getResources().getString(R.string.DOMICILIO_USUARIO), cliente.getDomicilio());
                                usuario.put(getResources().getString(R.string.EMAIL_USUARIO), cliente.getCorreo_electronico());
                                usuario.put(getResources().getString(R.string.TIPO_USUARIO), cliente.getTipo_usuario());
                                usuario.put(getResources().getString(R.string.FOTO_USUARIO), cliente.getFoto_perfil());

                                db.collection(getResources().getString(R.string.USUARIOS_TABLE)).document(cliente.getId_usuaio()).set(usuario);
                            } else if (userType.equals("Paseador")) {
                                UsuarioPaseador paseador = new UsuarioPaseador(
                                        "",
                                        BASE_USER_PHOTO,
                                        auth.getCurrentUser().getUid(),
                                        name,
                                        gender,
                                        age,
                                        phone,
                                        addres,
                                        mail,
                                        userType
                                );
                                usuario.put(getResources().getString(R.string.ID_USUARIO), paseador.getId_usuaio());
                                usuario.put(getResources().getString(R.string.CAPACITACION_USUARIO), paseador.getCapacitacion());
                                usuario.put(getResources().getString(R.string.NOMBRE_USUARIO), paseador.getNombre());
                                usuario.put(getResources().getString(R.string.GENERO_USUARIO), paseador.getGenero());
                                usuario.put(getResources().getString(R.string.EDAD_USUARIO), paseador.getEdad());
                                usuario.put(getResources().getString(R.string.TELEFONO_USUARIO), paseador.getNumero_telefonico());
                                usuario.put(getResources().getString(R.string.DOMICILIO_USUARIO), paseador.getDomicilio());
                                usuario.put(getResources().getString(R.string.EMAIL_USUARIO), paseador.getCorreo_electronico());
                                usuario.put(getResources().getString(R.string.TIPO_USUARIO), paseador.getTipo_usuario());
                                usuario.put(getResources().getString(R.string.FOTO_USUARIO), paseador.getFoto_perfil());

                                db.collection(getResources().getString(R.string.USUARIOS_TABLE)).document(paseador.getId_usuaio()).set(usuario);
                            }

                            auth.signOut();
                            NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_register_to_navigation_login, null);
                        } else
                            Toast.makeText(requireActivity(), R.string.not_registered, Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Toast.makeText(requireActivity(), R.string.not_registered, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireActivity(), R.string.not_registered, Toast.LENGTH_SHORT).show();
            }
        });

        binding.bttnCancel.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.action_navigation_register_to_navigation_login, null));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}