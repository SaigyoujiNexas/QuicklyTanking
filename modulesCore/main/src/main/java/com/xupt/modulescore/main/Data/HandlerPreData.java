package com.xupt.modulescore.main.Data;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.xupt.modulescore.main.Pre.PreRunFragment;

public class HandlerPreData extends Handler {
    final String TAG = "PreDataHandlerTAG";
    PreDataActivity preDataActivity;
    PreRunFragment preRunFragment;

    public HandlerPreData(@NonNull Looper looper, PreDataActivity preDataActivity) {
        super(looper);
        this.preDataActivity = preDataActivity;
        Log.d(TAG,"0");
    }
    public HandlerPreData(@NonNull Looper looper, PreRunFragment preRunFragment) {
        super(looper);
        this.preRunFragment = preRunFragment;
        Log.d(TAG,"1");
    }

    public static final int finishProgress = 1;
    public static final int finishTotalMile_Prerunfragment = 2;
    public static final int errorRequest = -1;
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case finishProgress:
                preDataActivity.RefreshDataItem();
                break;
            case finishTotalMile_Prerunfragment:
                preRunFragment.refreshTotalMile();
                break;
            case errorRequest:
                preRunFragment.toastError();
                break;
        }
    }
}
