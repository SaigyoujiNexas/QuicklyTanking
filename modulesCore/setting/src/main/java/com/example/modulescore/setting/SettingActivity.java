package com.example.modulescore.setting;

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.setting_container, new SettingsFragment()).commit();

    }

    @Override
    public void onClick(View view) {

    }
}