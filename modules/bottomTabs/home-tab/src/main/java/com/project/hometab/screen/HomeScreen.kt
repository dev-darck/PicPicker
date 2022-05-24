package com.project.hometab.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.project.hometab.views.HomePager
import com.project.hometab.views.HomeScrollableTabRow
import timber.log.Timber

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
        HomeScrollableTabRow(
            tabs = titles,
            selectedTabIndex = pagerState.currentPage,
            pagerState = pagerState,
        )
        HomePager(count = titles.count(), spanCount, pagerState, viewModel)
    }
}


