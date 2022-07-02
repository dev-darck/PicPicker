package com.project.photodetail.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.project.common_compos_ui.theme.appTypographyH4
import com.project.common_resources.R
import com.project.common_ui.CollagePhoto
import com.project.common_ui.DotState
import com.project.common_ui.RowDot
import com.project.common_ui.TagsBottom
import com.project.common_ui.common_error.Error
import com.project.common_ui.extansions.clickableSingle
import com.project.common_ui.extansions.dominateSelectionColor
import com.project.common_ui.extansions.orDefault
import com.project.common_ui.loader.PulsingLoader
import com.project.common_ui.tab.Point
import com.project.common_ui.tab.SizeProportion
import com.project.image_loader.GlideImage
import com.project.image_loader.ImageSize
import com.project.model.*
import com.project.navigationapi.config.CollectionScreenRout
import com.project.photodetail.viewmodel.PhotoDetailViewModel
import com.project.photodetail.viewmodel.PhotoState
import com.project.util.extensions.toPrettyString
import kotlinx.coroutines.launch
import kotlin.math.min

private val userIconSize = 24.dp

@Composable
fun PhotoDetail(viewModel: PhotoDetailViewModel) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.photo()
    }

    when (val state = viewModel.photo.collectAsState().value) {
        is PhotoState.Success -> {
            Photo(state.result, viewModel)
        }

        is PhotoState.Loading -> {
            PulsingLoader(delay = 600)
        }

        else -> Error {
            viewModel.retryLoading()
        }
    }
}

@Composable
private fun Photo(
    photo: Photo,
    viewModel: PhotoDetailViewModel,
) {
    val systemUiController = rememberSystemUiController()

    val colorTheme = MaterialTheme.colors.background
    val useDarkIcons = MaterialTheme.colors.isLight

    val onBack: () -> Unit = {
        systemUiController.setSystemBarsColor(
            color = colorTheme, darkIcons = useDarkIcons
        )
        viewModel.navigateUp()
    }

    BackHandler {
        onBack()
    }

    val lazyState = rememberLazyListState()
    val offset = remember { derivedStateOf { lazyState.firstVisibleItemScrollOffset * 2 } }
    val itemIndex = remember { derivedStateOf { lazyState.firstVisibleItemIndex * 2 } }
    var state by remember { mutableStateOf(true) }

    val transition = updateTransition(
        targetState = state, label = ""
    )
    val alpha by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 300, easing = LinearEasing
            )
        }, label = ""
    ) { animationState ->
        if (!animationState) 1F else 0F
    }

    val size = max(
        0.dp, 50.dp * min(
            1f, 1 - (offset.value / 500F + itemIndex.value)
        )
    )

    val animationSize by animateDpAsState(size, tween(150, easing = LinearEasing))

    Column(modifier = Modifier.fillMaxSize()) {
        Header(
            modifier = Modifier, photo, onBack = onBack, state, systemUiController
        ) {
            state = it
        }
        UserBlock(photo, modifier = Modifier.alpha(alpha))
        InfoBlock(
            photo, modifier = Modifier.alpha(alpha), animationSize
        )
        photo.tags?.takeIf { it.isNotEmpty() }?.let {
            TagsBottom(
                modifier = Modifier.alpha(alpha).height(size),
                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 16.dp),
                tags = it
            )
        }
        UserCollection(photo, modifier = Modifier.alpha(alpha), lazyState, onClick = { item, curator ->
            systemUiController.setSystemBarsColor(
                color = colorTheme, darkIcons = useDarkIcons
            )
            viewModel.navigate(
                CollectionScreenRout.createRoute(
                    item.id.orEmpty(),
                    item.title.orEmpty(),
                    item.totalPhotos.orDefault,
                    item.user.name(curator)
                )
            )
        })
    }
}

