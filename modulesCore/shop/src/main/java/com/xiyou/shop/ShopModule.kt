package com.xiyou.shop

import android.content.Context
import com.xiyou.shop.databbase.ShopDatabase
import com.xiyou.shop.service.ShopRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ShopModule {

    @Provides
    fun providesShopRepository(shopDatabase: ShopDatabase) = ShopRepository(shopDatabase.shopDao())
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Provides
    @Singleton
    fun providesShopDatabase(@ApplicationContext context: Context) = ShopDatabase.getDatabase(context, CoroutineScope(
        SupervisorJob()))
}