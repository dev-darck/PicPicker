package com.project.image_loader

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Loading
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.project.util.BlurHash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.roundToInt

/**
 * The image must be larger than the container.
 * */
data class ImageSize(
    private val _width: Dp,
    private val _height: Dp,
    val scaleSize: Float = 1.5F
) {
    val width: Int
        get() = (_width.value * scaleSize).roundToInt()
    val height: Int
        get() = (_height.value * scaleSize).roundToInt()
}

@Composable
private fun requestImage(data: Any, size: ImageSize? = null) = ImageRequest
    .Builder(LocalContext.current)
    .data(data)
    .let {
        if (size != null) it.size(size.width, size.height)
        else it.size(Size.ORIGINAL)
    }
    .build()

@Composable
fun CoilImage(
    data: Any,
    modifier: Modifier = Modifier,
    shapes: CornerBasedShape = MaterialTheme.shapes.medium,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop,
    blurHash: String? = null,
    size: ImageSize? = null,
    errorState: @Composable () -> Unit = { },
) {
    val painter = rememberAsyncImagePainter(
        model = requestImage(data, size),
        contentScale = contentScale,
    )

    when (painter.state) {
        is Loading -> {
            if (blurHash != null) {
                AnimatedVisibility(
                    painter.state is Loading,
                    modifier = modifier,
                    enter = fadeIn(animationSpec = tween(1000))
                ) {
                    BlurHash(
                        blurHash,
                        modifier,
                        shapes,
                        contentScale
                    )
                }
            }
            Timber.i("Loading $data")
        }
        is Success -> {
            AnimatedVisibility(
                painter.state is Success,
                modifier = modifier,
                enter = fadeIn(animationSpec = tween(1000))
            ) {
                Box(
                    modifier = modifier
                        .clip(shapes),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = modifier.fillMaxSize(),
                        painter = painter,
                        contentDescription = contentDescription,
                        contentScale = contentScale
                    )
                }
            }
            Timber.i("Success")
        }
        is AsyncImagePainter.State.Error -> {
            errorState()
            Timber.i("error $data")
        }
        is AsyncImagePainter.State.Empty -> {
            Timber.i("Empty")
        }
    }
}

@Composable
private fun BlurHash(
    blurHash: String,
    modifier: Modifier,
    shapes: CornerBasedShape = MaterialTheme.shapes.medium,
    contentScale: ContentScale,
) {
    var rememberBitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(key1 = blurHash) {
        launch(Dispatchers.IO) {
            BlurHash().execute(blurHash, 200, 200) {
                rememberBitmap = it
            }
        }
    }

    if (rememberBitmap != null) {
        Box(
            modifier = modifier.clip(shapes),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = rememberBitmap!!.asImageBitmap(),
                contentDescription = "",
                modifier = modifier.fillMaxSize(),
                contentScale = contentScale
            )
        }
    }
}