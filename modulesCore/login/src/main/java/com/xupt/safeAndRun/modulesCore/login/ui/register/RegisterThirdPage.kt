package com.xupt.safeAndRun.modulesCore.login.ui.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.xupt.safeAndRun.modulesCore.login.entity.LoginAccount
import com.xupt.safeAndRun.modulesCore.login.entity.RegisterAccount
import com.xupt.safeAndRun.modulesCore.login.mvvm.LoginViewModel
import com.xupt.safeAndRun.modulesCore.login.util.checkInput
import com.xupt.safeAndRun.modulesCore.login.util.findActivity
import com.xupt.safeAndRun.modulespublic.common.constant.RoutePath

/**
 * @author Yuki
 * This jetpack compose function is Login Third Page.
 * notify user input the user name.
 * after he inputed, the account is created.
 * if user exit after this page, background should put the default value in the database.
 */
@Composable
fun RegisterThirdPage(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){

    val context = LocalContext.current.findActivity()
    // the status of the two passwd are same.
    var name by remember{mutableStateOf("")}
    val uiStatus by remember{ loginViewModel.uiStatus }
    val registerStatus = uiStatus as LoginViewModel.UiStatus.Register
    Scaffold(
        bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                registerStatus.name = name
                if(name.checkInput("用户昵称不能为空")) {
                    loginViewModel.judgeUserName(name) {
                        loginViewModel.handleIntent(
                            LoginViewModel.Action.Register(
                                RegisterAccount(tel = registerStatus.tel, name = registerStatus.name, passwd = registerStatus.passwd, verifyCode = registerStatus.verifyCode)){
                                loginViewModel.handleIntent(LoginViewModel.Action.Login(LoginViewModel.LoginMethod.BY_PASSWD, LoginAccount(registerStatus.tel, registerStatus.passwd)){
                                    navController.navigate(RoutePath.MAIN)
                                    context?.finish()
                                })
                            })
                            }
                        }
                    }){
                Icon(Icons.Filled.ArrowForward, contentDescription = "next")
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(top = 125.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "现在, 给您的账户取一个好听的名字吧",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "用户昵称") },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp)
                )
            }
        }
    )
}