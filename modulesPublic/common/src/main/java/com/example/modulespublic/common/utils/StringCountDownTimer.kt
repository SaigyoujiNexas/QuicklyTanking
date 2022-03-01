package com.example.modulespublic.common.utils

import android.os.CountDownTimer

class StringCountDownTimer(timeInMillis: Long, countDownInterval: Long, val onFinished: () -> Unit = {}): CountDownTimer(timeInMillis, countDownInterval){

    var StringVal = ""
    override fun onTick(millisUntilFinished: Long) {
        StringVal = "${millisUntilFinished / 1000}s"
    }


    override fun onFinish() {
        onFinished.invoke()

    }
}