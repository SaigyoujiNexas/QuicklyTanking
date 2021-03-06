package com.xupt.safeAndRun.modulesCore.login.ui.register

import android.text.TextUtils
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_third
import com.xupt.safeAndRun.modulesCore.login.mvvm.RegisterViewModel
import com.xupt.safeAndRun.modulesCore.login.util.checkInput
import com.xupt.safeAndRun.modulesCore.login.util.findActivity

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
                if(registerViewModel.passwd.checkInput("?????????????????????") && passwdVrf.checkInput("?????????????????????"){
                        TextUtils.equals(registerViewModel.passwd, passwdVrf)
                    })
                {
                    navController.navigate(register_third)
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
                    text = "??????, ????????????????????????????????????",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = registerViewModel.passwd,
                    onValueChange = { registerViewModel.passwd = it
                        VrfStatus = TextUtils.isEmpty(registerViewModel.passwd)|| TextUtils.isEmpty(passwdVrf) ||
                                TextUtils.equals(registerViewModel.passwd, passwdVrf)
                                    },
                    label = { Text(text = "????????????") },
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
                        VrfStatus = TextUtils.isEmpty(registerViewModel.passwd)|| TextUtils.isEmpty(passwdVrf) || TextUtils.equals(registerViewModel.passwd, passwdVrf) },
                    label = { Text(text = "????????????") },
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
                        Icon(Icons.Filled.Warning, contentDescription = "?????????????????????", tint = Color.Red)
                        Text(text = "???????????????????????????", fontSize = 12.sp)
                    }
                }
            }
        }
    )
}
@Preview
@Composable
fun RegisterSecondPreview(){
    RegisterSecondPage()
}