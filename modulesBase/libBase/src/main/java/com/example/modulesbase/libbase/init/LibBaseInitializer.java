package com.example.modulesbase.libbase.init;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.modulesbase.libbase.cache.AppCache;
import com.example.modulesbase.libbase.cache.Preferences;
import com.example.modulesbase.libbase.util.Logger;
import com.example.modulesbase.libbase.util.PropertiesUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Yuki
 * This class can initialize some libBase setting.
 */
public class LibBaseInitializer implements Initializer<Object> {
    @NonNull
    @Override
    public Object create(@NonNull Context context) {
        PropertiesUtil.init(context);
        var tag = PropertiesUtil.props.getProperty("tag");
        var isShowLog = PropertiesUtil.props.getProperty("isShowLog");
        Logger.Companion.init(TextUtils.isEmpty(tag)? "TAG": tag,
                !TextUtils.equals(isShowLog, "false"));
        AppCache.Companion.getINSTANCE().init(context, tag);
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
