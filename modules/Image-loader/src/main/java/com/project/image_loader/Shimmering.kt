package com.project.image_loader

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview

val defaultGradient = listOf(
    Color.Red.copy(alpha = 0.03F),
    Color.Red.copy(alpha = 0.09F),
    Color.Red.copy(alpha = 0.03F),
)

@Composable
fun Shimmering(
    modifier: Modifier = Modifier,
    shimmerDelayDuration: Int = 300,
    shimmerDuration: Int = 1600,
    gradient: List<Color> = defaultGradient,
    alphaDuration: Int = shimmerDuration,
    content: @Composable BoxScope.(Brush) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val width = with(LocalDensity.current) { (maxWidth).toPx() }
        val height = with(LocalDensity.current) { (maxHeight).toPx() }
        val gradientWidth: Float = (0.2f * height)
        val tweenAnim = tweenParameters(
            shimmerDuration,
            shimmerDelayDuration
        )

        val transition = rememberInfiniteTransition()

        val xShimmer = shimmerAxis(
            widthPx = width,
            infiniteTransition = transition,
            gradientWidth = gradientWidth,
            tweenAnim = tweenAnim
        ).value

        val yShimmer = shimmerAxis(
            widthPx = height,
            infiniteTransition = transition,
            gradientWidth = gradientWidth,
            tweenAnim = tweenAnim
        ).value

        val alpha by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = alphaDuration
                    delayMillis = shimmerDelayDuration
                    0.7f at alphaDuration / 2
                },
                repeatMode = RepeatMode.Reverse
            )
        )

        val brush = linearGradient(
            colors = gradient,
            start = Offset(xShimmer - gradientWidth, yShimmer - gradientWidth),
            end = Offset(xShimmer, yShimmer)
        )
        Box(modifier = Modifier.alpha(alpha)) {
            content(brush)
        }
    }
}

@Composable
private fun shimmerAxis(
    widthPx: Float,
    infiniteTransition: InfiniteTransition,
    gradientWidth: Float,
    tweenAnim: DurationBasedAnimationSpec<Float>,
): State<Float> {
    return infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = (widthPx + gradientWidth),
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
    Shimmering(
        Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.small)
            .background(it))
    }
}