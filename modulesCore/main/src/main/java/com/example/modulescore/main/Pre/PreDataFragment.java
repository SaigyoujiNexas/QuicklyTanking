package com.example.modulescore.main.Pre;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.modulescore.main.DataBase.MyDataBase;
import com.example.modulescore.main.DataBase.RunningRecord;
import com.example.modulescore.main.R;
import com.example.modulescore.main.Util.TimeManager;

public class PreDataFragment extends Fragment {
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
        final String TAG = "PreDataFragmentonCreateViewTAG";
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pre_data, container, false);
        linearLayout = view.findViewById(R.id.linearlayout_pre_data);
        Handler handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                RefreshDataItem();
            }
        };
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
        return view;
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
        //startTimetext.setText(TimeManager.formatseconds(record.getStartTime()));
        distancetext.setText(record.getDistance());
        durationtext.setText(TimeManager.formatseconds(record.getRunningtime()));
        calorietext.setText(record.getCalorie());
        linearLayout.addView(view);
        Log.d("LinearAddView","FINISH");
    }

}