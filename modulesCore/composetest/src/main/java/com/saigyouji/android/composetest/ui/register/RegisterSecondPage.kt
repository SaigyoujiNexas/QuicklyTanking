package com.saigyouji.android.composetest.ui.register

import android.text.TextUtils
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.saigyouji.android.composetest.mvvm.RegisterViewModel
import com.saigyouji.android.composetest.mvvm.VerifyViewModel
import com.saigyouji.android.composetest.util.checkInput
import com.saigyouji.android.composetest.util.findActivity

/**
 * @author Yuki
 * This function is register second page. Notify user setting the account password.
 * and verify password secondly the make sure user inputed which is user in mind.
 */

@Composable
fun RegisterSecondPage(registerViewModel: RegisterViewModel = viewModel(),
                       navController: NavHostController = rememberNavController()
){

    val context = LocalContext.current.findActivity()
    var passwd by remember { mutableStateOf("") }
    var passwdVrf by remember { mutableStateOf("") }
    // the status of the two passwd are same.
    var VrfStatus by remember{ mutableStateOf(true)}
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
                if(passwd.checkInput("请输入账号密码") && passwdVrf.checkInput("请再次确认密码"){
                        TextUtils.equals(passwd, passwdVrf)
                    })
                {
                    registerViewModel.passwd = passwd
                    navController.navigate("register_third")
                }
            }) {
                Icon(Icons.Filled.ArrowForward, contentDescription = "next")
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = 125.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "现在, 为您的账号设置一个密码吧",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = passwd,
                    onValueChange = { passwd = it
                        VrfStatus = TextUtils.isEmpty(passwd)|| TextUtils.isEmpty(passwdVrf) || TextUtils.equals(passwd, passwdVrf)
                                    },
                    label = { Text(text = "账号密码") },
                    maxLines = 1,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp)
                )
                OutlinedTextField(
                    value = passwdVrf,
                    onValueChange = {
                        passwdVrf = it
                        VrfStatus = TextUtils.isEmpty(passwd)|| TextUtils.isEmpty(passwdVrf) || TextUtils.equals(passwd, passwdVrf) },
                    label = { Text(text = "确认密码") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp)
                )
                if(!VrfStatus) {
                    Row(modifier = Modifier.padding(start = 24.dp, top = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Warning, contentDescription = "两次密码不一致", tint = Color.Red)
                        Text(text = "两次密码输入不一致", fontSize = 12.sp)
                    }
                }
            }
        }
    )
}
@Preview
@Composable
fun RegisterSecondPreview(){
    RegisterFirstPage()
}