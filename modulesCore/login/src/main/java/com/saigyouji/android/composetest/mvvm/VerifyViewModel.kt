package com.saigyouji.android.composetest.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtil
import com.example.modulesbase.libbase.util.GSON
import com.example.modulespublic.common.net.BaseResponse
import com.google.gson.Gson
import com.saigyouji.android.composetest.net.LoginService
import com.saigyouji.android.composetest.net.VerifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel
@Inject
constructor(val verifyService: VerifyService) : ViewModel()
{
    var timeLeft: Long by mutableStateOf(0)
    var btClickable : Boolean by mutableStateOf(true)
    var btString: String by mutableStateOf("发送短信验证码")
    /**
     * verify network function, give the verify code support.
     */
    fun verify(tel: String, onSuccess: () -> Unit = {}) = viewModelScope.launch {

       val verifyRequest = VerifyService.Companion.VerifyRequest(tel)
        var res : BaseResponse<String?>? = null
        try{
            res = verifyService.verify(verifyRequest)
        }catch (e: Exception){
            res = BaseResponse<String?>(e.message, 200, e.message)
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

    fun checkVerify(tel:String, code: String, onSuccess: () -> Unit = {}) = viewModelScope.launch {
        var res: BaseResponse<String?>?
        try{
            res = verifyService.checkVerify(tel, code)
        }catch(e:Exception){
            res = BaseResponse(e.localizedMessage, 200, e.message)
        }
        res?.let {
            when(it.isSuccess){
                true -> {
                    onSuccess.invoke()
                }
                else ->{
                    ToastUtil.showToast("验证码校验失败: ${it.msg}")
                }
            }
        }
    }

    companion object{
        const val WAIT_TIME = 60000L

   }


}