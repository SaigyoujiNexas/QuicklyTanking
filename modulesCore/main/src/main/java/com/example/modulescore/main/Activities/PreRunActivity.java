package com.example.modulescore.main.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.modulescore.main.Pre.BottomAdapter;
import com.example.modulescore.main.Pre.PreDataFragment;
import com.example.modulescore.main.Pre.PreRunFragment;
import com.example.modulescore.main.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class PreRunActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView img_setting;
    ImageView img_close;
    private List<Fragment> fragmentList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_run);
        final String TAG = "PreRunActivityTAG";
        img_setting = findViewById(R.id.image_setting_prerun);
        img_close = findViewById(R.id.image_close_prerun);
        viewPager2 = findViewById(R.id.viewpager2_preRun);
        img_close.setOnClickListener(this);
        img_setting.setOnClickListener(this);
        bottomNavigationView = findViewById(R.id.preRunBottomNavigation);
        BottomAdapter bottomAdapter = new BottomAdapter(this);
        PreRunFragment preRunFragment = new PreRunFragment();
        PreDataFragment preDataFragment = new PreDataFragment();
        fragmentList.add(preRunFragment);
        fragmentList.add(preDataFragment);
        bottomAdapter.setFragmentList(fragmentList);
        viewPager2.setAdapter(bottomAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).isChecked();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.runPage:
                        Log.d(TAG,"runPage");
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.dataPage:
                        Log.d(TAG,"dataPage");
                        viewPager2.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });
        myRequestPermissions();
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
    public class CloseActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            PreRunActivity.this.finish();
        }
    }
    private void myRequestPermissions(){
        //ACCESS_FINE_LOCATION通过WiFi或移动基站的方式获取用户错略的经纬度信息，定位精度大概误差在30~1500米
        //ACCESS_FINE_LOCATION，通过GPS芯片接收卫星的定位信息，定位精度达10米以内
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);
        }
    }
}