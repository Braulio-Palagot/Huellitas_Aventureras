package org.equiposeis.huellitasaventureras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.equiposeis.huellitasaventureras.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;
    public static SharedPreferences preferences;
    public static final String PREFS_NAME = "org.equiposeis.huellitasaventureras.sharedpreferences";
    public static final String IS_LOGGED = "IS_LOGGED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (preferences.getBoolean(IS_LOGGED, false)) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_login, R.id.navigation_register)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_auth);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

}