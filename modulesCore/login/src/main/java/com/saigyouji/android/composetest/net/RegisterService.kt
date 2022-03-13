package com.saigyouji.android.composetest.net

import com.saigyouji.android.composetest.net.response.RegisterResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisterService {
    @POST(REGISTER)
    suspend fun register(
        @Query("username") username: String?,
        @Query("phone") phone: String?,
        @Query("usersMessage") usersMessage: String?,
        @Query("password") password: String?
    ): RegisterResponse<String?>
    companion object {
        private const val REGISTER = "register"
    }
}