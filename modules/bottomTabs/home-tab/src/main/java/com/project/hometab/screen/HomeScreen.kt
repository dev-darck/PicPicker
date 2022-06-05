package com.project.hometab.screen

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.project.common_ui.grid.ScrollState
import com.project.common_ui.grid.rememberScrollState
import com.project.hometab.views.HomePager
import com.project.hometab.views.HomeScrollableTabRow

private val titles = listOf("Trending", "New")

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun HomeScreen() {
    val pagerState = rememberPagerState()
    val viewModel: HomeViewModel = hiltViewModel()
    val spanCount = 2
    val screenDensity = LocalConfiguration.current.densityDpi / 160f
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    LaunchedEffect(key1 = viewModel) {
        viewModel.setNeedSize(screenDensity, screenWidthDp)
        viewModel.photoFirstPage(spanCount)
    }
    HomeScreen(viewModel, pagerState, spanCount)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
    pagerState: PagerState,
    spanCount: Int,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var isVisible by remember { mutableStateOf(true) }
        val scrollState = rememberScrollState {
            isVisible = it == ScrollState.SCROLL_STATE_IDLE
        }
        val transition = updateTransition(
            targetState = isVisible,
            label = ""
        )
        val size by transition.animateDp(
            transitionSpec = {
                tween(
                    durationMillis = if (!isVisible) 100 else 500,
                    delayMillis = if (!isVisible) 0 else 500,
                    easing = LinearOutSlowInEasing)
            },
            label = ""
        ) { animationState ->
            if (animationState) 50.dp else 0.dp
        }
        HomeScrollableTabRow(
            tabs = titles,
            selectedTabIndex = pagerState.currentPage,
            pagerState = pagerState,
            modifier = Modifier.height(size)
        )
        HomePager(count = titles.count(), spanCount, scrollState, pagerState, viewModel)
    }
}


