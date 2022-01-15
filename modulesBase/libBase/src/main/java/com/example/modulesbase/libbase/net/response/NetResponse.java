package com.example.modulesbase.libbase.net.response;

public interface NetResponse<T> {
    T getData();
    String getMsg();
    String getCode();
    boolean isSuccess();
}
