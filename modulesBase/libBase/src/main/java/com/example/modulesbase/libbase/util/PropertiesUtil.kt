package com.example.modulesbase.libbase.util

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.util.*

class PropertiesUtil {
    companion object{
        @JvmField
        val props: Properties = Properties()
        @JvmStatic
        fun init(c: Context)
        {
            var input: InputStream? = null
            try{
                input = c.assets.open("appConfig.properties");
                props.load(input);
            }catch (e: IOException){
                e.printStackTrace()
            }finally {
                input?.close()
            }
        }
    }
}