package com.example.modulesbase.libbase.net;


import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetWorkModule {
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl("")
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

}
