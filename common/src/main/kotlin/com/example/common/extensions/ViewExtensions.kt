package com.example.common.extensions

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.example.common.ConstantsAnimation

/**
 * Change visibility of view, true is visible, false is not visible
 * @param visible value to apply to view
 */
fun View.changeVisibility(visible: Boolean) {
    if (this.isVisible == visible) {
        return
    }

    this.visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * Rotate view X degrees
 * @param degrees quantity of rotation
 */
fun View.rotate(degrees: Float) {
    ViewCompat.animate(this).rotation(degrees).setDuration(ConstantsAnimation.DURATION_TIME)
        .start()
}