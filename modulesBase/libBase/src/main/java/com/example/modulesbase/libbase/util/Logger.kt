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
                Log.i(tag, str)
        }
        @JvmStatic
        fun e(tag: String = TAG, str: String){
            if(SHOW_LOG)
                Log.e(tag, str)
        }
        @JvmStatic
        fun v(tag: String = TAG, str: String){
            if(SHOW_LOG)
                Log.v(tag, str)
        }
        @JvmStatic
        fun d(tag: String = TAG, str: String){
            if(SHOW_LOG)
                Log.d(tag, str)
        }
        @JvmStatic
        fun w(tag: String = TAG, str: String){
            if(SHOW_LOG)
                Log.w(tag, str)
        }

    }
}