package com.zhiwen.bilibilivideo.exoplayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.zhiwen.bilibilivideo.databinding.LayoutListWrapperPlayerViewBinding
import com.zhiwen.bilibilivideo.ext.setImageUrl

// 显示视频画面、高斯模糊背景、以及响应点击事件
class WrapperPlayerView(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {

    val viewBinding = LayoutListWrapperPlayerViewBinding.inflate(LayoutInflater.from(context), this)

    //
    fun bindData(widthPx: Int, heightPx: Int, coverUrl: String, videoUrl: String, maxHeight: Int) {
        // 根据视频的宽高设置视图的背景和宽高
        viewBinding.cover.setImageUrl(coverUrl)

    }
}