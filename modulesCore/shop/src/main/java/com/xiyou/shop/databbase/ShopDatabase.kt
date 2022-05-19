package com.xiyou.shop.databbase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.xiyou.shop.entite.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(ShopItem::class),version = 1, exportSchema = false)
abstract class ShopDatabase: RoomDatabase(){
    abstract  fun shopDao(): ShopDao

    private class ShopDatabaseCallback(
        private val scope: CoroutineScope
    ):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database->
                scope.launch {
                    var shopDao = database.shopDao()

                    shopDao.deleteAll()

                    var shopItem = ShopItem(image = "https://img.alicdn.com/bao/uploaded/i2/1683458694/O1CN019Y5QMS2E5u1RGns7S_!!1683458694.jpg_468x468q75.jpg_.webp",
                    title = "JP健身裤男春夏高腰专业压缩打底裤弹力紧身跑步透气速干运动健美", price = 142.20, sellCount = 40, shopName = "MSK flame运动健身馆")
                    shopDao.insert(shopItem)

                    shopItem = ShopItem(image = "https://img.alicdn.com/bao/uploaded/i2/1683458694/O1CN019Y5QMS2E5u1RGns7S_!!1683458694.jpg_468x468q75.jpg_.webp",
                        title = "JP健身裤男春夏高腰专业压缩打底裤弹力紧身跑步透气速干运动健美", price = 142.20, sellCount = 40, shopName = "MSK flame运动健身馆")
                    shopDao.insert(shopItem)

                    shopItem = ShopItem(image = "https://img.alicdn.com/bao/uploaded/i2/1683458694/O1CN019Y5QMS2E5u1RGns7S_!!1683458694.jpg_468x468q75.jpg_.webp",
                        title = "JP健身裤男春夏高腰专业压缩打底裤弹力紧身跑步透气速干运动健美", price = 142.20, sellCount = 40, shopName = "MSK flame运动健身馆")
                    shopDao.insert(shopItem)
                }
            }
        }
    }
    companion object{
        @Volatile
        private var INSTANCE: ShopDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ShopDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                ShopDatabase::class.java, "shop_database")
                    .addCallback(ShopDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}