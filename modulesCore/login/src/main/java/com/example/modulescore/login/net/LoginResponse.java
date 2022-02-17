package com.example.modulescore.login.net;

import com.example.modulesbase.libbase.net.response.NetResponse;

public class LoginResponse implements NetResponse<String> {
    String msg;
    int code;
    String publicKey;

    public LoginResponse(String msg, int code, String publicKey) {
        this.msg = msg;
        this.code = code;
        this.publicKey = publicKey;
    }

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
