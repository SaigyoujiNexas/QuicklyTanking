package com.saigyouji.android.composetest.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtil
import com.example.modulespublic.common.net.BaseResponse
import com.saigyouji.android.composetest.net.LoginService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel
@Inject
constructor(val loginService: LoginService) : ViewModel()
{
    var timeLeft: Long by mutableStateOf(0)
    var btClickable : Boolean by mutableStateOf(true)
    var btString: String by mutableStateOf("发送短信验证码")
    /**
     * verify network function, give the verify code support.
     */
    fun verify(tel: String, onSuccess: () -> Unit = {}) = viewModelScope.launch {
        var res : BaseResponse<String?>? = null
        try{
            res = loginService.verify(tel)
        }catch (e: Exception){
            res = BaseResponse<String?>(e.message, 200, null)
        }
        res?.let {
            when(it.isSuccess)
            {
                true ->{
                    onSuccess.invoke()
                }
                else ->{
                    ToastUtil.showToast("验证码获取失败: ${it.msg}")
                }
            }
        }
    }
    companion object{
        const val WAIT_TIME = 60000L
   }
}