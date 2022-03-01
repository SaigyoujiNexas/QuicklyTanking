package com.saigyouji.android.composetest.ui.register

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.common.utils.ToastUtil
import com.example.modulespublic.common.utils.StringCountDownTimer
import com.saigyouji.android.composetest.mvvm.RegisterViewModel
import com.saigyouji.android.composetest.mvvm.VerifyViewModel
import com.saigyouji.android.composetest.status.AnimatorController
import com.saigyouji.android.composetest.util.checkInput
import com.saigyouji.android.composetest.util.findActivity

/**
 * @author Yuki
 * This is register first page, notify user input the phone number and input the
 * correct verify code from participant phone number that will to register.
 */
@Composable
fun RegisterFirstPage(verifyViewModel: VerifyViewModel = viewModel(),
                      registerViewModel: RegisterViewModel = viewModel(),
                      navController: NavHostController = rememberNavController()
){
    var tel by remember { mutableStateOf("") }
    var verify by remember { mutableStateOf("") }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {
                Icons.Filled.Create
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(tel.checkInput("请输入手机号") && verify.checkInput("请输入短信验证码"){
                    if(it.length != 6) {
                        ToastUtil.showToast("请输入6位有效数字")
                        false }
                        true
                    })
                {
                    registerViewModel.tel = tel
                    registerViewModel.verify = verify
                    navController.navigate("register_second")
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
                text = "请输入您的手机号",
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
                        enabled = verifyViewModel.btClickable
                        ,
                        onClick = {
                            if(tel.checkInput("请输入手机号"))
                                verifyViewModel.verify(tel) {
                                    AnimatorController(verifyViewModel).start()
                                }
                        }
                    ) {
                        Text(text = verifyViewModel.btString)
                    }
                    OutlinedTextField(
                        value = verify,
                        maxLines = 1,
                        onValueChange = { verify = it },
                        label = { Text(text = "短信验证码") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .constrainAs(textField)
                            {
                                width = Dimension.preferredWrapContent
                                start.linkTo(parent.start, margin = 24.dp)
                                top.linkTo(parent.top, margin = 24.dp)
                                end.linkTo(button.start, margin = 12.dp)
                            }
                    )
                }
            }
        }
    )
}
@Preview
@Composable
fun preView(){
    RegisterFirstPage()
}