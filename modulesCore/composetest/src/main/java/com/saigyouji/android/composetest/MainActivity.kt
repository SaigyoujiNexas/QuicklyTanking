package com.saigyouji.android.composetest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.common.utils.ToastUtil
import com.saigyouji.android.composetest.mvvm.LoginViewModel
import com.saigyouji.android.composetest.mvvm.RegisterViewModel
import com.saigyouji.android.composetest.mvvm.VerifyViewModel
import com.saigyouji.android.composetest.net.LoginService
import com.saigyouji.android.composetest.ui.LoginFrontPage
import com.saigyouji.android.composetest.ui.register.RegisterFirstPage
import com.saigyouji.android.composetest.ui.register.RegisterForthPage
import com.saigyouji.android.composetest.ui.register.RegisterSecondPage
import com.saigyouji.android.composetest.ui.register.RegisterThirdPage
import com.saigyouji.android.composetest.ui.theme.QuicklyTankingTheme
import com.saigyouji.android.composetest.widget.FabIcon
import com.saigyouji.android.composetest.widget.FabOption
import com.saigyouji.android.composetest.widget.MultiFabItem
import com.saigyouji.android.composetest.widget.MultiFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"
@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : AppCompatActivity(){


    lateinit var navController : NavHostController


     val loginViewModel: LoginViewModel by viewModels()
    val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuicklyTankingTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "register_forth"){
                        composable("first"){ ExpandFloatingButton(navController)}
                        composable("login"){
                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
                            LoginFrontPage.LoginFrontPage(navController,loginViewModel,verifyViewModel) }
                        composable("register_first"){
                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
                            RegisterFirstPage(verifyViewModel,registerViewModel, navController)}
                        composable("register_second"){ RegisterSecondPage(registerViewModel, navController)}
                        composable("register_third"){ RegisterThirdPage(registerViewModel, navController)}
                        composable("register_forth"){ RegisterForthPage(registerViewModel, navController)}
                    }
                }
                
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@ExperimentalAnimationApi
@Composable
fun ExpandFloatingButton(navController: NavHostController){
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Expand Floating Action Button",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        },
        floatingActionButton = {
            MultiFloatingActionButton(
                items = listOf(
                    MultiFabItem(
                        id = 1,
                        iconRes = R.drawable.ic_baseline_error_24,
                        label = "Add User"
                    ),
                    MultiFabItem(
                        id = 2,
                        iconRes = R.drawable.ic_baseline_error_24,
                        label = "Add User"
                    ),
                    MultiFabItem(
                        id = 3,
                        iconRes = R.drawable.ic_baseline_error_24,
                        label = "Add User"
                    )
                ),
                fabIcon = FabIcon(iconRes = R.drawable.ic_baseline_error_24,
                iconRotate = 45f),
                onFabItemClicked = {
                    Toast.makeText(context, it.label, Toast.LENGTH_SHORT).show()
                    navController.navigate("login")
                },
                fabOption = FabOption(
                    iconTint = Color.White,
                    showLabel = true
                )
            )
        },
        content = {
            LazyColumn(modifier = Modifier
                .background(Color.White)
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(20){ index ->
                    Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(10.dp, 5.dp, 10.dp, 5.dp)
                                .background(Color.White),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp)
                    ){
                        Column(
                            modifier = Modifier
                                .padding(5.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.back),
                                    contentDescription = "Item Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                                Column {
                                    Text(
                                        text = "Make it Easy ${index + 1}",
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    Text(
                                        text = "LOren Ipsum is simple Item ${index + 1}",
                                        color = Color.Gray,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuicklyTankingTheme {
        Greeting("Android")
    }
}