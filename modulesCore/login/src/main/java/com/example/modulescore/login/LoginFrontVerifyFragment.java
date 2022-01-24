package com.example.modulescore.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

public class LoginFrontVerifyFragment extends Fragment {


    private LoginFrontVerifyFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_front_verify, container, false);
    }


    public static Fragment getFragmentInstance(){

        return new LoginFrontVerifyFragment();
    }
}
