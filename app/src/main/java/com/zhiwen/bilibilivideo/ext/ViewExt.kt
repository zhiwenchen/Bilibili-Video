package com.zhiwen.bilibilivideo.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import jp.wasabeef.glide.transformations.BlurTransformation

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

fun ImageView.load(imageUrl: String, callback: (Bitmap) -> Unit) {
    Glide.with(this).asBitmap().load(imageUrl).into(object : BitmapImageViewTarget(this) {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            super.onResourceReady(resource, transition)
            callback(resource)
        }
    })
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

fun ImageView.setBlurImageUrl(blurUrl: String, radius: Int) {
    Glide.with(this).load(blurUrl)
        .override(radius)
        .transform(BlurTransformation()).dontAnimate().into(object : DrawableImageViewTarget(this) {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                super.onResourceReady(resource, transition)
                background = resource
            }
        })
}