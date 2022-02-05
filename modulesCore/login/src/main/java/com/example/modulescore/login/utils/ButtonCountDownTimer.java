package com.example.modulescore.login.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

public class ButtonCountDownTimer extends CountDownTimer {
    private Button v;
    public ButtonCountDownTimer(long millisInFuture, long countDownInterval, Button view) {
        super(millisInFuture, countDownInterval);
        this.v = view;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        if(v != null) {
            if (v.isClickable())
                v.setClickable(false);
            v.setText(millisUntilFinished / 1000 + "s");
        }
    }
    @Override
    public void onFinish() {
        if(v != null) {
            v.setText("重新获取验证码");
            v.setClickable(true);
        }
    }
}
