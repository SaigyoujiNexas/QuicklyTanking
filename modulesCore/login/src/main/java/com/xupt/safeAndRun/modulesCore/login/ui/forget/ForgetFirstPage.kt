package com.xupt.safeAndRun.modulesCore.login.ui.forget

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.utils.ToastUtil
import com.xupt.safeAndRun.modulesCore.login.mvvm.ForgetViewModel
import com.xupt.safeAndRun.modulesCore.login.mvvm.LoginViewModel
import com.xupt.safeAndRun.modulesCore.login.mvvm.VerifyViewModel
import com.xupt.safeAndRun.modulesCore.login.net.response.LoginResponse
import com.xupt.safeAndRun.modulesCore.login.status.AnimatorController
import com.xupt.safeAndRun.modulesCore.login.ui.LoginFrontPage
import com.xupt.safeAndRun.modulesCore.login.util.checkInput
import com.xupt.safeAndRun.modulesbase.libbase.cache.Preferences
import com.xupt.safeAndRun.modulespublic.common.constant.KeyPool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@Composable
fun ForgetFirstPage(forgetViewModel: ForgetViewModel = viewModel(),
verifyViewModel: VerifyViewModel = viewModel()){
val crt_scp = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
                    BottomAppBar(
                        cutoutShape = MaterialTheme.shapes.small.copy(
                            CornerSize(50)
                        )
                    ) {

                    }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                //first, forget the password.
                forgetViewModel.forgetPassword {
                    //then login.
                    crt_scp.launch {
                        val response: LoginResponse
                        try {
                            response = forgetViewModel.login()
                        }catch (e: Exception) {
                            ToastUtil.showToast("登录失败 ${e.localizedMessage}")
                            return@launch
                        }
                        response.apply {
                            Preferences.saveString(KeyPool.TOKEN, data)
                            ToastUtil.showToast("登录成功")
                        }

                    }
                }
            }) {
                Icon(Icons.Filled.Done, contentDescription = "forget_next")
            }
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 125.dp)
            ) {
                Text (text = "忘记密码了?", modifier = Modifier.align(Alignment.CenterHorizontally))
                //tel input.
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = forgetViewModel.tel,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    onValueChange = {forgetViewModel.tel = it},
                    label = {Text(text = "手机号")})

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
                                end.linkTo(parent.end)
                            }
                            .animateContentSize(),
                        enabled = verifyViewModel.btClickable,
                        onClick = {
                            if (forgetViewModel.tel.checkInput(toastMsg = "手机号不可为空")) {
                                verifyViewModel.verify(forgetViewModel.tel){
                                    AnimatorController(verifyViewModel).start()
                                }
                            }
                        }
                    ) {
                        Text(text = verifyViewModel.btString)
                    }
                    OutlinedTextField(
                        value = forgetViewModel.code,
                        maxLines = 1,
                        onValueChange = { forgetViewModel.code = it },
                        label = { Text(text = "短信验证码") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .constrainAs(textField)
                            {
                                width = Dimension.fillToConstraints
                                start.linkTo(parent.start)
                                top.linkTo(parent.top, margin = 12.dp)
                                end.linkTo(button.start, margin=12.dp)
                            }
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = forgetViewModel.passwd,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = {forgetViewModel.passwd = it},
                    label = {Text(text = "新密码")})
            }
        }
    )
}

@Preview
@Composable
fun PreviewForgetFirstPage(){
    ForgetFirstPage()
}