package com.saigyouji.android.composetest.ui

import android.text.TextUtils
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.util.UUIDUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.utils.ToastUtil
import com.example.modulespublic.common.constant.RoutePath
import com.saigyouji.android.composetest.NavPath.register_first
import com.saigyouji.android.composetest.mvvm.LoginViewModel
import com.saigyouji.android.composetest.mvvm.VerifyViewModel
import com.saigyouji.android.composetest.net.LoginService
import com.saigyouji.android.composetest.status.AnimatorController
import com.saigyouji.android.composetest.ui.register.RegisterFirstPage
import com.saigyouji.android.composetest.util.findActivity
import java.util.*

object LoginFrontPage {
    sealed class LoginState {
        object Passwd : LoginState()
        object Verify : LoginState()
        fun isLoginByPasswd() = this == Passwd
        fun toggleValue() = if (isLoginByPasswd()) Verify else Passwd
    }


    @Composable
    fun rememberLoginState() = remember { mutableStateOf<LoginState>(LoginState.Passwd) }

    @ExperimentalAnimationApi
    @Composable
    fun LoginFrontPage(
        navController: NavHostController = rememberNavController(),
        loginViewModel: LoginViewModel = viewModel(),
        verifyViewModel: VerifyViewModel = viewModel(),
        loginState: MutableState<LoginState> = rememberLoginState()
    ) {

        val context = LocalContext.current.findActivity()
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
                    if(checkInput(loginViewModel.tel, "手机号不能为空")
                            ) {
                        if (loginState.value.isLoginByPasswd()) {
                            if(checkInput(loginViewModel.passwd, "密码不能为空"))
                                loginViewModel.loginByPasswd(onSuccess = {
                                    ToastUtil.showToast("login success")
                              //      ARouter.getInstance().build(RoutePath.MAIN).navigation(
                              //          context)
                              //      context?.finish()
                                })
                        }
                        else {
                            if(checkInput(loginViewModel.verifyCode, "验证码不能为空", otherJudge = {
                                if(it.length < 4)
                                {
                                    ToastUtil.showToast("验证码应为4位数字")
                                    false
                                }
                                true
                            }))
                                loginViewModel.loginByVerify(onSuccess = {
                                //    ToastUtil.showToast("login success")
                                    ARouter.getInstance().build(RoutePath.MAIN).navigation(context)
                                    context?.finish()
                                })
                        }
                    }
                }) {
                    Icon(Icons.Filled.Done, contentDescription = "next")
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .padding(top = 125.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "欢迎使用， 请输入您的手机号",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    OutlinedTextField(
                        value = loginViewModel.tel,
                        onValueChange = { loginViewModel.tel = it },
                        label = { Text(text = "手机号") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 24.dp)
                    )
                    if (loginState.value.isLoginByPasswd()) {
                        OutlinedTextField(
                            value = loginViewModel.passwd,
                            onValueChange = { loginViewModel.passwd = it },
                            label = { Text(text = "密码") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .padding(top = 24.dp)
                        )
                    } else
                    {
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
                                    }
                                    .animateContentSize()
                                ,
                                enabled = verifyViewModel.btClickable,
                                onClick = {
                                    if(checkInput(str = loginViewModel.tel, toastMsg = "手机号不可为空"))
                                    {
                                        loginViewModel.sendCode{
                                            AnimatorController(verifyViewModel).start()
                                        }
                                    }
                                }
                            ) {
                                Text(text = verifyViewModel.btString)
                            }
                            OutlinedTextField(
                                value = loginViewModel.verifyCode,
                                maxLines = 1,
                                onValueChange = { loginViewModel.verifyCode = it },
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

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, top = 24.dp)
                    ) {
                        Row {
                            Text(
                                text = if (loginState.value.isLoginByPasswd())
                                    "使用短信验证码登录"
                                else
                                    "使用账号密码登录",
                                color = Color.Gray,
                                modifier = Modifier
                                    .background(color = Color.Transparent)
                                    .clickable(
                                        enabled = true,
                                        role = Role.Button,
                                        onClick = {
                                            loginState.value = loginState.value.toggleValue()
                                        })
                                    .wrapContentSize()
                            )
                            Text(text = "忘记密码", color = Color.Gray,
                                modifier = Modifier
                                    .background(Color.Transparent)
                                    .clickable {

                                    }
                            )
                        }
                    }
                }
            }
        )

    }

    private fun checkInput(str: String, toastMsg: String, otherJudge:(String) -> Boolean = {true}): Boolean{
        if(TextUtils.isEmpty(str)) {
            ToastUtil.showToast(toastMsg)
            return false
        }
        return otherJudge.invoke(str)
    }


}

@ExperimentalAnimationApi
@Preview
@Composable
fun previewLoginFrontPage(){
    LoginFrontPage.LoginFrontPage()
}

