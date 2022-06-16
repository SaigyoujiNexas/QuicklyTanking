package com.xupt.modulescore.main.Target;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modulescore.main.R;

public class TargetAdapter extends RecyclerView.Adapter {
    public enum ItemType {
        ITEM0, ITEM1,ITEM2,ITEM3
    }

    Context context;
    private ScrollPickerView scrollPickerView0;
    private ScrollPickerView scrollPickerView1;
    private ScrollPickerView scrollPickerView2;
    private ScrollPickerView scrollPickerView3;
    private int viewPagerPostion = 0;
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
            return new TargetViewHolder2(context,LayoutInflater.from(parent.getContext()).inflate(R.layout.target_item2,parent,false));
        }
        else if(viewType == ItemType.ITEM3.ordinal()){
            return new TargetViewHolder3(context,LayoutInflater.from(parent.getContext()).inflate(R.layout.target_item3,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TargetViewHolder0) {
            TargetViewHolder0 targetViewHolder0 = (TargetViewHolder0) holder;
            targetViewHolder0.initView();
            scrollPickerView0 = targetViewHolder0.getScrollPickerView();
            //scrollPickerView0.onScrolled(0,0);
            Log.i("onBindViewHolder0", "");
        } else if (holder instanceof TargetViewHolder1) {
            TargetViewHolder1 targetViewHolder1 = (TargetViewHolder1) holder;
            targetViewHolder1.initView();
            scrollPickerView1 = targetViewHolder1.getScrollPickerView();
            //scrollPickerView1.onScrolled(0,0);
            Log.i("onBindViewHolder1", "");
        } else if (holder instanceof TargetViewHolder2) {
            TargetViewHolder2 targetViewHolder2 = (TargetViewHolder2) holder;
            targetViewHolder2.initView();
            scrollPickerView2 = targetViewHolder2.getScrollPickerView();
            //scrollPickerView2.onScrolled(0,0);
            Log.i("onBindViewHolder2", "");
        } else if (holder instanceof TargetViewHolder3) {
            TargetViewHolder3 targetViewHolder3 = (TargetViewHolder3) holder;
            targetViewHolder3.initView();
            scrollPickerView3 = targetViewHolder3.getScrollPickerView();
            //scrollPickerView3.onScrolled(0,0);
            Log.i("onBindViewHolder3", "");
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        String TAG = "getItemViewType";
        if(position == 0) {
            Log.d(TAG,"0");
            return ItemType.ITEM0.ordinal();        //获取某个枚举对象的位置索引值
        }else if(position == 1){
            Log.d(TAG,"1");
            return ItemType.ITEM1.ordinal();
        }else if(position == 2){
            Log.d(TAG,"2");
            return ItemType.ITEM2.ordinal();
        }else if(position == 3){
            Log.d(TAG,"3");
            return ItemType.ITEM3.ordinal();
        }
        return 0;
    }

    public int getViewPagerPostion() {
        return viewPagerPostion;
    }

    public void setViewPagerPostion(int viewPagerPostion) {
        this.viewPagerPostion = viewPagerPostion;
    }


    public ScrollPickerView getScrollPickerView0() {
        return scrollPickerView0;
    }

    public ScrollPickerView getScrollPickerView1() {
        return scrollPickerView1;
    }

    public ScrollPickerView getScrollPickerView2() {
        return scrollPickerView2;
    }

    public ScrollPickerView getScrollPickerView3() {
        return scrollPickerView3;
    }
}
