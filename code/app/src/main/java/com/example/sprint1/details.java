package com.example.sprint1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import android.location.Location;

// GeoCoder to covert the location to real city and street
import android.location.Geocoder;
import android.location.Address;



public class details extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        // get reference
        TextView textBAC = findViewById(R.id.text_BAC);
        TextView textEstimateTime = findViewById(R.id.text_estimateTime);
        TextView textTestDetails = findViewById(R.id.text_testDetails);
        Button buttonHome = findViewById(R.id.button_homePage);

        // format the time
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a, MMM dd yyyy");
        String currentTime = sdf.format(new Date());

        // request location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }




        // get BAC from the previous activity


        // update the BAC value


        // estimate sober time
//        int soberHours = estimateSoberHours(bacValue);
//        textEstimate.setText("You might drive after " + soberHours + " hours");


        // show test details
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        String locationString = "Unknown Location"; // fallback string if no location is available
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            // GeoCoder to show real location name instead of the coordinate
                            Geocoder geocoder = new Geocoder(this);
                            try {
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                if (addresses != null && !addresses.isEmpty()) {
                                    Address address = addresses.get(0);
                                    locationString = address.getAddressLine(0);  // full address
                                } else {
                                    locationString = latitude + ", " + longitude;
                                }
                            } catch (Exception e) {
                                locationString = latitude + ", " + longitude;
                            }
                        }

                        textTestDetails.setText("Time: " + currentTime + "\nLocation: " + locationString);
                    });
        } else {
            textTestDetails.setText("Time: " + currentTime + "\nLocation: permission denied");
        }



        // button to lead to homepage








        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

//    private int estimateSoberHours(double bac){
//        if(bac <= 0.02) return 0;
//        else if(bac <= 0.05) return 1;
//        else if(bac <= 0.08) return 3;
//        else return 6;
//    }
}