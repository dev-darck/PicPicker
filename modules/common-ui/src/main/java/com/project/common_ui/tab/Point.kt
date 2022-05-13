package com.project.common_ui.tab

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun Point(modifier: Modifier = Modifier, sizeProportion: SizeProportion, color: Color) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = color,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = size.minDimension / sizeProportion.size
        )
    }
}

enum class SizeProportion(val size: Int) {
    SMALL(10),
    MIDDLE(5),
}
