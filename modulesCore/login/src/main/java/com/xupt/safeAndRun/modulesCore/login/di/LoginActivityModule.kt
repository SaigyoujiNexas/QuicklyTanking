package com.xupt.safeAndRun.modulesCore.login.di

import com.xupt.safeAndRun.modulesCore.login.net.ForgetService
import com.xupt.safeAndRun.modulesCore.login.net.LoginService
import com.xupt.safeAndRun.modulesCore.login.net.RegisterService
import com.xupt.safeAndRun.modulesCore.login.net.VerifyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object LoginActivityModule {
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }
    @Provides
    fun provideRegisterService(retrofit: Retrofit): RegisterService {
        return retrofit.create(RegisterService::class.java)
    }
    @Provides
    fun provideVerifyService(retrofit: Retrofit): VerifyService {
        return retrofit.create(VerifyService::class.java)
    }
    @Provides
    fun provideForgetService(retrofit: Retrofit): ForgetService {
        return retrofit.create(ForgetService::class.java)
    }
}