package com.saigyouji.android.composetest.di

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.saigyouji.android.composetest.net.LoginService
import com.saigyouji.android.composetest.net.RegisterService
import com.saigyouji.android.composetest.net.VerifyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
object LoginActivityModule {
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }
    @Provides
    fun provideRegisterService(retrofit: Retrofit): RegisterService{
        return retrofit.create(RegisterService::class.java)
    }
    @Provides
    fun provideVerifyService(retrofit: Retrofit): VerifyService{
        return retrofit.create(VerifyService::class.java)
    }

}