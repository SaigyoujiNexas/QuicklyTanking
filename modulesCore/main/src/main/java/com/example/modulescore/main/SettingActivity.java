package com.example.modulescore.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout settingItem1;
    ConstraintLayout settingItem2;
    ConstraintLayout settingItem3;
    ConstraintLayout settingItem4;
    ConstraintLayout settingItem5;
    ConstraintLayout settingItem6;
    ConstraintLayout settingItem7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settingItem1 = findViewById(R.id.SettingsItem1);
        settingItem2 = findViewById(R.id.SettingsItem2);
        settingItem3 = findViewById(R.id.SettingsItem3);
        settingItem4 = findViewById(R.id.SettingsItem4);
        settingItem5 = findViewById(R.id.SettingsItem5);
        settingItem6 = findViewById(R.id.SettingsItem6);
        settingItem7 = findViewById(R.id.SettingsItem7);
        settingItem1.setClickable(true);
        settingItem2.setClickable(true);
        settingItem3.setClickable(true);
        settingItem4.setClickable(true);
        settingItem5.setClickable(true);
        settingItem6.setClickable(true);
        settingItem7.setClickable(true);
    }

    @Override
    public void onClick(View view) {

    }
}