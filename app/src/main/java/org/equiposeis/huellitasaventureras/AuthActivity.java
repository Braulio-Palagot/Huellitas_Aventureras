package org.equiposeis.huellitasaventureras;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;

import org.equiposeis.huellitasaventureras.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;
    public static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static SharedPreferences preferences;
    public static final String PREFS_NAME = "org.equiposeis.huellitasaventureras.sharedpreferences";
    public static final String DONT_KEEP_LOGGED = "IS_LOGGED";
    public static String BASE_USER_PHOTO = "https://firebasestorage.googleapis.com/v0/b/huellitas-aventureras.appspot.com/o/ProfilePictures%2FuserNoPicture.jpg?alt=media&token=a0988f39-5a75-4491-97e9-77bc97170698";
    public static final int PERMISSION_ID = 34;
    public static FirebaseFirestore db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (preferences.getBoolean(DONT_KEEP_LOGGED,false))
            auth.signOut();

        if ((auth.getCurrentUser() != null)) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }

        if (!checkPermission())
            requestPermissions();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_login, R.id.navigation_register)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_auth);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private boolean checkPermission() {
        return ((ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_ID);
    }
}