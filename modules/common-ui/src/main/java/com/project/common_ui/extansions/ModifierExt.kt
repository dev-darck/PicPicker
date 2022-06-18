package com.project.common_ui.extansions

import androidx.compose.animation.core.*
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.project.common_ui.extansions.ClickState.Pressed

const val DEFAULT_SIZE = 1F
const val DURATION = 200

private const val LABEL = "animationClick"
private const val TRANSACTION_LABEL = "transactionClick"
private const val SCALE_SIZE = .95F

enum class ClickState {
    Pressed,
    Up,
    Cancel,
}

fun Modifier.addTouch(
    downAction: () -> Unit = { },
    upAction: () -> Unit = { },
    cancel: () -> Unit = { }
): Modifier = composed {
    pointerInput("") {
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

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    indication: Indication? = null,
    onClick: () -> Unit
) = composed { composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = indication,
        interactionSource = remember { MutableInteractionSource() }
    )
} }

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}