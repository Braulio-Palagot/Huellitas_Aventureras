package org.equiposeis.huellitasaventureras.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentPaymentFormatBinding;

import java.util.Objects;


public class PaymentFormat extends Fragment {

    //Variables de PaymentFormat
    private FragmentPaymentFormatBinding binding;
    private String TitularCard;
    private int CVV, CardOption;

    private final String[] CardDetails = getResources().getStringArray(R.array.CardDetails);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        binding = FragmentPaymentFormatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.bttnEndPayment.setOnClickListener(v -> {

            TitularCard = Objects.requireNonNull(binding.txtTitularCard.getText()).toString();

            if (binding.txtCardDetails.getText().toString().equals("CLABE")){
                CardOption = 0;
            }else if(binding.txtCardDetails.getText().toString().equals("Número de tarjeta")){
                CardOption = 1;
            }else {
                CardOption = 2;
            }

            CVV = Integer.parseInt(Objects.requireNonNull(binding.txtCVV.getEditText().getText().toString()));

            //Método para mandar datos a la base.

            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_payment_to_navigation_profile, null);
        });



        binding.bttnCancelPayment.setOnClickListener(v ->

                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_payment_to_navigation_profile, null));

        return root;
    }

    @Override
    public void  onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

}