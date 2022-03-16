package com.example.modulescore.main.Pre;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.modulespublic.common.base.MyDataBase;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulescore.main.R;
import com.example.modulescore.main.Trace.TraceActivity;
import com.example.modulescore.main.Util.TimeManager;

import java.text.SimpleDateFormat;

public class PreDataFragment extends Fragment implements View.OnClickListener{
    public PreDataFragment() {
    }
    LinearLayout linearLayout;
    RunningRecord[] runningRecords;
    PreHandler preHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preHandler = new PreHandler(Looper.getMainLooper(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pre_data_item0, container, false);
        linearLayout = view.findViewById(R.id.linearlayout_pre_data);
        QueryAllRunningRecords();
        return view;
    }

    private void QueryAllRunningRecords(){
        final String TAG = "QueryAllRunningRecordsTAG";
        new Thread(new Runnable() {
            @Override
            public void run() {
                runningRecords = MyDataBase.getsInstance(getActivity().getApplicationContext()).runningDao().loadAllRunningRecordss();
                Log.d(TAG, String.valueOf(runningRecords.length));
                Message message = new Message();
                message.what = PreHandler.finishProgress;
                preHandler.sendMessage(message);
            }
        }).start();
    }
    public void RefreshDataItem(){
        final String TAG = "RefreshDataItem";
        Log.d(TAG, String.valueOf(runningRecords.length));
        for(RunningRecord record: runningRecords){
            LinearAddView(record);
        }
    }
    private void LinearAddView(RunningRecord record){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.runrecord_item,linearLayout,false);
        TextView startTimetext = view.findViewById(R.id.startTimetext_runrecord);
        TextView distancetext = view.findViewById(R.id.distancetext_runrecord);
        TextView durationtext = view.findViewById(R.id.duration_text_runrecorditem);
        TextView calorietext = view.findViewById(R.id.calorietext_runrecoritem);
        TextView speedtext = view.findViewById(R.id.speedtext_runrecoritem);
        TextView recordDateText = view.findViewById(R.id.runrecord_Date);
        SimpleDateFormat yearFormat = new SimpleDateFormat ("yyyy年");
        SimpleDateFormat dateFormat = new SimpleDateFormat ("MM月dd日");
        SimpleDateFormat minuteFormat = new SimpleDateFormat ("hh:mm");
        recordDateText.setText(dateFormat.format(record.getStartTime()));
        startTimetext.setText(minuteFormat.format(record.getStartTime()));
        distancetext.setText(record.getDistance()+","+record.getId());
        durationtext.setText(TimeManager.formatseconds(record.getRunningtime()));
        calorietext.setText(record.getCalorie());
        speedtext.setText(record.getSpeed());
        linearLayout.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TraceActivity.class);
                intent.setType(String.valueOf(record.getId()));
                startActivity(intent);
            }
        });
        Log.d("LinearAddView","FINISH");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
}