package com.example.common.utils

import android.content.Context
import com.example.modulespublic.common.widget.LoadingDialog

class DialogUtil {
    companion object{
        @JvmStatic
        fun showLoadingMsg(context: Context, msg: String, iconRes: Int, duration: Int): LoadingDialog{
            val dialog = LoadingDialog(context)
            dialog.setType(LoadingDialog.TYPE.TYPE_MSG)
                .setMessage(msg)
                .setIcon(iconRes)
            dialog.disMissDelayed(duration)
            return dialog
        }
        @JvmStatic
        fun showLoading(context: Context, msg: String): LoadingDialog{
            val dialog = LoadingDialog(context)
            dialog.setType(LoadingDialog.TYPE.TYPE_LOADING)
                .setMessage(msg)
            return dialog
        }


    }
}