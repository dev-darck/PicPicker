package com.project.util

import android.graphics.Bitmap

class BlurHash(
    private var punch: Float = 1F,
) {
    fun execute(
        blurString: String,
        width: Int,
        height: Int,
        callBack: (Bitmap?) -> Unit = { },
    ) {
        BlurHashDecoder.decode(
            blurString,
            width,
            height,
            punch,
        ).run(callBack)
    }
}