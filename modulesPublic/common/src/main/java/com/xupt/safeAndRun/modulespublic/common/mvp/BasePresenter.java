package com.xupt.safeAndRun.modulespublic.common.mvp;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.common.mvp.IView;

public class BasePresenter<V extends IView> implements  IPresenter<V>, LifecycleEventObserver {
    protected V mView;

    @Override
    public void attach(V view) {
        mView = view;
        mView.getLifecycle().addObserver(this);
    }

    @Override
    public void detach() {
        if(mView != null)
            mView.getLifecycle().removeObserver(this);
        mView = null;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if(event == Lifecycle.Event.ON_DESTROY)
            detach();
    }
}
