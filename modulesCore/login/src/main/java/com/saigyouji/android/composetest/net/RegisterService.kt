package com.saigyouji.android.composetest.net

import com.example.modulespublic.common.net.BaseResponse
import com.saigyouji.android.composetest.net.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisterService {

    @POST(REGISTER)
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String?>

    companion object {
        private const val REGISTER = "register"
        data class RegisterRequest(
            val username:String,
            val phone: String,
            val usersMessage: String,
            val password: String
        )
    }
}