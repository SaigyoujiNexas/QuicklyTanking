package com.saigyouji.android.composetest.mvvm

import android.media.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtil
import com.saigyouji.android.composetest.net.LoginService
import com.saigyouji.android.composetest.net.runResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(val registerService: LoginService): ViewModel()
{
    var tel : String by mutableStateOf("")

    var verify: String by mutableStateOf("")

    var passwd: String by mutableStateOf("")

    var name: String by mutableStateOf("")

    var head: Image? by mutableStateOf(null)

    fun runRecord(dis: String, time: String, `when`: String) = viewModelScope.launch {
        var response: runResponse? = null
        try {
            val response = registerService.runRecord(dis, time, `when`)
            ToastUtil.showToast("${response.code} ${response.msg}")
        }catch (e: Exception){
            ToastUtil.showToast(e.message?:e.toString())
        }
    }
}