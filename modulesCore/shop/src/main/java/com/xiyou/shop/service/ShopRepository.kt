package com.xiyou.shop.service

import androidx.annotation.WorkerThread
import com.xiyou.shop.databbase.ShopDao
import com.xiyou.shop.entite.ShopItem

class ShopRepository(private val shopDao: ShopDao) {
    val allItem = shopDao.getAllShopItem()

    @WorkerThread
    suspend fun insert(item: ShopItem) = shopDao.insert(item)

    @WorkerThread
    suspend fun delete(item: ShopItem) = shopDao.delete(item)
}