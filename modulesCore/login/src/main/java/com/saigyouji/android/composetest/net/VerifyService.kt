package com.saigyouji.android.composetest.net

import com.example.modulespublic.common.net.BaseResponse
import com.saigyouji.android.composetest.mvvm.VerifyViewModel
import com.saigyouji.android.composetest.net.VerifyService.Companion.CHECK_VERIFY
import retrofit2.http.*

interface VerifyService {

    @POST(SEND_VERIFY)
    suspend fun verify(@Body request: VerifyRequest)
    :BaseResponse<String?>

    @POST(CHECK_VERIFY)
    suspend fun checkVerify(@Query("phone") phone: String, @Query("verify_code") verifyCode: String):
            BaseResponse<String?>

    companion object{
        const val SEND_VERIFY = "getMessage"
        const val CHECK_VERIFY = "check_verify"
        data  class VerifyRequest(
            val phone: String
        )
    }

}