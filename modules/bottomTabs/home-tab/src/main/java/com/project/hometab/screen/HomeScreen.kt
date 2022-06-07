package com.project.hometab.screen

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.project.common_ui.common_error.Error
import com.project.common_ui.grid.ScrollState
import com.project.common_ui.grid.StaggeredGrid
import com.project.common_ui.grid.rememberScrollState
import com.project.common_ui.paging.*
import com.project.hometab.screen.HomeState.Exception
import com.project.hometab.views.HomePager
import com.project.hometab.views.HomeScrollableTabRow
import com.project.image_loader.Shimmering

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


@Composable
@OptIn(ExperimentalPagerApi::class)
private fun HomeScreen(
    viewModel: HomeViewModel,
    pagerState: PagerState,
    spanCount: Int,
) {
    val state = viewModel.newPhotosFlow.collectAsState().value
    when (state) {
        is HomeState.Success -> Home(viewModel, pagerState, state, spanCount)
        is HomeState.Loading -> Shimmer(spanCount)
        is Exception -> Error()
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun Home(
    viewModel: HomeViewModel,
    pagerState: PagerState,
    state: HomeState.Success,
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
                    easing = LinearOutSlowInEasing
                )
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
        HomePager(
            titles.count(),
            spanCount,
            scrollState,
            pagerState,
            state,
            viewModel::photos
        )
    }
}

@Composable
fun Shimmer(spanCount: Int) {
    val paging = PagingData(listShimmerModel, SettingsPaging(10, countForNextPage = 0)).rememberAsNewPage { }
    Box(modifier = Modifier.fillMaxSize()) {
        StaggeredGrid(
            modifier = Modifier,
            spanCount = spanCount,
            isScrollEnabled = true,
            measureHeight = { it.height },
            data = paging,
            content = { photo ->
                Shimmering(modifier = Modifier.padding(10.dp).fillMaxWidth()) {
                    Spacer(
                        modifier = Modifier.aspectRatio(photo.ratio)
                            .background(it, shape = RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                    )
                }
            }
        )
    }
}


