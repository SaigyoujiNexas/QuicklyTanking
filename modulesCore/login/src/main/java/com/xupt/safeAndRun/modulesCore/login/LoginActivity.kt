package com.xupt.safeAndRun.modulesCore.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xupt.safeAndRun.modulesCore.login.NavPath.forget
import com.xupt.safeAndRun.modulespublic.common.constant.RoutePath
import com.xupt.safeAndRun.modulesCore.login.NavPath.login
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_fifth
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_first
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_fourth
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_second
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_third
import com.xupt.safeAndRun.modulesCore.login.mvvm.ForgetViewModel
import com.xupt.safeAndRun.modulesCore.login.mvvm.LoginViewModel
import com.xupt.safeAndRun.modulesCore.login.mvvm.RegisterViewModel
import com.xupt.safeAndRun.modulesCore.login.mvvm.VerifyViewModel
import com.xupt.safeAndRun.modulesCore.login.ui.LoginFrontPage
import com.xupt.safeAndRun.modulesCore.login.ui.forget.ForgetFirstPage
import com.xupt.safeAndRun.modulesCore.login.ui.register.*
import com.xupt.safeAndRun.modulesCore.login.ui.theme.QuicklyTankingTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

//@Route(path = RoutePath.LOG_IN)
@AndroidEntryPoint
@ExperimentalAnimationApi
class LoginActivity : AppCompatActivity(){


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
                    NavHost(navController = navController, startDestination = login){
                        composable(login){
                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
                            LoginFrontPage.LoginFrontPage(navController,loginViewModel,verifyViewModel) }
                        composable(register_first){
                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
                            RegisterFirstPage(verifyViewModel,registerViewModel, navController)}
                        composable(register_second){ RegisterSecondPage(registerViewModel, navController) }
                        composable(register_third){ RegisterThirdPage(registerViewModel, navController)}
                        composable(register_fourth){ RegisterForthPage(registerViewModel, navController)}
                        composable(register_fifth){ RegisterFifthPage(registerViewModel, navController)}
                        composable(forget){
                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
                            val forgetViewModel = hiltViewModel<ForgetViewModel>()
                            ForgetFirstPage(forgetViewModel, verifyViewModel)}
                    }
                }
                
            }
        }
    }
}
