package com.project.hometab.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.project.hometab.views.ViewsConfig.animationDurationMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun HomeTab(
    tabIndex: Int,
    isSelected: Boolean,
    title: String,
    scope: CoroutineScope,
    pagerState: PagerState
) {
    val interactionSource = remember { MutableInteractionSource() }
    val currentTextColor by animateTextColorAsState(isSelected)
    Text(
        style = MaterialTheme.typography.caption,
        color = currentTextColor,
        text = title,
        modifier = Modifier
            .wrapContentWidth()
            .height(ViewsConfig.tabHeight.dp)
            .wrapContentHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                scope.launch {
                    pagerState.animateScrollToPage(tabIndex)
                }
            }
            .padding(horizontal = ViewsConfig.tabHorizontalPaddings.dp)
    )
}

@Composable
private fun animateTextColorAsState(isSelected: Boolean) = animateColorAsState(
    targetValue = if (isSelected) {
        MaterialTheme.colors.onSurface
    } else MaterialTheme.colors.onSecondary,
    animationSpec = tween(durationMillis = animationDurationMillis, easing = FastOutSlowInEasing)
)