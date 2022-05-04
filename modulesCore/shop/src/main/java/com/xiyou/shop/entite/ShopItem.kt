package com.xiyou.shop.entite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_table")
data class ShopItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo
    val image: String,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val price: Double,
    @ColumnInfo
    val shopName: String,
    @ColumnInfo
    val sellCount: Int
)
