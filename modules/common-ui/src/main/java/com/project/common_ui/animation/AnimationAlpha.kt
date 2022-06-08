package com.project.common_ui.animation

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable

@Composable
fun transition(state: Boolean, label: String): Transition<Boolean> = updateTransition(
    targetState = state,
    label = label
)

@Composable
fun  Transition<Boolean>.alphaAnimation(label: String = "") = animateFloat(
        transitionSpec = { tween(1000, easing = LinearOutSlowInEasing) },
        label = label
    ) { animationState ->
        if (animationState) 1F else 0F
    }