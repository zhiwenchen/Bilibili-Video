package com.zhiwen.bilibilivideo.ext

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

fun View.setVisibility(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun TextView.setTextVisibility(text: String?, goneWhenNull: Boolean = true) {
    if (TextUtils.isEmpty(text) && goneWhenNull) {
        visibility = View.GONE
    } else {
        this.text = text
        visibility = View.VISIBLE
    }
}

fun ImageView.setImageUrl(url: String?, isCircle: Boolean = false) {
    val builder = Glide.with(this).load(url)
    if (isCircle) {
        builder.transform(CircleCrop())
    }
    val layoutParams = this.layoutParams
    if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
        builder.override(layoutParams.width, layoutParams.height)
    }
    if (!url.isNullOrEmpty()) {
        setVisibility(true)
    }
    builder.into(this)
}