package com.example.modulescore.main;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;

public class RunActivity extends BaseActivity implements View.OnClickListener {
    FloatingActionButton startRunButton;
    FloatingActionButton stopRunButton;
    ProgressButton finishRunButton;
    CardView toMapCard;
    ConstraintLayout constraintLayout_run;
    boolean isRun = true;
    TextView animationText;
    ImageView animationBackGround;
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
        //ColorStateList stopColor = ContextCompat.getColorStateList(getApplicationContext(), R.color.red);
        //isRunColor = ContextCompat.getColorStateList(getApplicationContext(), R.color.green);
    }
    private void initLoc(){
        //ACCESS_FINE_LOCATION通过WiFi或移动基站的方式获取用户错略的经纬度信息，定位精度大概误差在30~1500米
        //ACCESS_FINE_LOCATION，通过GPS芯片接收卫星的定位信息，定位精度达10米以内
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default: {
                Log.d("11111", "!00");
                break;
            }
            case R.id.stopRunButton:
            case R.id.startRunButton:{
                Log.d("11111", "!11");
                if (isRun == true) {
                    isRun = false;
                    Log.d("11111", "!22");
                    //startRunButton.setBackgroundTintList(stopColor);
                    stopRunButton.hide();
                    startRunButton.show();
                    finishRunButton.show();
                } else if (isRun == false) {
                    isRun = true;
                    Log.d("11111", "!33");
                    stopRunButton.show();
                    startRunButton.hide();
                    finishRunButton.hide();
                }
                break;
            }
            case R.id.finishRunButton:
                break;
            case R.id.toMapCard:
                Intent intent = new Intent(this, RunningActivity.class);
                startActivity(intent);
                break;
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
                Toast.makeText(this, "再按一次返回主界面", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}