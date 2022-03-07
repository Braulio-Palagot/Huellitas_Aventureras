package org.equiposeis.huellitasaventureras;

import static org.equiposeis.huellitasaventureras.AuthActivity.DONT_KEEP_LOGGED;
import static org.equiposeis.huellitasaventureras.AuthActivity.preferences;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import org.equiposeis.huellitasaventureras.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

private ActivityMainBinding binding;
public static int IMAGE_REQUEST_CODE = 10;
public static FirebaseFirestore db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     db = FirebaseFirestore.getInstance();
     binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_edit_profile, R.id.navigation_add_pet,
                R.id.navigation_payment, R.id.navigation_ride_details, R.id.navigation_ride_request)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (preferences.getBoolean(DONT_KEEP_LOGGED, false)) {
            FirebaseAuth.getInstance().signOut();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (preferences.getBoolean(DONT_KEEP_LOGGED, false)) {
            FirebaseAuth.getInstance().signOut();
        }
    }
}