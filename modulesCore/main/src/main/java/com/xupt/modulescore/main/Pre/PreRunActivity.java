package com.xupt.modulescore.main.Pre;


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
import androidx.viewpager2.widget.ViewPager2;

import com.amap.api.maps.MapsInitializer;
import com.xupt.modulescore.main.Pre.Mine.MineFragment;
import com.example.modulescore.main.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class PreRunActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_setting;
    ImageView img_close;
    private List<Fragment> fragmentList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 0;
    CardView card_setting_back;
    CardView card_music_prerun;

    final String TAG = "PreRunActivityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_run);
        MapsInitializer mapsInitializer = new MapsInitializer();
        //更新隐私合规状态,需要在初始化地图之前完成
        mapsInitializer.updatePrivacyShow(this, true, true);
        //更新同意隐私状态,需要在初始化地图之前完成
        mapsInitializer.updatePrivacyAgree(this, true);
        card_setting_back = findViewById(R.id.card_setting_back);
        card_music_prerun = findViewById(R.id.card_music_prerun);
        final String TAG = "PreRunActivityTAG";
        img_setting = findViewById(R.id.image_setting_prerun);
        img_close = findViewById(R.id.image_close_prerun);
        viewPager2 = findViewById(R.id.viewpager2_preRun);
        img_close.setOnClickListener(this);
        img_setting.setOnClickListener(this);
        bottomNavigationView = findViewById(R.id.preRunBottomNavigation);
        BottomAdapter bottomAdapter = new BottomAdapter(this);
        PreRunFragment preRunFragment = new PreRunFragment();
        MineFragment mineFragment = new MineFragment();
        CommunityFragment communityFragment = new CommunityFragment();
        fragmentList.add(preRunFragment);
        fragmentList.add(communityFragment);
        fragmentList.add(mineFragment);

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
                MenuItem item = bottomNavigationView.getMenu().getItem(position);
                item.setChecked(true);
                if (position == 0) {
                    Log.d(TAG, "runPage");
                    card_music_prerun.setVisibility(View.VISIBLE);
                    card_setting_back.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "NrunPage");
                    card_music_prerun.setVisibility(View.GONE);
                    card_setting_back.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                var id = item.getItemId();
                if(id == R.id.runPage) {
                    Log.d(TAG, "runPage");
                    viewPager2.setCurrentItem(0);
                    card_music_prerun.setVisibility(View.VISIBLE);
                    card_setting_back.setVisibility(View.VISIBLE);
                }
                else if(id == R.id.communityPage) {
                    Log.d(TAG, "NrunPage");
                    viewPager2.setCurrentItem(1);
                    card_music_prerun.setVisibility(View.GONE);
                    card_setting_back.setVisibility(View.GONE);
                }
                else if(id == R.id.minePage){
                        Log.d(TAG, "NrunPage");
                        viewPager2.setCurrentItem(2);
                        card_music_prerun.setVisibility(View.GONE);
                        card_setting_back.setVisibility(View.GONE);
                }
                return true;
            }
        });
        myRequestPermissions();
    }




    @Override
    public void onClick(View view) {
        var id = view.getId();
        if(id == R.id.image_close_prerun) {
            CloseActivityReceiver closeReceiver = new CloseActivityReceiver();
            IntentFilter intentFilter = new IntentFilter("closeactivity");
            registerReceiver(closeReceiver, intentFilter);
            Intent intent = new Intent();
            intent.setAction("closeactivity");
            sendBroadcast(intent);
        }
        else if(id == R.id.image_setting_prerun){

        }
    }

    public class CloseActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            PreRunActivity.this.finish();
        }
    }

    private void myRequestPermissions() {
        //ACCESS_FINE_LOCATION通过WiFi或移动基站的方式获取用户错略的经纬度信息，定位精度大概误差在30~1500米
        //ACCESS_FINE_LOCATION，通过GPS芯片接收卫星的定位信息，定位精度达10米以内
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WAKE_LOCK,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);
        }
    }
}