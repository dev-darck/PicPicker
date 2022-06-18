package com.project.common_ui

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.common_ui.loader.Dot

private val spaceItem = 20.dp

data class DotModel(
    @StringRes val title: Int,
    val onClick: () -> Unit,
)

enum class DotState {
    EXPANDED,
    COLLAPSED
}

fun createSpacing(count: Int): Dp = spaceItem * 10

@Composable
fun RowDot(
    modifier: Modifier,
    count: Int,
    toState: DotState,
    items: List<DotModel> = emptyList(),
    circleColor: Color = MaterialTheme.colors.secondary,
    circleSize: Dp = 14.dp,
    spaceSize: Dp = 2.dp,
    stateChanged: (state: DotState) -> Unit,
) {
    val transition: Transition<DotState> = updateTransition(targetState = toState, label = "")
    val resources = LocalContext.current.resources
    val alpha: Float by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 50)
        }, label = ""
    ) { state ->
        if (state == DotState.EXPANDED) 1f else 0f
    }
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
        Spacer(Modifier.size(4.dp))
        val maxLength = items.maxOf { resources.getString(it.title).length }
        val width = (maxLength * MaterialTheme.typography.caption.fontSize.value).dp + 20.dp
        items.forEach {
            Item(
                it,
                Modifier
                    .clickable { it.onClick() }
                    .alpha(alpha),
                width
            )
            Spacer(Modifier.size(spaceSize))
        }
    }
}

@Composable
private fun Item(model: DotModel, modifier: Modifier = Modifier, width: Dp) {
    Box(
        modifier = modifier
            .height(spaceItem)
            .width(width)
            .background(color = Color.LightGray, shape = RoundedCornerShape(20.dp)),
        Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(model.title),
            maxLines = 1,
            style = MaterialTheme.typography.caption,
            color = Color.Black
        )
    }
}