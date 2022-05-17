package com.project.image_loader

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import timber.log.Timber

@Composable
private fun requestImage(data: Any) = ImageRequest
    .Builder(LocalContext.current)
    .data(data)
    .size(Size.ORIGINAL)
    .build()

@Composable
fun CoilImage(
    data: Any,
    modifier: Modifier = Modifier,
    enableShimmer: Boolean = true,
    shapes: CornerBasedShape = MaterialTheme.shapes.medium,
    shimmerDelayDuration: Int = 300,
    shimmerDuration: Int = 1600,
    gradient: List<Color> = defaultGradient,
    alphaDuration: Int = shimmerDuration,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Crop,
    errorState: @Composable () -> Unit = { },
) {
    val painter = rememberAsyncImagePainter(
        model = requestImage(data),
        contentScale = contentScale,
    )

    when (painter.state) {
        is AsyncImagePainter.State.Loading -> {
            if (enableShimmer) {
                Shimmering(
                    modifier,
                    shimmerDelayDuration,
                    shimmerDuration,
                    gradient,
                    alphaDuration
                ) {
                    Spacer(modifier = modifier
                        .fillMaxWidth()
                        .clip(shapes)
                        .background(it))
                }
            }
            Timber.i("Loading $data")
        }
        is AsyncImagePainter.State.Success -> {
            Box(
                modifier = modifier.clip(shapes),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = modifier.fillMaxSize(),
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = contentScale
                )
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