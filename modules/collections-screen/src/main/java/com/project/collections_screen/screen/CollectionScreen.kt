package com.project.collections_screen.screen

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.collections_screen.model.CollectionInfoModel
import com.project.collections_screen.viewmodel.CollectionState.Loading
import com.project.collections_screen.viewmodel.CollectionState.Success
import com.project.collections_screen.viewmodel.CollectionViewModel
import com.project.common_resources.R
import com.project.common_ui.animation.alphaAnimation
import com.project.common_ui.animation.transition
import com.project.common_ui.common_error.Error
import com.project.common_ui.extansions.*
import com.project.common_ui.grid.StaggeredGrid
import com.project.common_ui.paging.PagingData
import com.project.common_ui.paging.rememberAsNewPage
import com.project.common_ui.tab.Point
import com.project.common_ui.tab.SizeProportion
import com.project.image_loader.GlideImage
import com.project.image_loader.ImageSize
import com.project.model.PhotoModel
import com.project.navigationapi.config.PhotoDetailRoute

private val userIconSize = 24.dp
private val rounded = 15.dp
private val padding = 10.dp

@Composable
fun CollectionScreen(viewModel: CollectionViewModel) {
    val screenDensity = LocalConfiguration.current.densityDpi / 160f
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    LaunchedEffect(viewModel) {
        viewModel.setNeedSize(screenDensity, screenWidthDp)
        viewModel.photosByCollectionFirst()
    }
    when (val state = viewModel.photos.collectAsState().value) {
        is Loading -> {
        }

        is Success -> {
            ListPhoto(state.result, state.collectionInfoModel, viewModel)
        }

        else -> {
            Error {
                viewModel.retryLoading()
            }
        }
    }
}

@Composable
private fun ListPhoto(
    pagingData: PagingData<PhotoModel>,
    collectionInfoModel: CollectionInfoModel,
    viewModel: CollectionViewModel,
) {
    Column(
        modifier = Modifier.systemGesturesPadding().fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${collectionInfoModel.totalPhoto} ${stringResource(id = R.string.photos)}",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSecondary
            )
            Spacer(modifier = Modifier.size(5.dp))
            Point(
                modifier = Modifier.size(15.dp),
                sizeProportion = SizeProportion.MIDDLE,
                color = MaterialTheme.colors.onSecondary
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = collectionInfoModel.curatorName,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSecondary
            )
        }

        Spacer(modifier = Modifier.size(5.dp))

        val paging = pagingData.rememberAsNewPage {
            viewModel.photosByCollection(it)
        }

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
        ) {
            StaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                spanCount = 2,
                data = paging,
                measureHeight = { it.measureHeight },
                content = { photo ->
                    PhotoCard(photo) {
                        viewModel.navigate(PhotoDetailRoute.createRoute(it))
                    }
                }
            )
        }
    }
}

@Composable
private fun PhotoCard(photo: PhotoModel, clickPhoto: (String) -> Unit = { }) {
    var isVisible by remember { mutableStateOf(false) }
    var state by remember { mutableStateOf(ClickState.Cancel) }
    val alpha by transition(isVisible, photo.id.orEmpty()).alphaAnimation()
    val scale by transition(state).clickAnimation(isVisible)
    if (state == ClickState.Up && scale == DEFAULT_SIZE && isVisible) {
        LaunchedEffect(key1 = state) { clickPhoto(photo.id.orEmpty()) }
    }
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .aspectRatio(photo.aspectRatio)
            .scale(scale)
            .addTouch(
                downAction = { state = ClickState.Pressed },
                upAction = { state = ClickState.Up },
                cancel = { state = ClickState.Cancel }
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