package com.example.modulescore.login.liveData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.modulescore.login.entity.LoginState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class LoginViewModel extends ViewModel {

    public LoginState curState = LoginState.ACCOUNT;

    @Inject
    LoginViewModel(){

    }

}
