package com.example.modulescore.main.Identification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.common.utils.ToastUtil;
import com.example.modulescore.main.R;
import com.example.modulespublic.common.constant.Constant;
import com.example.modulespublic.common.constant.SPUtils;
import com.example.modulespublic.common.net.BaseResponse;
import com.example.modulespublic.common.net.DiscernResultResponse;
import com.example.modulespublic.common.net.GetRequest_Interface;
import com.example.modulespublic.common.net.GetTokenItem;

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
    String access_token = "24.89f9c3eb78a22cb5bb6ffb21f80290fd.2592000.1655390639.282335-26232617";
    Retrofit retrofit;
    RecyclerView recycler_identificationActivity;
    ImageView img_identificationActivity;
    DiscernResultResponse discernResultResponse;
    HandlerIdentification handlerIdentification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        getAccessToken();
        requestIdentificationResult();
        recycler_identificationActivity = findViewById(R.id.recycler_identificationActivity);
        img_identificationActivity = findViewById(R.id.img_identificationActivity);
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
               Log.d(TAG,"token:"+response.body().getAccess_token()+"detail:"+response);
               //鉴权Token
               access_token = response.body().getAccess_token();
               //过期时间 秒
               long expiresIn = response.body().getExpires_in();
               //当前时间 秒
               long currentTimeMillis = System.currentTimeMillis() / 1000;
               //放入缓存
               SPUtils.putString(Constant.TOKEN, access_token, IdentificationActivity.this);
               SPUtils.putLong(Constant.GET_TOKEN_TIME, currentTimeMillis, IdentificationActivity.this);
               SPUtils.putLong(Constant.TOKEN_VALID_PERIOD, expiresIn, IdentificationActivity.this);
               Log.d(TAG+"tokentoken:",",expiresIn:"+expiresIn+",currentTimeMillis"+currentTimeMillis);
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
        Call<DiscernResultResponse> call = request.getDiscernResultResponse(access_token,"https://www.csrven.com/images/menu_cover_b/f3067a6e886111e6b87c0242ac110003_640w_640h.jpg", (float) 0.95);//获得call对象
        call.enqueue(new Callback<DiscernResultResponse>() {
            @Override
            public void onResponse(Call<DiscernResultResponse> call, Response<DiscernResultResponse> response) {
                discernResultResponse = response.body();
                Log.d(TAG, response.errorBody()+","+response+","+discernResultResponse.getLog_id() + ","+discernResultResponse.getResult_num());
                if (discernResultResponse.getResult() == null) {
                    Log.w(TAG, "RESULT == NULL");
                    ToastUtil.Companion.showToast("未识别出相应结果");
                } else {
                    Message message = new Message();
                    message.what = HandlerIdentification.finishIdentification;
                    handlerIdentification = new HandlerIdentification(Looper.getMainLooper(),IdentificationActivity.this);
                    handlerIdentification.sendMessage(message);
                    Log.d(TAG, discernResultResponse.getResult().size() + ","+discernResultResponse.getResult().get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<DiscernResultResponse> call, Throwable t) {
                Log.w(TAG,t.toString());
            }
        });
    }

    public void refreshItemIdentification(){
        Log.d("111111111111111111111","!111111");
        AdapterIdentification adapterIdentification = new AdapterIdentification(discernResultResponse.getResult());
        recycler_identificationActivity.setAdapter(adapterIdentification);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_identificationActivity.setLayoutManager(linearLayoutManager);
        adapterIdentification.notifyDataSetChanged();
    }
    /**
     * Token是否过期
     *
     * @return
     */
    private boolean isTokenExpired() {
        //获取Token的时间
        long getTokenTime = SPUtils.getLong(Constant.GET_TOKEN_TIME, 0, this);
        //获取Token的有效时间
        long effectiveTime = SPUtils.getLong(Constant.TOKEN_VALID_PERIOD, 0, this);
        //获取当前系统时间
        long currentTime = System.currentTimeMillis() / 1000;

        return (currentTime - getTokenTime) >= effectiveTime;
        //取出缓存中的获取Token的时间，然后获取Token的有效时长，再获取当前系统时间，然后通过当前系统时间减去获得Token的时间，
        // 得到的值再与Token有效期做比较，如果大于等于有效期则说明Token过期，返回true，否则返回false。
    }

    /**
     * 获取鉴权Token
     */
    private String getAccessToken() {
        String token = SPUtils.getString(Constant.TOKEN, null, this);
        Log.d(TAG,"SPtoken:"+token);
        if (token == null) {
            //访问API获取接口
            requestBaiduToken();
        } else {
            //则判断Token是否过期
            if (isTokenExpired()) {
                //过期
                requestBaiduToken();
            } else {
                access_token = token;
            }
        }
        return access_token;
    }
}