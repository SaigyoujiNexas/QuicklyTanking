package com.xupt.safeAndRun.modulesCore.login.ui.forget

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.xupt.safeAndRun.modulesCore.login.entity.ForgetAccount
import com.xupt.safeAndRun.modulesCore.login.entity.LoginAccount
import com.xupt.safeAndRun.modulesCore.login.mvvm.LoginViewModel
import com.xupt.safeAndRun.modulesCore.login.util.checkInput
import com.xupt.safeAndRun.modulesCore.login.util.findActivity
import com.xupt.safeAndRun.modulespublic.common.constant.RoutePath
import kotlinx.coroutines.launch

@Composable
fun ForgetFirstPage(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController = rememberNavController()) {

    val crt_scp = rememberCoroutineScope()
    val uiStatus by remember { loginViewModel.uiStatus }
    if(uiStatus !is LoginViewModel.UiStatus.Forget){
        throw Exception("uiStatus is not LoginViewModel.UiStatus.Forget")
    }
    val forgetUiStatus = uiStatus as LoginViewModel.UiStatus.Forget
    val context = LocalContext.current
    var tel by remember{ mutableStateOf("") }
    var passwd by remember{ mutableStateOf("") }
    var verifyCode by remember{ mutableStateOf("") }
    Scaffold(
        bottomBar = {
                    BottomAppBar(
                        cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(50))) {
                    }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                //first, forget the password.
                loginViewModel.handleIntent(LoginViewModel.Action.Forget(ForgetAccount(tel, passwd, verifyCode)){
                    //then login.
                    crt_scp.launch {
                        loginViewModel.handleIntent(LoginViewModel.Action.Login(LoginViewModel.LoginMethod.BY_PASSWD, LoginAccount(tel, passwd)){
                            navController.navigate(RoutePath.MAIN)
                            context.findActivity()?.finish()
                        })
                    }
                })
            }) {
                Icon(Icons.Filled.Done, contentDescription = "forget_next")
            }
        },
        content = { paddingValue->
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValue)
                    .padding(horizontal = 24.dp, vertical = 125.dp)
            ) {
                Text (text = "忘记密码了?", modifier = Modifier.align(Alignment.CenterHorizontally))
                //tel input.
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    value = tel,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    onValueChange = {tel = it},
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
                        enabled = forgetUiStatus.sendCount == 0,
                        onClick = {
                            if (tel.checkInput(toastMsg = "手机号不可为空")) {
                                loginViewModel.handleIntent(LoginViewModel.Action.SendCode(tel))
                            }
                        }
                    ) {
                        Text(text = if (forgetUiStatus.sendCount == 0) "发送验证码" else forgetUiStatus.sendCount.toString())
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
                    value = passwd,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { passwd = it },
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