package com.project.common_ui.textField

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.common_ui.extansions.textField
import com.project.common_ui.extansions.textFieldOnSurface

@Composable
internal fun BaseTextField(
    textInit: String = "",
    label: String,
    height: Dp = 40.dp,
    focusElevation: Dp = 8.dp,
    nonFocusElevation: Dp = 0.dp,
    onValueChange: (String) -> Unit,
    decorationBox: @Composable (
        showHint: Boolean,
        innerTextField: @Composable () -> Unit
    ) -> Unit
) {
    if (label.isNotEmpty()) {
        Label(text = label)
    }

    val focusManager = LocalFocusManager.current
    var focus by remember { mutableStateOf(false) }

    val elevation: Dp by animateDpAsState(
        targetValue = if (focus) focusElevation else nonFocusElevation,
        animationSpec = tweenSpec()
    )

    Surface(
        elevation = elevation,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colors.textField,
        modifier = Modifier
            .onFocusChanged { focus = it.isFocused }
            .fillMaxWidth()
            .height(height)
            .border(1.dp, color = MaterialTheme.colors.onSecondary, shape = MaterialTheme.shapes.small)
    ) {
        BaseTextField(textInit, onValueChange, decorationBox) {
            focus = false
            focusManager.clearFocus()
        }
    }
}

@Composable
private fun Label(text: String) = Text(
    text = text,
    textAlign = TextAlign.Start,
    style = MaterialTheme.typography.caption,
    color = MaterialTheme.colors.textFieldOnSurface,
    modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
)

@Composable
private fun tweenSpec(): TweenSpec<Dp> = tween(
    durationMillis = 300,
    easing = LinearOutSlowInEasing
)

@Composable
private fun BaseTextField(
    textInit: String = "",
    onValueChange: (String) -> Unit,
    decorationBox: @Composable (
        showHint: Boolean,
        innerTextField: @Composable () -> Unit
    ) -> Unit,
    clearFocus: () -> Unit = {}
) {
    var text by remember { mutableStateOf(textInit) }

    BasicTextField(
        value = text,
        singleLine = true,
        onValueChange = { value ->
            onValueChange(value)
            text = value
        },
        keyboardActions = KeyboardActions(onDone = {
            clearFocus()
        }),
        textStyle = MaterialTheme.typography.h3
            .copy(color = MaterialTheme.colors.textFieldOnSurface),
        decorationBox = { innerTextField ->
            decorationBox(text.isEmpty()) {
                innerTextField()
            }
        },
    )
}