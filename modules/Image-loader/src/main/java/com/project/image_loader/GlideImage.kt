package com.project.image_loader

import android.graphics.drawable.Drawable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.project.common_resources.R
import com.project.image_loader.LocalGlideProvider.clear
import com.project.util.BlurHash
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * The image must be larger than the container.
 * */
data class ImageSize(
    private val _width: Dp? = null,
    private val _height: Dp? = null,
    val scaleSize: Float = 1F,
) {
    val width: Int
        get() = if (_width == null) SIZE_ORIGINAL else (_width.value * scaleSize).roundToInt()
    val height: Int
        get() = if (_height == null) SIZE_ORIGINAL else (_height.value * scaleSize).roundToInt()
}

@Composable
fun GlideImage(
    data: Any,
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(id = R.string.default_content_descriptions),
    contentScale: ContentScale = ContentScale.Crop,
    shapes: CornerBasedShape = MaterialTheme.shapes.medium,
    blurHash: String? = null,
    imageSize: ImageSize = ImageSize(),
    loadSuccess: () -> Unit = {},
) {
    val context = LocalContext.current
    val localManager = LocalGlideProvider.getGlideRequestBuilder()
    key(data) {
        ImageLoad(
            data,
            executeImageRequest = {
                callbackFlow {
                    var target: FlowCustomTarget? = FlowCustomTarget(this)
                    val placeHolder = if (blurHash != null) {
                        BlurHash().execute(
                            context.resources,
                            blurHash,
                            100, 100
                        )
                    } else {
                        null
                    }
                    localManager.override(imageSize.width, imageSize.height)
                        .let { builder ->
                            if (placeHolder != null) {
                                builder.placeholder(placeHolder)
                            } else {
                                builder
                            }
                        }.load(data)
                        .into(target!!)

                    awaitClose {
                        clear(context, target!!)
                        target = null
                    }
                }
            },
            content = {
                ActiveView(
                    contentDescription = contentDescription,
                    state = it,
                    modifier = modifier,
                    contentScale = contentScale,
                    shapes = shapes,
                )
                if (it is GlideImageState.Success) {
                    loadSuccess()
                }
            }
        )
    }
}

@Composable
private fun ActiveView(
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    shapes: CornerBasedShape = MaterialTheme.shapes.medium,
    state: GlideImageState,
) {
    val transition = updateTransition(
        targetState = state,
        label = contentDescription
    )

    val alpha by transition.animateFloat(
        transitionSpec = { tween(1000, easing = LinearOutSlowInEasing) }, label = ""
    ) { animationState ->
        if (animationState is GlideImageState.Success) 1F else .6F
    }

    val blurAlpha by transition.animateFloat(
        transitionSpec = { tween(500, easing = LinearOutSlowInEasing) }, label = ""
    ) { animationState ->
        if (animationState is GlideImageState.Loading) 1F else .6F
    }

    when (state) {
        is GlideImageState.Loading -> {
            Image(
                bitmap = state.placeholder,
                contentDescription = contentDescription,
                modifier = modifier
                    .alpha(blurAlpha)
                    .fillMaxSize()
                    .clip(shapes),
                contentScale = contentScale,
            )
        }

        is GlideImageState.Success -> {
            Image(
                modifier = modifier
                    .alpha(alpha)
                    .fillMaxSize()
                    .clip(shapes),
                bitmap = state.imageBitmap,
                contentDescription = contentDescription,
                contentScale = contentScale,
            )
        }

        else -> {

        }
    }
}
