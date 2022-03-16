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
    @POST(FORGET)
    suspend fun forget(@Body request: ForgetRequest): BaseResponse<String?>

    companion object {
        private const val LOGIN = "login"
        private const val FORGET = "forget"
        private const val SEND_CODE = "loginMessage"
        const val MODE_PASSWD = 0
        const val MODE_VERIFY = 1
        data class ForgetRequest(
            val tel: String,
            val verifyCode: String,
            val newPasswd: String,
        )
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
}