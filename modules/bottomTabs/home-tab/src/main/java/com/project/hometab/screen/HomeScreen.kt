package com.project.hometab.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.project.common_ui.common_error.Error
import com.project.common_ui.grid.ScrollState
import com.project.common_ui.grid.StaggeredGrid
import com.project.common_ui.grid.rememberScrollState
import com.project.common_ui.paging.PagingData
import com.project.common_ui.paging.SettingsPaging
import com.project.common_ui.paging.rememberAsNewPage
import com.project.hometab.screen.HomeState.Exception
import com.project.hometab.viewmodule.HomeViewModel
import com.project.hometab.views.HomePager
import com.project.hometab.views.HomeScrollableTabRow
import com.project.image_loader.Shimmering

private val titles = listOf("Trending", "New")

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val pagerState = rememberPagerState()
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
        is Exception -> Error {
            viewModel.retryLoading()
        }
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
        modifier = Modifier.wrapContentSize()
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
                    delayMillis = 300,
                    easing = LinearEasing
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
            viewModel::photos,
            clickPhoto = {
                viewModel.clickPhoto(it)
            }
        )
    }
}

@Composable
fun Shimmer(spanCount: Int) {
    val paging = PagingData(listShimmerModel, SettingsPaging(10, countForNextPage = 0)).rememberAsNewPage { }
    Box(modifier = Modifier.wrapContentSize()) {
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


