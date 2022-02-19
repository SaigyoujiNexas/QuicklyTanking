package com.example.modulescore.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        My.ActivityManager.pushOneActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        My.ActivityManager.popOneActivity(this);
    }
}
