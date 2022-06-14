package com.xiyou.community.di

import com.xiyou.community.net.CommunityService
import com.xiyou.community.repository.CommunityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
class CommunityModule {
    @Provides
    fun provideCommunityService(retrofit: Retrofit): CommunityService =
        retrofit.create(CommunityService::class.java)
    @Provides
    fun provideCommunityRepository(service: CommunityService) = CommunityRepository(service)

}