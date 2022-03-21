package com.example.modulespublic.common.di;

import com.example.modulespublic.common.net.ApiService;
import com.example.modulespublic.common.net.GetRequest_Interface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;

import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class ApplicationModule {

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }
    @Provides
    @Singleton
    public GetRequest_Interface provideRequestInterfaces(Retrofit retrofit){
        return retrofit.create(GetRequest_Interface.class);
    }
}
