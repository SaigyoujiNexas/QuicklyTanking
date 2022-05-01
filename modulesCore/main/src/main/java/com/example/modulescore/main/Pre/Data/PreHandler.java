package com.example.modulescore.main.Pre.Data;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.modulescore.main.Pre.Data.PreDataActivity;

public class PreHandler extends Handler {
    PreDataActivity preDataActivity;
    public PreHandler(@NonNull Looper looper, PreDataActivity preDataActivity) {
        super(looper);
        this.preDataActivity = preDataActivity;
    }

    public PreHandler(@NonNull Looper looper, @Nullable Callback callback, PreDataActivity preDataActivity) {
        super(looper, callback);
        this.preDataActivity = preDataActivity;
    }

    public static final int finishProgress = 1;

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case finishProgress:
                preDataActivity.RefreshDataItem();
                break;
        }
    }
}
