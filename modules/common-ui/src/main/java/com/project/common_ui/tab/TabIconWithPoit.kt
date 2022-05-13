package com.project.common_ui.tab

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TabIconWitchPoint(
    modifier: Modifier = Modifier,
    contentDescription: String,
    imageVector: ImageVector,
    sizeIcon: Dp = 24.dp,
    sizeProportion: SizeProportion = SizeProportion.SMALL,
    animationProgress: Float = 1F,
    color: Color = MaterialTheme.colors.secondary
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .size(sizeIcon)
                .align(Alignment.CenterHorizontally)
        )
        Point(
            sizeProportion = sizeProportion,
            color = color,
            modifier = Modifier
                .fillMaxSize()
                .alpha(animationProgress)
                .align(Alignment.CenterHorizontally)
        )
    }
}