package com.example.modulescore.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TargetAdapter extends RecyclerView.Adapter {
    public enum ItemType {
        ITEM0, ITEM1,ITEM2,ITEM3
    }

    Context context;
    public TargetAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ItemType.ITEM0.ordinal()) {
            return new TargetViewHolder0(context, LayoutInflater.from(parent.getContext()).inflate(R.layout.target_item0, parent, false));

        }else if(viewType == ItemType.ITEM1.ordinal()){
            return new TargetViewHolder1(context,LayoutInflater.from(parent.getContext()).inflate(R.layout.target_item1,parent,false));
        }
        else if(viewType == ItemType.ITEM2.ordinal()){
            return new My.TargetViewHolder2(context,LayoutInflater.from(parent.getContext()).inflate(R.layout.target_item2,parent,false));
        }
        else if(viewType == ItemType.ITEM3.ordinal()){
            return new My.TargetViewHolder3(context,LayoutInflater.from(parent.getContext()).inflate(R.layout.target_item3,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TargetViewHolder0) {
            TargetViewHolder0 targetViewHolder0 = (TargetViewHolder0) holder;
            targetViewHolder0.initView();
            Log.d("onBindViewHolder0", "");
        } else if (holder instanceof TargetViewHolder1) {
            TargetViewHolder1 targetViewHolder1 = (TargetViewHolder1) holder;
            targetViewHolder1.initView();
            Log.d("onBindViewHolder1", "");
        } else if (holder instanceof My.TargetViewHolder2) {
            My.TargetViewHolder2 targetViewHolder2 = (My.TargetViewHolder2) holder;
            targetViewHolder2.initView();
            Log.d("onBindViewHolder2", "");
        } else if (holder instanceof My.TargetViewHolder3) {
            My.TargetViewHolder3 targetViewHolder3 = (My.TargetViewHolder3) holder;
            targetViewHolder3.initView();
            Log.d("onBindViewHolder3", "");
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return ItemType.ITEM0.ordinal();        //获取某个枚举对象的位置索引值
        }else if(position == 1){
            return ItemType.ITEM1.ordinal();
        }else if(position == 2){
            return ItemType.ITEM2.ordinal();
        }else if(position == 3){
            return ItemType.ITEM3.ordinal();
        }
        return 0;
    }
}
