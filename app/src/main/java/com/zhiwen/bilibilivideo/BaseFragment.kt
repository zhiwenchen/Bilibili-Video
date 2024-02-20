package com.zhiwen.bilibilivideo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseFragment:Fragment() {

    private val TAG = this::class.java.simpleName+"-"+this.hashCode()

    override fun onCreate(savedInstanceState: Bundle?) {
        logd("{$TAG}-onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logd("{$TAG}-onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        logd("{$TAG}-onResume")
        super.onResume()
    }

    override fun onPause() {
        logd("{$TAG}-onPause")
        super.onPause()
    }

    override fun onDestroy() {
        logd("{$TAG}-onDestroy")

        super.onDestroy()
    }

    override fun onDestroyView() {
        logd("{$TAG}-onDestroyView")
        super.onDestroyView()
    }

}