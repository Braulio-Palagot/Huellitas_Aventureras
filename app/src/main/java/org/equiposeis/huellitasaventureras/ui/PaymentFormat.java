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
    private String TitularCard = "", CardOption = "", Numero = "";
    private int CVV = 0;
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

        binding.bttnEndPayment.setOnClickListener(v -> {


            MetodoPago met = new MetodoPago();

            if(!binding.txtTitularCard.getText().toString().isEmpty()){
                TitularCard = binding.txtTitularCard.getText().toString();
            }
            if (!binding.txtCardDetails.getText().toString().isEmpty()){
                CardOption =binding.txtCardDetails.getText().toString();
            }
            if (!binding.txtIntroduce.getText().toString().isEmpty()) {
                Numero = binding.txtIntroduce.getText().toString();
            }
            if (!binding.txtCVV.getText().toString().isEmpty()){
                CVV = Integer.parseInt(binding.txtCVV.getText().toString());
            }

            if (!TitularCard.isEmpty() && !CardOption.isEmpty() && !Numero.isEmpty() && CVV!=0) {

                met.setID_Usuario(user.getUid());
                met.setTitular(TitularCard);
                met.setMetodo_pago(CardOption);
                met.setNumero_pago(Numero);
                met.setCVV(CVV);

                //M??todo para mandar datos a la base.
                Map<String, Object> MetPago_db = new HashMap<>();
                MetPago_db.put(getResources().getString(R.string.ID_USUARIO), met.getID_Usuario());
                MetPago_db.put(getResources().getString(R.string.name_hint), met.getTitular());
                MetPago_db.put(getResources().getString(R.string.payment_type), met.getMetodo_pago());
                MetPago_db.put(getResources().getString(R.string.number), met.getNumero_pago());
                MetPago_db.put(getResources().getString(R.string.cvv_hint), met.getCVV());

                db.collection(getResources().getString(R.string.payment_method/*verificar*/)).document(user.getUid() + CardOption)
                        .set(MetPago_db)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(requireActivity(), getResources().getString(R.string.succefull_register_payment), Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_navigation_payment_to_navigation_profile, null);
                        })
                        .addOnFailureListener(e -> Toast.makeText(requireActivity(), getResources().getString(R.string.registry_error), Toast.LENGTH_SHORT).show());

            }else{
                Toast.makeText(requireActivity(), getResources().getString(R.string.incomplete_fields), Toast.LENGTH_SHORT).show();
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