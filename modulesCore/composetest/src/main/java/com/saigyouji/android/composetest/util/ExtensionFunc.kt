package com.saigyouji.android.composetest.util

import android.content.Context
import android.content.ContextWrapper
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.common.utils.ToastUtil

fun Context.findActivity(): AppCompatActivity? = when(this){
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/**
 * if The String is empty, return false,
 * when string is right imput, return true.
 */
fun String.checkInput(toastMsg: String, otherJudge:(String) -> Boolean = {true}): Boolean{
    if(TextUtils.isEmpty(this)) {
        ToastUtil.showToast(toastMsg)
        return false
    }
    return otherJudge.invoke(this)
}