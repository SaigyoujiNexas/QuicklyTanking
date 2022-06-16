package com.xupt.safeAndRun.modulespublic.common.mvp;

import com.example.common.mvp.IView;

public interface IPresenter<V extends IView>{
    void attach(V view);

    void detach();
}
