package com.example.modulespublic.common.base

import android.app.Application
import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.example.modulesbase.libbase.util.PropertiesUtil

open class BaseApplication : Application() {
    override fun onCreate() {
        val isRelease = PropertiesUtil.props.getProperty("isRelease")
        if(!TextUtils.equals(isRelease, "true"))
        {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        super.onCreate()
    }
}