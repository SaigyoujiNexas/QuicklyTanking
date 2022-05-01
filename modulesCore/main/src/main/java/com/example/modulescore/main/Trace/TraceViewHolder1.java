package com.example.modulescore.main.Trace;


import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modulescore.main.R;
import com.example.modulespublic.common.base.record;
import com.example.modulespublic.common.utils.TimeManager;
import com.example.modulespublic.common.base.RunningRecord;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        final String TAG = "onBindTAG";
        calorietext.setText(selectedRecord.getCalorie()+"卡");

        SimpleDateFormat yearFormat = new SimpleDateFormat ("yyyy年");
        SimpleDateFormat dateFormat = new SimpleDateFormat ("MM月dd日");
        SimpleDateFormat minuteFormat = new SimpleDateFormat ("hh:mm");
        long milliSecond = Long.parseLong(selectedRecord.getStartTime());
        Date date = new Date();
        date.setTime(milliSecond);
        Log.d(TAG,""+selectedRecord.getStartTime());
        starttimetext.setText(dateFormat.format(milliSecond));
        duartiontext.setText(TimeManager.formatseconds(selectedRecord.getRunningtime()));
        speedtext.setText(selectedRecord.getSpeed());
        distancetext.setText(String.valueOf(selectedRecord.getDistance()));
    }
}