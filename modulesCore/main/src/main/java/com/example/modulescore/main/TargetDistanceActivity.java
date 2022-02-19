package com.example.modulescore.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TargetDistanceActivity extends BaseActivity implements View.OnClickListener{
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    Button btn_back;
    final String[] tabs = new String[]{"距离","时长","热量","配速"};
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
        My.TargetAdapter targetAdapter = new My.TargetAdapter(this);
        viewPager2.setOrientation(viewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(targetAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position==0){
                    Log.d("onPageSelected11111","1");
                }else if(position == 1){
                    Log.d("onPageSelected11111","2");
                }else if(position == 2){
                    Log.d("onPageSelected11111","3");
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_targetActivity:
                Log.d("btn_back_targetActivity","");
                this.finish();
                startActivity(new Intent(this,PreRunActivity.class));
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