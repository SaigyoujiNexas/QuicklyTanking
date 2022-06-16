package com.xupt.modulescore.main.net;


import com.xupt.modulescore.main.db.RunningRecord;
import com.xupt.safeAndRun.modulespublic.common.net.BaseResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetRequest_Interface {

    @POST("upLoadRoad")
    @Headers("token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImI4OWU2ZjFiN2JmMjVhYzVhNDFhMTIwZmZkMmZlNmM4IiwiZXhwIjoxNjUxMjc0NzIxLCJ1c2VybmFtZSI6Imxpem9uZ2JpbiJ9.FI9dKKGa2llVFU4EiscM_AKOEufg3IqfdW6DH-pbEIY")
    Call<BaseResponse<RunningRecord>> postRuuningRecord(@Body RunningRecord record);

    @Headers("token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImI4OWU2ZjFiN2JmMjVhYzVhNDFhMTIwZmZkMmZlNmM4IiwiZXhwIjoxNjUxMjc0NzIxLCJ1c2VybmFtZSI6Imxpem9uZ2JpbiJ9.FI9dKKGa2llVFU4EiscM_AKOEufg3IqfdW6DH-pbEIY")
    @GET("getRoad")
    Call<List<RunningRecord>> getAllRunningRecords();

    @Headers("token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImI4OWU2ZjFiN2JmMjVhYzVhNDFhMTIwZmZkMmZlNmM4IiwiZXhwIjoxNjUxMjc0NzIxLCJ1c2VybmFtZSI6Imxpem9uZ2JpbiJ9.FI9dKKGa2llVFU4EiscM_AKOEufg3IqfdW6DH-pbEIY")
    @GET("getTotalMile")
    Call<Double> getTotalMile();

    @FormUrlEncoded
    @POST("oauth/2.0/token")
    Call<GetTokenItem> getBaiduToken(@Field("grant_type")String grant_type, @Field("client_id") String apiKey,
                                     @Field("client_secret") String secretKey);

    @FormUrlEncoded
    @POST("rest/2.0/image-classify/v2/dish")
    @Headers("Content-Type:application/x-www-form-urlencoded;charset=utf-8")
    Call<DiscernResultResponse> getDiscernResultResponse(@Field("access_token") String access_token,
                                                         @Field("image") String url, @Field("filter_threshold")float filter_threshold);

    @Multipart
    @POST("modification")
    @Headers("token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImI4OWU2ZjFiN2JmMjVhYzVhNDFhMTIwZmZkMmZlNmM4IiwiZXhwIjoxNjUxMjc0NzIxLCJ1c2VybmFtZSI6Imxpem9uZ2JpbiJ9.FI9dKKGa2llVFU4EiscM_AKOEufg3IqfdW6DH-pbEIY")
    Call<Object> uploadUserImg( @Part MultipartBody.Part file);
//    @Provides
//    @Singleton
//    public static GetRequest_Interface provideGetRequest_Interface(Retrofit retrofit){
//        return retrofit.create(GetRequest_Interface.class);
//    }
}
