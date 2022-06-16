package com.xupt.safeAndRun.modulesbase.libbase.net.response;

public interface NetCallback<T> {
    void onSuccess(T data);
    void onFailure(Throwable throwable);
}
