package com.example.modulespublic.common.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

open class BaseApplication : Application() {
    override fun onCreate() {
        ARouter.openDebug()
        ARouter.openLog()
        ARouter.init(this)
        super.onCreate()
    }
}