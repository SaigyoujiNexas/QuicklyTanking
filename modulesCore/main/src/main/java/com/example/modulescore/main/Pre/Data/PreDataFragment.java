package com.example.modulescore.main.Pre.Data;

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

import com.example.modulescore.main.Pre.PreHandler;
import com.example.modulespublic.common.base.MyDataBase;
import com.example.modulespublic.common.base.Rr;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulescore.main.R;
import com.example.modulescore.main.Trace.TraceActivity;
import com.example.modulespublic.common.net.BaseResponse;
import com.example.modulespublic.common.utils.TimeManager;
import com.example.modulespublic.common.base.record;
import com.example.modulespublic.common.net.GetRequest_Interface;
import com.example.modulespublic.common.net.Request;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    String baseUrl = "http://116.62.180.44:8081/";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preHandler = new PreHandler(Looper.getMainLooper(),this);
        //requestAllRunningRecords();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pre_data_item0, container, false);
        linearLayout = view.findViewById(R.id.linearlayout_pre_data);
        QueryAllRunningRecords();
        requestAllRunningRecords();
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
        distancetext.setText(record.getDistance()+","+record.getDistance());
        durationtext.setText(TimeManager.formatseconds(record.getRunningtime()));
        calorietext.setText(record.getCalorie());
        speedtext.setText(record.getSpeed());
        linearLayout.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TraceActivity.class);
                intent.setType(String.valueOf(record.getUsername()));
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
    List<RunningRecord> records =  new ArrayList<RunningRecord>();
    RunningRecord record = new RunningRecord();
    private void requestAllRunningRecords(){
        final String TAG = "requestRunningRecordsTAG";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<List<RunningRecord>> call = request.getAllRunningRecords();//获得call对象
        call.enqueue(new Callback<List<RunningRecord>>() {
            @Override
            public void onResponse(Call<List<RunningRecord>> call, Response<List<RunningRecord>> response) {
                //assert response.body() != null;
//                String jsonStr = new String(response.body());//把原始数据转为字符串
//                Log.e("retrofit获取到的数据", jsonStr);
                message = response.message();
                records = (List<RunningRecord>) response.body();
                //record = (RunningRecord) response.body();
                Log.d(TAG,"body:"+response.body()+",errorBody:"+response.errorBody()+",message:"+response.message()+",tostring:"+response.toString());
                Log.d(TAG,records.toString()+","+records.get(0).toString());
            }

            @Override
            public void onFailure(Call<List<RunningRecord>> call, Throwable t) {
                Log.d(TAG,"Retrofit_onFailure "+t.toString()+t);
                Toast.makeText(getActivity(), "连接错误", Toast.LENGTH_LONG).show();
            }
        });
    }

    //上传服务器
    public void uploadFanganFile(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body;
//        if("0".equals(tag)) {
//            body = MultipartBody.Part.createFormData("headSculpture", file.getName(), requestFile);
//        }else {
//            body = MultipartBody.Part.createFormData("background", file.getName(), requestFile);
//        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//        Call<IClass0> call;
////        if("0".equals(tag)) {
////            call = nameRequest.upload2(RetrofitBase.mobileToken, RetrofitBase.uid, body);
////        }else {
////            call = nameRequest.upload1(RetrofitBase.mobileToken, RetrofitBase.uid, body);
////        }
//        call.enqueue(new Callback<IClass0>() {
//            @Override
//            public void onResponse(Call<IClass0> call, Response<IClass0> response) {
//                Message message = new Message();
//                message.obj = response.body().getCode();
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onFailure(Call<IClass0> call, Throwable t) {
//                Log.d("Personal_TAG", "请求失败" + t.toString());
//            }
//        });
    }
}
