package org.equiposeis.huellitasaventureras;

import static org.equiposeis.huellitasaventureras.AuthActivity.DONT_KEEP_LOGGED;
import static org.equiposeis.huellitasaventureras.AuthActivity.preferences;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.equiposeis.huellitasaventureras.dataModels.Mascota;
import org.equiposeis.huellitasaventureras.dataModels.Paseo;
import org.equiposeis.huellitasaventureras.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static FirebaseAuth auth = FirebaseAuth.getInstance();
    public static FirebaseUser user = auth.getCurrentUser();
    private ActivityMainBinding binding;
    public static int IMAGE_REQUEST_CODE = 10;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static Mascota mascotaSeleccionada = null;
    public static Paseo paseoSeleccionado = null;
    public static String ADD_PET_TYPE = "ADD_PET";
    public static boolean PETS_ALREADY_DOWNLOADED = false;
    public static boolean RIDES_ALREADY_DOWNLOADED = false;
    public static Task<QuerySnapshot> mascotasUsuarioQuery = db.collection("Mascota").whereEqualTo("ID_Cliente", user.getUid()).get();
    public static Task<QuerySnapshot> mascotasQuery = db.collection("Mascota").get();
    public static Task<DocumentSnapshot> userQuery = db.collection("Usuarios").document(user.getUid()).get();
    public static Task<QuerySnapshot> employeesQuery = db.collection("Usuarios").whereEqualTo("Tipo_Usuario", 1).get();
    public static Task<QuerySnapshot> clientsQuery = db.collection("Usuarios").whereEqualTo("Tipo_Usuario", 0).get();
    public static Task<QuerySnapshot> walksQuery = db.collection("Paseos").get();
    public static FirebaseStorage storage = FirebaseStorage.getInstance();

    public static StorageReference PROFILE_PHOTO_REFERENCE = storage.getReference().child("ProfilePictures/" + user.getUid());
    public static boolean PROFILE_PHOTO_EDITED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = auth.getCurrentUser();

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
    protected void onDestroy() {
        super.onDestroy();
        if (preferences.getBoolean(DONT_KEEP_LOGGED, false)) {
            FirebaseAuth.getInstance().signOut();
        }
    }
}