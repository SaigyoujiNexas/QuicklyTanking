package com.xupt.safeAndRun.modulesCore.login.mvvm

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.ToastUtil
import com.xupt.safeAndRun.modulesCore.login.entity.ForgetAccount
import com.xupt.safeAndRun.modulesCore.login.entity.LoginAccount
import com.xupt.safeAndRun.modulesCore.login.entity.RegisterAccount
import com.xupt.safeAndRun.modulesCore.login.net.ForgetService
import com.xupt.safeAndRun.modulesCore.login.net.LoginService
import com.xupt.safeAndRun.modulesCore.login.net.RegisterService
import com.xupt.safeAndRun.modulesCore.login.net.VerifyService
import com.xupt.safeAndRun.modulesCore.login.net.response.LoginResponse
import com.xupt.safeAndRun.modulesbase.libbase.cache.Preferences
import com.xupt.safeAndRun.modulespublic.common.constant.KeyPool
import com.xupt.safeAndRun.modulespublic.common.net.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val loginService: LoginService,
    private val registerService: RegisterService,
    private val forgetService: ForgetService,
    private val verifyService: VerifyService,
) : ViewModel() {

    private var _uiStatus: MutableState<UiStatus> = mutableStateOf(UiStatus.Login())
    val uiStatus: State<UiStatus>
        get() = _uiStatus
    private fun sendCode(tel: String, onSuccess: () -> Unit) = viewModelScope.launch {
        launch(Dispatchers.Main) {
            uiStatus.value.sendCount = 60
            val num = 60
            val timer = object: CountDownTimer((num + 1) * 1000L, 1000L){
                override fun onTick(millisUntilFinished: Long) {
                    uiStatus.value.sendCount--
                    _uiStatus.value = _uiStatus.value
                }

                override fun onFinish() {
                    uiStatus.value.sendCount = 0
                    _uiStatus.value = (_uiStatus.value)
                }
            }
            timer.start()
        }
        val res: BaseResponse<String?> = try {
            loginService.sendCode(LoginService.Companion.SendCodeRequest(tel))
        } catch (e: Exception) {
            BaseResponse(e.localizedMessage, 200, e.localizedMessage)
        }
        res.let {
            when (it.isSuccess) {
                true -> onSuccess.invoke()
                else -> ToastUtil.showToast(it.msg)
            }
        }
    }

    fun loginByPasswd(loginAccount: LoginAccount, onSuccess: () -> Unit = {}) = viewModelScope.launch {
            var res: LoginResponse?
            try {
                res = loginService.login(
                    LoginService.Companion.LoginRequest(
                        LoginService.MODE_PASSWD,
                        loginAccount.tel,
                        loginAccount.code,
                        null,
                        Preferences.getString(KeyPool.ID, ""),
                        null
                    )
                )
            } catch (e: Exception) {
                res =
                    LoginResponse(
                        e.message,
                        200,
                        null
                    )
            }
            res?.let {
                when (it.isSuccess) {
                    true -> {
                        Log.d(TAG, "loginByPasswd: get token: ${it.data}")
                        Preferences.saveString(KeyPool.TOKEN, it.data)
                        onSuccess.invoke()
                    }

                    else -> {
                        ToastUtil.showToast("登陆失败: ${res.msg}")
                    }
                }
            }
        }

    fun loginByVerify(loginAccount: LoginAccount, onSuccess: () -> Unit) = viewModelScope.launch {
        var res: LoginResponse?
        try {
            res = loginService.login(
                LoginService.Companion.LoginRequest(
                    LoginService.MODE_VERIFY,
                    null,
                    null,
                    loginAccount.tel,
                    Preferences.getString(KeyPool.ID, ""),
                    loginAccount.code
                )
            )
        } catch (e: Exception) {
            res = LoginResponse(
                e.localizedMessage,
                200,
                e.localizedMessage
            )
        }
        res?.let {
            when (it.isSuccess) {
                true -> {
                    Preferences.saveString(KeyPool.TOKEN, it.data)
                    onSuccess.invoke()
                }

                else -> {
                    ToastUtil.showToast("登陆失败: ${it.msg}")
                }
            }
        }

    }
    fun handleIntent(action: Action){
        when(action){
            Action.IntoLogin -> {
                _uiStatus.value = UiStatus.Login()
            }
            Action.IntoForget -> {
                _uiStatus.value = UiStatus.Forget()
            }
            Action.IntoRegister -> {
                _uiStatus.value = UiStatus.Register(1)
            }
            is Action.SwitchLoginMethod -> {
                _uiStatus.value = UiStatus.Login(action.loginMethod)
            }
            is Action.Register -> {
                register(action.registerAccount, action.onSuccess)
            }
            is Action.Login -> {
                if(uiStatus.value.sendCount == 0) {
                    when (action.loginMethod) {

                        LoginMethod.BY_PASSWD -> {
                            loginByPasswd(action.loginAccount, action.onSuccess)
                        }

                        LoginMethod.BY_VERIFY_CODE -> {
                            loginByVerify(action.loginAccount, action.onSuccess)
                        }
                    }
                }
            }
            is Action.Forget -> {
                forget(action.forgetAccount, action.onSuccess)
            }
            is Action.SendCode ->{
                sendCode(action.tel, action.onSuccess)
            }
            Action.Back -> {
                (uiStatus.value as? UiStatus.Register)?.let { registerStatus ->
                    if(registerStatus.step > 1) {
                        _uiStatus.value = registerStatus.copy(step = registerStatus.step - 1)
                    }
                }
            }
            Action.Next -> {
                (uiStatus.value as? UiStatus.Register)?.let { registerStatus ->
                    if (registerStatus.step < 3) {
                        _uiStatus.value = registerStatus.copy(step = registerStatus.step + 1)
                    }
                }
            }
        }
    }

    fun judgeUserName(name: String, onSuccess: () -> Unit) = viewModelScope.launch {
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
    fun forget(forgetAccount: ForgetAccount, onSuccess: () -> Unit) = viewModelScope.launch{
        val res: BaseResponse<String?> = try {
            forgetService.findPassword(ForgetService.Companion.ForgetRequest(forgetAccount.tel, forgetAccount.verifyCode, forgetAccount.passwd))

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
    fun register(registerAccount: RegisterAccount, onSuccess: () -> Unit) = viewModelScope.launch {
        var registerResponse : BaseResponse<String?>?
        try {
            val request = RegisterService.Companion.RegisterRequest(registerAccount.name, registerAccount.tel, registerAccount.verifyCode, registerAccount.passwd)
            registerResponse = registerService.register(request)
        }
        catch (e: Exception){
            registerResponse = BaseResponse( e.localizedMessage, 200, e.localizedMessage)
        }
        registerResponse?.let{
            when(it.isSuccess){
                true -> {
                    onSuccess.invoke()
                }
                else ->{
                    ToastUtil.showToast("注册失败: ${it.msg}")
                }
            }
        }
    }
    enum class LoginMethod{
        BY_PASSWD,
        BY_VERIFY_CODE
    }
    sealed class UiStatus: Cloneable{
        var sendCount: Int = 0

        data class Login(val loginMethod: LoginMethod = LoginMethod.BY_PASSWD): UiStatus(){
            override fun clone(): Any {
                return super.clone()
            }
        }
        data class Register(var step: Int, var tel: String = "", var verifyCode: String = "", var passwd: String = "", var name: String = ""): UiStatus()

        class Forget:UiStatus()
    }
    sealed interface Action{
        class Login(val loginMethod: LoginMethod, val loginAccount: LoginAccount, val onSuccess: () -> Unit = {}): Action
        class Register(val registerAccount: RegisterAccount, val onSuccess: () -> Unit = {}): Action

        class Forget(val forgetAccount: ForgetAccount, val onSuccess: () -> Unit = {}): Action
        object Back: Action
        object Next: Action
        object IntoLogin: Action
        object IntoForget: Action
        object IntoRegister: Action
        class SendCode(val tel: String, val onSuccess: () -> Unit = {}): Action

        class SwitchLoginMethod(val loginMethod: LoginMethod): Action
    }

}