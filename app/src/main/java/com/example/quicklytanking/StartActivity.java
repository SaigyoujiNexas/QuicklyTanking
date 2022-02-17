package com.example.quicklytanking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.modulesbase.libbase.cache.Preferences;
import com.example.modulespublic.common.constant.KeyPool;
import com.example.modulespublic.common.constant.RoutePath;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        String string = Preferences.getString(KeyPool.PUBLIC_KEY, "");
        if(TextUtils.isEmpty(string)){
            ARouter.getInstance().build(RoutePath.LOG_IN).navigation(this);
        }
        else{
            ARouter.getInstance().build(RoutePath.MAIN).navigation(this);
        }
    }
}