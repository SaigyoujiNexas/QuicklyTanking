package com.example.modulescore.login;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import java.lang.ref.WeakReference;

public class LoginFrontAccountFragment extends Fragment {

    private LoginFrontAccountFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_front_account, container, false);

    }
    public static Fragment getFragmentInstance(){

        return new LoginFrontAccountFragment();
    }

}
