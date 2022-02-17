package com.example.modulespublic.common.net;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/INDEX.TXT")
    public Observable<BaseResponse<String>> test();
}
