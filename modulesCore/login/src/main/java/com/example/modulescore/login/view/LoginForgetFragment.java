package com.example.modulescore.login.view;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.utils.ToastUtil;
import com.example.modulesbase.libbase.cache.Preferences;
import com.example.modulesbase.libbase.net.RequestModel;
import com.example.modulesbase.libbase.net.response.NetCallback;
import com.example.modulescore.login.databinding.FragmentLoginForgetBinding;
import com.example.modulescore.login.net.LoginService;
import com.example.modulespublic.common.utils.ButtonCountDownTimer;
import com.example.modulespublic.common.constant.KeyPool;
import com.example.modulespublic.common.constant.RoutePath;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class LoginForgetFragment extends Fragment {

    private FragmentLoginForgetBinding binding;

    @Inject
    public LoginService loginService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginForgetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            binding.etLoginFgtPasswdCfm.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!TextUtils.equals(s, binding.etLoginFgtPasswd.getText()))
                        binding.tvLoginForgetInvalidPasswd.setVisibility(VISIBLE);
                }
            });
            binding.btLoginForgetSendVerifyCode.setOnClickListener(v -> {
                //check tel
                var tel = binding.etLoginForgetTel.getText();
                if(TextUtils.isEmpty(tel))
                    ToastUtil.Companion.showToast("手机号不能为空");
                    RequestModel.Companion.request(loginService.verify(tel.toString()), this, new NetCallback<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        new ButtonCountDownTimer(
                                60000, 1000,
                                binding.btLoginForgetSendVerifyCode, "重新获取验证码").start();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtil.Companion.showToast("验证码获取失败: "+ throwable.getMessage());
                    }
                });
            });
            binding.btLoginForgetReset.setOnClickListener(v ->{
                if(checkInput()) {
                    binding.tvLoginForgetInvalidPasswd.setVisibility(View.INVISIBLE);
                    binding.tvLoginForgetInvalidVrfCd.setVisibility(View.INVISIBLE);
                    binding.tvLoginForgetInvalidTel.setVisibility(View.INVISIBLE);
                    binding.btLoginForgetReset.setClickable(false);
                    var tel = binding.tvLoginForgetTel.getText().toString();
                    var verifyCode = binding.etLoginForgetVerify.getText().toString();
                    var passwd = binding.etLoginFgtPasswd.getText().toString();
                    RequestModel.Companion.request(loginService.forget(tel, verifyCode, passwd), this, new NetCallback<Object>() {
                        @Override
                        public void onSuccess(Object data) {
                            ToastUtil.Companion.showToast("密码重置成功, 即将自动登录");
                            login(tel, passwd);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            ToastUtil.Companion.showToast("重置失败" + throwable.getMessage());
                            binding.btLoginForgetReset.setClickable(true);
                        }
                    });
                }
            });
    }
    private void login(String tel, String passwd){
        RequestModel.Companion.request(
                loginService.login(
                        LoginService.MODE_PASSWD, tel, passwd, null, java.util.UUID.randomUUID().toString(), null)
                , this, new NetCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Preferences.saveString(KeyPool.PUBLIC_KEY, data);
                        ARouter.getInstance().build(RoutePath.MAIN).navigation(getActivity());
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        ToastUtil.Companion.showToast("登陆失败" + throwable.getMessage());
                    }
                });
    }
    private boolean checkInput(){
        if(TextUtils.isEmpty(binding.etLoginForgetTel.getText()))
        {
            binding.tvLoginForgetInvalidTel.setVisibility(VISIBLE);
            return false;
        }
        if(TextUtils.isEmpty(binding.etLoginForgetVerify.getText()))
        {
            binding.tvLoginForgetInvalidVrfCd.setVisibility(VISIBLE);
            return false;
        }
        if(TextUtils.isEmpty(binding.etLoginFgtPasswd.getText()) || TextUtils.isEmpty(binding.etLoginForgetVerify.getText()))
        {
            binding.tvLoginForgetInvalidPasswd.setVisibility(VISIBLE);
            return false;
        }
        if(TextUtils.equals(binding.etLoginFgtPasswd.getText(), binding.etLoginFgtPasswdCfm.getText()))
        {
            binding.tvLoginForgetInvalidPasswd.setVisibility(VISIBLE);
            return false;
        }
        return true;
    }
}
