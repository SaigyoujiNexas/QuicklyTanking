package com.example.modulespublic.common.base;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.utils.DisplayUtil;
import com.example.common.utils.ToastUtil;
import com.example.modulesbase.libbase.cache.Preferences;
import com.example.modulesbase.libbase.util.PropertiesUtil;
import com.example.modulespublic.common.constant.KeyPool;

import java.util.UUID;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        var isRelease = PropertiesUtil.props.getProperty("isRelease");
        if(!TextUtils.equals(isRelease, "true"))
        {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        DisplayUtil.Companion.init(this);
        ToastUtil.Companion.init(this);
        if(TextUtils.isEmpty(Preferences.getString(KeyPool.ID, "")))
        {
            var uuid = UUID.randomUUID().toString();
            Preferences.saveString(KeyPool.ID, uuid);
        }
    }
}
