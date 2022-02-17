package com.example.modulescore.login

import com.example.modulescore.login.net.LoginResponse
import com.example.mudulescore.login.MainNetWorkFake
import com.google.common.truth.Truth
import com.google.gson.Gson
import org.junit.Rule
import org.junit.Test

class NetWorkTest {
    @Test
    fun whenLoginSuccess(){
        val netFake = MainNetWorkFake("NO")
        val login = netFake.login(0, null, null, null, null, null)
        val  blockingFirst = login.blockingFirst()
        val fromJson = Gson().fromJson<LoginResponse>(
            "{msg = \"Success\", code = 300, publicKey = \"Shit\"}",
            LoginResponse::class.java
        )
        Truth.assertThat(fromJson.msg).isEqualTo("Success")
    }
}