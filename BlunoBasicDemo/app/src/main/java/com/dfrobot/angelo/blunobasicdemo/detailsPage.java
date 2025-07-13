package com.dfrobot.angelo.blunobasicdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class detailsPage extends AppCompatActivity {

    private TextView textBAC, textEstimateTime, textTimeLocation;
    private Button buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textBAC = findViewById(R.id.textBAC);
        textEstimateTime = findViewById(R.id.text_estimateTime);
        textTimeLocation = findViewById(R.id.text_timeLocation);
        buttonHome = findViewById(R.id.button_home);

        // Receive BAC result from TransitionActivity
        String bacResult = getIntent().getStringExtra("bac_value");
        if (bacResult != null) {
            textBAC.setText(bacResult + "%");
        }

        // Optional: Set dynamic estimated time and location if passed
        String estimate = getIntent().getStringExtra("estimate_time");
        String details = getIntent().getStringExtra("test_details");

        if (estimate != null) textEstimateTime.setText(estimate);
        if (details != null) textTimeLocation.setText(details);

        // Back to homePage
        buttonHome.setOnClickListener(v -> {
            Intent intent = new Intent(detailsPage.this, homePage.class);
            startActivity(intent);
        });
    }
}
