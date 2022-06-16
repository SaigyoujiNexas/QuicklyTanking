package com.xupt.safeAndRun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;

import com.xupt.safeAndRun.R;
import com.xupt.safeAndRun.modulesbase.libbase.cache.Preferences;
import com.xupt.safeAndRun.modulespublic.common.constant.KeyPool;
import com.xupt.safeAndRun.modulespublic.common.constant.RoutePath;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        String string = Preferences.getString(KeyPool.TOKEN, "");
        if(TextUtils.isEmpty(string)){
            ARouter.getInstance().build(RoutePath.LOG_IN).navigation(this);
        }
        else{
            ARouter.getInstance().build(RoutePath.MAIN).navigation(this);
        }
    }
}