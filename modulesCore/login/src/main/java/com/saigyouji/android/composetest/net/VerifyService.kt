package com.saigyouji.android.composetest.net

import com.example.modulespublic.common.net.BaseResponse
import com.saigyouji.android.composetest.net.VerifyService.Companion.CHECK_VERIFY
import retrofit2.http.GET
import retrofit2.http.Query

interface VerifyService {

    @GET(SEND_VERIFY)
    suspend fun verify(@Query("phone") phone: String):
            BaseResponse<String?>

    @GET(CHECK_VERIFY)
    suspend fun checkVerify(@Query("phone") phone: String, @Query("verify_code") verifyCode: String):
            BaseResponse<String?>

    companion object{
        const val SEND_VERIFY = "get_verify"
        const val CHECK_VERIFY = "check_verify"
    }

}