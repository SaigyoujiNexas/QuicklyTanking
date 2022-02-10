package com.example.modulescore.login.view;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.modulescore.login.R;

public class LoginFrontAccountFragment extends Fragment {

    private LoginFrontAccountFragment(){

    }
    public EditText account;
    public EditText passwd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        var v = inflater.inflate(R.layout.fragment_login_front_account, container, false);
        account = v.findViewById(R.id.et_login_front_account);
        passwd = v.findViewById(R.id.et_login_front_account_passwd);
        return v;

    }

    public boolean checkInput(){
        if(TextUtils.isEmpty(account.getText())) {
            Toast.makeText(getContext(), "请输入账号", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(passwd.getText())) {
            Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }

    public static Fragment getFragmentInstance(){

        return new LoginFrontAccountFragment();
    }
}
