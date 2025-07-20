package com.dfrobot.angelo.blunobasicdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class detailsPage extends AppCompatActivity {

    private TextView textBAC, textEstimateTime, textTimeLocation;
    private Button buttonHome, backBtn;
    //private Button backBtn;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textBAC = findViewById(R.id.textBAC);
        textEstimateTime = findViewById(R.id.text_estimateTime);
        textTimeLocation = findViewById(R.id.text_timeLocation);
        buttonHome = findViewById(R.id.button_home);
        backBtn = findViewById(R.id.button_back); // find back button

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Receive BAC result from TransitionActivity
        String bacResult = getIntent().getStringExtra("bac_value");
        if (bacResult != null) {
            textBAC.setText(bacResult + "%");
        }


        // Estimated time from previous activity
        String estimate = getIntent().getStringExtra("estimate_time");
        if (estimate != null) textEstimateTime.setText(estimate);

        // Show loading message for location
        textTimeLocation.setText("Time: " + getCurrentTime() + "\nLocation: Fetching...");

        // Fetch GPS location
        getLocation();

//        // Optional: Set dynamic estimated time and location if passed
//        String estimate = getIntent().getStringExtra("estimate_time");
//        String details = getIntent().getStringExtra("test_details");
//
//        if (estimate != null) textEstimateTime.setText(estimate);
//        if (details != null) textTimeLocation.setText(details);

        // Back to homePage
        buttonHome.setOnClickListener(v -> {
            Intent intent = new Intent(detailsPage.this, homePage.class);
            startActivity(intent);
        });

        // Back to main page
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(detailsPage.this, MainActivity.class);
            startActivity(intent);
        });


    }
    // Method to get the location
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                updateLocationDetails(location);
            } else {
                textTimeLocation.setText("Time: " + getCurrentTime() + "\nLocation: Unknown");
            }
        }).addOnFailureListener(e -> textTimeLocation.setText("Time: " + getCurrentTime() + "\nLocation: Unknown"));
    }

    private void updateLocationDetails(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String city = address.getLocality() != null ? address.getLocality() : "Unknown City";
                String province = address.getAdminArea() != null ? address.getAdminArea() : "Unknown Province";
                String country = address.getCountryName() != null ? address.getCountryName() : "Unknown Country";

                // ✅ Add this new logic for legal BAC limit
                double legalLimit = getLegalLimit(country);

                String locationText = "Time: " + getCurrentTime() +
                        "\nLocation: " + city + ", " + province + ", " + country +
                        "\nLegal BAC Limit: " + legalLimit + "%";

                textTimeLocation.setText(locationText);
            } else {
                textTimeLocation.setText("Time: " + getCurrentTime() + "\nLocation: Unknown");
            }
        } catch (IOException e) {
            e.printStackTrace();
            textTimeLocation.setText("Time: " + getCurrentTime() + "\nLocation: Unknown");
        }
    }


    private String getCurrentTime() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            textTimeLocation.setText("Time: " + getCurrentTime() + "\nLocation: Permission Denied");
        }
    }
    private double getLegalLimit(String country) {
        switch (country) {
            case "China":
                return 0.02;
            case "Canada":
            case "United States":
                return 0.08;
            case "Germany":
            case "France":
                return 0.05;
            case "Japan":
            case "India":
                return 0.03;
            case "Pakistan":
                return 0.00;
            default:
                return 0.08; // Default if country not in list
        }
    }

}



