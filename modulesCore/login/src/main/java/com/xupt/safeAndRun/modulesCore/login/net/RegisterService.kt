package com.xupt.safeAndRun.modulesCore.login.net

import com.xupt.safeAndRun.modulespublic.common.net.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST(REGISTER)
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String?>

    @POST(JUDGE_USER_NAME)
    suspend fun judgeUserName(@Body request: JudgeUserNameRequest): BaseResponse<String?>

    companion object {
        private const val REGISTER = "register"
        private const val JUDGE_USER_NAME = "judgeUsername"

        data class JudgeUserNameRequest(
            val username: String
        )
        data class RegisterRequest(
            val username:String,
            val phone: String,
            val usersMessage: String,
            val password: String
        )
    }

}