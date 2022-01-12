package com.example.modulesbase.libbase.net.exception;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

public class ApiException extends Exception {
    private static final int UNKNOWN_ERROR = -0x10;
    private static final int PARSE_ERROR = -0x11;
    private static final int NETWORK_ERROR = -0x12;

    private String code;
    private String errorMsg;

    public ApiException(String code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public static ApiException handleException(Throwable e)
    {
        if(e instanceof ApiException){
            return (ApiException)e;
        }
        ApiException ex;
        if(e instanceof JsonParseException ||
        e instanceof JSONException ||
        e instanceof ParseException)
        {
            return new ApiException(String.valueOf(PARSE_ERROR), "数据解析错误");
        }
        else if(e instanceof ConnectException
    || e instanceof UnknownHostException
        ||e instanceof SocketTimeoutException){
            return new ApiException(String.valueOf(NETWORK_ERROR), "网络请求异常");
        }
        else{
            return new ApiException(String.valueOf(UNKNOWN_ERROR), "未知错误");
        }
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code='" + code + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
