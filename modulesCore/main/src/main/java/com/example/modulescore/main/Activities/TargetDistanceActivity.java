package com.example.modulescore.main.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.modulescore.main.R;
import com.example.modulescore.main.Target.TargetAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.EventBus;

public class TargetDistanceActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    Button btn_back;
    final String[] tabs = new String[]{"距离","时长","热量","配速"};
    CardView startDistanceRunView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_distance);
        initView();
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back_targetActivity);
        viewPager2 = findViewById(R.id.target_distance_viewpager2);
        tabLayout = findViewById(R.id.tabLayout_TargetDistance);
        TargetAdapter targetAdapter = new TargetAdapter(this);
        viewPager2.setOrientation(viewPager2.ORIENTATION_HORIZONTAL);
        startDistanceRunView = findViewById(R.id.start_distanceRun_Card);
        viewPager2.setAdapter(targetAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                String TAG = "onPageSelected_TAG";
                if(position==0){
                    Log.d(TAG,"0");
                }else if(position == 1){
                    Log.d(TAG,"1");
                }else if(position == 2){
                    Log.d(TAG,"2");
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        viewPager2.setOffscreenPageLimit(3);
//        tabLayout.setFocusableInTouchMode(false);   //触摸是否获得焦点
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
            }
        }).attach();

        btn_back.setOnClickListener(this);
        startDistanceRunView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_targetActivity:
                Log.d("btn_back_targetActivity","");
                this.finish();
                startActivity(new Intent(this,PreRunActivity.class));
                break;
            case R.id.start_distanceRun_Card:

                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}