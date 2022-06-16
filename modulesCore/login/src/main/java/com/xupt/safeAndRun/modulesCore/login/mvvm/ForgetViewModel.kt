package com.xupt.safeAndRun.modulesCore.login.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtil
import com.xupt.safeAndRun.modulespublic.common.net.BaseResponse
import com.xupt.safeAndRun.modulesCore.login.net.ForgetService
import com.xupt.safeAndRun.modulesCore.login.net.LoginService
import com.xupt.safeAndRun.modulesCore.login.net.VerifyService
import com.xupt.safeAndRun.modulesCore.login.net.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetViewModel
@Inject constructor(
    private val forgetService: ForgetService,
    private val loginService: LoginService,
    private val verifyService: VerifyService) : ViewModel() {
    var tel by mutableStateOf("")
    var passwd by mutableStateOf("")
    var code by mutableStateOf("")
    fun forgetPassword(onSuccess: () -> Unit) = viewModelScope.launch {
        val res: BaseResponse<String?> = try {
            forgetService.findPassword(ForgetService.Companion.ForgetRequest(tel, code, passwd))

        } catch (e: Exception) {
            BaseResponse(e.localizedMessage, 200, e.localizedMessage)
        }
        res.run {
            when (isSuccess) {
                true -> onSuccess.invoke()
                else -> ToastUtil.showToast(msg)
            }
        }
    }

    suspend fun login(): LoginResponse {
        val resquest = LoginService.Companion.LoginRequest(
            code = LoginService.MODE_PASSWD,
            username = tel,
            phone = tel,
            password = passwd
        )
        return loginService.login(resquest)
    }
}