package com.project.common_ui.extansions

import android.graphics.Bitmap
import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.palette.graphics.Palette
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun Bitmap.dominateSelectionColor(top: Int = 0, bottom: Int): Pair<Int, Boolean> =
    suspendCancellableCoroutine { continuation ->
        Palette.from(this)
            .setRegion(0, top, this.width, bottom)
            .maximumColorCount(3)
            .generate { palette ->
                palette ?: continuation.cancel()
                val rgb = palette!!.vibrantSwatch?.rgb

                val statusBarColorRgb = rgb ?: Color.Transparent.toArgb()

                val hsl = FloatArray(3)
                ColorUtils.colorToHSL(statusBarColorRgb, hsl)
                val isLight = statusBarColorRgb.isColorDark()
                continuation.resume(statusBarColorRgb to isLight)
            }
    }

fun Int.isColorDark(@FloatRange(from = 0.0, to = 1.0) threshold: Float = 0.5f): Boolean {
    val darkness = 1 - (this.red * 0.299 + this.green * 0.587 + this.blue * 0.114) / 255
    return darkness >= threshold
}