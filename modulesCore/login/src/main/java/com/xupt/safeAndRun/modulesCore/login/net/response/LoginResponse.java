package com.xupt.safeAndRun.modulesCore.login.net.response;

import com.xupt.safeAndRun.modulesbase.libbase.net.response.NetResponse;

public class LoginResponse implements NetResponse<String> {
    String msg;
    int code;
    String data;

    public LoginResponse(String msg, int code, String publicKey) {
        this.msg = msg;
        this.code = code;
        this.data = publicKey;
    }

    @Override
    public String getData() {
        return data;
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
