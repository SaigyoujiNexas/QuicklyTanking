package com.example.mudulescore.login

import com.example.modulescore.login.net.LoginResponse
import com.example.modulescore.login.net.LoginService
import com.example.modulescore.login.net.RegisterResponse
import com.example.modulespublic.common.net.BaseResponse
import io.reactivex.Observable

class TestingFakes {
}
class MainNetWorkFake(val res : String) : LoginService{
    override fun login(
        code: Int,
        username: String?,
        password: String?,
        phone: String?,
        imei: String?,
        message: String?
    ): Observable<LoginResponse>{
        val response = LoginResponse(res, 300, "OK")
        return Observable.just(response)
    }

    override fun register(
        username: String?,
        phone: String?,
        usersMessage: String?,
        password: String?
    ): Observable<RegisterResponse<String>> {
        TODO("Not yet implemented")
    }

    override fun verify(phone: String?): Observable<BaseResponse<Any>> {
        TODO("Not yet implemented")
    }
}