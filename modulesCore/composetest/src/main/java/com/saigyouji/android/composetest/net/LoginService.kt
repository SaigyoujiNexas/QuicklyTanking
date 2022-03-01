package com.saigyouji.android.composetest.net

import retrofit2.http.GET
import com.example.modulespublic.common.net.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @GET(LOGIN)
    suspend fun login(
        @Query("code") code: Int,  //MODE_PASSWD is login by password, MODE_VERIFY is login by verify code.
        @Query("username") username: String?,
        @Query("password") password: String?,
        @Query("phone") phone: String?,
        @Query("imei") imei: String?,
        @Query("message") message: String?
    ): LoginResponse

    @GET(REGISTER)
    suspend fun register(
        @Query("username") username: String?,
        @Query("phone") phone: String?,
        @Query("usersMessage") usersMessage: String?,
        @Query("password") password: String?
    ): RegisterResponse<String?>

    @GET(VERIFY)
    suspend fun verify(@Query("phone") phone: String):
            BaseResponse<String?>

    @GET(FORGET)
    suspend fun forget(
        @Query("tel") tel: String?,
        @Query("verifyCode") verifyCode: String?,
        @Query("newPasswd") newPasswd: String?
    ): BaseResponse<String?>

    @POST("/run/addRunRecord")
    suspend fun runRecord(
        @Query("distance") dis: String,
        @Query("runTime") time: String,
        @Query("runWhen") `when`: String,
        @Header("token") str: String = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDUyNDcwMTUsImV4cCI6MTY0NTI1MDYxNSwidXNlcklkIjoiMSJ9.GKube0YrCS-phWOG1rc2t4EQnPseqyZi88SetoknTB8"
    ): runResponse

    companion object {
        const val LOGIN = "login"
        const val REGISTER = "register"
        const val VERIFY = "verify"
        const val FORGET = "forget"
        const val MODE_PASSWD = 0
        const val MODE_VERIFY = 1
    }
}