package com.example.modulescore.main.UI.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.maps.MapsInitializer;
import com.example.modulescore.main.EventBus.MessageEvent;
import com.example.modulescore.main.UI.View.ProgressButton;
import com.example.modulescore.main.R;
import com.example.modulescore.main.Util.TimeManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RunActivity extends BaseActivity implements View.OnClickListener {
    FloatingActionButton startRunButton;
    FloatingActionButton stopRunButton;
    ProgressButton finishRunButton;
    CardView toMapCard;
    ConstraintLayout constraintLayout_run;
    boolean isRun = true;
    TextView animationText;
    ImageView animationBackGround;
    TickerView distanceview;
    TickerView passedTimeView;
    TextView speedText;
    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        EventBus.getDefault().register(this);
        startRunButton = findViewById(R.id.startRunButton);
        stopRunButton = findViewById(R.id.stopRunButton);
        finishRunButton = findViewById(R.id.finishRunButton);
        toMapCard = findViewById(R.id.toMapCard);
        animationText = findViewById(R.id.text_animation);
        animationBackGround = findViewById(R.id.animationBackGround);
        constraintLayout_run = findViewById(R.id.constraintLayout_Run);
        distanceview = findViewById(R.id.distanceView_Run);
        speedText = findViewById(R.id.speedText_run);
        passedTimeView = findViewById(R.id.passedTime_run);
        passedTimeView.setCharacterLists(TickerUtils.provideNumberList());
        passedTimeView.setAnimationDuration(500);//设置动画持续时间
        distanceview.setCharacterLists(TickerUtils.provideNumberList());
        distanceview.setAnimationDuration(500);
        startRunButton.hide();
        finishRunButton.hide();
        //设置监听器
        startRunButton.setOnClickListener(this);
        stopRunButton.setOnClickListener(this);
        finishRunButton.setOnClickListener(this);
        toMapCard.setOnClickListener(this);
        startAnimation();
        finishRunButton.setListener(new ProgressButton.ProgressButtonFinishCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onCancel() {
            }
        });
        timeRunnable = new TimeRunnable();
        mHandler.post(timeRunnable);
        requestPermissions();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.stopRunButton: {
                changeButtonState();
                mHandler.removeCallbacks(timeRunnable);//取消处理
                break;
            }
            case R.id.startRunButton:{
                changeButtonState();
                mHandler.post(timeRunnable);//开始计时
                break;
            }
            case R.id.toMapCard:
                Log.d("","toMap");
                Intent intent = new Intent(this,RunningActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void changeButtonState(){
        if (isRun == true) {
            isRun = false;
            stopRunButton.hide();
            startRunButton.show();
            finishRunButton.show();
        } else if (isRun == false) {
            isRun = true;
            stopRunButton.show();
            startRunButton.hide();
            finishRunButton.hide();
        }
    }
    private void startAnimation(){
        constraintLayout_run.postDelayed(new Runnable() {
            @Override
            public void run() {
                constraintLayout_run.setVisibility(View.INVISIBLE);
            }
        },1000);
        //stopRunButton.setVisibility(View.INVISIBLE);
        ObjectAnimator back_animator0 = ObjectAnimator.ofFloat(animationBackGround,"scaleY",1,50);
        ObjectAnimator back_animator1 = ObjectAnimator.ofFloat(animationBackGround,"scaleX",1,50);
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(animationText, "scaleY", 1f, 3f, 5f,7f,5f,3f,1f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(animationText, "scaleX", 1f, 3f, 5f,7f,5f,3f,1f);
        AnimatorSet backAnimator = new AnimatorSet();
        backAnimator.play(back_animator0).with(back_animator1);
        backAnimator.setDuration(2500);
        backAnimator.start();
        AnimatorSet animatorSet0 = new AnimatorSet();
        animatorSet0.play(animator0).with(animator1);
        animatorSet0.setDuration(1000);
        animatorSet0.start();
        AnimatorSet animatorSet1 = animatorSet0.clone();
        AnimatorSet animatorSet2 = animatorSet1.clone();
        AnimatorSet animatorSet3 = animatorSet1.clone();
        animatorSet0.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationText.setText("2");
                animatorSet1.start();
            }
        });
        animatorSet1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationText.setText("1");
                animatorSet2.start();
            }
        });
        animatorSet2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationText.setText("Go!");
                animatorSet3.start();
            }
        });
        animatorSet3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationText.setVisibility(View.GONE);
                animationBackGround.setVisibility(View.GONE);
                stopRunButton.setVisibility(View.VISIBLE);
                constraintLayout_run.setVisibility(View.VISIBLE);
            }
        });
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)//监听粘性事件
    public void onEvent(MessageEvent event) {
        Log.d("onEvent_RunActivity", event.getFormattedPassedTime() + event.getDistance() + event.getSpeed());
        if (event.getDistance() != null && distanceview != null) {
            distanceview.setText(event.getDistance());
        }
        if (event.getFormattedPassedTime() != null && passedTimeView != null){
            passedTimeView.setText(TimeManager.formatseconds(event.getFormattedPassedTime()));
        }
        if(event.getSpeed()!=null && speedText!=null) {
            speedText.setText(event.getSpeed());
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //用于检测是否连按两下
    private long time = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - time > 1000)) {
                Toast.makeText(this, "结束跑步请点击完成按钮", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            }
        }
        return true;
    }
    Long passedSeconds = Long.valueOf(0);
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private class TimeRunnable implements Runnable {
        @Override
        public void run() {
            MessageEvent event = new MessageEvent();
            event.setFormattedPassedTime(passedSeconds);
            passedSeconds++;
            EventBus.getDefault().post(event);
            mHandler.postDelayed(this, 1000);
        }
    }
    private TimeRunnable timeRunnable = null;
    private void requestPermissions(){
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