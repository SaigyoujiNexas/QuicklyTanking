package com.example.modulescore.main.Run;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.LatLng;
import com.example.modulescore.main.Activities.RunActivity;
import com.example.modulescore.main.EventBus.MessageEvent;
import com.example.modulescore.main.R;
import com.example.modulespublic.common.utils.AppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LocationService extends Service implements LocationSource,AMapLocationListener {
    //开始时间
    Date startTime;
    String settedTime;
    int passedTime;


    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 0;
    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener mListener = null;//定位监听器



    public LocationService() {
    }
//    private Binder myBinder;
//    class MyBinder extends Binder{
//        public MyBinder() {
//        }
//        private void start
//    }
    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        Log.d("Service_onCreate","onCreate invoke");
        super.onCreate();
        initForeground();
        EventBus.getDefault().register(this);
    }
    /**
     * 每次通过startService()方法启动Service时都会被回调。
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("onStartCommand_invoke","");
        initLoc();
        initTime();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initForeground(){
        Intent intent = new Intent(this, RunActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notification; //创建服务对象
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ID", "NAME", manager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(channel);
            notification = new NotificationCompat.Builder(this).setChannelId("ID");
        } else {
            notification = new NotificationCompat.Builder(this);
        }

        notification.setContentTitle(AppUtil.getAppName(getApplicationContext()))
                .setContentText(AppUtil.getAppName(getApplicationContext())+"正在记录你的运动")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        Notification notification1 = notification.build();
        //startForground()接收两个参数，第一个是通知的id类似于notify()方法的第一个参数，第二个参数是构建出的Notification对象。
        // 调用startForeground()方法后会让MyService变成一个前台服务，并在系统状态栏显示出来。
        startForeground(1,notification1);
    }

    private void initLoc(){
        //初始化 SDK context 全局变量，指定 sdcard 路径，设置鉴权所需的KEY。
        //注：如果在创建地图之前使用BitmapDescriptorFactory的功能，则必须通过MapsInitializer.initialize(Context)来设置一个可用的context。
        MapsInitializer mapsInitializer = new MapsInitializer();
        //更新隐私合规状态,需要在初始化地图之前完成
        mapsInitializer.updatePrivacyShow(this, true, true);
        //更新同意隐私状态,需要在初始化地图之前完成
        mapsInitializer.updatePrivacyAgree(this,true);
        //定位发起端
        try {
            mLocationClient = new AMapLocationClient(this);
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

    private void initTime(){
        //初始化开始时间
        startTime = new Date();
        passedTime = (int) (new Date().getTime() - startTime.getTime()) / 1000;
        timeRunnable = new TimeRunnable();
        mHandler.post(timeRunnable);
        //当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    //绘制路线
    List<LatLng> path = new ArrayList<LatLng>();
    //此次运动总距离
    float distanceThisTime = 0;
    //平均速度
    float avgSpeed = 0;
    //上一次定位位置和当前定位位置，用于计算距离
    LatLng lastLatLng, nowLatLng;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    Long passedSeconds = Long.valueOf(0);
    int weight = 60;
    MessageEvent messageEvent = new MessageEvent();
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        String TAG = "Service_LocationChanged";
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                float nowSpeed = amapLocation.getSpeed();
                if (!isFirstLoc) {//如果不是第一次定位，则把上次定位信息传给lastLatLng，并且计算距离
                    Log.d(TAG,"avgSpeed"+avgSpeed);
                    lastLatLng = nowLatLng;
                }
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度
                Log.d(TAG,latitude+","+longitude);
                //新位置
                nowLatLng = new LatLng(latitude, longitude);
                //路径添加当前位置
                path.add(nowLatLng);
                //如果不是第一次定位，就计算距离
                if (!isFirstLoc) {
                    //float tempDistance = AMapUtils.calculateLineDistance(nowLatLng, lastLatLng);

                    //Log.d(TAG, "tempDistance"+tempDistance);
                    //计算总距离
                    distanceThisTime = getDistance(path);
                    //messageEvent.setDistance(decimalFormat.format(distanceThisTime / 1000.0));
                    messageEvent.setDistance(decimalFormat.format(getDistance(path)/1000.0));
                    //distance = getDistance(path);
                    //发送速度
                    nowSpeed = distanceThisTime/passedSeconds;
                    Log.d(TAG, "nowsepped"+distanceThisTime/passedSeconds);
                    if (nowSpeed == 0) {
                        messageEvent.setSpeed("--");
                        Log.d(TAG,"--");
                    } else {
                        messageEvent.setSpeed(decimalFormat.format(nowSpeed));
                        Log.d(TAG,"speed"+decimalFormat.format(nowSpeed));
                    }
                    messageEvent.setCalorie(decimalFormat.format(weight*distanceThisTime/1000*1.036));
                    messageEvent.setmPathPointsLine(path);
                    EventBus.getDefault().post(messageEvent);
                }else if(isFirstLoc){//如果是第一次，那么改isFirstLoc为false，则之后都不是第一次了
                    isFirstLoc = false;
                    Log.d(TAG,"FirstLoc0");
                }
                //点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(amapLocation);
            }else{
                Log.d(TAG,"ERROR:"+amapLocation.getErrorCode());
            }
        } else {
            Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_LONG).show();
        }
    }

    private LocationBinder locationBinder;
    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        locationBinder = new LocationBinder();
        return locationBinder;
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        Log.d("onDestroy_invoke","");
        super.onDestroy();
        mLocationClient.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEvent(MessageEvent event) {
        Log.d("onEvent_Service",event.isRunning()+"");
    };

    //计算距离
    private float getDistance(List<LatLng> list) {
        float distance = 0;
        if (list == null || list.size() == 0) {
            return distance;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            LatLng firstLatLng = list.get(i);
            LatLng secondLatLng = list.get(i + 1);
            double betweenDis = AMapUtils.calculateLineDistance(firstLatLng,
                    secondLatLng);
            distance = (float) (distance + betweenDis);
        }
        return distance;
    }
    // 激活定位
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    private TimeRunnable timeRunnable = null;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private class TimeRunnable implements Runnable {
        @Override
        public void run() {
            messageEvent.setFormattedPassedTime(passedSeconds);
            passedSeconds++;
            EventBus.getDefault().post(messageEvent);
            mHandler.postDelayed(this, 1000);
        }
    }
    // 停止定位
    @Override
    public void deactivate() {
        mListener = null;
    }
    public class LocationBinder extends Binder {
        public void stopLocation(){
            mLocationClient.stopLocation();
            mHandler.removeCallbacks(timeRunnable);
        }
        public void startLocation(){
            mLocationClient.startLocation();
            mHandler.post(timeRunnable);//开始计时
        }
        /**
         * 获取当前Service的实例
         *
         * @return
         */
        public LocationService getService() {
            return LocationService.this;
        }
    }
}