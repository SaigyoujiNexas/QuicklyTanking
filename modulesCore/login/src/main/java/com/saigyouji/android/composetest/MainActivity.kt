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
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.utils.ToastUtil
import com.example.modulespublic.common.constant.RoutePath
import com.saigyouji.android.composetest.mvvm.LoginViewModel
import com.saigyouji.android.composetest.mvvm.RegisterViewModel
import com.saigyouji.android.composetest.mvvm.VerifyViewModel
import com.saigyouji.android.composetest.net.LoginService
import com.saigyouji.android.composetest.ui.LoginFrontPage
import com.saigyouji.android.composetest.ui.register.*
import com.saigyouji.android.composetest.ui.theme.QuicklyTankingTheme
import com.saigyouji.android.composetest.widget.FabIcon
import com.saigyouji.android.composetest.widget.FabOption
import com.saigyouji.android.composetest.widget.MultiFabItem
import com.saigyouji.android.composetest.widget.MultiFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainActivity"

@Route(path = RoutePath.LOG_IN)
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
                    NavHost(navController = navController, startDestination = "register_fifth"){
                        composable("login"){
                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
                            LoginFrontPage.LoginFrontPage(navController,loginViewModel,verifyViewModel) }
                        composable("register_first"){
                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
                            RegisterFirstPage(verifyViewModel,registerViewModel, navController)}
                        composable("register_second"){ RegisterSecondPage(registerViewModel, navController)}
                        composable("register_third"){ RegisterThirdPage(registerViewModel, navController)}
                        composable("register_forth"){ RegisterForthPage(registerViewModel, navController)}
                        composable("register_fifth"){ RegisterFifthPage(registerViewModel, navController)}
                    }
                }
                
            }
        }
    }
}
