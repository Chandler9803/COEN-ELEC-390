package com.dfrobot.angelo.blunobasicdemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    //private boolean isEditMode = false;
    //protected EditText editText1, editText2, editText3, editText4, editText5;
    //  protected Button saveBtn, pairBtn;
    protected Button backBtn,sharedataButton;
    // protected ImageButton editFieldBtn;

    //protected Switch alertSwitch, shareSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_settings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
        setupUI();
    }

    private void setupUI() {

        // sharedPreferenceHelper SharedpreferenceHelper = new sharedPreferenceHelper(getApplicationContext());

//        editText1 = findViewById(R.id.unitsText);
//        editText2 = findViewById(R.id.warningText);
//        editText3 = findViewById(R.id.ageText);
//        editText4 = findViewById(R.id.weightText);
//        editText5 = findViewById(R.id.nameText);
//
//        saveBtn = findViewById(R.id.saveBtn);
        //  editFieldBtn = findViewById(R.id.editFieldBtn);


        // Switch alertSwitch = findViewById(R.id.alertSwitch);
        // Switch shareSwitch = findViewById(R.id.shareSwitch);


        //   setEditMode(false);
        //datashare dialog fragment
        Button sharadataButton = findViewById(R.id.sharedataButton);
        sharadataButton.setOnClickListener(v -> {
            datashare_consent dialog = datashare_consent.newInstance();
            dialog.show(getSupportFragmentManager(), "ConsentDialog");
        });
        //back button function
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> {
            // Navigate to the next screen, for example: TransitionActivity
            Intent intent = new Intent(SettingsActivity.this, homePage.class);
            startActivity(intent);
        });
        //weight units weightDropdown menu
        Spinner weightDropdown = findViewById(R.id.weightUnits);
        //user can choose between kg and lbs
        String[] items = new String[]{"kg", "lbs"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        weightDropdown.setAdapter(adapter);
        //weight units weightDropdown menu
        Spinner BACdropdown = findViewById(R.id.BACLimit);
        //user can choose between kg and lbs
        String[] itemsBAC = new String[]{"Quebec", "British Columbia","Ontario","Alberta","Nova Scotia","Saskatchewan","Yukon"
        ,"New Brunswick","Manitoba","Newfoundland","Prince Edward Island"};

        ArrayAdapter<String> adapterBAC = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsBAC);

        BACdropdown.setAdapter(adapterBAC);
    }
}

//        editFieldBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    v.animate().alpha(0.5f).setDuration(100).withEndAction(() -> v.animate().alpha(1f).setDuration(100).start()).start();
//                }
//                PopupMenu popup = new PopupMenu(SettingsActivity.this, editFieldBtn);
//                popup.getMenu().add(isEditMode ? "View Settings" : "Edit Settings");
//
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//
//                       // setEditMode(item.getTitle().equals("Edit Settings"));
//                        return true;
//                    }
//                });
//
//                popup.show();
//            }
//        });


//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name1 = editText1.getText().toString();
//                String name2 = editText2.getText().toString();
//                String name3 = editText3.getText().toString();
//                String name4 = editText4.getText().toString();
//                String name5 = editText5.getText().toString();
//
//                boolean alertSwitchValue = alertSwitch.isChecked();
//                boolean shareSwitchValue = shareSwitch.isChecked();

//   SharedpreferenceHelper.saveAlertSwitch(alertSwitchValue);
// SharedpreferenceHelper.saveShareSwitch(shareSwitchValue);


//                if (name1.isEmpty() || name2.isEmpty() || name3.isEmpty() || name4.isEmpty() || name5.isEmpty()) {
//                    Toast.makeText(SettingsActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//SharedpreferenceHelper.saveName1(name1);
//SharedpreferenceHelper.saveName2(name2);
//SharedpreferenceHelper.saveName3(name3);
//SharedpreferenceHelper.saveName4(name4);
//SharedpreferenceHelper.saveName5(name5);

//
//                Toast.makeText(SettingsActivity.this, "Settings Have been saved", Toast.LENGTH_SHORT).show();
//  setEditMode(false);
// }
//   });

//        String name1 = SharedpreferenceHelper.getName1();
//        String name2 = SharedpreferenceHelper.getName2();
//        String name3 = SharedpreferenceHelper.getName3();
//        String name4 = SharedpreferenceHelper.getName4();
//        String name5 = SharedpreferenceHelper.getName5();

//        if (name1 != null && name2 != null && name3 != null && name4 != null && name5 != null) {
//
//            editText1.setText(name1);
//            editText2.setText(name2);
//            editText3.setText(name3);
//            editText4.setText(name4);
//            editText5.setText(name5);
//
//        }
//        alertSwitch.setChecked(SharedpreferenceHelper.getAlertSwitch());
//        shareSwitch.setChecked(SharedpreferenceHelper.getShareSwitch());
//
//
//    }

//        private void setEditMode ( boolean editable){
//            isEditMode = editable;
//
//            editText1.setEnabled(editable);
//            editText2.setEnabled(editable);
//            editText3.setEnabled(editable);
//            editText4.setEnabled(editable);
//
//            alertSwitch.setEnabled(editable);
//            shareSwitch.setEnabled(editable);
//
//            saveBtn.setVisibility(editable ? View.VISIBLE : View.GONE);
//        }
//


