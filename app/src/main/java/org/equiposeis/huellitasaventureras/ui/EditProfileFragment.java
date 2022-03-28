package org.equiposeis.huellitasaventureras.ui;

import static android.app.Activity.RESULT_OK;
import static org.equiposeis.huellitasaventureras.MainActivity.IMAGE_REQUEST_CODE;
import static org.equiposeis.huellitasaventureras.MainActivity.PROFILE_PHOTO_EDITED;
import static org.equiposeis.huellitasaventureras.MainActivity.PROFILE_PHOTO_REFERENCE;
import static org.equiposeis.huellitasaventureras.MainActivity.db;
import static org.equiposeis.huellitasaventureras.MainActivity.storage;
import static org.equiposeis.huellitasaventureras.MainActivity.user;
import static org.equiposeis.huellitasaventureras.MainActivity.userQuery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.equiposeis.huellitasaventureras.Glide.GlideApp;
import org.equiposeis.huellitasaventureras.R;
import org.equiposeis.huellitasaventureras.databinding.FragmentEditProfileBinding;

import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    // Creación de variables para mandar a la BD:
    private String name = "", addres = "", mail = "", userType = "", gender = "";
    private int age = 0;
    private long phone = 0;
    // Se crean los arrays de elemntos de los DropDown:
    private String[] genders = null;
    private String[] user_types = null;

    private final StorageReference profilePhotoReference = storage.getReference().child("ProfilePictures/" + user.getUid());
    Uri cropedUri = null;
    boolean IMAGE_SELECTED = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.imgUserPhoto.setDrawingCacheEnabled(true);
        binding.imgUserPhoto.buildDrawingCache();
        PROFILE_PHOTO_REFERENCE = profilePhotoReference;

        // Se cargan los datos del usuario:
        DocumentSnapshot userData = userQuery.getResult();
        binding.txtName.setText(userData.get(getResources().getString(R.string.NOMBRE_USUARIO)).toString());
        binding.txtGender.setText(userData.get(getResources().getString(R.string.GENERO_USUARIO)).toString());
        binding.txtAge.setText(userData.get(getResources().getString(R.string.EDAD_USUARIO)).toString());
        binding.txtPhone.setText(userData.get(getResources().getString(R.string.TELEFONO_USUARIO)).toString());
        binding.txtAddress.setText(userData.get(getResources().getString(R.string.DOMICILIO_USUARIO)).toString());
        binding.txtMail.setText(userData.get(getResources().getString(R.string.EMAIL_USUARIO)).toString());
        binding.txtUserType.setText(userData.get(getResources().getString(R.string.TIPO_USUARIO)).toString());

        GlideApp.with(EditProfileFragment.this)
                .load(userData.get("Foto_Usuario"))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgUserPhoto);

        // Se cargan los elementos de los arrays. Si hay que bajar de la BD, se hace aquí:
        genders = getResources().getStringArray(R.array.genders);
        user_types = getResources().getStringArray(R.array.user_types);

        // Se llenan los DropDown con los arrays creados:
        binding.txtGender.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, genders));
        binding.txtUserType.setAdapter(new ArrayAdapter(requireActivity(), R.layout.dropdown_item, user_types));

        binding.bttnPaymentMethod.setText(getResources().getString(R.string.payment_method, userData.get(getResources().getString(R.string.TIPO_USUARIO)).toString().equals("Cliente") ? "Pago" : "Cobro"));

        binding.bttnEditPhoto.setOnClickListener(v -> {
            // Se realiza la selección de foto desde el celular:
            loadImage();
        });

        binding.bttnFinish.setOnClickListener(v -> {
            // Se toman los valores string de los campos de texto, confirmando que no sean nulos:
            if (!binding.txtName.getText().toString().isEmpty()) {
                name = binding.txtName.getText().toString();
            }
            if (!binding.txtAddress.getText().toString().isEmpty()) {
                addres = binding.txtAddress.getText().toString();
            }
            if (!binding.txtMail.getText().toString().isEmpty()) {
                mail = binding.txtMail.getText().toString();
            }

            // Se toman los valores numéricos de de edad y teléfono:
            if (!binding.txtAge.getText().toString().isEmpty()) {
                age = Integer.parseInt(binding.txtAge.getText().toString());
            }
            if (!binding.txtPhone.getText().toString().isEmpty()) {
                phone = Long.parseLong(binding.txtPhone.getText().toString());
            }

            // Se selecciona el valor índice de cada selección de los DropDowns:
            if (!binding.txtGender.getText().toString().isEmpty()) {
                gender = binding.txtGender.getText().toString();
            }
            if (!binding.txtUserType.getText().toString().isEmpty()) {
                userType = binding.txtUserType.getText().toString();
            }

            if (!IMAGE_SELECTED) {
                PROFILE_PHOTO_EDITED = true;
                Map<String, Object> updateUser = new HashMap<>();
                updateUser.put(getResources().getString(R.string.NOMBRE_USUARIO), name);
                updateUser.put(getResources().getString(R.string.GENERO_USUARIO), gender);
                updateUser.put(getResources().getString(R.string.EDAD_USUARIO), age);
                updateUser.put(getResources().getString(R.string.TELEFONO_USUARIO), phone);
                updateUser.put(getResources().getString(R.string.DOMICILIO_USUARIO), addres);
                updateUser.put(getResources().getString(R.string.EMAIL_USUARIO), mail);
                updateUser.put(getResources().getString(R.string.TIPO_USUARIO), userType);
                db.collection(getResources().getString(R.string.USUARIOS_TABLE)).document(user.getUid()).update(updateUser);
                Toast.makeText(requireContext(), "Actualización Exitosa. Reincia la app.", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(EditProfileFragment.this).navigate(R.id.action_navigation_edit_profile_to_navigation_profile, null);
            } else {
                UploadTask uploadTask = profilePhotoReference.putFile(cropedUri);
                profilePhotoReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    Map<String, Object> updateUser = new HashMap<>();
                    updateUser.put(getResources().getString(R.string.NOMBRE_USUARIO), name);
                    updateUser.put(getResources().getString(R.string.GENERO_USUARIO), gender);
                    updateUser.put(getResources().getString(R.string.EDAD_USUARIO), age);
                    updateUser.put(getResources().getString(R.string.TELEFONO_USUARIO), phone);
                    updateUser.put(getResources().getString(R.string.DOMICILIO_USUARIO), addres);
                    updateUser.put(getResources().getString(R.string.EMAIL_USUARIO), mail);
                    updateUser.put(getResources().getString(R.string.TIPO_USUARIO), userType);
                    updateUser.put(getResources().getString(R.string.FOTO_USUARIO), uri.toString());
                    db.collection(getResources().getString(R.string.USUARIOS_TABLE)).document(user.getUid()).update(updateUser);
                    Toast.makeText(requireContext(), "Actualización Exitosa. Reincia la app.", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(EditProfileFragment.this).navigate(R.id.action_navigation_edit_profile_to_navigation_profile, null);
                });
            }
        });

        binding.bttnPaymentMethod.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_edit_profile_to_navigation_payment, null);
        });

        binding.bttnCancel.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_edit_profile_to_navigation_profile, null);
        });

        return root;
    }

    private void loadImage() {
        Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galery.setType("image/");
        startActivityForResult(galery.createChooser(galery, getResources().getString(R.string.select_media_app)), IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode != CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Uri path = null;
            try {
                path = data.getData();
                CropImage.activity(path)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .setRequestedSize(640, 640)
                        .start(getContext(), this);
            } catch (Exception e) {
                Log.e("Error", String.valueOf(e));
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            cropedUri = result.getUri();
            GlideApp.with(this)
                    .load(cropedUri)
                    .into(binding.imgUserPhoto);
            IMAGE_SELECTED = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
