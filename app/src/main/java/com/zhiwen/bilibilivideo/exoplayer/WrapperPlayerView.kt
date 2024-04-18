package com.zhiwen.bilibilivideo.exoplayer

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.google.android.exoplayer2.Player
import com.zhiwen.bilibilivideo.R
import com.zhiwen.bilibilivideo.databinding.LayoutListWrapperPlayerViewBinding
import com.zhiwen.bilibilivideo.ext.setBlurImageUrl
import com.zhiwen.bilibilivideo.ext.setImageUrl
import com.zhiwen.bilibilivideo.ext.setVisibility
import com.zhiwen.bilibilivideo.util.PixUtil

// @JvmOverloads告诉编译器为带有默认参数值的函数生成重载函数，这些重载函数会覆盖所有可能的参数组合。
// 这在Kotlin内部是不需要的，因为Kotlin本身就支持默认参数值。
// 但当Java代码需要调用这些Kotlin函数时，Java并不直接支持带默认参数值的函数调用，
// 所以@JvmOverloads会生成多个重载方法，让Java代码可以像调用重载方法一样，调用这些带默认参数的Kotlin函数。
class WrapperPlayerView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    interface Listener {
        fun onTogglePlay(attachView: WrapperPlayerView)
    }
    private var pageListPlayer = PageListPlayer()
    private var callback: Listener? = null
    private var videoUrl:String? = null

    val viewBinding = LayoutListWrapperPlayerViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        viewBinding.playBtn.setOnClickListener {
            videoUrl?.let { it1 -> pageListPlayer.togglePlay(this, it1) }
//            callback?.onTogglePlay(this)
        }
    }

    fun bindData(widthPx: Int, heightPx: Int, coverUrl: String?, videoUrl: String, maxHeight: Int) {
        // 1、根据视频的widthPx,heightPx 动态计算出cover、 blur 以及wrapperView的宽高
        this.videoUrl = videoUrl

        viewBinding.cover.setImageUrl(coverUrl)

        if (widthPx < heightPx) {
            coverUrl?.run {
                viewBinding.blurBackground.setBlurImageUrl(this, 10)
                viewBinding.blurBackground.setVisibility(true)
            }
        } else {
            viewBinding.blurBackground.setVisibility(false)
        }

        setSize(widthPx, heightPx, PixUtil.getScreenWidth(), maxHeight)
    }

    private fun setSize(widthPx: Int, heightPx: Int, maxWidth: Int, maxHeight: Int) {
        // 这里要求做的事情 是 计算视频原始宽度>原始高度 /  原石高度>原石宽高时  cover、wrapperView等比缩放
        val coverWidth: Int
        val coverHeight: Int
        if (widthPx >= heightPx) {
            coverWidth = maxWidth
            coverHeight = (heightPx / (widthPx * 1.0f / maxWidth)).toInt()
        } else {
            coverHeight = maxHeight
            coverWidth = (widthPx / (heightPx * 1.0f / maxHeight)).toInt()
        }

        // 设置wrapper-view的宽高
        val wrapperViewParams = layoutParams
        wrapperViewParams.width = maxWidth
        wrapperViewParams.height = coverHeight
        layoutParams = wrapperViewParams

        // 设置高斯模糊背景view的宽高
        val blurParams = viewBinding.blurBackground.layoutParams
        blurParams.width = maxWidth
        blurParams.height = coverHeight
        viewBinding.blurBackground.layoutParams = blurParams

        // 设置cover-view封面图的宽高
        val coverParams: LayoutParams = viewBinding.cover.layoutParams as LayoutParams
        coverParams.width = coverWidth
        coverParams.height = coverHeight
        coverParams.gravity = Gravity.CENTER
        viewBinding.cover.scaleType = ImageView.ScaleType.FIT_CENTER
        viewBinding.cover.layoutParams = coverParams
    }

    fun setListener(callback: Listener) {
        this.callback = callback
    }

    // 开始播放，把播放器和播放控制器添加到WrapperPlayerView中
    fun onActive(playerView: View, controllerView: View) {
        val parent = playerView.parent
        if (parent != this) {
            if (parent != null) {
                (parent as ViewGroup).removeView(playerView)
            }
            val coverParams = viewBinding.cover.layoutParams
            this.addView(playerView, 1, coverParams)
        }

        val ctrlParent = controllerView.parent
        if (ctrlParent != this) {
            if (ctrlParent != null) {
                (ctrlParent as ViewGroup).removeView(controllerView)
            }
            val ctrlParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            ctrlParams.gravity = Gravity.BOTTOM
            this.addView(controllerView, ctrlParams)
        }
    }

    fun inActive() {
        viewBinding.cover.setVisibility(true)
        viewBinding.playBtn.setVisibility(true)
        viewBinding.playBtn.setImageResource(R.drawable.icon_video_play)
    }

    fun onControllerVisibilityChange(visibility: Int, playEnd: Boolean) {
        viewBinding.playBtn.setVisibility(if (playEnd) true else visibility == View.VISIBLE)
    }

    // 开始播放的时候把原本的预览封面和播放按钮隐藏，显示播放器和控制器
    // 暂停播放的时候再把预览页面显示出来
    fun onPlayerStateChanged(playing: Boolean, playbackState: Int) {
        if (playing) {
            viewBinding.cover.setVisibility(false)
            viewBinding.bufferView.setVisibility(false)
            viewBinding.playBtn.setVisibility(true)
            viewBinding.playBtn.setImageResource(R.drawable.icon_video_pause)
        } else if (playbackState == Player.STATE_ENDED) {
            viewBinding.cover.setVisibility(true)
            viewBinding.playBtn.setVisibility(true)
            viewBinding.playBtn.setImageResource(R.drawable.icon_video_play)
        } else if (playbackState == Player.STATE_BUFFERING) {
            viewBinding.bufferView.setVisibility(true)
        }
    }
}