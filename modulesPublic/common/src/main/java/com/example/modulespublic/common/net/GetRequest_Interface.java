package com.example.modulespublic.common.net;

import com.example.modulespublic.common.base.RunningRecord;

import java.util.List;

import javax.inject.Singleton;

import dagger.Provides;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetRequest_Interface {
    @POST("upLoadRoad")
    Call<BaseResponse<RunningRecord>> postRuuningRecord(@Body RunningRecord record);

    @POST("getRoad")
    Call<Object> getAllRunningRecords(@Body Request request);
    @POST("getRoad")
    Call<BaseResponse<List<RunningRecord>>> getAllRunningRecords2(@Body Request request);
//    @Provides
//    @Singleton
//    public static GetRequest_Interface provideGetRequest_Interface(Retrofit retrofit){
//        return retrofit.create(GetRequest_Interface.class);
//    }
}
