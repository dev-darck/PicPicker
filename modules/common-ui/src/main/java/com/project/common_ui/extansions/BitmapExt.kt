package com.project.common_ui.extansions

import android.graphics.Bitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun Bitmap.dominateTopSelectionColor(bottom: Int): Pair<Int, Boolean> =
    suspendCancellableCoroutine { continuation ->
        Palette.from(this)
            .setRegion(0, 0, this.width, bottom)
            .maximumColorCount(3)
            .generate { palette ->
                palette ?: continuation.cancel()

                val statusBarColorRgb =
                    palette!!.dominantSwatch?.rgb ?: androidx.compose.ui.graphics.Color.Transparent.toArgb()

                val hsl = FloatArray(3)
                ColorUtils.colorToHSL(statusBarColorRgb, hsl)
                val isLight = hsl[2] >= .5f
                continuation.resume(statusBarColorRgb to isLight)
            }
    }