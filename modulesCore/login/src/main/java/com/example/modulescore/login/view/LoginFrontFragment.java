package com.example.modulescore.login.view;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.modulesbase.libbase.cache.Preferences;
import com.example.modulesbase.libbase.net.RequestModel;
import com.example.modulesbase.libbase.net.response.NetCallback;
import com.example.modulesbase.libbase.util.Logger;
import com.example.modulescore.login.R;
import com.example.modulescore.login.entity.LoginState;
import com.example.modulescore.login.liveData.LoginViewModel;
import com.example.modulescore.login.net.LoginService;
import com.example.modulespublic.common.constant.KeyPool;
import com.example.modulespublic.common.constant.RoutePath;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFrontFragment extends Fragment {
    private static final String TAG = "LoginFrontFragment";
    private LoginFrontAccountFragment loginAccountFragment;
    private LoginFrontVerifyFragment loginVerifyFragment;
    private TextView loginStateText;
    private TextView forget;
    private TextView register;
    private Button next;
    @Inject
    public LoginService loginService;

    public LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        var v = inflater.inflate(R.layout.fragment_login_front, container, false);
        loginAccountFragment = (LoginFrontAccountFragment) LoginFrontAccountFragment.getFragmentInstance();
        loginVerifyFragment =(LoginFrontVerifyFragment) LoginFrontVerifyFragment.getFragmentInstance();

        loginStateText = v.findViewById(R.id.tv_login_front_state);
        forget = v.findViewById(R.id.tv_login_front_forget);
        register = v.findViewById(R.id.tv_login_front_register);
        next = v.findViewById(R.id.bt_login_front_next);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i(TAG, "the inject LoginService = " + loginService);
        loginStateText.setOnClickListener(v->{
           if(loginViewModel.curState == LoginState.ACCOUNT)
                setLoginState(LoginState.VERIFY);
            else if(loginViewModel.curState == LoginState.VERIFY)
                setLoginState(LoginState.ACCOUNT);
        });

        register.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.loginRegisterFragment,null)
        );
        next.setOnClickListener(v->{
            login();
        });
        forget.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.loginForgetFragment, null));
    }
    private void login(){
        if(loginViewModel.curState == LoginState.ACCOUNT) {
            if (loginAccountFragment.checkInput() != false) {
                int code = LoginService.MODE_PASSWD;
                String userName = loginAccountFragment.account.getText().toString();
                String password = loginAccountFragment.passwd.getText().toString();
                RequestModel.Companion.request(loginService.login(code, userName, password, null, Build.MODEL, null), this, new NetCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        Preferences.saveString(KeyPool.PUBLIC_KEY, data);
                        ARouter.getInstance().build(RoutePath.MAIN).navigation(getActivity());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(getContext(), "登录失败, " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        else {
                if(loginVerifyFragment.checkInput() != false) {
                    int code = LoginService.MODE_VERIFY;
                    String tel = loginVerifyFragment.tel.getText().toString();
                    String verifyCode = loginVerifyFragment.verify_code.getText().toString();
                    RequestModel.Companion.request(loginService.login(code, null, null, tel, Build.MODEL, verifyCode), this, new NetCallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_LONG).show();
                            Preferences.saveString(KeyPool.PUBLIC_KEY, data);
                            ARouter.getInstance().build(RoutePath.MAIN).navigation(getActivity());
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            Toast.makeText(getContext(), "登陆失败, " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
        }
    }



    @Override
    public void onResume() {
        setLoginState(LoginState.ACCOUNT);
        super.onResume();
    }



    private void setLoginState(LoginState state)
    {
        Logger.i(TAG, "setLoginState invoked!");
        loginViewModel.curState = state;
        if(loginViewModel.curState == LoginState.ACCOUNT)
        {
            loginStateText.setText(R.string.login_by_verify);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fcv_login_front_state, loginAccountFragment).commit();
        }
        else if(loginViewModel.curState == LoginState.VERIFY){
            loginStateText.setText(R.string.login_by_account);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fcv_login_front_state, loginVerifyFragment).commit();
        }

    }
}
