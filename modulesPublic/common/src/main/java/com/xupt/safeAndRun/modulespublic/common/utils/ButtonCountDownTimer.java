package com.xupt.safeAndRun.modulespublic.common.utils;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * This Util is used for count down the time with a button.
 */
public class ButtonCountDownTimer extends CountDownTimer {
    private Button v;
    private String end;

    /**
     *
     * @param millisInFuture the millis of the time
     * @param countDownInterval switch the count down per countDownInterval
     * @param view the button view
     * @param strOnEnd  the text show on this button when count down completed.
     */
    public ButtonCountDownTimer(long millisInFuture, long countDownInterval, Button view, String strOnEnd) {
        super(millisInFuture, countDownInterval);
        this.v = view;
        this.end = strOnEnd;
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
            v.setText(end);
            v.setClickable(true);
        }
    }
}
