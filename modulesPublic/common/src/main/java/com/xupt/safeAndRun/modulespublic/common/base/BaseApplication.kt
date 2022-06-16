package com.xupt.safeAndRun.modulespublic.common.base

import android.app.Application
import android.text.TextUtils
import dagger.hilt.android.HiltAndroidApp
import com.xupt.safeAndRun.modulesbase.libbase.util.PropertiesUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.utils.DisplayUtil
import com.example.common.utils.ToastUtil
import com.xupt.safeAndRun.modulesbase.libbase.cache.Preferences
import com.xupt.safeAndRun.modulespublic.common.constant.KeyPool
import java.util.*

@HiltAndroidApp
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val isRelease = PropertiesUtil.props.getProperty("isRelease")
        if (!TextUtils.equals(isRelease, "true")) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        DisplayUtil.init(this)
        ToastUtil.init(this)
        if (TextUtils.isEmpty(Preferences.getString(KeyPool.ID, ""))) {
            val uuid = UUID.randomUUID().toString()
            Preferences.saveString(KeyPool.ID, uuid)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        ToastUtil.destory()
    }
}