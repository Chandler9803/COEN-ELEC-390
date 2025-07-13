package com.dfrobot.angelo.blunobasicdemo;
import android.content.Intent;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;



public class homePage extends AppCompatActivity {

    private Button startTestBtn;
    private Button button_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        startTestBtn = findViewById(R.id.button_start_test);
        button_settings= findViewById(R.id.button_settings);

        startTestBtn.setOnClickListener(v -> {
            // Navigate to the next screen, for example: TransitionActivity
            Intent intent = new Intent(homePage.this, MainActivity.class);
            startActivity(intent);
        });
        startTestBtn.setOnClickListener(v -> {
            // Navigate to the next screen, for example: TransitionActivity
            Intent intent = new Intent(homePage.this, MainActivity.class);
            startActivity(intent);
        });
        button_settings.setOnClickListener(v -> {
            // Navigate to the next screen, for example: TransitionActivity
            Intent intent = new Intent(homePage.this, settingsPage.class);
            startActivity(intent);
        });
    }
}
