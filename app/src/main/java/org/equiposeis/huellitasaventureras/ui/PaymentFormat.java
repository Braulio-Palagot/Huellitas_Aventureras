package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.db;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.UsuarioCliente;
import org.equiposeis.huellitasaventureras.databinding.FragmentPaymentFormatBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class PaymentFormat extends Fragment {

    //Variables de PaymentFormat
    private FragmentPaymentFormatBinding binding;
    private String TitularCard = "";
    private int CVV = 0, CardOption = 0;

    private String[] CardDetails = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        binding = FragmentPaymentFormatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Pago del servicio

        UsuarioCliente pago = new UsuarioCliente("",0,"PagoServ","",0,0,0,"","",0);
        String metodo_pago = pago.getMetodo_pago();

        Map<String, Object> pago_db = new HashMap<>();
        pago_db.put("metodo_pago", pago.getMetodo_pago());

        db.collection("UsuarioCliente").document("LA")
                .set(pago_db)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(),"No se pudo",Toast.LENGTH_SHORT).show();
                    }
                });



        binding.bttnEndPayment.setOnClickListener(v -> {


            if(binding.txtTitularCard.getText().toString().isEmpty()){
                TitularCard = binding.txtTitularCard.getText().toString();
            }

            if (binding.txtCardDetails.getText().toString().equals("CLABE")){
                CardOption = 0;
            }else if(binding.txtCardDetails.getText().toString().equals("Número de tarjeta")){
                CardOption = 1;
            }else {
                CardOption = 2;
            }
          
            if (binding.txtCVV.getEditText().getText().toString().isEmpty()){
                CVV = Integer.parseInt(binding.txtCVV.getEditText().getText().toString());
            }

            //Método para mandar datos a la base.

            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_payment_to_navigation_profile, null);
        });

        CardDetails = getResources().getStringArray(R.array.CardDetails);


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