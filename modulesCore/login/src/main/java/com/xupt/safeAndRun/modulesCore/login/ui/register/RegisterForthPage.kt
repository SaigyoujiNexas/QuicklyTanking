package com.xupt.safeAndRun.modulesCore.login.ui.register

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.text.TextUtils
import android.widget.NumberPicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_fifth
import com.xupt.safeAndRun.modulesCore.login.mvvm.RegisterViewModel
import com.xupt.safeAndRun.modulesCore.login.util.findActivity

/**
 * @author Yuki
 * This jetpack compose function is register forth page.
 * notify user input his birthday and his weight and his height.
 * after that, link to register fifth page, notify user select a head photo.
 */
@Composable
fun RegisterForthPage(registerViewModel: RegisterViewModel = viewModel(),
        navController: NavHostController = rememberNavController()
){
    val context = LocalContext.current.findActivity()!!
    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("选择出生日期")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .build()
    val calendar: Calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    datePicker.addOnPositiveButtonClickListener {millis:Long->
        calendar.timeInMillis = millis
        registerViewModel.birthday = dateFormat.format(calendar)
    }
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
                navController.navigate(register_fifth)
            }) {
                Icon(Icons.Filled.ArrowForward, contentDescription = "next")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = 125.dp)
                .fillMaxWidth()
        ) {
            Text(
                fontSize = 16.sp,
                text = "现在, 我们需要一些您的个人数据\n以便为您定制最优方案",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .padding(start = 24.dp, top = 24.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ) {
                Text(
                    text = "您的生日: ${registerViewModel.birthday}", modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Button(
                    onClick = {
                        datePicker.show(context.supportFragmentManager, "tag")
                    },
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    if (TextUtils.isEmpty(registerViewModel.birthday))
                        Text(text = "点击设置")
                    else
                        Text(text = "重新设置")
                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 24.dp, top = 24.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = "您的身高(cm)", modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    AndroidView(factory = { context ->
                        val numberPicker = NumberPicker(context)
                        numberPicker.apply {
                            maxValue = 250
                            minValue = 3
                            setOnValueChangedListener { _, _, newVal -> registerViewModel.height = newVal.toString()}
                        }
                    }, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterVertically)
                ) {

                    Text(text = "您的体重(kg)", modifier = Modifier.align(Alignment.CenterHorizontally))
                    AndroidView(factory = { context ->
                        val numberPicker = NumberPicker(context)
                        numberPicker.apply {
                            maxValue = 200
                            minValue = 20
                            setOnValueChangedListener{_, _, newVal -> registerViewModel.weight = newVal.toString()}
                        }
                    }, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

@Preview
@Composable
fun previewRegisterForthPage(){
    RegisterForthPage()
}
