package com.saigyouji.android.composetest.ui.register

import android.text.TextUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
 * This jetpack compose function is Login Third Page.
 * notify user input the user name.
 * after he inputed, the account is created.
 * if user exit after this page, background should put the default value in the database.
 */
@Composable
fun RegisterThirdPage(registerViewModel: RegisterViewModel = viewModel(),
                      navController: NavHostController = rememberNavController()
){

    val context = LocalContext.current.findActivity()
    // the status of the two passwd are same.
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
                if(registerViewModel.name.checkInput("用户昵称不能为空")) {
                    navController.navigate("register_forth")
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
                    text = "现在, 给您的账户取一个好听的名字吧",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    value = registerViewModel.name,
                    onValueChange = { registerViewModel.name = it },
                    label = { Text(text = "用户昵称") },
                    maxLines = 1,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp)
                )
            }
        }
    )
}