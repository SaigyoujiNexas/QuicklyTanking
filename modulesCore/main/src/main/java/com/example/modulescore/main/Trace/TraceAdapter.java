package com.example.modulescore.main.Trace;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.model.LatLng;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulescore.main.R;

import java.util.ArrayList;

public class TraceAdapter extends RecyclerView.Adapter {
    private RunningRecord selectedRecord;
    private Bundle savedInstanceState;
    private TraceActivity traceActivity;

    public TraceAdapter(RunningRecord selectedRecord,TraceActivity traceActivity,Bundle savedInstanceState) {
        this.selectedRecord = selectedRecord;
        this.savedInstanceState = savedInstanceState;
        this.traceActivity =traceActivity;
    }

    public enum ItemType {
        ITEM0, ITEM1
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TraceAdapter.ItemType.ITEM0.ordinal()) {
            return new TraceViewHolder0(selectedRecord,savedInstanceState,traceActivity,LayoutInflater.from(parent.getContext()).inflate(R.layout.trace_item0, parent, false));
        }else if(viewType == TraceAdapter.ItemType.ITEM1.ordinal()){
            Log.d("11111111","");
            return new TraceViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.trace_item1,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TraceViewHolder0){
            TraceViewHolder0 traceViewHolder0 = (TraceViewHolder0) holder;
            ArrayList<LatLng> pathPointsLine = (ArrayList<LatLng>) selectedRecord.getPathPointsLine();
            traceViewHolder0.addTrace(pathPointsLine.get(0),pathPointsLine.get(pathPointsLine.size()-1),pathPointsLine);
        }else if(holder instanceof TraceViewHolder1){
            TraceViewHolder1 traceViewHolder1 = (TraceViewHolder1) holder;
            traceViewHolder1.onBind(selectedRecord);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder instanceof TraceViewHolder0){
            TraceViewHolder0 holder0  = (TraceViewHolder0)holder;
            holder0.onDestory();
        }else if (holder instanceof TraceViewHolder1){
            TraceViewHolder1 holder1 = (TraceViewHolder1) holder;
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return ItemType.ITEM0.ordinal();        //获取某个枚举对象的位置索引值
        }else if(position == 1) {
            return ItemType.ITEM1.ordinal();
        }
        return 0;
    }
}
