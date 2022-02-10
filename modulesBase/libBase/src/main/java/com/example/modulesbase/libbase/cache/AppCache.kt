package com.example.modulesbase.libbase.cache

import android.content.Context
import android.os.FileUtils
import java.io.File

class AppCache {
    private lateinit var aCache:ACache

    private fun initAppDir(context: Context, cacheName: String)
    {
        val mainDir = File(context.cacheDir.absolutePath, cacheName)
        val fileDir = File(mainDir.absolutePath, "file")
        val tempDir = File(mainDir.absolutePath, "temp")

       // FileUtil.makeDir(mainDir)

    }

    fun init(context: Context, cacheName: String)
    {
        Preferences.init(context.applicationContext)
        aCache = ACache.get(context.applicationContext, cacheName)
    }
    fun save(key: String, value: String){
        aCache.put(key, value)
    }
    fun getString(key:String): String{
        return aCache.getAsString(key)
    }
    fun remove(key: String){
        aCache.remove(key)
    }

    companion object{
        val INSTANCE = AppCache()
    }

}