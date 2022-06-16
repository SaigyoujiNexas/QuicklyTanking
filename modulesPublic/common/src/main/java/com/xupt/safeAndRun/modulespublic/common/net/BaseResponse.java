package com.xupt.safeAndRun.modulespublic.common.net;

import com.xupt.safeAndRun.modulesbase.libbase.net.response.NetResponse;

public class BaseResponse<T> implements NetResponse<T> {
    T data;
    int code;
    String message;
    public BaseResponse(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.message = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getMsg() {
        return message;
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
