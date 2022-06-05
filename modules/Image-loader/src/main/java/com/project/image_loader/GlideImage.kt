package com.project.image_loader

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.bumptech.glide.request.transition.Transition
import com.project.common_resources.R
import com.project.util.BlurHash
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
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

private sealed class State {
    data class Loading(val placeHolder: Drawable?) : State()
    data class Success(val image: ImageBitmap) : State()
    object Error : State()
    object Default : State()
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
) {
    var state by remember { mutableStateOf<State>(State.Default) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    DisposableEffect(key1 = data, effect = {
        val glide = Glide.with(context)
        var target: CustomTarget<Bitmap>? = null
        val blur = scope.async {
            if (blurHash != null) {
                BlurHash.execute(
                    context.resources,
                    blurHash.orEmpty(),
                    100, 100
                )
            } else {
                null
            }
        }
        val job = scope.launch {
            val placeHolder = blur.await()
            target = GlideDelegate.targetListener(data) { state = it }
            glide.asBitmap()
                .override(imageSize.width, imageSize.height)
                .let { builder ->
                    if (placeHolder != null) {
                        builder.placeholder(placeHolder)
                    } else {
                        builder
                    }
                }
                .load(data)
                .into(target!!)
        }

        onDispose {
            glide.clear(target)
            job.cancel()
        }
    })

    ActiveView(
        contentDescription = contentDescription,
        state = state,
        modifier = modifier,
        contentScale = contentScale,
        shapes = shapes,
    )
}

@Composable
private fun ActiveView(
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    shapes: CornerBasedShape = MaterialTheme.shapes.medium,
    state: State,
) {
    val transition = updateTransition(
        targetState = state,
        label = contentDescription
    )

    val alpha by transition.animateFloat(
        transitionSpec = { tween(1000, easing = LinearOutSlowInEasing) },
        label = ""
    ) { animationState ->
        if (animationState is State.Success) 1F else .6F
    }

    val blurAlpha by transition.animateFloat(
        transitionSpec = { tween(500, easing = LinearOutSlowInEasing) },
        label = ""
    ) { animationState ->
        if (animationState is State.Loading) 1F else .6F
    }

    when (state) {
        is State.Loading -> {
            BlurHash(
                modifier.alpha(blurAlpha),
                contentDescription,
                state.placeHolder,
                shapes,
                contentScale
            )
        }
        is State.Success -> {
            BoxWithConstraints(
                modifier = modifier
                    .alpha(alpha)
                    .clip(shapes),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = modifier,
                    bitmap = state.image,
                    contentDescription = contentDescription,
                    contentScale = contentScale
                )
            }
        }
        is State.Error -> {

        }
        is State.Default -> {
            Spacer(modifier = modifier)
        }
    }
}

@Composable
private fun BlurHash(
    modifier: Modifier,
    contentDescription: String,
    placeHolder: Drawable?,
    shapes: CornerBasedShape = MaterialTheme.shapes.medium,
    contentScale: ContentScale,
) {
    if (placeHolder != null) {
        val bitmap = (placeHolder as? BitmapDrawable)?.bitmap?.asImageBitmap() ?: return
        BoxWithConstraints(
            modifier = modifier.clip(shapes),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = bitmap,
                contentDescription = contentDescription,
                modifier = modifier.fillMaxSize(),
                contentScale = contentScale
            )
        }
    }
}

private object GlideDelegate {
    fun targetListener(
        data: Any,
        callback: (State) -> Unit = {},
    ): CustomTarget<Bitmap> =
        object : CustomTarget<Bitmap>() {

            override fun onLoadStarted(placeholder: Drawable?) {
                super.onLoadStarted(placeholder)
                callback(State.Loading(placeholder))
                Timber.i("Start load image")
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                Timber.i("Start load cleared")
            }

            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?,
            ) {
                callback(State.Success(resource.asImageBitmap()))
                Timber.i("Success load image $data")
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                callback(State.Error)
                Timber.i("Success load error $data")
            }
        }
}
