package com.example.modulescore.main;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TargetViewHolder3 extends RecyclerView.ViewHolder {
    private final Context context;
    TextView TargetText;
    ScrollPickerView scrollPickerView;
    View line;
    ConstraintLayout constraintLayout;
    public TargetViewHolder3(@NonNull Context context, View itemView) {
        super(itemView);
        this.context = context;
        TargetText = itemView.findViewById(R.id.text_targetdistance_TargetDistance_item3);
        scrollPickerView = itemView.findViewById(R.id.scrollPickerView_target_item3);
        line = itemView.findViewById(R.id.line_target_item3);
        constraintLayout = itemView.findViewById(R.id.item_constraint_item3);
    }

    public void initView() {

        List<String> list = new ArrayList<>();
        String itemData0 = "02'00\"";
        String itemData1 = "02'10\"";
        String itemData2 = "02'20\"";
        String itemData3 = "02'30\"";
        String itemData4 = "02'40\"";
        String itemData5 = "02'50\"";
        String itemData6 = "03'00\"";
        String itemData7 = "03'10\"";
        String itemData8 = "03'20\"";
        String itemData9 = "03'30\"";
        String itemData10 = "03'40\"";
        String itemData11 = "03'50\"";
        String itemData12 = "04'00\"";
        String itemData13 = "04'10\"";
        String itemData14 = "04'20\"";
        String itemData15 = "04'30\"";
        String itemData16 = "04'40\"";
        String itemData17 = "04'50\"";
        String itemData18 = "05'00\"";
        String itemData19 = "05'10\"";
        String itemData20 = "05'20\"";
        String itemData21 = "05'30\"";
        String itemData22 = "05'40\"";
        String itemData23 = "05'50\"";
        String itemData24 = "06'10\"";
        String itemData25 = "06'20\"";
        String itemData26 = "06'30\"";
        String itemData27 = "06'40\"";
        String itemData28 = "06'50\"";
        String itemData29 = "07'00\"";
        String itemData30 = "07'10\"";
        String itemData31 = "07'20\"";
        String itemData32 = "07'30\"";
        String itemData33 = "07'40\"";
        String itemData34 = "07'50\"";
        String itemData35 = "08'00\"";

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
        list.add(itemData14);
        list.add(itemData15);
        list.add(itemData16);
        list.add(itemData17);
        list.add(itemData18);
        list.add(itemData19);
        list.add(itemData20);
        list.add(itemData21);
        list.add(itemData22);
        list.add(itemData23);
        list.add(itemData24);
        list.add(itemData25);
        list.add(itemData26);
        list.add(itemData27);
        list.add(itemData28);
        list.add(itemData29);
        list.add(itemData30);
        list.add(itemData31);
        list.add(itemData32);
        list.add(itemData33);
        list.add(itemData34);
        list.add(itemData35);
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