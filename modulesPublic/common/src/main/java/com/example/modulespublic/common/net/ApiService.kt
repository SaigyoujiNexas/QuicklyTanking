package com.example.modulespublic.common.net

import com.example.modulespublic.common.net.ApiService
import com.example.modulespublic.common.net.BaseResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST(SET_USER_INFO)
    suspend fun setUserInfo(
        @Header("token") token: String?,
        @Part head: MultipartBody.Part,
    ): BaseResponse<String?>

    companion object {
        const val SET_USER_INFO = "modification"
    }
}