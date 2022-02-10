package com.example.modulespublic.common.net;

import com.example.modulesbase.libbase.net.response.NetResponse;

public class BaseResponse<T> implements NetResponse<T> {
    private T data;
    private int code;
    private String msg;

    @Override
    public T getData() {
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
