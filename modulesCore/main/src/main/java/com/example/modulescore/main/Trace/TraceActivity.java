package com.example.modulescore.main.Trace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.example.modulespublic.common.base.MyDataBase;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulescore.main.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TraceActivity extends AppCompatActivity {

    final String[] tabs = new String[]{"轨迹","信息"};
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    RunningRecord selectedRecord;
    Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_trace);
        Long recordId = Long.parseLong(getIntent().getType());
        String username = getIntent().getType();
        TraceHandler traceHandler = new TraceHandler(this,Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                //selectedRecord = MyDataBase.getsInstance(getApplicationContext()).runningDao().queryRunningRecordById(recordId);
                selectedRecord = MyDataBase.getsInstance(getApplicationContext()).runningDao().queryRunningRecordByUsername("lizongbin");
                Message message = new Message();
                message.what = traceHandler.finishQuery;
                traceHandler.sendMessage(message);
            }
        }).start();
        viewPager2 = findViewById(R.id.viewpager2_TarceActivity);
        tabLayout = findViewById(R.id.tabLayout_TarceActivity);
        viewPager2.setOrientation(viewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        viewPager2.setOffscreenPageLimit(2);
    }
    public void initViewPager2Adapter(){
        TraceAdapter traceAdapter = new TraceAdapter(selectedRecord,this,savedInstanceState);
        viewPager2.setAdapter(traceAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
            }
        }).attach();
    }
}