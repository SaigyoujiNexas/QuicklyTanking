package com.saigyouji.android.composetest.net.response;

import com.example.modulesbase.libbase.net.response.NetResponse;
import com.example.modulespublic.common.base.RunningRecord;

import java.util.List;

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
    public List<RunningRecord> getList() {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return code != 200;
    }
}
