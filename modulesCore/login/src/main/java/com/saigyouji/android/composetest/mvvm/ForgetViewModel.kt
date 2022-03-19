package com.saigyouji.android.composetest.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtil
import com.example.modulespublic.common.net.BaseResponse
import com.saigyouji.android.composetest.net.ForgetService
import com.saigyouji.android.composetest.net.LoginService
import com.saigyouji.android.composetest.net.VerifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetViewModel
@Inject constructor(private val forgetService: ForgetService) : ViewModel()
{
    var tel by mutableStateOf("")
    var passwd by mutableStateOf("")
    var code by mutableStateOf("")
    fun forgetPassword(onSuccess: () -> Unit) = viewModelScope.launch {
        val res: BaseResponse<String?> = try {
            forgetService.findPassword(LoginService.Companion.ForgetRequest(tel, code, passwd))

        }catch (e: Exception){
            BaseResponse(e.localizedMessage, 200, e.localizedMessage)
        }
        res.run {
            when(isSuccess){
                true-> onSuccess.invoke()
                else -> ToastUtil.showToast(msg)
            }
        }
    }
}