package com.example.modulescore.main.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.modulescore.main.Pre.BottomAdapter;
import com.example.modulescore.main.Pre.PreRunFragment;
import com.example.modulescore.main.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PreRunActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView img_setting;
    ImageView img_close;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_run);
        img_setting = findViewById(R.id.image_setting_prerun);
        img_close = findViewById(R.id.image_close_prerun);
        img_close.setOnClickListener(this);
        img_setting.setOnClickListener(this);
        bottomNavigationView = findViewById(R.id.preRunBottomNavigation);
        BottomAdapter bottomAdapter = new BottomAdapter(this);
        PreRunFragment preRunFragment = new PreRunFragment();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.runPage:
                        replaceFragment(preRunFragment);
                        break;
                    case R.id.dataPage:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_close_prerun:
                CloseActivityReceiver closeReceiver = new CloseActivityReceiver();
                IntentFilter intentFilter = new IntentFilter("closeactivity");
                registerReceiver(closeReceiver, intentFilter);
                Intent intent = new Intent();
                intent.setAction("closeactivity");
                sendBroadcast(intent);
                break;
            case R.id.image_setting_prerun:
                break;

            default:
                break;
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.preRunFragment_layout,fragment);
        fragmentTransaction.commit();
    }
    public class CloseActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            PreRunActivity.this.finish();
        }
    }
}