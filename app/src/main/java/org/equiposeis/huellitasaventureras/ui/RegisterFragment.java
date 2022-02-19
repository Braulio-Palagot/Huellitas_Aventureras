package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentRegisterBinding;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    // Creación de variables para mandar a la BD:
    private String name = "", addres = "", mail = "", passOne = "", passTwo = "";
    private int gender = 0, age = 0, phone = 0, type = 0;
    // Se crean los arrays de elemntos de los DropDown:
    private String[] genders = null;
    private String[] user_types = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
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
                name = binding.txtName.getText().toString();
            }
            if (!binding.txtAddress.getText().toString().isEmpty()) {
                addres = binding.txtAddress.getText().toString();
            }
            if (!binding.txtMail.getText().toString().isEmpty()) {
                mail = binding.txtMail.getText().toString();
            }
            if (!binding.txtPassword.getText().toString().isEmpty()) {
                passOne = binding.txtPassword.getText().toString();
            }
            if (!binding.txtRepeatPassword.getText().toString().isEmpty()) {
                passTwo = binding.txtRepeatPassword.getText().toString();
            }

            // Se toman los valores numéricos de de edad y teléfono:
            if (!binding.txtAge.getText().toString().isEmpty()) {
                age = Integer.parseInt(binding.txtAge.getText().toString());
            }
            if (!binding.txtPhone.getText().toString().isEmpty()) {
                phone = Integer.parseInt(binding.txtPhone.getText().toString());
            }

            // Se selecciona el valor índice de cada selección de los DropDowns:
            if (!binding.txtGender.getText().toString().isEmpty()) {
                if (binding.txtGender.getText().toString().equals("Masculino")) {
                    gender = 0;
                } else if (binding.txtGender.getText().toString().equals("Femenino")) {
                    gender = 1;
                } else {
                    gender = 2;
                }
            }
            if (!binding.txtUserType.getText().toString().isEmpty()) {
                if (binding.txtUserType.getText().toString().equals("Cliente")) {
                    gender = 0;
                } else if (binding.txtGender.getText().toString().equals("Paseador")) {
                    gender = 1;
                }
            }

            // Se confirma que el correo no esté vacío y que las contraseñas coincidan para
            // realizar el registro:
            if (!mail.isEmpty()) {
                if (passOne.equals(passTwo)) {
                    //Mandar datos a la base de datos.
                    NavHostFragment.findNavController(this).navigate(R.id.action_navigation_notifications_to_navigation_dashboard, null);
                } else {
                    Toast.makeText(requireActivity(), R.string.not_loged, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireActivity(), R.string.not_loged, Toast.LENGTH_SHORT).show();
            }
        });

        binding.bttnCancel.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.action_navigation_notifications_to_navigation_dashboard, null));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}