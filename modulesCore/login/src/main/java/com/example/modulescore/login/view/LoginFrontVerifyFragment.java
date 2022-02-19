package com.example.modulescore.login.view;

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

import com.example.modulesbase.libbase.net.RequestModel;
import com.example.modulesbase.libbase.net.response.NetCallback;
import com.example.modulescore.login.R;
import com.example.modulescore.login.net.LoginService;
import com.example.modulespublic.common.utils.ButtonCountDownTimer;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFrontVerifyFragment extends Fragment {

    public EditText tel;
    public EditText verify_code;
    public Button sendVerifyCode;

    @Inject
    LoginService loginService;
    private LoginFrontVerifyFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        var v = inflater.inflate(R.layout.fragment_login_front_verify, container, false);;
        tel = v.findViewById(R.id.et_login_front_verify_tel);
        verify_code = v.findViewById(R.id.et_login_front_verify_code);
        sendVerifyCode = v.findViewById(R.id.bt_login_front_verify);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendVerifyCode.setOnClickListener(v ->{
            if(TextUtils.isEmpty(tel.getText()))
                Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_LONG).show();
            else{
                RequestModel.Companion.request(loginService.verify(tel.getText().toString()), this, new NetCallback<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        new ButtonCountDownTimer(60000, 1000,
                                sendVerifyCode, "重新获取验证码").start();
                        Toast.makeText(getContext(), "验证码获取成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(getContext(), "验证码获取失败，" + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    public boolean checkInput(){
        if(TextUtils.isEmpty(tel.getText())) {
            Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(verify_code.getText())) {
            Toast.makeText(getContext(), "请输入短信验证码", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static Fragment getFragmentInstance(){

        return new LoginFrontVerifyFragment();
    }
}
