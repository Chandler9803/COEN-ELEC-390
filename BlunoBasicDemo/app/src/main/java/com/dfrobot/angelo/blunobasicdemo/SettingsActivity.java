package com.dfrobot.angelo.blunobasicdemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    private boolean isEditMode = false;
    protected EditText editText1, editText2, editText3, editText4, editText5;
    protected Button saveBtn, pairBtn;
    protected ImageButton backBtn, editFieldBtn;

    protected Switch alertSwitch, shareSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
        //  setupUI();
    }

    private void setupUI() {

        sharedPreferenceHelper SharedpreferenceHelper = new sharedPreferenceHelper(getApplicationContext());

        editText1 = findViewById(R.id.unitsText);
        editText2 = findViewById(R.id.warningText);
        editText3 = findViewById(R.id.ageText);
        editText4 = findViewById(R.id.weightText);
        editText5 = findViewById(R.id.nameText);

        saveBtn = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backBtn);
        editFieldBtn = findViewById(R.id.editFieldBtn);
        pairBtn = findViewById(R.id.pairBtn);

        Switch alertSwitch = findViewById(R.id.alertSwitch);
        Switch shareSwitch = findViewById(R.id.shareSwitch);


        setEditMode(false);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    v.animate().alpha(0.5f).setDuration(100).withEndAction(() -> v.animate().alpha(1f).setDuration(100).start()).start();
                }
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    v.animate().alpha(0.5f).setDuration(100).withEndAction(() -> v.animate().alpha(1f).setDuration(100).start()).start();
                }
                PopupMenu popup = new PopupMenu(SettingsActivity.this, editFieldBtn);
                popup.getMenu().add(isEditMode ? "View Settings" : "Edit Settings");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        setEditMode(item.getTitle().equals("Edit Settings"));
                        return true;
                    }
                });

                popup.show();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = editText1.getText().toString();
                String name2 = editText2.getText().toString();
                String name3 = editText3.getText().toString();
                String name4 = editText4.getText().toString();
                String name5 = editText5.getText().toString();

                boolean alertSwitchValue = alertSwitch.isChecked();
                boolean shareSwitchValue = shareSwitch.isChecked();

                SharedpreferenceHelper.saveAlertSwitch(alertSwitchValue);
                SharedpreferenceHelper.saveShareSwitch(shareSwitchValue);


                if (name1.isEmpty() || name2.isEmpty() || name3.isEmpty() || name4.isEmpty() || name5.isEmpty()) {
                    Toast.makeText(SettingsActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedpreferenceHelper.saveName1(name1);
                SharedpreferenceHelper.saveName2(name2);
                SharedpreferenceHelper.saveName3(name3);
                SharedpreferenceHelper.saveName4(name4);
                SharedpreferenceHelper.saveName5(name5);


                Toast.makeText(SettingsActivity.this, "Settings Have been saved", Toast.LENGTH_SHORT).show();
                setEditMode(false);
            }
        });

        String name1 = SharedpreferenceHelper.getName1();
        String name2 = SharedpreferenceHelper.getName2();
        String name3 = SharedpreferenceHelper.getName3();
        String name4 = SharedpreferenceHelper.getName4();
        String name5 = SharedpreferenceHelper.getName5();

        if (name1 != null && name2 != null && name3 != null && name4 != null && name5 != null) {

            editText1.setText(name1);
            editText2.setText(name2);
            editText3.setText(name3);
            editText4.setText(name4);
            editText5.setText(name5);

        }
        alertSwitch.setChecked(SharedpreferenceHelper.getAlertSwitch());
        shareSwitch.setChecked(SharedpreferenceHelper.getShareSwitch());


    }

    private void setEditMode(boolean editable) {
        isEditMode = editable;

        editText1.setEnabled(editable);
        editText2.setEnabled(editable);
        editText3.setEnabled(editable);
        editText4.setEnabled(editable);

        alertSwitch.setEnabled(editable);
        shareSwitch.setEnabled(editable);

        saveBtn.setVisibility(editable ? View.VISIBLE : View.GONE);
    }
}


