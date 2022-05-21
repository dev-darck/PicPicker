package com.project.image_loader

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

val defaultGradient = listOf(
    Color.Red.copy(alpha = 0.02F),
    Color.Red.copy(alpha = 0.06F),
    Color.Red.copy(alpha = 0.02F),
)

@Composable
fun Shimmering(
    enableAlpha: Boolean = false,
    shimmerDelayDuration: Int = 300,
    shimmerDuration: Int = 1300,
    gradient: List<Color> = defaultGradient,
    alphaDuration: Int = shimmerDuration,
    content: @Composable ColumnScope.(Brush) -> Unit,
) {
    val transition = rememberInfiniteTransition()
    val translateAnim = shimmerAxis(
        infiniteTransition = transition,
        tweenAnim = tweenParameters(shimmerDuration, shimmerDelayDuration)
    )

    val alpha = transition.takeIf { enableAlpha }?.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = alphaDuration
                delayMillis = shimmerDelayDuration
                0.7f at alphaDuration / 4
            },
            repeatMode = RepeatMode.Reverse
        )
    )?.value ?: 1f

    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Column(modifier = Modifier.alpha(alpha)) {
        content(brush)
    }
}

@Composable
private fun shimmerAxis(
    infiniteTransition: InfiniteTransition,
    tweenAnim: DurationBasedAnimationSpec<Float>,
): State<Float> {
    return infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 2000F,
        animationSpec = infiniteRepeatable(
            animation = tweenAnim,
            repeatMode = RepeatMode.Restart
        )
    )
}

private fun tweenParameters(
    shimmerDuration: Int,
    shimmerDelayDuration: Int,
): DurationBasedAnimationSpec<Float> {
    return tween(
        durationMillis = shimmerDuration,
        easing = LinearOutSlowInEasing,
        delayMillis = shimmerDelayDuration
    )
}


@Composable
@Preview(showBackground = true)
fun ShimmerPreview() {
    Shimmering {
        Spacer(modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.small)
            .background(it))
    }
}