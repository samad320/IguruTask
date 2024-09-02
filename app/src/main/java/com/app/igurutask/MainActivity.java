package com.app.igurutask;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_PICK_IMAGE = 3;

    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private TextView locationInfo;
    private UserAdapter userAdapter;
    private User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserRepository userRepository = new UserRepository(getApplicationContext());
        UserViewModelFactory factory = new UserViewModelFactory(userRepository);
        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);


        recyclerView = findViewById(R.id.recycler_view);
        locationInfo = findViewById(R.id.location_info);

        userAdapter = new UserAdapter(user -> {
            selectedUser = user;
            handleImageUpload();
        });

        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUsers(2).observe(this, users -> userAdapter.submitList(users));

        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            getLocation();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLocation() {

        Location location = new Location("dummyprovider");
        location.setLatitude(37.7749);
        location.setLongitude(-122.4194);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String addressText = String.format("Lat: %f, Long: %f\n%s, %s",
                        location.getLatitude(),
                        location.getLongitude(),
                        address.getLocality(),
                        address.getCountryName());
                locationInfo.setText(addressText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleImageUpload() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                if (imageBitmap != null) {
                    saveImageToDatabase(imageBitmap);
                }
            }
        }
    }

    private void saveImageToDatabase(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageData = byteArrayOutputStream.toByteArray();

        UserEntity userEntity = new UserEntity();
        userEntity.setUploadedImageUri(Arrays.toString(imageData));

        new Thread(() -> {
            AppDatabase.getDatabase(MainActivity.this).userDao().insert(userEntity);

            runOnUiThread(() -> userAdapter.notifyDataSetChanged());
        }).start();
    }
}
