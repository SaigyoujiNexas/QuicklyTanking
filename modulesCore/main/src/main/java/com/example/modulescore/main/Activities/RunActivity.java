package com.example.modulescore.main.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.amap.api.maps.model.LatLng;
import com.example.modulescore.main.Run.LocationService;
import com.example.modulespublic.common.base.MyDataBase;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulescore.main.EventBus.MessageEvent;
import com.example.modulescore.main.UI.View.ProgressButton;
import com.example.modulescore.main.R;
import com.example.modulespublic.common.utils.TimeManager;
import com.example.modulespublic.common.net.BaseResponse;
import com.example.modulespublic.common.net.GetRequest_Interface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RunActivity extends AppCompatActivity implements View.OnClickListener {

    public GetRequest_Interface getRequestInterface;

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
    RunningRecord record = new RunningRecord();
    TextView calorieText;
    List<LatLng> mPathPointsLine;
    //初始化开始时间
    Date startTime = new Date();
    Long passedSeconds;
    MessageEvent event = new MessageEvent();
    LocationService.LocationBinder locationBinder;
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
        calorieText = findViewById(R.id.consumedCalories);
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
        event.setRunning(true);
        EventBus.getDefault().post(event);
        initService();
        finishRunButton.setListener(new ProgressButton.ProgressButtonFinishCallback() {
            @Override
            public void onFinish() {
                final String TAG = "FINISH_RUNNING";
                Log.d(TAG,"start finish");
                record.setId(Long.valueOf(001));
                record.setUsername("1");
                record.setCalorie((String) calorieText.getText());
                record.setDistance( distanceview.getText());
                record.setRunningtime(passedSeconds);
                record.setSpeed((String) speedText.getText());
                record.setPathPointsLine(mPathPointsLine);
                //record.setStartTime(startTime);
                record.setStartTime(startTime.getTime());

                FinishRunReceiver finishRunReceiver = new FinishRunReceiver();
                IntentFilter intentFilter = new IntentFilter("finishRun");
                registerReceiver(finishRunReceiver, intentFilter);
                Intent intent = new Intent();
                intent.setAction("finishRun");
                sendBroadcast(intent);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyDataBase.getsInstance(getApplicationContext()).runningDao().insertRunningRecord(record);
                        Log.d(TAG,record.toString());
                        Log.d(TAG+"length", String.valueOf(MyDataBase.getsInstance(getApplicationContext()).runningDao().loadAllRunningRecordss().length));
                        String baseUrl = "http://116.62.180.44:8080/";
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
                        Call<BaseResponse<RunningRecord>> call = request.postRuuningRecord(record);//获得call对象
                        call.enqueue(new Callback<BaseResponse<RunningRecord>>() {
                            @Override
                            public void onResponse(Call<BaseResponse<RunningRecord>> call, Response<BaseResponse<RunningRecord>> response) {
                                //assert response.body() != null;
                                Log.d(TAG,"body:"+response.body()+",errorBody:"+response.errorBody()+",message:"+response.message()+",tostring:"+response.toString());

                            }

                            @Override
                            public void onFailure(Call<BaseResponse<RunningRecord>> call, Throwable t) {
                                Log.d(TAG,"Retrofit_onFailure "+t.toString()+t);
                                Toast.makeText(RunActivity.this, "保存数据库时出现错误..", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }).start();
                finish();

                //Observable<BaseResponse<RunningRecord>> observable = getRequestInterface.postRuuningRecord(record);
//                RequestModel.Companion.request(getRequestInterface.postRuuningRecord(record),RunActivity.this, new NetCallback<RunningRecord>() {
//                    @Override
//                    public void onSuccess(RunningRecord data) {
//                        Log.d("RunActivityRetrofit:1",data.toString());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable throwable) {
//
//                    }
//                });
            }

            @Override
            public void onCancel() {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startRunButton:{
                changeButtonState();
                locationBinder.startLocation();
                event.setRunning(true);
                EventBus.getDefault().post(event);
                break;
            }
            case R.id.stopRunButton: {
                changeButtonState();
                locationBinder.stopLocation();
                event.setRunning(false);
                EventBus.getDefault().post(event);
                break;
            }
            case R.id.toMapCard:
                Log.d("","toMap");
                Intent intent = new Intent(this, RunningActivity.class);
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
        if(event.getRunningRecord()!=null){
            record = event.getRunningRecord();
        }
        if(event.getCalorie()!=null) {
            calorieText.setText(event.getCalorie());
        }
        if(event.getmPathPointsLine()!=null){
            mPathPointsLine = event.getmPathPointsLine();
        }
        if (event.getFormattedPassedTime()!=null && passedTimeView !=null) {
            passedSeconds = event.getFormattedPassedTime();
            passedTimeView .setText(TimeManager.formatseconds(event.getFormattedPassedTime()));
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
    private void initService(){
        Intent bindIntent = new Intent(this, LocationService.class);
        //然后调用bindService()方法将MainActivity和MyService进行绑定。
//bindService()方法接收3个参数，
//第一个参数是Intent对象，第二个是创建出的ServiceConnection实例，
//第三个是标志位，传入BIND_AUTO_CREATE表示Activity和Service绑定后自动创建服务。
//会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会得到执行。
        bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);
    }
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            locationBinder = (LocationService.LocationBinder) iBinder;
            Log.d("onServiceConnected_Run",locationBinder.getService().toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    public class FinishRunReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            RunActivity.this.finish();
        }
    }
}