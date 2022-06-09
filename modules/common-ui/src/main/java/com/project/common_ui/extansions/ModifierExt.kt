package com.project.common_ui.extansions

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.project.common_ui.extansions.ClickState.Pressed

const val DEFAULT_SIZE = 1F
const val DURATION = 100

private const val LABEL = "animationClick"
private const val TRANSACTION_LABEL = "transactionClick"
private const val SCALE_SIZE = .9F

enum class ClickState {
    Pressed,
    Up,
    Cancel,
}

@Composable
fun Modifier.addTouch(
    downAction: () -> Unit = { },
    upAction: () -> Unit = { },
    cancel: () -> Unit = { }
): Modifier {
    return pointerInput("") {
        detectTapGestures(onPress = {
            downAction()
            val success = tryAwaitRelease()
            if (success) {
                upAction()
            } else {
                cancel()
            }
        })
    }
}

@Composable
fun transition(state: ClickState): Transition<ClickState> = updateTransition(
    targetState = state,
    label = LABEL
)

@Composable
fun Transition<ClickState>.clickAnimation(isStartAnimation: Boolean = true) = animateFloat(
    transitionSpec = { tween(DURATION, easing = LinearOutSlowInEasing) },
    label = TRANSACTION_LABEL
) { animationState ->
    if (animationState == Pressed && isStartAnimation) SCALE_SIZE else DEFAULT_SIZE
}