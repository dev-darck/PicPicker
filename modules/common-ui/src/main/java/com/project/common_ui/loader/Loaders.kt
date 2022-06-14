package com.project.common_ui.loader

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun circlesAnimation(): List<Animatable<Float, AnimationVector1D>> = listOf(
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) },
    remember { Animatable(initialValue = 0f) }
)

@Composable
fun TypingLoader(
    modifier: Modifier = Modifier,
    circleSize: Dp = 10.dp,
    circleColor: Color = MaterialTheme.colors.secondary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp,
) {
    val circles = circlesAnimation()

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    val lastCircle = circleValues.size - 1

    Row {
        circleValues.forEachIndexed { index, value ->
            Dot(
                modifier
                    .size(circleSize)
                    .graphicsLayer { translationY = -value * distance },
                circleColor
            )
            if (index != lastCircle) Spacer(modifier = Modifier.width(spaceBetween))
        }
    }
}

@Composable
fun PulsingLoader(
    modifier: Modifier = Modifier,
    circleSize: Dp = 14.dp,
    delay: Int = 350,
    spaceSize: Dp = 2.dp,
    circleColor: Color = MaterialTheme.colors.secondary,
) {
    val circles = circlesAnimation()

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            val delayByIndex = delay * index
            animatable.animateTo(
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = delay * 4
                        0f at delayByIndex with LinearEasing
                        1f at delayByIndex + delay with LinearEasing
                        0f at delayByIndex + delay * 2
                    }
                )
            )
        }
    }

    val circleValues = circles.map { it.value }

    Row {
        circleValues.forEachIndexed { _, value ->
            Dot(
                modifier
                    .scale(value)
                    .size(circleSize),
                circleColor
            )
            Spacer(Modifier.width(spaceSize))
        }
    }
}


@Composable
fun Dot(
    modifier: Modifier = Modifier,
    circleColor: Color = MaterialTheme.colors.secondary,
) = Spacer(
    modifier
        .background(
            color = circleColor,
            shape = CircleShape
        )
)