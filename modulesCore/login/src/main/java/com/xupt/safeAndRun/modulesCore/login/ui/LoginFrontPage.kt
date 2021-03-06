package com.xupt.safeAndRun.modulesCore.login.ui

import android.text.TextUtils
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.compose.rememberNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.utils.ToastUtil
import com.xupt.safeAndRun.modulesCore.login.NavPath
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_first
import com.xupt.safeAndRun.modulesCore.login.mvvm.LoginViewModel
import com.xupt.safeAndRun.modulesCore.login.mvvm.VerifyViewModel
import com.xupt.safeAndRun.modulesCore.login.status.AnimatorController
import com.xupt.safeAndRun.modulesCore.login.util.findActivity
import com.xupt.safeAndRun.modulespublic.common.constant.RoutePath

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
                    Text(text = "?????????????????????????",
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
                    if (checkInput(loginViewModel.tel, "?????????????????????")
                    ) {
                        if (loginState.value.isLoginByPasswd()) {
                            if (checkInput(loginViewModel.passwd, "??????????????????"))
                                loginViewModel.loginByPasswd(onSuccess = {
                                    ToastUtil.showToast("login success")
                                    Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
                                        ARouter.getInstance().build(RoutePath.MAIN).navigation(
                                              context)
                                          context?.finish()
                                })
                        } else {
                            if (checkInput(loginViewModel.verifyCode, "?????????????????????", otherJudge = {
                                    if (it.length < 4) {
                                        ToastUtil.showToast("???????????????4?????????")
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
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 125.dp)
                ) {
                    Text(
                        text = "??????????????? ????????????????????????",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    OutlinedTextField(
                        value = loginViewModel.tel,
                        onValueChange = { loginViewModel.tel = it },
                        label = { Text(text = "?????????") },
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
                            label = { Text(text = "??????") },
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
                                    }
                                    .animateContentSize(),
                                enabled = verifyViewModel.btClickable,
                                onClick = {
                                    if (checkInput(
                                            str = loginViewModel.tel,
                                            toastMsg = "?????????????????????"
                                        )
                                    ) {
                                        loginViewModel.sendCode {
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
                                label = { Text(text = "???????????????") },
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
                    Row(modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = if (loginState.value.isLoginByPasswd())
                                "???????????????????????????"
                            else
                                "????????????????????????",
                            color = Color.Gray,
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Color.Transparent)
                                .clickable(
                                    enabled = true,
                                    role = Role.Button,
                                    onClick = {
                                        loginState.value = loginState.value.toggleValue()
                                    })
                        )
                        Text(text = "????????????", color = Color.Gray,
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
fun previewLoginFrontPage() {
    LoginFrontPage.LoginFrontPage()
}

