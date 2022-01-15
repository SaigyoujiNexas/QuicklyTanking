package com.example.modulesbase.libbase.util

import android.content.Context
import java.io.IOException
import java.util.*

class PropertiesUtil {
    companion object{
        @JvmField
        val props: Properties = Properties()
        @JvmStatic
        fun init(c: Context)
        {
            try{
                val input = c.assets.open("appConfig.properties");
                props.load(input);
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }
}