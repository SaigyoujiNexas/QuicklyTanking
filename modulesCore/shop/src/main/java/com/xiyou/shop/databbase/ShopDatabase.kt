package com.xiyou.shop.databbase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xiyou.shop.entite.ShopItem

@Database(entities = arrayOf(ShopItem::class),version = 1, exportSchema = false)
abstract class ShopDatabase: RoomDatabase(){
    abstract  fun shopDao(): ShopDao

    companion object{
        @Volatile
        private var INSTANCE: ShopDatabase? = null

        fun getDatabase(context: Context): ShopDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                ShopDatabase::class.java, "shop_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}