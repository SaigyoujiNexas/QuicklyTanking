package com.xiyou.shop.databbase

import androidx.room.*
import com.xiyou.shop.entite.ShopItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {
    @Query("SELECT * FROM shop_table ORDER BY sellCount")
    fun getAllShopItem(): Flow<List<ShopItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShopItem)

    @Delete
    suspend fun delete(item: ShopItem)
}