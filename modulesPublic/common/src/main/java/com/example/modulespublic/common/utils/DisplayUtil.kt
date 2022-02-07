package com.example.common.utils

import android.content.Context
import android.os.Build
import android.view.WindowManager
import kotlin.math.roundToInt

class DisplayUtil {

    companion object{
        private var CONTEXT : Context? = null
        fun init(context: Context){
            CONTEXT = context.applicationContext
        }



        fun getScreenWidth(): Int{

            val wm = CONTEXT?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                wm?.currentWindowMetrics.bounds.width()
            else
                wm.defaultDisplay.width
        }
        fun getScreenHeight(): Int{
            val wm = CONTEXT?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                wm?.currentWindowMetrics.bounds.height()
            else
                wm.defaultDisplay.height
        }

        fun getStatusBarHeight(): Int{
            var result = 0;
            val resId = CONTEXT!!.resources.getIdentifier("status_bar_height", "dimen", "android")
            if(resId > 0)
                result = CONTEXT!!.resources.getDimensionPixelSize(resId)
            return result
        }
        fun dp2px(dpValue: Float): Int{
            val scale = CONTEXT!!.resources.displayMetrics.density
            return (dpValue * scale).roundToInt()
        }
        fun px2dp(pxValue: Float): Int{
            val scale = CONTEXT!!.resources.displayMetrics.density
            return (pxValue / scale).roundToInt()
        }
        fun sp2px(spValue: Float): Int{
            val scale = CONTEXT!!.resources.displayMetrics.scaledDensity
            return (spValue * scale).roundToInt()
        }

    }
}