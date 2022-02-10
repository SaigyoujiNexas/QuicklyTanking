package com.example.modulescore.login.view;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.modulesbase.libbase.cache.Preferences;
import com.example.modulesbase.libbase.net.RequestModel;
import com.example.modulesbase.libbase.net.response.NetCallback;
import com.example.modulescore.login.R;
import com.example.modulescore.login.net.LoginService;
import com.example.modulescore.login.utils.ButtonCountDownTimer;
import com.example.modulespublic.common.constant.KeyPool;
import com.example.modulespublic.common.constant.RoutePath;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginRegisterFragment extends Fragment {

    private EditText name;
    private EditText tel;
    private EditText verifyCode;
    private EditText passwd;
    private EditText verifyPasswd;
    private Button sendVerifyCode;
    private Button register;

    @Inject
    LoginService loginService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        var v = inflater.inflate(R.layout.fragment_login_register, container, false);
        name = v.findViewById(R.id.et_login_register_name);
        tel = v.findViewById(R.id.et_login_register_tel);
        verifyCode = v.findViewById(R.id.et_login_register_verify_code);
        passwd = v.findViewById(R.id.et_login_register_passwd);
        verifyPasswd = v.findViewById(R.id.et_login_register_passwd_confirm);
        sendVerifyCode = v.findViewById(R.id.bt_login_register_send_verify_code);
        register = v.findViewById(R.id.bt_login_register);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sendVerifyCode.setOnClickListener(v ->{
            if(TextUtils.isEmpty(tel.getText()))
                Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_LONG).show();
            else{
                RequestModel.Companion.request(loginService.verify(tel.getText().toString()), this, new NetCallback<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        new ButtonCountDownTimer(60000, 1000, sendVerifyCode).start();
                        Toast.makeText(getContext(), "验证码获取成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(getContext(), "验证码获取失败，" +throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        register.setOnClickListener(v->{
            if(TextUtils.isEmpty(tel.getText()))
                Toast.makeText(getContext(), "手机号不能为空", Toast.LENGTH_LONG).show();
            else if(TextUtils.isEmpty(name.getText()))
                Toast.makeText(getContext(), "昵称不能为空", Toast.LENGTH_LONG).show();
            else if(TextUtils.isEmpty(verifyCode.getText()))
                Toast.makeText(getContext(), "验证码不能为空", Toast.LENGTH_LONG).show();
            else if(TextUtils.isEmpty(passwd.getText()))
                Toast.makeText(getContext(), "密码不能为空", Toast.LENGTH_LONG).show();
            else if (!checkPasswdInput())
                Toast.makeText(getContext(), "两次密码确认不一致", Toast.LENGTH_LONG).show();
            else{
                RequestModel.Companion.request(loginService.register(
                        name.getText().toString(), tel.getText().toString(),
                        verifyCode.getText().toString(), passwd.getText().toString()
                ), this, new NetCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(getContext(), "注册成功, 即将自动登录", Toast.LENGTH_SHORT);
                        login();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(getContext(), "注册失败, " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }


        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void login() {
        RequestModel.Companion.request(loginService.login(LoginService.MODE_PASSWD, tel.getText().toString(),
                passwd.getText().toString(), null, Build.MODEL, null), this, new NetCallback<String>() {
            @Override
            public void onSuccess(String data) {
                Preferences.saveString(KeyPool.PUBLIC_KEY, data);
                ARouter.getInstance().build(RoutePath.MAIN).navigation(getContext());
            }
            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getContext(), "自动登陆失败, " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean checkPasswdInput()
    {
        return passwd.equals(verifyPasswd);
    }
}
