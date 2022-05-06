package com.example.common.extensions

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.example.common.ConstantsAnimation

fun View.changeVisibility(visible: Boolean) {
    if (this.isVisible == visible) {
        return
    }

    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.rotate(degrees: Float) {
    ViewCompat.animate(this).rotation(degrees).setDuration(ConstantsAnimation.DURATION_TIME)
        .start()
}