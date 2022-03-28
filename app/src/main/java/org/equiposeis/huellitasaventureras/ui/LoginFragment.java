package org.equiposeis.huellitasaventureras.ui;

import static org.equiposeis.huellitasaventureras.AuthActivity.DONT_KEEP_LOGGED;
import static org.equiposeis.huellitasaventureras.AuthActivity.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;

import org.equiposeis.huellitasaventureras.MainActivity;
import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Se crea una instancia del servicio de Autenticación:
        FirebaseAuth auth = FirebaseAuth.getInstance();

        binding.bttnRegister.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_login_to_navigation_register, null);
        });

        binding.bttnLogin.setOnClickListener(v -> {
            if (!binding.txtMail.getText().toString().isEmpty()) {
                if (!binding.txtPassword.getText().toString().isEmpty()) {
                    auth.signInWithEmailAndPassword(binding.txtMail.getText().toString().trim(), binding.txtPassword.getText().toString()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            SharedPreferences.Editor preferencesEditor = preferences.edit();
                            if (!binding.chkKeepLogin.isChecked()) {
                                preferencesEditor.putBoolean(
                                        DONT_KEEP_LOGGED,
                                        true
                                );
                            } else {
                                preferencesEditor.putBoolean(
                                        DONT_KEEP_LOGGED,
                                        false
                                );
                            }
                            preferencesEditor.apply();
                            startActivity(new Intent(requireActivity(), MainActivity.class));
                            requireActivity().finish();
                        } else {
                            Toast.makeText(requireActivity(), R.string.not_loged, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}