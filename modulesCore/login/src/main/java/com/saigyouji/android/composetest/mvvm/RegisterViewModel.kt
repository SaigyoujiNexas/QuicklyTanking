package com.saigyouji.android.composetest.mvvm

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toFile
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtil
import com.example.modulesbase.libbase.cache.Preferences
import com.example.modulesbase.libbase.net.RequestModel
import com.example.modulesbase.libbase.net.response.NetCallback
import com.example.modulespublic.common.constant.KeyPool
import com.example.modulespublic.common.net.ApiService
import com.example.modulespublic.common.net.BaseResponse
import com.example.modulespublic.common.utils.FileUtil
import com.saigyouji.android.composetest.net.LoginService
import com.saigyouji.android.composetest.net.RegisterService
import com.saigyouji.android.composetest.net.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val registerService: RegisterService,
    private val apiService: ApiService,
    private val loginService: LoginService
): ViewModel()
{
    var tel : String by mutableStateOf("")
    var verify: String by mutableStateOf("")
    var passwd: String by mutableStateOf("")
    var name: String by mutableStateOf("")
    var head: Uri by mutableStateOf(Uri.EMPTY)
    var birthday: String by mutableStateOf("")
    var weight : String by mutableStateOf("")
    var height: String by mutableStateOf("")
    var gender: String by mutableStateOf("")

    fun register(onSuccess: (BaseResponse<String?>) -> Unit) = viewModelScope.launch {
        var registerResponse : BaseResponse<String?>?
        try {
            val request = RegisterService.Companion.RegisterRequest(name, tel, verify, passwd)
            registerResponse = registerService.register(request)
        }
        catch (e: Exception){
            registerResponse = BaseResponse( e.localizedMessage, 200, e.localizedMessage)
        }
        registerResponse?.let{
            when(it.isSuccess){
                true -> {
                    onSuccess.invoke(it)
                }
                else ->{
                    ToastUtil.showToast("注册失败: ${it.msg}")
                }
            }
        }
    }
    fun judgeUserName(onSuccess: () -> Unit) = viewModelScope.launch {
        var res: BaseResponse<String?>?
        try {
            res = registerService.judgeUserName(RegisterService.Companion.JudgeUserNameRequest(name))
        }catch(e: Exception)
        {
            res = BaseResponse( e.localizedMessage, 200, e.localizedMessage)
        }
        res?.let{
            if(it.code.equals("200"))
                ToastUtil.showToast(it.msg)
            else
                onSuccess.invoke()
        }
    }

    fun setUserInfo(onSuccess: (String?) -> Unit) = viewModelScope.launch {
        var res: BaseResponse<String?>?
        try{
            res = apiService.setUserInfo(Preferences.getString("token", ""),FileUtil.imageFileToMultipartBody(head.toFile()))
        }catch (e: Exception){
            res = BaseResponse(e.localizedMessage, 200, e.localizedMessage)
        }
        res?.let {
            if(res.isSuccess)
                onSuccess.invoke(res.message)
            else
                ToastUtil.showToast(res.message)
        }
    }
    fun login(onSuccess: () -> Unit) = viewModelScope.launch {
        var res: LoginResponse
        try{
            res = loginService.login(LoginService.Companion.LoginRequest(
                LoginService.MODE_PASSWD,null,
                passwd, tel, Preferences.getString(KeyPool.ID, ""), null))
        }catch (e: Exception){
            res = LoginResponse(e.localizedMessage, 200, e.localizedMessage)
        }
        res.let{
            if(res.isSuccess) {
                Preferences.saveString("token", res.data)
                onSuccess.invoke()
            }
            else
                ToastUtil.showToast(res.msg)
        }
    }
}