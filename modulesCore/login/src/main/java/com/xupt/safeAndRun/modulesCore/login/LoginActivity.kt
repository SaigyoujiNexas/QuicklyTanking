package com.xupt.safeAndRun.modulesCore.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xupt.safeAndRun.modulesCore.login.NavPath.forget
import com.xupt.safeAndRun.modulesCore.login.NavPath.login
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_first
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_fourth
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_second
import com.xupt.safeAndRun.modulesCore.login.NavPath.register_third
import com.xupt.safeAndRun.modulesCore.login.mvvm.LoginViewModel
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


    private val loginViewModel: LoginViewModel by viewModels()
//    private val registerViewModel: RegisterViewModel by viewModels()

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
//                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
                            LoginFrontPage.LoginFrontPage(loginViewModel, navController) }
                        composable(register_first){
//                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
                            RegisterFirstPage(loginViewModel, navController)}
                        composable(register_second){ RegisterSecondPage(loginViewModel, navController) }
                        composable(register_third){ RegisterThirdPage(loginViewModel, navController)}
//                        composable(register_fourth){ RegisterForthPage(loginViewModel, navController)}
//                        composable(register_fifth){ RegisterFifthPage(loginViewModel, navController)}
                        composable(forget){
//                            val verifyViewModel = hiltViewModel<VerifyViewModel>()
//                            val forgetViewModel = hiltViewModel<ForgetViewModel>()
                            ForgetFirstPage(loginViewModel, navController)}
                    }
                    val uiState by loginViewModel.uiStatus
                    when(uiState){
                        is LoginViewModel.UiStatus.Login -> {
                            navController.navigate(login)
                        }
                        is LoginViewModel.UiStatus.Register -> {
                            when((uiState as LoginViewModel.UiStatus.Register).step){
                                1 -> navController.navigate(register_first)
                                2 -> navController.navigate(register_second)
                                3 -> navController.navigate(register_third)
                                4 -> navController.navigate(register_fourth)
                            }
                        }
                        is LoginViewModel.UiStatus.Forget -> {
                            navController.navigate(forget)
                        }
                    }
                }
                
            }
        }
    }
}
