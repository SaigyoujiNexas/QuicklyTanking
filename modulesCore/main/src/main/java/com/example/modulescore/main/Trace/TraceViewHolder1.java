package com.example.modulescore.main.Trace;


import android.view.View;

import androidx.annotation.NonNull;
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
import com.example.modulescore.main.DataBase.RunningRecord;
import com.example.modulescore.main.R;

import java.util.List;

public class TraceViewHolder1 extends RecyclerView.ViewHolder {

    private RunningRecord selectedRecord;
    public TraceViewHolder1(RunningRecord selectedRecord,@NonNull View itemView) {
        super(itemView);
        this.selectedRecord = selectedRecord;
    }

}