package com.example.common.utils

import android.app.Application
import android.text.TextUtils
import android.widget.Toast

object ToastUtil{
    var application: Application? = null
    private var duration: Int = Toast.LENGTH_LONG
    fun init(application: Application)
        {
            this.application = application
        }
    fun setDuration(duration: Int){
        this.duration = duration
    }
        fun showToast(message: Int){
            if(message <= 0) return
            if(application == null) throw IllegalArgumentException("Toast Util Not init!");
            Toast.makeText(application, message, duration)
        }
        fun showToast(message: String){
            if(application == null) throw IllegalArgumentException("Toast Util Not init!");
            if(TextUtils.isEmpty(message)) return
            Toast.makeText(application, message, duration)
        }
    fun destory(){
        application = null
    }
}