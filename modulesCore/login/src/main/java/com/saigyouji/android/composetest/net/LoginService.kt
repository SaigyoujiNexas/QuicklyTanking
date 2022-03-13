package com.saigyouji.android.composetest.net

import retrofit2.http.GET
import com.example.modulespublic.common.net.BaseResponse
import com.saigyouji.android.composetest.net.response.LoginResponse
import com.saigyouji.android.composetest.net.response.RegisterResponse
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST(LOGIN)
    suspend fun login(
        @Query("code") code: Int,  //MODE_PASSWD is login by password, MODE_VERIFY is login by verify code.
        @Query("username") username: String?,
        @Query("password") password: String?,
        @Query("phone") phone: String?,
        @Query("imei") imei: String?,
        @Query("message") message: String?
    ): LoginResponse



    @POST(FORGET)
    suspend fun forget(
        @Query("tel") tel: String?,
        @Query("verifyCode") verifyCode: String?,
        @Query("newPasswd") newPasswd: String?
    ): BaseResponse<String?>

    companion object {
        private const val LOGIN = "login"

        private const val FORGET = "forget"
        const val MODE_PASSWD = 0
        const val MODE_VERIFY = 1
    }
}