@Composable
private fun Header(
    modifier: Modifier,
    photo: Photo,
    onBack: () -> Unit = {},
    newState: Boolean,
    systemUiController: SystemUiController,
    state: (Boolean) -> Unit = { },
) {
    var isLightState by remember { mutableStateOf(Pair(false, false)) }
    val scope = rememberCoroutineScope()
    val transition = updateTransition(
        targetState = newState, label = ""
    )
    val alpha by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 300, easing = LinearEasing
            )
        }, label = ""
    ) { animationState ->
        if (!animationState) 1F else 0F
    }
    BoxWithConstraints(
        modifier = if (newState) modifier.fillMaxSize() else modifier.height(330.dp),
        contentAlignment = if (newState) Alignment.Center else Alignment.TopStart
    ) {
        GlideImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(330.dp).alpha(alpha).wrapContentWidth(),
            imageSize = ImageSize(_height = 330.dp),
            data = photo.urls.regular.orEmpty(),
            shapes = RoundedCornerShape(0.dp),
            loadSuccess = { image ->
                state(false)
                scope.launch {
                    val (color, isLight) = image.dominateSelectionColor(bottom = 100.dp.value.toInt())
                    val (_, isLightBottom) = image.dominateSelectionColor(
                        top = 330.dp.value.toInt() - 10.dp.value.toInt(), bottom = 330.dp.value.toInt()
                    )
                    systemUiController.setStatusBarColor(Color(color).copy(.5F), !isLight)
                    isLightState = Pair(isLight, isLightBottom)
                }
            })
        photo.location?.let {
            Location(isLightState.second, alpha, it)
        }
        var toState by remember { mutableStateOf(DotState.COLLAPSED) }
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .alpha(alpha)
                .padding(top = 20.dp)
                .height(50.dp).statusBarsPadding(),
            verticalAlignment = Alignment.Top,
        ) {
            Spacer(Modifier.size(30.dp))
            IconBottom(
                Modifier,
                isLightState.first,
                R.drawable.back_icon,
                onBack,
                Color.White,
            )
            Spacer(Modifier.weight(1F, true))
            RowDot(
                count = 3,
                modifier = Modifier.padding(end = 30.dp),
                circleSize = 6.dp,
                spaceSize = 4.dp,
                circleColor = if (isLightState.first) Color.White else Color.Black,
                toState = toState,
                stateChanged = {
                    toState = it
                },
                content = {
                    DropdownMenu(
                        modifier = Modifier,
                        expanded = toState == DotState.EXPANDED,
                        onDismissRequest = { toState = DotState.COLLAPSED }
                    ) {
                        DropdownMenuItem(
                            modifier = Modifier.height(20.dp),
                            onClick = {}
                        ) {
                            Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.share),
                                    style = MaterialTheme.typography.h4,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                        Spacer(Modifier.size(2.dp))
                        DropdownMenuItem(
                            modifier = Modifier.height(20.dp),
                            onClick = {}
                        ) {
                            Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(R.string.info),
                                    style = MaterialTheme.typography.h4,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            )
        }
        if (newState) {
            PulsingLoader(delay = 600)
        }
    }
}

@Composable
private fun BoxScope.Location(isLightState: Boolean, alpha: Float, location: Location) {
    Row(
        modifier = Modifier.alpha(alpha)
            .align(Alignment.BottomStart)
            .padding(start = 30.dp, bottom = 10.dp)
            .clickable {

            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.location_icon),
            modifier = Modifier.size(8.dp, 11.dp),
            tint = if (isLightState) Color.White else Color.Black,
            contentDescription = stringResource(R.string.location)
        )
        Spacer(Modifier.size(10.dp))
        Text(
            text = location.name() ?: stringResource(R.string.location),
            style = MaterialTheme.typography.h4,
            color = if (isLightState) Color.White else Color.Black
        )
    }
}

