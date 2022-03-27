package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.MainActivity.db;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseUser;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.MetodoPago;
import org.equiposeis.huellitasaventureras.databinding.FragmentPaymentFormatBinding;

import java.util.HashMap;
import java.util.Map;


public class PaymentFormat extends Fragment {

    //Variables de PaymentFormat
    private FragmentPaymentFormatBinding binding;
    private String TitularCard = "", CardOption1 = "", Numero = "";
    private int CVV = 0, CardOption = 0;
    private FirebaseUser user = null;
    private String[] CardDetails = null;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentFormatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user = auth.getCurrentUser();
        CardDetails = getResources().getStringArray(R.array.CardDetails);

        binding.txtCardDetails.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, CardDetails));

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


            MetodoPago met = new MetodoPago("", "", "", "", 0);
            String ID_Usuario = met.getID_Usuario();
            String Titurlar = met.getTitular();
            String Numbero_pago = met.getNumero_pago();
            Integer CVVS = met.getCVV();

            if(!binding.txtTitularCard.getText().toString().isEmpty()){
                TitularCard = binding.txtTitularCard.getText().toString();
            }
            if (binding.txtCardDetails.getText().toString().equals("CLABE")){
                CardOption = 0;
                CardOption1="CLABE";
            }else if(binding.txtCardDetails.getText().toString().equals("Número de tarjeta")){
                CardOption = 1;
                CardOption1="Número de tarjeta";
            }else {
                CardOption = 2;
                CardOption1="Número de cuenta";
            }
            if (!binding.txtIntroduce.getText().toString().isEmpty()) {
                Numero = binding.txtIntroduce.getText().toString();
            }
            if (!binding.txtCVV.getText().toString().isEmpty()){
                CVV = Integer.parseInt(binding.txtCVV.getText().toString());
            }

            if (!TitularCard.isEmpty() && !CardOption1.isEmpty() && !Numero.isEmpty() && CVV!=0) {

            met.setID_Usuario(user.getUid());
            met.setTitular(TitularCard);
            met.setMetodo_pago(CardOption1);
            met.setNumero_pago(Numero);
            met.setCVV(CVV);

            //Método para mandar datos a la base.
            Map<String, Object> MetPago_db = new HashMap<>();
            MetPago_db.put("ID_Usuario", met.getID_Usuario());
            MetPago_db.put("Nombre", met.getTitular());
            MetPago_db.put("Tipo de Pago", met.getMetodo_pago());
            MetPago_db.put("Numero", met.getNumero_pago());
            MetPago_db.put("CVV", met.getCVV());

                db.collection("Metodo de Pago").document(user.getUid() + CardOption1)
                        .set(MetPago_db)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(requireActivity(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_payment_to_navigation_profile, null);
                        })
                        .addOnFailureListener(e -> Toast.makeText(requireActivity(), "Fallo en el Registro", Toast.LENGTH_SHORT).show());

            }else{
                Toast.makeText(requireActivity(), "Campos Incompletos", Toast.LENGTH_SHORT).show();
            }

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