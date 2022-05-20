package com.example.common.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat
import arrow.core.Option

/**
 * Obtains the action bar size from system
 * @return size of the action bar
 */
fun Context.getActionBarSize(): Int {
    val tv = TypedValue()
    return if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    } else {
        0
    }
}

/**
 * Obtains color from compat resources
 * @param colorId resource color id
 * @return color from compar resources
 */
@ColorInt
fun Context.getColorCompat(@ColorRes colorId: Int) =
    ResourcesCompat.getColor(resources, colorId, null)

/**
 * Obtains color from style
 * @param style style id to get the color
 * @param attr attribute of the style to get the color
 * @param defaultColor color id to return if something fails
 * @return color from style
 */
@ColorInt
fun Context.getColorFromStyle(@StyleRes style: Int, @AttrRes attr: Int, @ColorInt defaultColor: Int): Int {
    val ta = obtainStyledAttributes(style, intArrayOf(attr))
    val themeColor = ta.getColor(0, defaultColor)
    ta.recycle()
    return themeColor
}

/**
 * Obtains color or return default color if not exists
 * @param optionColor optional color to get
 * @param colorInt default color
 */
fun Context.getColorOrDefault(optionColor: Option<Int>, @ColorInt colorInt: Int) =
    optionColor.fold(
        {
            colorInt
        },
        {
            getColorCompat(it)
        }
    )