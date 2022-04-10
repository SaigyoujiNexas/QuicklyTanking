package com.saigyouji.android.composetest.net.response;

import com.example.modulesbase.libbase.net.response.NetResponse;
import com.example.modulespublic.common.base.RunningRecord;

import java.util.List;

public class RegisterResponse<T> implements NetResponse<T> {
    int code;
    String msg;
    T data;

    public RegisterResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

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
