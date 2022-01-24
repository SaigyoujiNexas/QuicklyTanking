package com.example.modulescore.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.modulesbase.libbase.util.Logger;


public class LoginFrontFragment extends Fragment {
    private static final String TAG = "LoginFrontFragment";
    private enum loginState{ ACCOUNT, VERIFY }

    private loginState curState = loginState.ACCOUNT;
    private Fragment loginAccountFragment;
    private Fragment loginVerifyFragment;
    private TextView loginStateText;
    private TextView forget;
    private TextView register;
    private Button next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        var v = inflater.inflate(R.layout.fragment_login_front, container, false);

        loginAccountFragment = LoginFrontAccountFragment.getFragmentInstance();
        loginVerifyFragment = LoginFrontVerifyFragment.getFragmentInstance();

        loginStateText = v.findViewById(R.id.tv_login_front_state);
        forget = v.findViewById(R.id.tv_login_front_forget);
        register = v.findViewById(R.id.tv_login_front_register);
        next = v.findViewById(R.id.bt_login_front_next);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginStateText.setOnClickListener(v->{
           if(curState == loginState.ACCOUNT)
                setLoginState(loginState.VERIFY);
            else if(curState == loginState.VERIFY)
                setLoginState(loginState.ACCOUNT);
        });

        register.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.loginRegisterFragment,null)
        );

    }



    @Override
    public void onResume() {
        setLoginState(loginState.ACCOUNT);
        super.onResume();
    }



    private void setLoginState(loginState state)
    {
        Logger.i(TAG, "setLoginState invoked!");
        curState = state;
        if(curState == loginState.ACCOUNT)
        {
            loginStateText.setText(R.string.login_by_account);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fcv_login_front_state, loginAccountFragment).commit();
        }
        else if(curState == loginState.VERIFY){
            loginStateText.setText(R.string.login_by_verify);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fcv_login_front_state, loginVerifyFragment).commit();
        }

    }
}
