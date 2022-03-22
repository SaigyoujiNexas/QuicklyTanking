package com.example.modulescore.main.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulescore.main.EventBus.MessageEvent;
import com.example.modulescore.main.R;
import com.example.modulescore.main.Util.TimeManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RunningActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, View.OnClickListener {

    private static final String TAG = "RunningActivity";

    MapView mapView;
    private AMap aMap;
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener mListener = null;//定位监听器
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
    //绘制路线
    List<LatLng> path = new ArrayList<LatLng>();

    Long passedSeconds;
    int weight = 60;
    RunningRecord record = new RunningRecord();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    MessageEvent messageEvent = new MessageEvent();//跑步时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
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
        initMapUI();
        initLoc();
        Intent startRunIntent = new Intent(this,RunActivity.class);
        startActivity(startRunIntent);
        if(getIntent().getType()!= null && getIntent().getType().equals(TargetDistanceActivity.isTarget)){
            Log.d(TAG+"hasTarget","");
            checkTarget();
        }
        FinishRunReceiver finishRunReceiver = new FinishRunReceiver();
        IntentFilter intentFilter = new IntentFilter("finishRun");
        registerReceiver(finishRunReceiver, intentFilter);
    }

    private void checkTarget(){
        final String TAG = "checkTargetTAG";
        SharedPreferences sharedPreferences = getSharedPreferences("target", Context.MODE_PRIVATE);
        Log.d(TAG,sharedPreferences.getString("targetString","000000"));
    }

    private void initMapUI(){
        //初始化地图控制器对象
        aMap = mapView.getMap();
        //设置地图模式普通地图
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //设置定位源（locationSource）。
        aMap.setLocationSource(this);
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
    }
    //定位
    private void initLoc() {
        //初始化 SDK context 全局变量，指定 sdcard 路径，设置鉴权所需的KEY。
        //注：如果在创建地图之前使用BitmapDescriptorFactory的功能，则必须通过MapsInitializer.initialize(Context)来设置一个可用的context。
        MapsInitializer mapsInitializer = new MapsInitializer();
        //更新隐私合规状态,需要在初始化地图之前完成
        mapsInitializer.updatePrivacyShow(this, true, true);
        //更新同意隐私状态,需要在初始化地图之前完成
        mapsInitializer.updatePrivacyAgree(this,true);
        //定位发起端
        try {
            mLocationClient = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //当定位源获取的位置信息发生变化时回调此接口方法。
    //AMapLocation定位信息类。定位完成后的位置信息。
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        String TAG = "RunningActivity_LocationChanged";
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                float nowSpeed = amapLocation.getSpeed();
                if (!isFirstLoc) {//如果不是第一次定位，则把上次定位信息传给lastLatLng，并且计算距离
                    Log.d(TAG,"Speed"+avgSpeed);
                        lastLatLng = nowLatLng;
                }
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度
                Log.d(TAG,latitude+","+longitude);
                //新位置
                nowLatLng = new LatLng(latitude, longitude);

                //路径添加当前位置
                path.add(nowLatLng);
                //绘制路径
                aMap.addPolyline(
                        new PolylineOptions()
                                .addAll(path)
                                .width(20)
                                .color(ContextCompat.getColor(this, R.color.green)));
                //如果不是第一次定位，就计算距离
                if (!isFirstLoc) {
                    float tempDistance = AMapUtils.calculateLineDistance(nowLatLng, lastLatLng);
                    //计算总距离
                    distanceThisTime += tempDistance;
                    messageEvent.setDistance(decimalFormat.format(distanceThisTime / 1000.0));
                    //发送速度
                    nowSpeed = distanceThisTime/passedSeconds;
                    if (nowSpeed == 0) {
                        messageEvent.setSpeed("--");
                    } else {
                        messageEvent.setSpeed(decimalFormat.format(nowSpeed));
                    }
                    messageEvent.setCalorie(decimalFormat.format(weight*distanceThisTime/1000*1.036));
                    messageEvent.setmPathPointsLine(path);
                    EventBus.getDefault().post(messageEvent);
                }else if(isFirstLoc){//如果是第一次，那么改isFirstLoc为false，则之后都不是第一次了
                    //设置缩放级别
                    Log.d(TAG,"FirstLoc");
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(18));//设置地图缩放级别。
                    aMap.moveCamera(CameraUpdateFactory.changeTilt(0));//设置地图倾斜度。
                    isFirstLoc = false;
                    Log.d(TAG,"FirstLoc0");
                }
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(nowLatLng));//设置地图的中心点。
                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(amapLocation);
                Log.d(TAG,"FirstLoc00");
            }else{
                Log.d(TAG,"ERROR:"+amapLocation.getErrorCode());
            }
        } else {
            Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_LONG).show();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)//监听粘性事件
    public void onEvent(MessageEvent event) {
        Log.d("onEvent_RunningActivity",event.getFormattedPassedTime()+event.getDistance());
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
        if(event.isRunning()){
            mapView.onResume();
            mLocationClient.startLocation();
        }else if(!event.isRunning()){
            mapView.onPause();
            mLocationClient.stopLocation();
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
    // 激活定位
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    // 停止定位
    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        //在activity执行onDestroy时执行mapView.onDestroy()，销毁地图
//        mapView.onDestroy();
//        mLocationClient.onDestroy();
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