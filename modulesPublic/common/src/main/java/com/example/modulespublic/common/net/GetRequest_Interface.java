package com.example.modulespublic.common.net;

import com.example.modulespublic.common.base.RunningRecord;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetRequest_Interface {
    //get请求
    //有参方法
    @GET("api/rand.music")
    Call<RunningRecord> getJsonData1(@Query("sort") String sort, @Query("format") String format);
    //最后的部分uri相当与api/rand.music?sort=传入的参数&format=传入的参数
    //无参
    @GET("/")
    Call<RunningRecord> getJsonData(@Query("record")RunningRecord record);

    //post请求
    @FormUrlEncoded
    @POST("api/comments.163")
    Call<Object> postDataCall(@Field("format") String format);
}
