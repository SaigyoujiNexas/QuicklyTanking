package com.example.modulesbase.libbase.util

import android.util.Log

class Logger {
    companion object{
        private var TAG = "TAG";
        private var SHOW_LOG = true

        fun init(tag: String, isShowLog: Boolean)
        {
            TAG = tag
            SHOW_LOG = isShowLog
        }
        @JvmStatic
        fun i(tag: String = TAG, str: String){
            if(SHOW_LOG)
                Log.i(TAG, str)
        }
        @JvmStatic
        fun e(tag: String = TAG, str: String){
            if(SHOW_LOG)
                Log.e(TAG, str)
        }
        @JvmStatic
        fun v(tag: String = TAG, str: String){
            if(SHOW_LOG)
                Log.v(TAG, str)
        }
        @JvmStatic
        fun d(tag: String = TAG, str: String){
            if(SHOW_LOG)
                Log.d(TAG, str)
        }
        @JvmStatic
        fun w(tag: String = TAG, str: String){
            if(SHOW_LOG)
                Log.w(TAG, str)
        }

    }
}