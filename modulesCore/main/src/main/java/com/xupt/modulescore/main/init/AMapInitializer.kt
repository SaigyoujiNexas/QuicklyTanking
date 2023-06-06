package com.xupt.modulescore.main.init

import android.content.Context
import androidx.startup.Initializer
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapOptions
import com.amap.api.maps.MapsInitializer

/**
 * @author yuji
 * @date 2023/06/06
 * @description AMapInitializer, initialize AMap dependency.
 */
class AMapInitializer:Initializer<Any?> {
    override fun create(context: Context): Any{
        MapsInitializer.updatePrivacyShow(context, true, true)
        MapsInitializer.updatePrivacyAgree(context, true)
        return Any()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}