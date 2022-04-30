package com.example.modulescore.main.Pre;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.modulescore.main.Pre.Data.PreDataFragment;

public class PreHandler extends Handler {
    PreDataFragment preDataFragment;
    public PreHandler(@NonNull Looper looper, PreDataFragment preDataFragment) {
        super(looper);
        this.preDataFragment = preDataFragment;
    }

    public PreHandler(@NonNull Looper looper, @Nullable Callback callback, PreDataFragment preDataFragment) {
        super(looper, callback);
        this.preDataFragment = preDataFragment;
    }

    public static final int finishProgress = 1;

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case finishProgress:
                preDataFragment.RefreshDataItem();
                break;
        }
    }
}
