package com.xupt.safeAndRun.modulespublic.common.di;

import com.xupt.safeAndRun.modulespublic.common.net.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;

import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class ApplicationModule {
    /**
     *
     * @param retrofit the retrofit instance that hilt build in libbase.
     *
     * @return apiService instance.
     */
    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

}
