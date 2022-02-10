package com.example.modulescore.login.net;

import com.example.modulespublic.common.net.BaseResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface LoginService  {

    static final String LOGIN = "login";
    static final String REGISTER = "register";
     static final String VERIFY = "verify";

    public static final int MODE_PASSWD = 0;
    public static final int MODE_VERIFY = 1;

    @GET(LOGIN)
    Observable<LoginResponse>
    login(
            @Query("code") int code,        //MODE_PASSWD is login by password, MODE_VERIFY is login by verify code.
            @Query("username") String username,
            @Query("password") String password,
            @Query("phone") String phone,
            @Query("imei") String imei,
            @Query("message") String message);

    @GET(REGISTER)
    Observable<RegisterResponse<String>>
    register(
            @Query("username") String username,
            @Query("phone") String phone,
            @Query("usersMessage") String usersMessage,
            @Query("password") String password
    );
    @GET(VERIFY)
    Observable<BaseResponse<Object>> verify(@Query("phone") String phone);
}
