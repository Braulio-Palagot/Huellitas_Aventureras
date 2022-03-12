package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.MainActivity.db;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.dataModels.Persona;
import org.equiposeis.huellitasaventureras.databinding.FragmentPaymentFormatBinding;

import java.util.HashMap;
import java.util.Map;


public class PaymentFormat extends Fragment {

    //Variables de PaymentFormat
    private FragmentPaymentFormatBinding binding;
    private String TitularCard = "",CardOption1="",Type="";
    private int CVV = 0, CardOption = 0,Numero=0;
    private FirebaseUser user = null;
    private String[] CardDetails = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        binding = FragmentPaymentFormatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user = auth.getCurrentUser();
        CardDetails = getResources().getStringArray(R.array.CardDetails);

        binding.txtCardDetails.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, CardDetails));

        binding.bttnEndPayment.setOnClickListener(v -> {


            Persona met = new Persona("","",0,0,0L,"","",0,"",0, 0);
            String Titurlar = met.getNombre();
            Integer Number_pago = met.getNumer_pago();
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
                Numero = Integer.parseInt(binding.txtIntroduce.getText().toString().trim());
            }
            if (!binding.txtCVV.getText().toString().isEmpty()){
                CVV = Integer.parseInt(binding.txtCVV.getText().toString());
            }

            db.collection(getResources().getString(R.string.USUARIOS_TABLE)).document(user.getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (Integer.parseInt(document.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 0) {
                        Type="ID_Cliente";
                    } else if (Integer.parseInt(document.get(getResources().getString(R.string.TIPO_USUARIO)).toString()) == 1) {
                        Type="ID_Paseador";
                    }
                } else {
                    Log.e("USER_NOT_FOUND", "El usuario no se halló: ", task.getException());
                }
            });

            if (!TitularCard.isEmpty() && !CardOption1.isEmpty()&& Numero!=0 && CVV!=0) {
            met.setNombre(TitularCard);
            met.setMetodo_pago(CardOption1);
            met.setNumer_pago(Numero);
            met.setCVV(CVV);

            //Método para mandar datos a la base.
            Map<String, Object> MetPago_db = new HashMap<>();
            MetPago_db.put(Type, user.getUid());
            MetPago_db.put("Nombre", met.getNombre());
            MetPago_db.put("Tipo de Pago", met.getMetodo_pago());
            MetPago_db.put("Numero", met.getNumer_pago());
            MetPago_db.put("CVV", met.getCVV());

            db.collection("Metodo de Pago").document(user.getUid()+CardOption1)
                    .set(met)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(requireActivity(),"Registro Exitoso", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_payment_to_navigation_profile, null);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireActivity(),"Fallo en el Registro", Toast.LENGTH_SHORT).show();
                        }
                    });

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