@Composable
private fun UserBlock(
    photo: Photo, modifier: Modifier
) {
    Row(
        modifier = modifier
            .height(50.dp)
            .wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        GlideImage(
            imageSize = ImageSize(userIconSize, userIconSize, 2F),
            modifier = Modifier.size(userIconSize),
            data = photo.user?.profileImage?.medium.orEmpty(),
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            text = photo.user.name(),
        )
        Spacer(modifier = Modifier.weight(1F, true))
        IconBottom(
            Modifier, icon = R.drawable.like_icon, click = {

            }, color = MaterialTheme.colors.onSecondary
        )
        Spacer(modifier = Modifier.size(16.dp))
        IconBottom(
            Modifier, icon = R.drawable.add_collections, click = {

            }, color = MaterialTheme.colors.onSecondary
        )
        Spacer(modifier = Modifier.size(16.dp))

        IconBottom(
            Modifier, icon = R.drawable.download_tab, click = {

            }, color = MaterialTheme.colors.onSecondary
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
    Devider(modifier)
}

@Composable
private fun InfoBlock(
    photo: Photo,
    modifier: Modifier,
    height: Dp,
) {
    Row(
        modifier = modifier
            .height(height)
            .wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(.5F, true))
        Column(
            Modifier.weight(1F, true),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.h4,
                maxLines = 1,
                text = stringResource(R.string.views_title),
            )
            Text(
                style = appTypographyH4,
                maxLines = 1,
                text = (photo.views ?: 0).toPrettyString(),
            )
        }
        Spacer(Modifier.weight(.5F, true))
        Column(
            Modifier.weight(1F, true),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.h4, maxLines = 1, text = stringResource(R.string.download_title)
            )
            Text(
                style = appTypographyH4,
                maxLines = 1,
                text = (photo.downloads ?: 0).toPrettyString(),
            )
        }
        Spacer(Modifier.weight(.5F, true))
        Column(
            Modifier.weight(1F, true),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.h4,
                maxLines = 1,
                text = stringResource(R.string.likes_title),
            )
            Text(
                style = appTypographyH4,
                maxLines = 1,
                text = (photo.likes ?: 0).toPrettyString(),
            )
        }
        Spacer(Modifier.weight(.5F, true))
    }
    Devider(modifier)
}

@Composable
private fun UserCollection(
    photo: Photo,
    modifier: Modifier,
    state: LazyListState,
    onClick: (item: Result, curator: String) -> Unit,
) {
    val relatedCollections = photo.relatedCollections()
    LazyColumn(
        state = state,
        contentPadding = PaddingValues(),
        modifier = modifier.wrapContentWidth().navigationBarsPadding(),
        content = {
            item {
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = stringResource(R.string.related_collections),
                    modifier = modifier.wrapContentWidth().padding(horizontal = 17.dp),
                    style = MaterialTheme.typography.h2,
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
            items(count = relatedCollections.size, key = { relatedCollections[it].id }) {
                val item = relatedCollections[it]
                RelatedCollection(item, onClick)
            }
        })
}

@Composable
private fun RelatedCollection(item: Result, onClick: (item: Result, curator: String) -> Unit) {
    val curator = stringResource(id = R.string.curated_text)
    CollagePhoto(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickableSingle {
                onClick(item, curator)
            },
        item.previewPhotos
    )
    Spacer(modifier = Modifier.size(10.dp))
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = item.title.orEmpty(),
        style = MaterialTheme.typography.h2
    )
    Row(
        modifier = Modifier.padding(start = 20.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${item.totalPhotos.toString()} ${stringResource(id = R.string.photos)}",
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
            text = item.user.name(curator),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary
        )
    }
    Spacer(modifier = Modifier.size(10.dp))
    TagsBottom(
        contentPadding = PaddingValues(horizontal = 16.dp), tags = item.tags.orEmpty()
    )
    Spacer(modifier = Modifier.size(10.dp))
}

@Composable
private fun Devider(modifier: Modifier) {
    Spacer(
        modifier.height(1.dp).fillMaxWidth().padding(horizontal = 17.dp)
            .background(MaterialTheme.colors.secondaryVariant)
    )
}

@Composable
private fun IconBottom(
    modifier: Modifier,
    isLightState: Boolean = true,
    icon: Int,
    click: () -> Unit,
    color: Color,
) {
    IconButton(modifier = modifier.size(20.dp), onClick = {
        click()
    }) {
        val content = R.string.default_content_descriptions
        Icon(
            tint = if (isLightState) color else Color.Black,
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = stringResource(id = content),
        )
    }
}