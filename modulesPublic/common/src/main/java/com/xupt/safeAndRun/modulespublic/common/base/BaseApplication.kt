package com.xupt.safeAndRun.modulespublic.common.base

import android.app.Application
import android.text.TextUtils
import dagger.hilt.android.HiltAndroidApp
import com.xupt.safeAndRun.modulesbase.libbase.util.PropertiesUtil
import com.example.common.utils.DisplayUtil
import com.example.common.utils.ToastUtil
import com.xupt.safeAndRun.modulesbase.libbase.cache.Preferences
import com.xupt.safeAndRun.modulespublic.common.constant.KeyPool
import java.util.*
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val isRelease = PropertiesUtil.props.getProperty("isRelease")
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