package com.example.modulescore.main.Target;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modulescore.main.R;

import java.util.ArrayList;
import java.util.List;

public class TargetViewHolder1 extends RecyclerView.ViewHolder {
    private final Context context;
    TextView TargetText;
    ScrollPickerView scrollPickerView;
    View line;
    ConstraintLayout constraintLayout;
    public TargetViewHolder1(@NonNull Context context, View itemView) {
        super(itemView);
        this.context = context;
        TargetText = itemView.findViewById(R.id.text_targetdistance_TargetDistance_item1);
        scrollPickerView = itemView.findViewById(R.id.scrollPickerView_target_item1);
        line = itemView.findViewById(R.id.line_target_item1);
        constraintLayout = itemView.findViewById(R.id.item_constraint_item1);
    }

    public void initView() {

        List<String> list = new ArrayList<>();
        String itemData0 = "00:10:00";
        String itemData1 = "00:20:00";
        String itemData2 = "00:30:00";
        String itemData3 = "00:40:00";
        String itemData4 = "00:50:00";
        String itemData5 = "01:00:00";
        String itemData6 = "01:10:00";
        String itemData7 = "01:20:00";
        String itemData8 = "01:30:00";
        String itemData9 = "01:40:00";
        String itemData10 = "01:50:00";
        String itemData11 = "02:00:00";
        String itemData12 = "02:10:00";
        String itemData13 = "02:20:00";
        list.add(itemData0);
        list.add(itemData1);
        list.add(itemData2);
        list.add(itemData3);
        list.add(itemData4);
        list.add(itemData5);
        list.add(itemData6);
        list.add(itemData7);
        list.add(itemData8);
        list.add(itemData9);
        list.add(itemData10);
        list.add(itemData11);
        list.add(itemData12);
        list.add(itemData13);
        ScrollPickerAdapter.ScrollPickerAdapterBuilder<String> builder =
                new ScrollPickerAdapter.ScrollPickerAdapterBuilder<String>(context,TargetText)
                        .setDataList(list)
                        .selectedItemOffset(5)
                        .visibleItemNumber(10)
                        .setDivideLineColor("#564F5E")
                        .setItemViewProvider(null)
                        .setOnClickListener(new ScrollPickerAdapter.OnClickListener() {
                            @Override
                            public void onSelectedItemClicked(View v) {
//                                String text = (String) v.getTag();
//                                if (text != null) {
//                                    Toast.makeText(SampleActivity.this, text, Toast.LENGTH_SHORT).show();
//                                }
                            }
                        });
        ScrollPickerAdapter mScrollPickerAdapter = builder.build();
        scrollPickerView.setAdapter(mScrollPickerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        scrollPickerView.setLayoutManager(linearLayoutManager);
        ConstraintLayout.LayoutParams cl = (ConstraintLayout.LayoutParams) line.getLayoutParams();
        line.postDelayed(new Runnable() {
            @Override
            public void run() {
                cl.setMargins(0,(scrollPickerView.getItemSelectedOffset())*scrollPickerView.getmItemHeight()+scrollPickerView.getmItemHeight()/2-3,0,0);
                Log.d("initView_holder", String.valueOf(scrollPickerView.getItemSelectedOffset()*scrollPickerView.getmItemHeight()));
                Log.d("initView_holder", String.valueOf(scrollPickerView.getItemSelectedOffset()));
                Log.d("initView_holder", String.valueOf(scrollPickerView.getmItemHeight()));
                line.setLayoutParams(cl);
                line.bringToFront();
            }
        },200);
    }
}