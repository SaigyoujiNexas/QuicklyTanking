package com.example.modulescore.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SettingActivity extends AppCompatActivity{
    ConstraintLayout settingItem1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.setting_container, new SettingsFragment()).commit();

    }

}