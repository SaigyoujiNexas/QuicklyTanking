package com.xupt.modulescore.main.Trace;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TraceHandler extends Handler {
    public final static int finishQuery = 1;
    private TraceActivity traceActivity;
    public TraceHandler(TraceActivity traceActivity,@NonNull Looper looper) {
        super(looper);
        this.traceActivity = traceActivity;
    }

    public TraceHandler(@NonNull Looper looper, @Nullable Callback callback) {
        super(looper, callback);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case finishQuery:
                traceActivity.initViewPager2Adapter();
                break;
        }
    }

}
