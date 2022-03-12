package com.example.modulescore.main.Pre;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.logging.LogRecord;

public class PreHandler extends Handler {
    PreDataFragment preDataFragment;

    public static final int finishProgress = 1;

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case finishProgress:

                break;
        }
    }
}
