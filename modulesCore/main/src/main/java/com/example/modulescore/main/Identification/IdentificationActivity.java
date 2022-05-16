package com.example.modulescore.main.Identification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.modulescore.main.R;
import com.example.modulespublic.common.base.RunningRecord;
import com.example.modulespublic.common.net.GetRequest_Interface;
import com.example.modulespublic.common.net.GetTokenItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdentificationActivity extends AppCompatActivity {

    private String baseUrl="https://aip.baidubce.com/";
    private final String TAG = "IdentificationActivityTAG";
    String secretKey = "cNRIehDO0xxo89p0fZxxxb6O79hXwcv8";
    String apiKey = "uv7Y1XiG5broeDMU70AINwAh";
    String grant_type = "client_credentials";
    String access_token = "24.8e93c0cbe57d25501173302a756afbed.2592000.1655218392.282335-26232617";
    Retrofit retrofit
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        requestBaiduToken();
        requestIdentificationResult();
    }

   public void requestBaiduToken(){
       retrofit = new Retrofit.Builder()
               .baseUrl(baseUrl)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
       Call<GetTokenItem> call = request.getBaiduToken(grant_type,apiKey,secretKey);//获得call对象
       call.enqueue(new Callback<GetTokenItem>() {
           @Override
           public void onResponse(Call<GetTokenItem> call, Response<GetTokenItem> response) {
               Log.d(TAG,response.body().getAccess_token());
               access_token = response.body().getAccess_token();
           }

           @Override
           public void onFailure(Call<GetTokenItem> call, Throwable t) {
                Log.w(TAG,t.toString());
           }
       });
   }
    public void requestIdentificationResult(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<GetTokenItem> call = request.getDiscernResultResponse(access_token,,0.95);//获得call对象
        call.enqueue(new Callback<GetTokenItem>() {
            @Override
            public void onResponse(Call<GetTokenItem> call, Response<GetTokenItem> response) {
                Log.d(TAG,response.body().getAccess_token());
            }

            @Override
            public void onFailure(Call<GetTokenItem> call, Throwable t) {
                Log.w(TAG,t.toString());
            }
        });
    }
}