package com.example.modulescore.main.Identification;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

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
                Log.d("111111111111111111111","!!!111111");
                identificationActivity.refreshItemIdentification();
                break;
        }
    }
}
