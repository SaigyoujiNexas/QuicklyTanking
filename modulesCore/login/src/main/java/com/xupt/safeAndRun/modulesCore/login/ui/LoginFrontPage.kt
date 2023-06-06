package com.xupt.safeAndRun.modulesCore.login.ui

import android.os.CountDownTimer
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.common.utils.ToastUtil
import com.xupt.safeAndRun.modulesCore.login.NavPath
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_first
import com.xupt.safeAndRun.modulesCore.login.entity.LoginAccount
import com.xupt.safeAndRun.modulesCore.login.mvvm.LoginViewModel
import com.xupt.safeAndRun.modulesCore.login.util.findActivity
import com.xupt.safeAndRun.modulespublic.common.constant.RoutePath

object LoginFrontPage {

    @ExperimentalAnimationApi
    @Composable
    fun LoginFrontPage(
        loginViewModel: LoginViewModel = viewModel(),
        navController: NavHostController = rememberNavController(),
    ) {
        val uiStatus by remember { loginViewModel.uiStatus }
        if(uiStatus !is LoginViewModel.UiStatus.Login){
            throw IllegalStateException("LoginFrontPage must be called when uiStatus is Login")
        }
        val loginState = uiStatus as LoginViewModel.UiStatus.Login
        var tel by remember{ mutableStateOf("")}
        var passwd by remember{ mutableStateOf("")}
        var verifyCode by remember { mutableStateOf("") }
        val context = LocalContext.current.findActivity()
        var countDownTime by remember { mutableStateOf(0) }
        val counter = object:CountDownTimer((60) * 1000L, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                countDownTime = (millisUntilFinished / 1000L).toInt()
            }

            override fun onFinish() {
                countDownTime = 0;
            }
        }
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    cutoutShape = MaterialTheme.shapes.small.copy(
                        CornerSize(percent = 50)
                    )
                ) {
                    Text(text = "没有账号?创建一个",
                        Modifier
                            .clickable(enabled = true) {
                                navController.navigate(register_first)
                            }
                            .padding(start = 24.dp)
                    )
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    if (checkInput(tel, "手机号不能为空")
                    ) {
                        if (loginState.loginMethod == LoginViewModel.LoginMethod.BY_PASSWD){
                            if (checkInput(passwd, "密码不能为空")){
                                loginViewModel.handleIntent(
                                    LoginViewModel.Action.Login(
                                        LoginViewModel.LoginMethod.BY_PASSWD,
                                        LoginAccount(tel, passwd)){
                                        ToastUtil.showToast("login success")
                                        val request = NavDeepLinkRequest.Builder
                                            .fromUri(RoutePath.MAIN.toUri())
                                            .build()
                                        navController.navigate(request)
                                })}
                        } else {
                            if (checkInput(verifyCode, "验证码不能为空", otherJudge = {
                                    if (it.length < 4) {
                                        ToastUtil.showToast("验证码应为4位数字")
                                        false
                                    } else {
                                        true
                                    }
                                }))
                                loginViewModel.handleIntent(
                                    LoginViewModel.Action.Login(
                                        LoginViewModel.LoginMethod.BY_VERIFY_CODE,
                                        LoginAccount(tel, verifyCode)){
                                        val request = NavDeepLinkRequest.Builder
                                            .fromUri(RoutePath.MAIN.toUri())
                                            .build()
                                        navController.navigate(request)
                                    //    ToastUtil.showToast("login success")
                                    context?.finish()
                                })
                        }
                    }
                }) {
                    Icon(Icons.Filled.Done, contentDescription = "next")
                }
            },
            content = { paddingValue ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(paddingValue)
                        .padding(top = 125.dp)

                ) {
                    Text(
                        text = "欢迎使用， 请输入您的手机号",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    OutlinedTextField(
                        value = tel,
                        onValueChange = { tel = it },
                        label = { Text(text = "手机号") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 24.dp)
                    )
                    if (loginState.loginMethod == LoginViewModel.LoginMethod.BY_PASSWD) {
                        OutlinedTextField(
                            value = passwd,
                            onValueChange = { passwd = it },
                            label = { Text(text = "密码") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .padding(top = 24.dp)
                        )
                    } else {
                        ConstraintLayout(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .animateContentSize()
                        ) {
                            val (textField, button) = createRefs()
                            Button(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .constrainAs(button) {
                                        top.linkTo(textField.top)
                                        bottom.linkTo(textField.bottom)
                                        end.linkTo(parent.end, margin = 24.dp)
                                    }.animateContentSize(),
                                enabled = countDownTime == 0,
                                onClick = {
                                    counter.start()
                                    if (checkInput(tel, "手机号不可为空")) {
                                        loginViewModel.handleIntent(LoginViewModel.Action.SendCode(tel){
//                                            AnimatorController().start()
                                        })
                                    }
                                }
                            ) {
                                Text(text = "${countDownTime.takeIf { it != 0 }?: "发送验证码"}")
                            }
                            OutlinedTextField(
                                value = verifyCode,
                                maxLines = 1,
                                onValueChange = { verifyCode = it },
                                label = { Text(text = "短信验证码") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .constrainAs(textField)
                                    {
                                        width = Dimension.fillToConstraints
                                        start.linkTo(parent.start, margin = 24.dp)
                                        top.linkTo(parent.top, margin = 24.dp)
                                        end.linkTo(button.start, margin = 12.dp)
                                    }
                            )
                        }
                    }
                    //Bottom Line.
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = if (loginState.loginMethod == LoginViewModel.LoginMethod.BY_PASSWD)
                                "使用短信验证码登录"
                            else
                                "使用账号密码登录",
                            color = Color.Gray,
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Color.Transparent)
                                .clickable(
                                    enabled = true,
                                    role = Role.Button,
                                    onClick = {
                                        val targetStatus = if(loginState.loginMethod == LoginViewModel.LoginMethod.BY_PASSWD)
                                            LoginViewModel.LoginMethod.BY_VERIFY_CODE
                                        else
                                            LoginViewModel.LoginMethod.BY_PASSWD
                                        loginViewModel.handleIntent(LoginViewModel.Action.SwitchLoginMethod(targetStatus))
                                    })
                        )
                        Text(text = "忘记密码", color = Color.Gray,
                            modifier = Modifier
                                .wrapContentWidth()
                                .background(Color.Transparent)
                                .clickable {
                                    navController.navigate(NavPath.forget)
                                }
                        )
                    }

                }
            }
        )

    }

    private fun checkInput(
        str: String,
        toastMsg: String,
        otherJudge: (String) -> Boolean = { true }
    ): Boolean {
        if (TextUtils.isEmpty(str)) {
            ToastUtil.showToast(toastMsg)
            return false
        }
        return otherJudge.invoke(str)
    }


}

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewLoginFrontPage() {
    LoginFrontPage.LoginFrontPage()
}

