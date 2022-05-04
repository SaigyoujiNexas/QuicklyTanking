package com.xiyou.shop

import android.content.Context
import com.xiyou.shop.databbase.ShopDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ShopModule {

}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Provides
    fun providesShopDatabase(context: Context) = ShopDatabase.getDatabase(context)
}