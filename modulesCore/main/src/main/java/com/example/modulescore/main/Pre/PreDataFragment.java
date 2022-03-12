package com.example.modulescore.main.Pre;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pre_data, container, false);
        linearLayout = view.findViewById(R.id.linearlayout_pre_data);
        new Thread(new Runnable() {
            @Override
            public void run() {
                runningRecords = MyDataBase.getsInstance(getActivity().getApplicationContext()).runningDao().loadAllRunningRecordss();
            }
        }).start();
        for(RunningRecord record: runningRecords){
            LinearAddView(record);
        }
        return view;
    }

    private void LinearAddView(RunningRecord record){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.runrecord_item,linearLayout,false);
        TextView startTimetext = view.findViewById(R.id.startTimetext_runrecord);
        TextView distancetext = view.findViewById(R.id.distancetext_runrecord);
        TextView durationtext = view.findViewById(R.id.duration_text_runrecorditem);
        TextView calorietext = view.findViewById(R.id.calorietext_runrecoritem);
        MyDataBase.getsInstance(getActivity().getApplicationContext()).runningDao().insertRunningRecord();
        startTimetext.setText(TimeManager.formatseconds(record.getStartTime()));
        distancetext.setText(record.getDistance());
        durationtext.setText(TimeManager.formatseconds(record.getRunningtime()));
        calorietext.setText(record.getCalorie());
    }
}