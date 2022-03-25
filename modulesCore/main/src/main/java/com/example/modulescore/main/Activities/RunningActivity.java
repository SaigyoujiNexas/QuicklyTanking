package com.example.modulescore.main.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.example.modulescore.main.Run.LocationService;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulescore.main.EventBus.MessageEvent;
import com.example.modulescore.main.R;
import com.example.modulespublic.common.utils.TimeManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;


public class RunningActivity extends AppCompatActivity implements  View.OnClickListener {

    final String TAG = "RunningActivity";
    MapView mapView;
    private AMap aMap;
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    //数据显示
    TextView tv_mapSpeed;

    TextView tv_passedTime;
    FloatingActionButton backButton;

    TickerView tv_mapDistance;

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    //此次运动总距离
    float distanceThisTime = 0;
    //平均速度
    float avgSpeed = 0;
    //上一次定位位置和当前定位位置，用于计算距离
    LatLng lastLatLng, nowLatLng;
    //LatLng:地理坐标基本数据结构
    LocationService.LocationBinder locationBinder;
    Long passedSeconds;
    int weight = 60;
    RunningRecord record = new RunningRecord();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    MessageEvent messageEvent = new MessageEvent();//跑步时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        final String TAG = "RunningActivity_onCreate";
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_running);
        //在activity执行onCreate时执行mapView.onCreate(savedInstanceState)，创建地图
        mapView = findViewById(R.id.map);
        tv_passedTime = findViewById(R.id.passedTime_running);
        mapView.onCreate(savedInstanceState);
        backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_mapSpeed = findViewById(R.id.speedText);
        tv_mapDistance = findViewById(R.id.distanceTicker);
        tv_mapDistance.setCharacterLists(TickerUtils.provideNumberList());
        tv_mapDistance.setAnimationDuration(500);

        Intent startRunIntent = new Intent(this,RunActivity.class);
        startActivity(startRunIntent);
        if(getIntent().getType()!= null && getIntent().getType().equals(TargetDistanceActivity.isTarget)){
            Log.d(TAG+"hasTarget","");
            checkTarget();
        }
        startLocationService();
        FinishRunReceiver finishRunReceiver = new FinishRunReceiver();
        IntentFilter intentFilter = new IntentFilter("finishRun");
        registerReceiver(finishRunReceiver, intentFilter);
        initService();
    }

    private void checkTarget(){
        final String TAG = "checkTargetTAG";
        SharedPreferences sharedPreferences = getSharedPreferences("target", Context.MODE_PRIVATE);
        Log.d(TAG,sharedPreferences.getString("targetString","000000"));
    }
    private void initService(){
        Intent bindIntent = new Intent(this,LocationService.class);
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
            Log.d("onServiceConnected_running",locationBinder.getService().toString());
            initMapUI();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private void initMapUI(){
        //初始化地图控制器对象
        aMap = mapView.getMap();
        //设置地图模式普通地图
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //设置定位源（locationSource）。
        aMap.setLocationSource(locationBinder.getService());
        //设置是否打开定位图层（myLocationOverlay）。
        aMap.setMyLocationEnabled(true);

        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        //设置定位按钮是否可见。
        settings.setMyLocationButtonEnabled(true);

        //定位小蓝点（当前位置）的绘制样式类。
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        // 设置圆形的边框颜色
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        //设置是否显示定位小蓝点，true 显示，false不显示。
        myLocationStyle.showMyLocation(true);
        //设置我的位置展示模式,定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
        myLocationStyle.myLocationType(myLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        //设置定位图层,我的位置图层（myLocationOverlay）的样式。
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));//设置地图缩放级别。
        aMap.moveCamera(CameraUpdateFactory.changeTilt(0));//设置地图倾斜度。
    }

    public void updateCamera(List<LatLng> path){
        final String TAG = "updateCamera";
        //绘制路径
        aMap.addPolyline(
                new PolylineOptions()
                        .addAll(path)
                        .width(20)
                        .color(ContextCompat.getColor(this, R.color.green)));
        //将地图移动到定位点
        //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(path.get(path.size()-1), 20));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(path.get(path.size()-1)));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)//监听粘性事件
    public void onEvent(MessageEvent event) {
        final String TAG = "onEvent_running";
        //Log.d("onEvent_RunningActivity",event.getFormattedPassedTime()+event.getDistance());
        //将持续秒数转化为mm:ss并显示到控件
        if (event.getFormattedPassedTime()!=null && tv_passedTime!=null) {
            passedSeconds = event.getFormattedPassedTime();
            tv_passedTime.setText(TimeManager.formatseconds(event.getFormattedPassedTime()));
        }
        if (event.getDistance()!=null && tv_mapDistance!=null) {
            tv_mapDistance.setText(event.getDistance());
        }
        //显示速度
        if(event.getSpeed()!=null && tv_mapSpeed!=null) {
            tv_mapSpeed.setText(event.getSpeed());
        }
//        if(event.isRunning()){
//            mapView.onResume();
//            //mLocationClient.startLocation();
//        }else if(!event.isRunning()){
//            mapView.onPause();
//            //mLocationClient.stopLocation();
//        }
        if(event.getmPathPointsLine()!=null){
            Log.d(TAG,"getmPathPointsLine"+event.getmPathPointsLine().size());
            updateCamera(event.getmPathPointsLine());
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default:
                break;
            case R.id.btn_back:

        }
    }
    private void startLocationService(){
        //在activity中启动自定义本地服务LocationService
        getApplicationContext().startService(new Intent(getApplicationContext(), LocationService.class));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        //在activity执行onDestroy时执行mapView.onDestroy()，销毁地图
        mapView.onDestroy();
        unbindService(serviceConnection);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        //在activity执行onResume时执行mapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        //在activity执行onPause时执行mapView.onPause ()，暂停地图的绘制
        //mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        //结束时停止更新位置
        //mLocationClient.stopLocation();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//        mapView.onSaveInstanceState(outState);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this,RunActivity.class));
        }
        return true;
    }
    public class FinishRunReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            RunningActivity.this.finish();
        }
    }
}