package com.example.modulesbase.libbase.net.response;


import com.example.modulespublic.common.base.RunningRecord;

import java.util.List;

public interface NetResponse<T> {
    T getData();
    String getMsg();
    String getCode();
    List<RunningRecord> getList();
    boolean isSuccess();
}
