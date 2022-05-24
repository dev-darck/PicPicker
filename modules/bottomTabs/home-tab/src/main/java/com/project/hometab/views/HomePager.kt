package com.project.hometab.views

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.project.common_ui.grid.StaggeredGrid
import com.project.common_ui.loader.PulsingLoader
import com.project.common_ui.paging.PagingState
import com.project.common_ui.paging.PagingState.Loading
import com.project.common_ui.paging.rememberAsNewPage
import com.project.hometab.screen.HomeState
import com.project.hometab.screen.HomeViewModel
import com.project.image_loader.GlideImage

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePager(
    count: Int,
    spanCount: Int,
    pagerState: PagerState,
    viewModel: HomeViewModel,
) {
    val state = viewModel.newPhotosFlow.collectAsState().value
    if (state is HomeState.Success) {
        HorizontalPager(
            count = count,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            if (page != pagerState.currentPage) return@HorizontalPager
            val paging = state.result.rememberAsNewPage {
                viewModel.photos(it)
            }
            val newPageState = paging.statePaging.collectAsState().value
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                StaggeredGrid(
                    modifier = Modifier.padding(bottom = if (newPageState is Loading) 30.dp else 0.dp),
                    spanCount = spanCount,
                    data = paging,
                    measureHeight = { it.measureheight },
                    content = { photo ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            GlideImage(
                                modifier = Modifier.aspectRatio(photo.aspectRatio),
                                data = photo.urls.small.orEmpty(),
                                blurHash = photo.blurHash.orEmpty(),
                            )
                        }
                    }
                )
                when (newPageState) {
                    is Loading -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.size(15.dp))
                            PulsingLoader()
                            Spacer(modifier = Modifier.size(15.dp))
                        }
                    }
                    is PagingState.Error -> {
                        val context = LocalContext.current
                        Toast.makeText(
                            context,
                            "Connection error, please try later",
                            Toast.LENGTH_LONG
                        ).show()
                        paging.updateState(PagingState.Success)
                    }
                    else -> {

                    }
                }
            }
        }
    }
}