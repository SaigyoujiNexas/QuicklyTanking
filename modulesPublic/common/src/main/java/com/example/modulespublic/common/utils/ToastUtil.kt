package com.example.common.utils

import android.app.Application
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import com.example.modulespublic.common.R
import com.hjq.toast.ToastUtils

class ToastUtil {
    companion object{
        fun init(application: Application)
        {
            ToastUtils.init(application)
            ToastUtils.setView(R.layout.view_toast)
            ToastUtils.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 0, DisplayUtil.dp2px(125f))
            ToastUtils.getToast().duration = Toast.LENGTH_SHORT
        }
        fun showToast(message: Int){
            if(message <= 0) return
            ToastUtils.show(message)
        }
        fun showToast(message: String){
            if(TextUtils.isEmpty(message)) return
            ToastUtils.show(message)
        }

    }
}