package com.example.modulesbase.libbase.net.response;


import java.util.List;

public interface NetResponse<T> {
    T getData();
    String getMsg();
    String getCode();
    boolean isSuccess();
}
