package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentProfileBinding;
import org.equiposeis.huellitasaventureras.databinding.FragmentRegisterBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    // Creación de variables para mandar a la BD:
    private String name, addres, mail, passOne, passTwo;
    private int gender, age, phone, type;
    // Se crean los arrays de elemntos de los DropDown:
    private final String[] genders = getResources().getStringArray(R.array.genders);
    private final String[] user_types = getResources().getStringArray(R.array.user_types);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Se llenan los DropDown con los arrays creados:
        binding.txtGender.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, genders));
        binding.txtUserType.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, user_types));

        // Se crea el click listener para registrarse:
        binding.bttnRegister.setOnClickListener(v -> {
            // Se toman los valores string de los campos de texto, confirmando que no sean nulos:
            name = Objects.requireNonNull(binding.txtName.getText()).toString();
            addres = Objects.requireNonNull(binding.txtAddress.getText()).toString();
            mail = Objects.requireNonNull(binding.txtMail.getText()).toString();
            passOne = Objects.requireNonNull(binding.txtPassword.getText()).toString();
            passTwo = Objects.requireNonNull(binding.txtRepeatPassword.getText()).toString();

            // Se toman los valores numéricos de de edad y teléfono:
            age = Integer.parseInt(Objects.requireNonNull(binding.txtAge.getText()).toString());
            phone = Integer.parseInt(Objects.requireNonNull(binding.txtPhone.getText()).toString());

            // Se selecciona el valor índice de cada selección de los DropDowns:
            if (binding.txtGender.getText().toString().equals("Masculino")) {
                gender = 0;
            } else if (binding.txtGender.getText().toString().equals("Femenino")) {
                gender = 1;
            } else {
                gender = 2;
            }
            if (binding.txtUserType.getText().toString().equals("Cliente")) {
                gender = 0;
            } else if (binding.txtGender.getText().toString().equals("Paseador")) {
                gender = 1;
            }

            // Se confirma que el correo no esté vacío y que las contraseñas coincidan para
            // realizar el registro:
            if (!mail.isEmpty()) {
                if (passOne.equals(passTwo)) {
                    //Mandar datos a la base de datos.
                    requireActivity().onBackPressed();
                } else {
                    Toast.makeText(requireActivity(), R.string.not_loged, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.bttnCancel.setOnClickListener(v -> requireActivity().onBackPressed());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
