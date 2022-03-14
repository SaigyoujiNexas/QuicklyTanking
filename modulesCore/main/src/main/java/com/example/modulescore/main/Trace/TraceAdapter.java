package com.example.modulescore.main.Trace;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modulescore.main.R;
import com.example.modulescore.main.Target.TargetAdapter;
import com.example.modulescore.main.Target.TargetViewHolder0;
import com.example.modulescore.main.Target.TargetViewHolder1;
import com.example.modulescore.main.Target.TargetViewHolder2;
import com.example.modulescore.main.Target.TargetViewHolder3;

public class TraceAdapter extends RecyclerView.Adapter {
    public enum ItemType {
        ITEM0, ITEM1
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TraceAdapter.ItemType.ITEM0.ordinal()) {
            return new TraceViewHolder0( LayoutInflater.from(parent.getContext()).inflate(R.layout.trace_item0, parent, false));
        }else if(viewType == TraceAdapter.ItemType.ITEM1.ordinal()){
            return new TraceViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.target_item1,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
