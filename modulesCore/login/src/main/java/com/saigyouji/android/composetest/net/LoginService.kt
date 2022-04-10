package com.saigyouji.android.composetest.net

import com.example.modulespublic.common.net.BaseResponse
import com.saigyouji.android.composetest.net.response.LoginResponse
import com.saigyouji.android.composetest.net.response.RegisterResponse
import retrofit2.http.*

interface LoginService {
    @POST(LOGIN)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(SEND_CODE)
    suspend fun sendCode(@Body request: SendCodeRequest): BaseResponse<String?>

    companion object {
        private const val LOGIN = "login"
        private const val SEND_CODE = "loginMessage"
        const val MODE_PASSWD = 0
        const val MODE_VERIFY = 1

        data class SendCodeRequest(
            val phone : String
        )
        data class LoginRequest(
            val code: Int,
            //MODE_PASSWD is login by password, MODE_VERIFY is login by verify code.

            val username: String?,
            val password: String?,
            val phone: String?,
            val imei: String,
            val message:String?
        )
    }
    @GET("oauth/2.0/token?grant_type=client_credentials&client_id=GMjmqqOG1GSahCNxQBL8Si4A&client_secret=0Q1Ge4HZtw2HbikZ0FRMmkgmPNce79xH")
    suspend fun test(): String?
}