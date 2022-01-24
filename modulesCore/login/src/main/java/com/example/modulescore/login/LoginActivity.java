package com.example.modulescore.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.modulespublic.common.constant.RoutePath;

/**
 * 本模块全程使用navigation导航驱动， 使用伪状态驱动模型来进行页面更新。
 * 全软件的登陆注册窗口
 */

@Route(path = RoutePath.LOG_IN)
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
}
