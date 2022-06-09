package com.project.hometab.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.project.common_ui.animation.alphaAnimation
import com.project.common_ui.animation.transition
import com.project.common_ui.extansions.*
import com.project.common_ui.extansions.ClickState.*
import com.project.common_ui.grid.StaggeredGrid
import com.project.common_ui.loader.PulsingLoader
import com.project.common_ui.paging.PagingState
import com.project.common_ui.paging.PagingState.Loading
import com.project.common_ui.paging.rememberAsNewPage
import com.project.hometab.screen.HomeState
import com.project.image_loader.GlideImage
import com.project.image_loader.ImageSize
import com.project.model.PhotoModel

private val userIconSize = 24.dp
private val rounded = 15.dp
private val padding = 10.dp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePager(
    count: Int,
    spanCount: Int,
    scrollState: RecyclerView.OnScrollListener? = null,
    pagerState: PagerState,
    state: HomeState.Success,
    onNewPage: (Int) -> Unit = { },
    clickPhoto: (String) -> Unit = { }
) {
    HorizontalPager(
        count = count,
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
    ) { page ->
        if (page != pagerState.currentPage) return@HorizontalPager
        Page(spanCount = spanCount, scrollState = scrollState, state = state, onNewPage, clickPhoto)
    }
}

@Composable
private fun Page(
    spanCount: Int,
    scrollState: RecyclerView.OnScrollListener? = null,
    state: HomeState.Success,
    newPage: (Int) -> Unit,
    clickPhoto: (String) -> Unit = { },
) {
    val paging = state.result.rememberAsNewPage(newPage)
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
                PhotoCard(photo, clickPhoto)
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
private fun PhotoCard(photo: PhotoModel, clickPhoto: (String) -> Unit = { }) {
    var isVisible by remember { mutableStateOf(false) }
    var state by remember { mutableStateOf(Cancel) }
    val alpha by transition(isVisible, photo.id.orEmpty()).alphaAnimation()
    val scale by transition(state).clickAnimation(isVisible)
    if (state == Up && scale == DEFAULT_SIZE) {
        LaunchedEffect(key1 = state) { clickPhoto(photo.id.orEmpty()) }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .scale(scale)
            .addTouch(
                downAction = { state = Pressed },
                upAction = { state = Up },
                cancel = { state = Cancel }
            ),
        contentAlignment = Alignment.TopStart
    ) {
        GlideImage(
            modifier = Modifier
                .height(photo.measureHeight.dp)
                .fillMaxWidth(),
            data = photo.urls?.small.orEmpty(),
            blurHash = photo.blurHash.orEmpty(),
            loadSuccess = {
                isVisible = true
            }
        )
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(colors = gradient),
                    shape = RoundedCornerShape(topStart = rounded, topEnd = rounded)
                )
                .alpha(alpha)
                .padding(top = padding)
                .height(userIconSize)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            GlideImage(
                imageSize = ImageSize(userIconSize, userIconSize, 1.5F),
                modifier = Modifier.size(userIconSize),
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