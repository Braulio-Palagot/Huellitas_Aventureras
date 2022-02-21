package org.equiposeis.huellitasaventureras.ui;

import static android.app.Activity.RESULT_OK;
import static org.equiposeis.huellitasaventureras.MainActivity.IMAGE_REQUEST_CODE;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    // Creación de variables para mandar a la BD:
    private String name = "", addres = "", mail = "", passOne = "", passTwo = "";
    private int gender = 0, age = 0, phone = 0, userType = 0;
    // Se crean los arrays de elemntos de los DropDown:
    private String[] genders = null;
    private String[] user_types = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Se cargan los elementos de los arrays. Si hay que bajar de la BD, se hace aquí:
        genders = getResources().getStringArray(R.array.genders);
        user_types = getResources().getStringArray(R.array.user_types);

        // Se llenan los DropDown con los arrays creados:
        binding.txtGender.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, genders));
        binding.txtUserType.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, user_types));

        binding.bttnEditPhoto.setOnClickListener(v -> {
            // Se realiza la selección de foto desde el celular:
            loadImage();
        });

        binding.bttnFinish.setOnClickListener(v -> {
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
                    userType = 0;
                } else if (binding.txtGender.getText().toString().equals("Paseador")) {
                    userType = 1;
                }
            }
        });

        binding.bttnPaymentMethod.setOnClickListener(v -> {
            // Navegación al PaymentFormatFragment:
        });

        binding.bttnCancel.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        return root;
    }

    private void loadImage() {
        Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galery.setType("image/");
        startActivityForResult(galery.createChooser(galery, getResources().getString(R.string.select_media_app)), IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            Uri path = null;
            try {
                path = data.getData();
            } catch (Exception e) {
                Log.e("Error", String.valueOf(e));
            }
            binding.imgUserPhoto.setImageURI(path);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}