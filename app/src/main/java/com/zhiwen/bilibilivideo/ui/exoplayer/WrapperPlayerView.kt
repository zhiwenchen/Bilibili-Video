package com.zhiwen.bilibilivideo.ui.exoplayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.zhiwen.bilibilivideo.databinding.LayoutListWrapperPlayerViewBinding

class WrapperPlayerView(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {

        val viewBinding = LayoutListWrapperPlayerViewBinding.inflate(LayoutInflater.from(context),this)


}