package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentProfileBinding;
import org.equiposeis.huellitasaventureras.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private String name, addres, mail, passOne, passTwo;
    private int gender, age, phone, type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (type == 0) { //type se baja de la base de datos.
            binding.txtName.setVisibility(View.GONE);
            binding.txtAddres.setVisibility(View.GONE);
            binding.txtMail.setVisibility(View.GONE);
        }

        binding.bttnRegister.setOnClickListener(v -> {
            name = binding.txtName.getText().toString();
            addres = binding.txtAddres.getText().toString();
            mail = binding.txtMail.getText().toString();
            passOne = binding.txtPasswordOne.getText().toString();
            passTwo = binding.txtPasswordTwo.getText().toString();

            age = Integer.parseInt(binding.txtAge.getText().toString());
            phone = Integer.parseInt(binding.txtPhone.getText().toString());
            gender = binding.spnrGender.getSelectedItemPosition();
            type = binding.spnrType.getSelectedItemPosition();

            if (!mail.isEmpty()) {
                if (passOne == passTwo) {
                    //Mandar datos a la base de datos.
                }
            }
        });

        binding.bttnCancel.setOnClickListener(v -> getActivity().onBackPressed());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
