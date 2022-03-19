package com.saigyouji.android.composetest.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtil
import com.example.modulesbase.libbase.cache.Preferences
import com.example.modulespublic.common.constant.KeyPool
import com.example.modulespublic.common.net.BaseResponse
import com.saigyouji.android.composetest.net.response.LoginResponse
import com.saigyouji.android.composetest.net.LoginService
import com.saigyouji.android.composetest.net.VerifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject constructor(
        private val loginService: LoginService
    ) : ViewModel() {

    var tel by mutableStateOf("")
    var passwd by mutableStateOf("")
    var verifyCode by mutableStateOf("")

    fun sendCode(onSuccess: () -> Unit) = viewModelScope.launch {
        val res: BaseResponse<String?> = try{
            loginService.sendCode(LoginService.Companion.SendCodeRequest(tel))
        }catch(e: Exception) {
            BaseResponse( e.localizedMessage, 200, e.localizedMessage)
        }
        res.let {
            when(it.isSuccess){
                true -> onSuccess.invoke()
                else -> ToastUtil.showToast(it.msg)
            }
        }
    }

        fun loginByPasswd( onSuccess: () -> Unit = {}) =

            viewModelScope.launch {
                var res: LoginResponse?
                try {
                    res = loginService.login(
                        LoginService.Companion.LoginRequest(
                        LoginService.MODE_PASSWD,
                        tel,
                        passwd,
                        null,
                        Preferences.getString(KeyPool.ID, ""),
                        null)
                    )
                }catch (e: Exception)
                {
                    res =
                        LoginResponse(
                            e.message,
                            200,
                            null
                        )
                }
                res?.let {
                    when(it.isSuccess){
                        true -> {
                            Preferences.saveString(KeyPool.PUBLIC_KEY, it.data)
                            onSuccess.invoke()
                        }
                        else -> {
                            ToastUtil.showToast("登陆失败: ${res.msg}")
                        }
                    }
                }
            }

    fun loginByVerify(onSuccess: () -> Unit = {}) = viewModelScope.launch {
            var res: LoginResponse? = null
        try {
            res = loginService.login(
                LoginService.Companion.LoginRequest(
                LoginService.MODE_VERIFY,
                null,
                null,
                tel,
                Preferences.getString(KeyPool.ID, ""),
                verifyCode
            )
            )
        }catch (e: Exception){
            res = LoginResponse(e.localizedMessage,
                200,
                e.localizedMessage
            )
        }
        res?.let {
            when(it.isSuccess)
            {
                true -> {
                    Preferences.saveString(KeyPool.PUBLIC_KEY, it.data)
                    onSuccess.invoke()
                }
                else -> {
                    ToastUtil.showToast("登陆失败: ${it.msg}")
                }
            }
        }
    }


}