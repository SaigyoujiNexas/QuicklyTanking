package com.xupt.safeAndRun.modulesCore.login.net

import com.xupt.safeAndRun.modulespublic.common.net.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgetService {

    @POST(FIND)
    suspend fun findPassword(@Body request: ForgetRequest): BaseResponse<String?>


    companion object {

        data class ForgetRequest(
            val phone: String,
            val code: String,
            val password: String,
        )
        private const val FIND = "findPassword"
    }

}