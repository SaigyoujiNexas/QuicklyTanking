package com.example.modulescore.login.di;

import com.example.modulescore.login.net.LoginService;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(ActivityComponent.class)
public class LoginActivityModule {

    @Provides
    public LoginService provideLoginService(Retrofit retrofit) {
        return retrofit.create(LoginService.class);
    }


}
