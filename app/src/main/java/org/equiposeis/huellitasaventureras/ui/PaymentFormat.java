package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentAddPetBinding;
import org.equiposeis.huellitasaventureras.databinding.FragmentPaymentFormatBinding;


public class PaymentFormat extends Fragment {

    //Variables de PaymentFormat
    private FragmentPaymentFormatBinding binding;
    private String TitularCard,Introducir;
    private int CVV ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        binding = FragmentPaymentFormatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.bttnFinalizar.setOnClickListener(v -> {
            TitularCard = binding.txtTitularCard.getText().toString();
            Introducir = binding.txtIntroducir.getText().toString();

            CVV = Integer.parseInt(binding.txtCVV.getText().toString());
        });
        return root;
    }

}