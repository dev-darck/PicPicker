package com.project.hometab.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.project.hometab.views.HomePager
import com.project.hometab.views.HomeScrollableTabRow
import timber.log.Timber

private val titles = listOf("Trending", "New", "Trending", "New", "Trending", "New")

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun HomeScreen() {
    Timber.d("called")
    val pagerState = rememberPagerState()
    val viewModel: HomeViewModel = hiltViewModel()
    val text = viewModel.state.value
    Timber.d(text.toString())

    HomeScreen(viewModel, pagerState)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeScreen(viewModel: HomeViewModel, pagerState: PagerState) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeScrollableTabRow(
            tabs = titles,
            selectedTabIndex = pagerState.currentPage,
            pagerState = pagerState,
        )
        HomePager(count = titles.count(), pagerState, viewModel.newPhotosFlow)
    }
}


