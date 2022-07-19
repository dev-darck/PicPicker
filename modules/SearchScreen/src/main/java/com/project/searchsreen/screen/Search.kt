package com.project.searchsreen.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.project.common_resources.R
import com.project.common_ui.CollagePhoto
import com.project.common_ui.TagsBottom
import com.project.common_ui.animation.alphaAnimation
import com.project.common_ui.animation.transition
import com.project.common_ui.common_error.Error
import com.project.common_ui.extansions.*
import com.project.common_ui.grid.StaggeredGrid
import com.project.common_ui.loader.PulsingLoader
import com.project.common_ui.paging.PagingData
import com.project.common_ui.paging.PagingState
import com.project.common_ui.paging.pagingItems
import com.project.common_ui.paging.rememberAsNewPage
import com.project.common_ui.tab.Point
import com.project.common_ui.tab.SizeProportion
import com.project.common_ui.textField.SearchTextField
import com.project.image_loader.GlideImage
import com.project.image_loader.ImageSize
import com.project.model.CollectionModel
import com.project.model.PhotoModel
import com.project.model.User
import com.project.model.name
import com.project.navigationapi.config.CollectionScreenRoute
import com.project.navigationapi.config.PhotoDetailRoute
import com.project.searchsreen.screen.TitleSelection.*
import com.project.searchsreen.viewmodel.SearchState.*
import com.project.searchsreen.viewmodel.SearchViewModel

private val userIconSize = 24.dp
private val rounded = 15.dp
private val padding = 10.dp


@Composable
fun Search(viewModel: SearchViewModel) {
    val titleSelection = viewModel.stateSelection.collectAsState().value
    val query = viewModel.query.collectAsState().value

    val screenDensity = LocalConfiguration.current.densityDpi / 160f
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(key1 = viewModel) {
        viewModel.setNeedSize(screenDensity, screenWidthDp)
    }

    Column(
        modifier = Modifier.systemGesturesPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.padding(horizontal = 20.dp)) {
            SearchTextField(
                textInit = query,
                modifier = Modifier,
                hint = stringResource(R.string.search_hint, stringResource(titleSelection.title).lowercase()),
            ) {
                viewModel.search(it, titleSelection)
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        SearchSelection(modifier = Modifier.padding(horizontal = 10.dp), titleSelection) { selection ->
            viewModel.updateTitleSelection(selection)
        }
        ResultSearch(viewModel)
    }
}

@Composable
private fun ColumnScope.ResultSearch(viewModel: SearchViewModel) {
    when (val state = viewModel.state.collectAsState().value) {
        is NotFindContent -> {

        }

        is Success -> {
            Spacer(modifier = Modifier.size(10.dp))
            if (state.isShowInfoNewResult) {
                Text(
                    text = "This search nothing found, now show last success result by \"${state.queryPositive}\"",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(5.dp))
            }
            when (state.selections) {
                PHOTOS -> {
                    ListPhoto(state.photos!!, viewModel)
                }

                PROFILE -> {
                    ListUsers(state.users!!, viewModel)
                }

                COLLECTIONS -> {
                    Collection(state.collections!!, viewModel)
                }
            }
        }

        is Exception -> {
            Error { viewModel.retry() }
        }

        is Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                PulsingLoader(delay = 600)
            }
        }
    }
}


@Composable
private fun Collection(
    pagingData: PagingData<CollectionModel>,
    viewModel: SearchViewModel,
) {
    val lazyState = rememberLazyListState()
    val items = pagingData.rememberAsNewPage(onNewPaging = {
        viewModel.search(page = it)
    })
    val state = items.statePaging.collectAsState(Loading).value
    LazyColumn(
        state = lazyState,
        modifier = Modifier.fillMaxSize(),
        content = {
            pagingItems(items) { collectionModel ->
                val curatorName = stringResource(id = R.string.curated_text)
                collectionModel?.let {
                    CardCollection(collectionModel = it) {
                        viewModel.navigate(
                            CollectionScreenRoute.createRoute(
                                it.id.orEmpty(),
                                it.title.orEmpty(),
                                it.totalPhotos.orDefault,
                                it.user.name(curatorName)
                            )
                        )
                    }
                }
            }
            item {
                when (state) {
                    is PagingState.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.padding(top = 15.dp))
                            PulsingLoader()
                            Spacer(modifier = Modifier.padding(bottom = 15.dp))
                        }
                    }

                    is PagingState.Error -> {
                        val context = LocalContext.current
                        Toast.makeText(
                            context,
                            "Connection error, please try later",
                            Toast.LENGTH_LONG
                        ).show()
                        items.updateState(PagingState.Success)
                    }

                    else -> {

                    }
                }
            }
        }
    )
}

@Composable
private fun CardCollection(collectionModel: CollectionModel, onClick: () -> Unit = {}) {
    Column(
        Modifier
            .padding(vertical = 10.dp)
            .fillMaxSize()
            .clickableSingle {
                onClick()
            }
    ) {
        CollagePhoto(
            modifier = Modifier.padding(horizontal = 16.dp),
            collectionModel.previewPhotos
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = collectionModel.title.orEmpty(),
            style = MaterialTheme.typography.h2
        )
        Row(
            modifier = Modifier.padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${collectionModel.totalPhotos.toString()} ${stringResource(id = R.string.photos)}",
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
                text = collectionModel.user.name(stringResource(id = R.string.curated_text)),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Spacer(modifier = Modifier.padding(top = 10.dp))
        TagsBottom(
            contentPadding = PaddingValues(horizontal = 16.dp),
            tags = collectionModel.tags.orEmpty()
        )
    }
}

@Composable
private fun ListPhoto(
    pagingData: PagingData<PhotoModel>,
    viewModel: SearchViewModel,
) {
    val paging = pagingData.rememberAsNewPage {
        viewModel.search(page = it)
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

@Composable
private fun ListUsers(
    pagingData: PagingData<User>,
    viewModel: SearchViewModel,
) {
    val data = pagingData.rememberAsNewPage {
        viewModel.search(page = it)
    }
    val state = data.statePaging.collectAsState(Loading).value
    val lazyState = rememberLazyListState()
    LazyColumn(
        state = lazyState,
        modifier = Modifier.fillMaxSize(),
        content = {
            pagingItems(data) { uesr ->
                uesr?.let {
                    UserUI(it) {

                    }
                }
            }

            item {
                when (state) {
                    is PagingState.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.padding(top = 15.dp))
                            PulsingLoader()
                            Spacer(modifier = Modifier.padding(bottom = 15.dp))
                        }
                    }

                    is PagingState.Error -> {
                        val context = LocalContext.current
                        Toast.makeText(
                            context,
                            "Connection error, please try later",
                            Toast.LENGTH_LONG
                        ).show()
                        data.updateState(PagingState.Success)
                    }

                    else -> {

                    }
                }
            }
        }
    )
}

@Composable
private fun UserUI(user: User, clickPhoto: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickableSingle { clickPhoto(user.id.orEmpty()) }
            .padding(top = padding)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        GlideImage(
            imageSize = ImageSize(100.dp, 100.dp, 1.5F),
            modifier = Modifier.size(56.dp).clip(CircleShape),
            data = user.profileImage?.large.orEmpty(),
        )
        Spacer(modifier = Modifier.size(10.dp))
        Column(
            modifier = Modifier.height(56.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                color = MaterialTheme.colors.onSecondary,
                text = user.name.orEmpty(),
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSecondary,
                text = user.username.orEmpty(),
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
}

private val gradient = listOf(
    Color.Black.copy(alpha = .3F),
    Color.Black.copy(alpha = 0F),
)