package com.zhiwen.bilibilivideo.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.zhiwen.bilibilivideo.databinding.LayoutLoadingStatusViewBinding

class LoadingStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding =
        LayoutLoadingStatusViewBinding.inflate(LayoutInflater.from(context), this)

    fun showEmpty(
        @DrawableRes resId: Int = 0,
        text: String? = null,
        clickListener: OnClickListener?
    ) {
//        if (resId != 0) {
//            binding.loading.setImageResource(resId)
//        }
//        if (!text.isNullOrEmpty()) {
//            binding.emptyText.text = text
//        }
        binding.emptyAction.setOnClickListener(clickListener)
    }

}