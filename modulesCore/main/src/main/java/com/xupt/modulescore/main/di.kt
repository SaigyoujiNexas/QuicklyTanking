package com.xupt.modulescore.main

import com.xupt.modulescore.main.net.GetRequest_Interface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class di {
    @Provides
    @Singleton
    fun provideRequestInterfaces(retrofit: Retrofit): GetRequest_Interface? {
        return retrofit.create(GetRequest_Interface::class.java)
    }
}