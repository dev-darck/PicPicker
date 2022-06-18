package com.project.common_ui

import androidx.annotation.StringRes
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.common_ui.loader.Dot

data class DotModel(
    @StringRes val title: Int,
    val onClick: () -> Unit,
)

enum class DotState {
    EXPANDED,
    COLLAPSED
}

@Composable
fun RowDot(
    modifier: Modifier,
    count: Int,
    toState: DotState,
    circleColor: Color = MaterialTheme.colors.secondary,
    circleSize: Dp = 14.dp,
    spaceSize: Dp = 2.dp,
    stateChanged: (state: DotState) -> Unit,
    content: @Composable () -> Unit,
) {
    val transition: Transition<DotState> = updateTransition(targetState = toState, label = "")
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier.padding(7.dp)
    ) {
        Row(
            modifier = modifier.height(6.dp)
                .clickable {
                    if (transition.currentState == DotState.COLLAPSED) {
                        stateChanged(DotState.EXPANDED)
                    } else {
                        stateChanged(DotState.COLLAPSED)
                    }
                },
        ) {
            repeat(count) {
                Dot(
                    Modifier
                        .size(circleSize),
                    circleColor
                )
                Spacer(Modifier.size(spaceSize))
            }
        }
        Spacer(Modifier.size(2.dp))
        content()
    }
}