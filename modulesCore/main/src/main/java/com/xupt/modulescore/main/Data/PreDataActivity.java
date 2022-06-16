package com.xupt.modulescore.main.Data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xupt.safeAndRun.modulesbase.libbase.util.PropertiesUtil;
import com.example.modulescore.main.R;
import com.xupt.modulescore.main.Trace.TraceActivity;
import com.xupt.modulescore.main.db.MyDataBase;
import com.xupt.modulescore.main.db.RunningRecord;
import com.xupt.modulescore.main.net.GetRequest_Interface;
import com.xupt.safeAndRun.modulespublic.common.utils.TimeManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreDataActivity extends AppCompatActivity implements View.OnClickListener{
    public PreDataActivity() {
    }
    LinearLayout linearLayout;
    HandlerPreData preDataHandler;
    String message;
    RunningRecord[] runningRecordsArray;
    public String baseUrl = PropertiesUtil.props.getProperty("baseUrl");
    String totalmile;
    TextView text_CumulativeDistance_predata;
    final String TAG = "PreDataActivityTAG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_data_item0);
        preDataHandler = new HandlerPreData(Looper.getMainLooper(),this);
        linearLayout = findViewById(R.id.linearlayout_pre_data_item0);
        requestAllRunningRecords();
        SharedPreferences sharedPreferences= getSharedPreferences("totalmile", Context.MODE_PRIVATE);
        totalmile = sharedPreferences.getString("totalmile","99.99");
        Log.d(TAG,String.valueOf(totalmile));
        if(totalmile.equals("99.99") || totalmile.equals(null)){
            Toast.makeText(this,"总里程获取错误",Toast.LENGTH_SHORT).show();
        }
        text_CumulativeDistance_predata = findViewById(R.id.text_CumulativeDistance_predata);
        text_CumulativeDistance_predata.setText(totalmile);
    }


    List<View> viewList = new ArrayList<>();

    public void RefreshDataItem(){
        final String TAG = "RefreshDataItem";
        Log.d(TAG, String.valueOf(runningRecordsArray.length));
        for(int i=0;i<runningRecordsArray.length;i++){
            LinearAddView(runningRecordsArray[i], i);
        }
    }

    private void LinearAddView(RunningRecord record,int i){
        final String TAG = "LinearAddViewTAG";
        View view = LayoutInflater.from(this).inflate(R.layout.runrecord_item,linearLayout,false);
        viewList.add(view);
        TextView startTimetext = view.findViewById(R.id.startTimetext_runrecord);
        TextView distancetext = view.findViewById(R.id.distancetext_runrecord);
        TextView durationtext = view.findViewById(R.id.duration_text_runrecorditem);
        TextView calorietext = view.findViewById(R.id.calorietext_runrecoritem);
        TextView speedtext = view.findViewById(R.id.speedtext_runrecoritem);
        TextView recordDateText = view.findViewById(R.id.runrecord_Date);
        SimpleDateFormat yearFormat = new SimpleDateFormat ("yyyy年");
        SimpleDateFormat dateFormat = new SimpleDateFormat ("MM月dd日");
        SimpleDateFormat minuteFormat = new SimpleDateFormat ("hh:mm");
        long milliSecond = Long.parseLong(record.getStartTime());
        Date date = new Date();
        date.setTime(milliSecond);
        Log.d(TAG,""+record.getStartTime());
        recordDateText.setText(dateFormat.format(milliSecond));
        view.setTag(dateFormat.format(milliSecond));
        startTimetext.setText(minuteFormat.format(milliSecond));
        if(i>0 && dateFormat.format(milliSecond).equals(viewList.get(i-1).getTag())){
            recordDateText.setVisibility(View.GONE);
        }
        distancetext.setText(record.getDistance()+"");
        durationtext.setText(TimeManager.formatseconds(record.getRunningtime()));
        calorietext.setText(record.getCalorie());
        speedtext.setText(record.getSpeed());
        linearLayout.addView(view);
        int finalI = i;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreDataActivity.this, TraceActivity.class);
                intent.setType(String.valueOf(record.getUsername()));
                intent.putExtra("index", finalI +"");
                startActivity(intent);
                Log.d("LinearAddViewTAG",finalI+","+ runningRecordsArray.length+",FINISH");
            }
        });
        i++;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    List<RunningRecord> recordList =  new ArrayList<RunningRecord>();

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
                recordList = (List<RunningRecord>) response.body();
                Log.d(TAG,response.body()+","+response.errorBody());
                if(recordList==null || recordList.size()==0){
                    Toast.makeText(PreDataActivity.this, "数据为空", Toast.LENGTH_LONG).show();
                    return;
                }
                for (int i = 0;i<recordList.size();i++){
                    recordList.get(i).setId(i);
                }
                //record = (RunningRecord) response.body();
                Log.d(TAG,recordList.size()+","+recordList.toString()+","+recordList.get(0).toString());
                runningRecordsArray = recordList.toArray(new RunningRecord[recordList.size()]);
                //Log.d(TAG,"body:"+response.body()+",errorBody:"+response.errorBody()+",message:"+response.message()+",tostring:"+response.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyDataBase.getsInstance(PreDataActivity.this).runningDao().insertRunningRecord(runningRecordsArray);
                        MyDataBase.getsInstance(PreDataActivity.this).runningDao().updateRunningRecordss(runningRecordsArray);
                        Log.d(TAG,"runningRecordsArraylength:"+runningRecordsArray.length);
                    }
                }).start();
                Log.d(TAG, String.valueOf(runningRecordsArray.length));
                Message message = new Message();
                message.what = HandlerPreData.finishProgress;
                preDataHandler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<List<RunningRecord>> call, Throwable t) {
                Log.d(TAG,"Retrofit_onFailure "+t.toString()+t);
                Toast.makeText(PreDataActivity.this, "连接错误", Toast.LENGTH_LONG).show();
            }
        });
    }
  //  private void QueryAllRunningRecords(){
//        final String TAG = "QueryAllRunningRecordsTAG";
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
//    }
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
