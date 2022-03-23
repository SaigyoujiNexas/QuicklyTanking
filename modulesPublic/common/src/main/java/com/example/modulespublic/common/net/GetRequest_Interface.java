package com.example.modulespublic.common.net;

import com.example.modulespublic.common.base.RunningRecord;

import javax.inject.Singleton;

import dagger.Provides;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetRequest_Interface {
    //无参
    @POST("upLoadRoad")
    Call<BaseResponse<RunningRecord>> postRuuningRecord(@Body RunningRecord record);

    //无参
    @POST("getRoad")
    Call<BaseResponse<RunningRecord>> getAllRunningRecords();
//    @Provides
//    @Singleton
//    public static GetRequest_Interface provideGetRequest_Interface(Retrofit retrofit){
//        return retrofit.create(GetRequest_Interface.class);
//    }
}
