package com.xupt.modulescore.main.Identification;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HandlerIdentification extends Handler {
    IdentificationActivity identificationActivity;


    public HandlerIdentification(@NonNull Looper looper, IdentificationActivity identificationActivity) {
        super(looper);
        this.identificationActivity = identificationActivity;
    }

    public HandlerIdentification(@NonNull Looper looper, @Nullable Callback callback, IdentificationActivity identificationActivity) {
        super(looper, callback);
        this.identificationActivity = identificationActivity;
    }

    public static final int finishIdentification = 0;
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case finishIdentification:
                identificationActivity.refreshItemIdentification();
                break;
        }
    }
}
