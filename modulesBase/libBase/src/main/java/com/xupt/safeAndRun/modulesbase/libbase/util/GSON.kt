package com.xupt.safeAndRun.modulesbase.libbase.util

import com.google.gson.Gson
import java.lang.ref.WeakReference

class GSON {
    companion object{
        private var instance : WeakReference<Gson>? = null
        fun getInstance(): WeakReference<Gson>
        {
            if (instance == null || instance!!.get() == null)
                instance = WeakReference(Gson())

            return instance!!
        }
    }
}