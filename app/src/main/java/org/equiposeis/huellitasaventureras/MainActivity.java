package org.equiposeis.huellitasaventureras;

import static org.equiposeis.huellitasaventureras.AuthActivity.DONT_KEEP_LOGGED;
import static org.equiposeis.huellitasaventureras.AuthActivity.auth;
import static org.equiposeis.huellitasaventureras.AuthActivity.preferences;

import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static FirebaseUser user = null;
    private ActivityMainBinding binding;
    public static int IMAGE_REQUEST_CODE = 10;
    public static FirebaseFirestore db = null;
    public static Mascota mascotaSeleccionada = null;
    public static String ADD_PET_TYPE = "ADD_PET";
    public static boolean ALREADY_DOWNLOADED = false;
    public static Task<QuerySnapshot> mascotasQuery = null;
    public static Task<DocumentSnapshot> userQuery = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = auth.getCurrentUser();

        new Thread(this::loadUserData).start();

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

    private void loadUserData() {
        userQuery = db.collection(getResources().getString(R.string.USUARIOS_TABLE)).document(user.getUid()).get();
        mascotasQuery = db.collection("Mascota").whereEqualTo("ID_Cliente", user.getUid()).get();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (preferences.getBoolean(DONT_KEEP_LOGGED, false)) {
            FirebaseAuth.getInstance().signOut();
        }
    }
}