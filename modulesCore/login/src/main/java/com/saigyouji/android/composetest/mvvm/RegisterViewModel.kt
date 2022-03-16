package com.saigyouji.android.composetest.mvvm

import android.media.Image
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
import com.saigyouji.android.composetest.net.VerifyService
import com.saigyouji.android.composetest.net.response.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val registerService: RegisterService,
    private val apiService: ApiService
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

    fun setUserInfo(lifecycleOwner: LifecycleOwner, onSuccess: (String?) -> Unit) {

        RequestModel.request(
            apiService.setUserInfo(
            Preferences.getString(KeyPool.PUBLIC_KEY, ""),
            FileUtil.ImageFileToMultpartBody(head.toFile()),
            name, gender, birthday, height, weight
        ),
            lifecycleOwner, object:NetCallback<String?>{
            override fun onSuccess(data: String?) {
                onSuccess.invoke(data)
            }
            override fun onFailure(throwable: Throwable?) {
                    ToastUtil.showToast(throwable?.localizedMessage?:"未知错误")
            }
        })
    }
}