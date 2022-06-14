package com.project.photodetail.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.project.util.extensions.toPrettyString
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.project.common_compos_ui.theme.GrayLight
import com.project.common_resources.R
import com.project.common_ui.DotModel
import com.project.common_ui.DotState
import com.project.common_ui.RowDot
import com.project.common_ui.createSpacing
import com.project.image_loader.GlideImage
import com.project.model.Photo
import com.project.photodetail.viewmodel.PhotoDetailViewModel
import com.project.photodetail.viewmodel.PhotoState
import com.project.common_ui.extansions.dominateTopSelectionColor
import com.project.common_ui.loader.PulsingLoader
import com.project.image_loader.ImageSize
import com.project.model.name
import kotlinx.coroutines.launch

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

        else -> {

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
    Column(modifier = Modifier.fillMaxSize()) {
        Header(
            modifier = Modifier,
            photo,
            onBack = onBack,
            state,
            systemUiController
        ) {
            state = it
        }
        UserBlock(photo, modifier = Modifier.alpha(alpha))
        InfoBlock(photo, modifier = Modifier.alpha(alpha))
        photo.tags?.let {
            Spacer(Modifier.size(10.dp).alpha(alpha))
            TagsBottom(
                Modifier.alpha(alpha),
                contentPadding = PaddingValues(horizontal = 16.dp),
                tags = it
            )
        }
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
    var isLightState by remember { mutableStateOf(true) }
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
    Box(
        modifier = if (newState) modifier.fillMaxSize() else modifier.height(330.dp),
        contentAlignment = if (newState) Alignment.Center else Alignment.TopStart
    ) {
        GlideImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(330.dp).alpha(alpha).fillMaxWidth(),
            imageSize = ImageSize(_height = 330.dp),
            data = photo.urls.regular.orEmpty(),
            shapes = RoundedCornerShape(0.dp),
            loadSuccess = { image ->
                state(false)
                scope.launch {
                    val (color, isLight) = image.dominateTopSelectionColor(100.dp.value.toInt())
                    systemUiController.setStatusBarColor(Color(color).copy(.5F), isLight)
                    isLightState = isLight
                }
            }
        )
        var toState by remember { mutableStateOf(DotState.COLLAPSED) }
        Row(
            modifier = Modifier.fillMaxWidth().alpha(alpha).padding(top = 20.dp).height(
                if (toState == DotState.COLLAPSED) 50.dp
                else createSpacing(list.size)
            ).statusBarsPadding(),
            verticalAlignment = Alignment.Top,
        ) {
            Spacer(Modifier.size(30.dp))
            IconBottom(
                Modifier,
                isLightState,
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
                circleColor = if (!isLightState) Color.White else Color.Black,
                toState = toState,
                items = list,
                stateChanged = {
                    toState = it
                })
        }
        if (newState) {
            PulsingLoader(delay = 600)
        }
    }
}

@Composable
private fun UserBlock(
    photo: Photo,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(),
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
            Modifier,
            icon = R.drawable.like_icon,
            click = {

            },
            color = MaterialTheme.colors.onSecondary
        )
        Spacer(modifier = Modifier.size(16.dp))
        IconBottom(
            Modifier,
            icon = R.drawable.download_tab,
            click = {

            },
            color = MaterialTheme.colors.onSecondary
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
    Spacer(
        modifier
            .height(1.dp)
            .fillMaxWidth()
            .padding(horizontal = 17.dp)
            .background(GrayLight)
    )
}

@Composable
private fun InfoBlock(
    photo: Photo,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(.5F, true))
        Column(
            Modifier.weight(1F, true),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                text = "Просмотры",
            )
            Text(
                style = MaterialTheme.typography.caption,
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
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                text = "Загрузки",
            )
            Text(
                style = MaterialTheme.typography.caption,
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
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                text = "Лайки",
            )
            Text(
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                text = (photo.likes ?: 0).toPrettyString(),
            )
        }
        Spacer(Modifier.weight(.5F, true))
    }
    Spacer(
        modifier
            .height(1.dp)
            .fillMaxWidth()
            .padding(horizontal = 17.dp)
            .background(GrayLight)
    )
}

@Composable
private fun userCollection(
    photo: Photo,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        content = {
            items(photo.currentUserCollections.size) {

            }
        })
}

val list = listOf(DotModel("Поделиться") {

}, DotModel("Информация") {

})

@Composable
private fun IconBottom(
    modifier: Modifier,
    isLightState: Boolean = false,
    icon: Int,
    click: () -> Unit,
    color: Color,
) {
    IconButton(modifier = modifier.size(20.dp), onClick = {
        click()
    }) {
        val content = R.string.default_content_descriptions
        Icon(
            tint = if (!isLightState) color else Color.Black,
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = stringResource(id = content),
        )
    }
}