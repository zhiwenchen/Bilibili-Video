package com.zhiwen.bilibilivideo

import android.app.Service
import android.content.Intent
import android.os.IBinder

class RemoteService: Service() {
    // 实现服务端的接口
    private val binder = object:IRemoteService.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {
            TODO("Not yet implemented")
        }

    }
    override fun onBind(p0: Intent?): IBinder? {
       return null
    }
}