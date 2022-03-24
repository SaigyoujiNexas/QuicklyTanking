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
import android.widget.Toast;

import com.example.modulescore.main.Activities.RunActivity;
import com.example.modulespublic.common.base.MyDataBase;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulescore.main.R;
import com.example.modulescore.main.Trace.TraceActivity;
import com.example.modulescore.main.Util.TimeManager;
import com.example.modulespublic.common.base.record;
import com.example.modulespublic.common.net.BaseResponse;
import com.example.modulespublic.common.net.GetRequest_Interface;
import com.example.modulespublic.common.net.Request;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreDataFragment extends Fragment implements View.OnClickListener{
    public PreDataFragment() {
    }
    LinearLayout linearLayout;
    RunningRecord[] runningRecords;
    PreHandler preHandler;
    String message;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preHandler = new PreHandler(Looper.getMainLooper(),this);
        requestAllRunningRecords();
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
    private void requestAllRunningRecords(){
        final String TAG = "requestRunningRecordsTAG";
        String baseUrl = "http://116.62.180.44:8080/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Request request1 = new Request();
        request1.setUsername("1");
        Call<Object> call = request.getAllRunningRecords(request1);//获得call对象
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                //assert response.body() != null;
//                String jsonStr = new String(response.body());//把原始数据转为字符串
//                Log.e("retrofit获取到的数据", jsonStr);
                message = response.message();
                Log.d(TAG,"body:"+response.body()+",errorBody:"+response.errorBody()+",message:"+response.message()+",tostring:"+response.toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d(TAG,"Retrofit_onFailure "+t.toString()+t);
                Toast.makeText(getActivity(), "连接错误", Toast.LENGTH_LONG).show();
            }
        });
        //1、创建Gson对象
        Gson gson = new Gson();
        //2、调用toJson(Object)将对象转为字符串
        String json = gson.toJson("{\n" +
                " \"code\": 500,\n" +
                " \"message\": \"查找成功\",\n" +
                " \"data\": [\n" +
                "  {\n" +
                "   \"id\": 1,\n" +
                "   \"pathPointsLine\": [\n" +
                "    {\n" +
                "     \"latitude\": 34.14932,\n" +
                "     \"longitude\": 108.9019\n" +
                "    },\n" +
                "    {\n" +
                "     \"latitude\": 34.14932,\n" +
                "     \"longitude\": 108.9019\n" +
                "    },\n" +
                "    {\n" +
                "     \"latitude\": 34.14932,\n" +
                "     \"longitude\": 108.9019\n" +
                "    },\n" +
                "    {\n" +
                "     \"latitude\": 34.14932,\n" +
                "     \"longitude\": 108.9019\n" +
                "    },\n" +
                "    {\n" +
                "     \"latitude\": 34.149483,\n" +
                "     \"longitude\": 108.90191\n" +
                "    },\n" +
                "    {\n" +
                "     \"latitude\": 34.149483,\n" +
                "     \"longitude\": 108.90191\n" +
                "    },\n" +
                "    {\n" +
                "     \"latitude\": 34.149483,\n" +
                "     \"longitude\": 108.90191\n" +
                "    },\n" +
                "    {\n" +
                "     \"latitude\": 34.149483,\n" +
                "     \"longitude\": 108.90191\n" +
                "    },\n" +
                "    {\n" +
                "     \"latitude\": 34.149483,\n" +
                "     \"longitude\": 108.90191\n" +
                "    }\n" +
                "   ],\n" +
                "   \"distance\": \"0.02\",\n" +
                "   \"runningtime\": 16,\n" +
                "   \"startTime\": \"2022-03-21T16:00:00.000+00:00\",\n" +
                "   \"calorie\": \"1.14\",\n" +
                "   \"speed\": \"1.22\",\n" +
                "   \"distribution\": null,\n" +
                "   \"username\": \"1\"\n" +
                "  }\n" +
                " ]\n" +
                "}");

        //3、将json字符串封装为java对象[json字符串 的属性名要和javabean的属性一样]
        record record = gson.fromJson(json, record.class);
    }
}