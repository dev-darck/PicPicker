package com.project.util

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

object BlurHash {
    fun execute(
        resources: Resources,
        blurString: String,
        width: Int,
        height: Int,
        punch: Float = 1F,
    ): Drawable = BlurHashDecoder.decode(
        blurString,
        width,
        height,
        punch,
    ).run {
        BitmapDrawable(resources, this)
    }
}