package com.example.modulescore.main.Trace;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modulescore.main.R;
import com.example.modulescore.main.Util.TimeManager;
import com.example.modulespublic.common.base.RunningRecord;

public class TraceViewHolder1 extends RecyclerView.ViewHolder {

    TextView distancetext;
    TextView speedtext;
    TextView duartiontext;
    TextView starttimetext;
    TextView calorietext;
//    TextView distancetext;
//    TextView distancetext;

    public TraceViewHolder1(@NonNull View itemView) {
        super(itemView);
        calorietext = itemView.findViewById(R.id.calorietext_trace_item1);
        starttimetext = itemView.findViewById(R.id.starttimetext_trace_item1);
        duartiontext = itemView.findViewById(R.id.duartiontext_trace_item1);
        speedtext = itemView.findViewById(R.id.speedtext_trace_item1);
        distancetext = itemView.findViewById(R.id.distancetext_trace_item1);
    }

    public void onBind(RunningRecord selectedRecord) {
        calorietext.setText(selectedRecord.getCalorie()+"卡");
        starttimetext.setText(TimeManager.formatDates(selectedRecord.getStartTime()));
        duartiontext.setText(TimeManager.formatseconds(selectedRecord.getRunningtime()));
        speedtext.setText(selectedRecord.getSpeed());
        distancetext.setText(selectedRecord.getDistance());
    }
}