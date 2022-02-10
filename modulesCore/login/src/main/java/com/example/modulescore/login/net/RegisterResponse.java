package com.example.modulescore.login.net;

import com.example.modulesbase.libbase.net.response.NetResponse;

public class RegisterResponse<T> implements NetResponse<T> {
    int code;
    String msg;
    T data;

    @Override
    public String getCode() {
        return String.valueOf(code);
    }


    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean isSuccess() {
        return code != 200;
    }
}
