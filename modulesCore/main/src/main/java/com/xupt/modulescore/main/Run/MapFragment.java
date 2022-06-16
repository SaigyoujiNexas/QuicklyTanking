package com.xupt.modulescore.main.Run;

import static android.content.Context.BIND_AUTO_CREATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.xupt.modulescore.main.EventBus.MessageEvent;
import com.example.modulescore.main.R;
import com.xupt.safeAndRun.modulespublic.common.utils.TimeManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MapFragment extends Fragment implements View.OnClickListener{

    final String TAG = "MapFragment";
    MapView mapView;
    private AMap aMap;
    //数据显示
    TextView tv_mapSpeed;

    TextView tv_passedTime;
    FloatingActionButton backButton;

    TickerView tv_mapDistance;

    //LatLng:地理坐标基本数据结构
    LocationService.LocationBinder locationBinder;
    Long passedSeconds;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        startLocationService();
        initService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);
        //在activity执行onCreate时执行mapView.onCreate(savedInstanceState)，创建地图

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.bringToFront();
        tv_passedTime = view.findViewById(R.id.passedTime_running);
        backButton = view.findViewById(R.id.btn_back);
        backButton.setOnClickListener(this);
        tv_mapSpeed = view.findViewById(R.id.speedText);
        tv_mapDistance = view.findViewById(R.id.distanceTicker);
        tv_mapDistance.setCharacterLists(TickerUtils.provideNumberList());
        tv_mapDistance.setAnimationDuration(500);

//        if(getActivity().getIntent().getType()!= null && getActivity().getIntent().getType().equals(TargetDistanceActivity.)){
//            Log.d(TAG+"hasTarget","");
//            checkTarget();
//        }

        return view;
    }



//    private void checkTarget(){
//        final String TAG = "checkTargetTAG";
//        SharedPreferences sharedPreferences = getSharedPreferences("target", Context.MODE_PRIVATE);
//        Log.d(TAG,sharedPreferences.getString("targetString","000000"));
//    }
    private void initService(){
        Log.d("initService","");
        Intent bindIntent = new Intent(getActivity(),LocationService.class);
        //然后调用bindService()方法将MainActivity和MyService进行绑定。
        //bindService()方法接收3个参数，
         //第一个参数是Intent对象，第二个是创建出的ServiceConnection实例，
        //第三个是标志位，传入BIND_AUTO_CREATE表示Activity和Service绑定后自动创建服务。
        //会使得MyService中的onCreate()方法得到执行，但onStartCommand()方法不会得到执行。
        getActivity().bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);
        Log.d("initService","");
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
            locationBinder = null;
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
                        .color(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.green)));
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
//        }else if(!event.isRunning()){
//            mapView.onPause();
//        }
        if(event.getmPathPointsLine()!=null){
            Log.d(TAG,"getmPathPointsLine"+event.getmPathPointsLine().size());
            updateCamera(event.getmPathPointsLine());
        }
    };

    @Override
    public void onClick(View view) {
        var id = view.getId();
        if(id == R.id.btn_back){
                RunActivity runActivity = (RunActivity) getActivity();
                runActivity.hideMapFragment();
        }
    }

    private void startLocationService(){
        //在activity中启动自定义本地服务LocationService
        getActivity().getApplicationContext().startService(new Intent(getActivity().getApplicationContext(), LocationService.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        //在activity执行onDestroy时执行mapView.onDestroy()，销毁地图
        getActivity().unbindService(serviceConnection);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        //在activity执行onResume时执行mapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        //在activity执行onPause时执行mapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        //结束时停止更新位置
        //mLocationClient.stopLocation();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Log.d("onCreateAnimation",enter+",0,"+transit);
        if(enter == true){
            Log.d("onCreateAnimation","1");
            return AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_right_in);
        }else if(enter == false){
            Log.d("onCreateAnimation","2");
            return AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_right_out);
        }
        return null;

    }


}