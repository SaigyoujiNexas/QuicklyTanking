package com.example.modulespublic.common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class CoreApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
