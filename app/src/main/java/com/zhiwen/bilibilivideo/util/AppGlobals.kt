package com.zhiwen.bilibilivideo.util

import android.app.Application

object AppGlobals {
    private var sApplication: Application? = null

    fun getApplication(): Application {
        if (sApplication == null) {
            kotlin.runCatching {
                sApplication=  Class.forName("android.app.ActivityThread").getMethod("currentApplication")
                    .invoke(null, *emptyArray()) as Application
            }.onFailure {
                it.printStackTrace()
            }
        }
        return sApplication!!
    }
}