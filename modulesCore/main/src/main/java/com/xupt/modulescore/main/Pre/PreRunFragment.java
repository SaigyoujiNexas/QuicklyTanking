package com.xupt.modulescore.main.Pre;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.common.utils.ToastUtil;
import com.xupt.safeAndRun.modulesbase.libbase.util.PropertiesUtil;
import com.xupt.modulescore.main.Data.HandlerPreData;
import com.xupt.modulescore.main.Run.RunActivity;
import com.xupt.modulescore.main.Activities.TargetDistanceActivity;
import com.example.modulescore.main.R;
import com.xupt.modulescore.main.net.GetRequest_Interface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class PreRunFragment extends Fragment implements View.OnClickListener{
    CardView card_startRun_preRun;
    CardView card_targetDistance_preRun;
    TextView text_CumulativeDistance_prerun;

    HandlerPreData preDataHandler;
    Double totalMile;
    final String TAG = "PreRunFragmentTAG";
    String baseUrl = PropertiesUtil.props.getProperty("baseUrl");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prerunfragment, container, false);
        card_startRun_preRun = view.findViewById(R.id.card_startRun_preRun);
        card_targetDistance_preRun = view.findViewById(R.id.card_targetDistance_preRun);
        text_CumulativeDistance_prerun = view.findViewById(R.id.text_CumulativeDistance_prerun);
        card_targetDistance_preRun.setOnClickListener(this);
        card_startRun_preRun.setOnClickListener(this);
        preDataHandler = new HandlerPreData(Looper.getMainLooper(),this);
        Log.d(TAG, String.valueOf(getContext().getClass())+","+getContext().toString());
        requestTotalMile();
        return view;
    }

    @Override
    public void onClick(View view) {
        var id = view.getId();
        if(id == R.id.card_startRun_preRun) {
            Intent startRunningIntent = new Intent(getActivity(), RunActivity.class);
            startActivity(startRunningIntent);
        }
        if(id == R.id.card_targetDistance_preRun){
                Intent targetDistanceIntent = new Intent(getActivity(), TargetDistanceActivity.class);
                startActivity(targetDistanceIntent);
        }
    }
    private void requestTotalMile(){
        final String TAG = "requestTotalMileTAG";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<Double> call = request.getTotalMile();//获得call对象
        call.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Message message = new Message();
                if(response.body()!=null)
                    totalMile = response.body();
                else {
                    message.what = HandlerPreData.errorRequest;
                    totalMile = 99.99;
                    preDataHandler.sendMessage(message);
                    return;
                }
                Log.d(TAG,response.body()+","+response);
                message.what = HandlerPreData.finishTotalMile_Prerunfragment;
                preDataHandler.sendMessage(message);
                SharedPreferences.Editor editor = getContext().getSharedPreferences("totalmile", Context.MODE_PRIVATE).edit();
                editor.putString("totalmile",String.valueOf(totalMile));
                editor.commit();
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.d(TAG,"Retrofit_onFailure "+t.toString()+t);
                Toast.makeText(getContext(), "获取总里程错误", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void refreshTotalMile(){
        text_CumulativeDistance_prerun.setText(String.valueOf(totalMile));
    }
    public void toastError(){
        ToastUtil.INSTANCE.showToast("获取总里程错误！");
    }
}
