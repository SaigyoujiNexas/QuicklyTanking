package com.saigyouji.android.composetest.net;

import com.example.modulesbase.libbase.net.response.NetResponse;

public class runResponse implements NetResponse<String> {

    String message;
    String code;
    String data;
    @Override
    public String getData() {
        return data;
    }

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public boolean isSuccess() {
        return Integer.parseInt(code)!= 200;
    }
}
