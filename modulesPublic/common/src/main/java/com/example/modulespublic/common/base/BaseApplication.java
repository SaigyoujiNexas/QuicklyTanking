package com.example.modulespublic.common.base;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.modulesbase.libbase.util.PropertiesUtil;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        var isRelease = PropertiesUtil.props.getProperty("isRelease");
        if(!TextUtils.equals(isRelease, "true"))
        {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        super.onCreate();
    }
}
