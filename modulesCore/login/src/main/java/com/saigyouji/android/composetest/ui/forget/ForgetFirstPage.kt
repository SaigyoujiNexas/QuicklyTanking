package com.saigyouji.android.composetest.ui.forget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saigyouji.android.composetest.mvvm.ForgetViewModel

@Composable
fun ForgetFirstPage(forgetViewModel: ForgetViewModel = viewModel()){
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
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Done, contentDescription = "forget_next")
            }
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text (text = "忘记密码了?");
                OutlinedTextField(value = forgetViewModel.tel, onValueChange = {

                },
                label = {Text(text = "手机号")})
            }
        }
    )
}

@Preview
@Composable
fun PreviewForgetFirstPage(){
    ForgetFirstPage()
}