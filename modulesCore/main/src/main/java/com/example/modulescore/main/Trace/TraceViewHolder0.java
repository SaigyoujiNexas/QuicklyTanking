package com.example.modulescore.main.Trace;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.modulescore.main.UI.View.DataMap;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulescore.main.R;

import java.util.List;

public class TraceViewHolder0 extends RecyclerView.ViewHolder {
    private PolylineOptions polylineOptions;
    private Polyline polyline;
    private AMap aMap;
    private List<LatLng> latLngList;
    DataMap mapView;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption LocationOption;
    private RunningRecord selectedRecord;
    private Bundle savedInstanceState;

    private TraceActivity traceActivity;
    public TraceViewHolder0(RunningRecord selectedRecord,Bundle savedInstanceState,TraceActivity traceActivity,@NonNull View itemView) {
        super(itemView);
        mapView = itemView.findViewById(R.id.mapView_tracefragment0);
        this.selectedRecord = selectedRecord;
        this.savedInstanceState = savedInstanceState;
        this.traceActivity =traceActivity;
        initMap();
    }

    private void initMap(){

        mapView.onCreate(savedInstanceState);// 此方法必须重写
        mapView.onResume();
        //初始化地图控制器对象
        aMap = mapView.getMap();
        //设置地图模式普通地图
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //设置定位源（locationSource）。
        //aMap.setLocationSource(this);
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
    public void addTrace(LatLng startPoint, LatLng endPoint,
                          List<LatLng> pathList){
        polylineOptions = new PolylineOptions()
                .addAll(pathList)
                .width(20)
                .color(ContextCompat.getColor(traceActivity, R.color.green));//追加一批顶点到线段的坐标集合。
        aMap.addPolyline(polylineOptions);
        aMap.addMarker(new MarkerOptions().position(
                startPoint).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.startpoint)));
        aMap.addMarker(new MarkerOptions().position(
                endPoint).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.endpoint)));
        try {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));//设置地图缩放级别。
            //设置显示在规定屏幕范围内的地图经纬度范围。
            //设置经纬度范围和mapView边缘的空隙，单位像素。这个值适用于区域的四个边。
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(), 16));//设置显示在规定屏幕范围内的地图经纬度范围。
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LatLngBounds getBounds() {//经纬度划分的一个矩形区域。
        LatLngBounds.Builder b = LatLngBounds.builder();
        if (latLngList == null) {
            return b.build();
        }
        for (int i = 0; i < latLngList.size(); i++) {
//区域包含传入的坐标。区域将进行小范围延伸包含传入的坐标。 更准确来说，它会考虑向东或向西方向扩展（哪一种方法可能环绕地图），
//并选择最小扩展的方法。 如果两种方向得到的矩形区域大小相同，则会选择向东方向扩展。
            b.include(latLngList.get(i));
        }
        return b.build();
    }
    public void onDestory(){
        mapView.onDestroy();
    }
}
