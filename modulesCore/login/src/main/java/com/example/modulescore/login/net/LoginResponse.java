package com.example.modulescore.login.net;

import com.example.modulesbase.libbase.net.response.NetResponse;

public class LoginResponse implements NetResponse<String> {
    private String msg;
    private int code;
    private String publicKey;


    @Override
    public String getData() {
        return publicKey;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getCode() {
        return String.valueOf(code);
    }

    @Override
    public boolean isSuccess() {
        return code != 200;
    }
}
