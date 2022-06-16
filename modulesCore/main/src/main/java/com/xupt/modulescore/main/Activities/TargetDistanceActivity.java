package com.xupt.modulescore.main.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import com.xupt.modulescore.main.Pre.PreRunActivity;
import com.example.modulescore.main.R;
import com.xupt.modulescore.main.Run.RunActivity;
import com.xupt.modulescore.main.Target.TargetAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TargetDistanceActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    Button btn_back;
    final String[] tabs = new String[]{"距离","时长","热量","配速"};
    CardView startDistanceRunView;
    static final String isTarget = "1";
    static final String notTarget = "0";
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
                String TAG = "onPageScrolled_TAG";
                Log.d(TAG,position+"");
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                String TAG = "onPageSelected_TAG";
                TargetAdapter targetAdapter1 = (TargetAdapter) viewPager2.getAdapter();
                targetAdapter1.setViewPagerPostion(position);
                if(position==0){
                    Log.d(TAG,"0");
                    viewPager2.postDelayed(() -> targetAdapter1.getScrollPickerView0().onScrolled(0,0),300);
                }else if(position == 1){
                    Log.d(TAG,"1");
                    viewPager2.postDelayed(() -> targetAdapter1.getScrollPickerView1().onScrolled(0,0),300);
                }else if(position == 2){
                    Log.d(TAG,"2");
                    viewPager2.postDelayed(() -> targetAdapter1.getScrollPickerView2().onScrolled(0,0),300);
                }else if(position == 3){
                    Log.d(TAG,"3");
                    viewPager2.postDelayed(() -> targetAdapter1.getScrollPickerView3().onScrolled(0,0),300);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                String TAG = "onPageScrollStateChanged_TAG";
                Log.d(TAG,state+"");
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
        int id = view.getId();
        if(id == R.id.btn_back_targetActivity) {
            Log.d("btn_back_targetActivity", "");
            this.finish();
            startActivity(new Intent(this, PreRunActivity.class));
        }
        else if(id == R.id.start_distanceRun_Card) {
            Intent startRunIntent = new Intent(this, RunActivity.class);
            startRunIntent.setType(isTarget);
            startRunIntent.setFlags(viewPager2.getCurrentItem());
            this.finish();
            startActivity(startRunIntent);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
