package com.xupt.modulescore.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.example.modulescore.setting.R;

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