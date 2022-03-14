package com.example.modulescore.main.Trace;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.modulescore.main.R;

import java.util.List;

public class TraceViewHolder0 extends RecyclerView.ViewHolder {
    private PolylineOptions polylineOptions;
    private Polyline polyline;
    private AMap aMap;
    private List<LatLng> latLngList;
    TextureMapView mapView;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption LocationOption;

    public TraceViewHolder0(@NonNull View itemView) {
        super(itemView);
        mapView = itemView.findViewById(R.id.mapView_tracefragment0);
        aMap = mapView.getMap();
    }

    private void addTrace(LatLng startPoint, LatLng endPoint,
                          List<LatLng> originList){
        polylineOptions.addAll(originList);//追加一批顶点到线段的坐标集合。
        aMap.addPolyline(polylineOptions);
        aMap.addMarker(new MarkerOptions().position(
                startPoint).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.startpoint)));
        aMap.addMarker(new MarkerOptions().position(
                endPoint).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.endpoint)));
        try {
            //设置显示在规定屏幕范围内的地图经纬度范围。
            //设置经纬度范围和mapView边缘的空隙，单位像素。这个值适用于区域的四个边。
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(), 16));
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
}
