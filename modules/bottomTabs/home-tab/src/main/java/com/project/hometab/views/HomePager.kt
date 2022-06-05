package com.project.hometab.views

import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
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
import com.project.model.PhotoModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePager(
    count: Int,
    spanCount: Int,
    scrollState: RecyclerView.OnScrollListener? = null,
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
            Page(spanCount = spanCount, scrollState = scrollState, state = state) {
                viewModel.photos(it)
            }
        }
    }
}

@Composable
private fun Page(
    spanCount: Int,
    scrollState: RecyclerView.OnScrollListener? = null,
    state: HomeState.Success,
    newPage: (Int) -> Unit,
) {
    val paging = state.result.rememberAsNewPage {
        newPage(it)
    }
    val newPageState = paging.statePaging.collectAsState().value
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        StaggeredGrid(
            modifier = Modifier.padding(bottom = if (newPageState is Loading) 30.dp else 0.dp),
            spanCount = spanCount,
            scrollState = scrollState,
            data = paging,
            measureHeight = { it.measureHeight },
            content = { photo ->
                PhotoCard(photo)
            }
        )
        PageLoadUi(newPageState) {
            paging.updateState(PagingState.Success)
        }
    }
}

@Composable
private fun PageLoadUi(newPageState: PagingState, error: () -> Unit) {
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
            error()
        }
        else -> {

        }
    }
}

@Composable
private fun PhotoCard(photo: PhotoModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopStart
    ) {
        var isVisible by remember { mutableStateOf(false) }
        GlideImage(
            modifier = Modifier.aspectRatio(photo.aspectRatio),
            data = photo.urls?.small.orEmpty(),
            blurHash = photo.blurHash.orEmpty(),
        ) {
            isVisible = true
        }
        val transition = updateTransition(
            targetState = isVisible,
            label = photo.id.orEmpty()
        )
        val alpha by transition.animateFloat(
            transitionSpec = { tween(1000, easing = LinearOutSlowInEasing) },
            label = photo.id.orEmpty()
        ) { animationState ->
            if (animationState) 1F else 0F
        }
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(colors = gradient),
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                )
                .alpha(alpha)
                .padding(top = 10.dp)
                .height(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            GlideImage(
                modifier = Modifier.size(24.dp),
                data = photo.user.profileImage?.medium.orEmpty(),
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                color = Color.White,
                text = photo.user.name.orEmpty(),
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

private val gradient = listOf(
    Color.Black.copy(alpha = .3F),
    Color.Black.copy(alpha = 0F),
)