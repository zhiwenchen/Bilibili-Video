package com.zhiwen.bilibilivideo.ext

import android.text.TextUtils
import android.view.View
import android.widget.TextView

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