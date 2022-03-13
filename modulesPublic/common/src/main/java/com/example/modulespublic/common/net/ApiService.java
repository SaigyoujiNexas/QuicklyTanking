package com.example.modulespublic.common.net;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    static final String SET_USER_INFO = "set/user_info";
    @GET("/INDEX.TXT")
    public Observable<BaseResponse<String>> test();
    @Multipart
    @POST(SET_USER_INFO)
    public Observable<BaseResponse<String>> setUserInfo
            (
                    @Header("token") String token,
                    @Part MultipartBody.Part head,
                    @Query("name") String name,
                    @Query("gender") String gender,
                    @Query("birthday") String birthday,
                    @Query("height") String height,
                    @Query("weight") String weight
            );
}
