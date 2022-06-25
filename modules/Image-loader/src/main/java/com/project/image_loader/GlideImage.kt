package com.project.image_loader

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.project.common_resources.R
import com.project.image_loader.LocalGlideProvider.clear
import com.project.util.BlurHash
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
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

@Composable
fun GlideImage(
    data: Any,
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(id = R.string.default_content_descriptions),
    contentScale: ContentScale = ContentScale.Crop,
    shapes: CornerBasedShape = MaterialTheme.shapes.medium,
    blurHash: String? = null,
    color: String? = null,
    imageSize: ImageSize = ImageSize(),
    loadSuccess: (Bitmap) -> Unit = {},
) {
    Timber.tag("IMAGE_LOADER_URL").i("loading url -> $data")
    val context = LocalContext.current
    val localManager = LocalGlideProvider.getGlideRequestBuilder()
    key(data) {
        ImageLoad(
            data,
            executeImageRequest = {
                callbackFlow {
                    var target: FlowCustomTarget? = FlowCustomTarget(this)
                    val blur = async {
                        when {
                            blurHash != null -> BlurHash().execute(
                                context.resources,
                                blurHash,
                                20, 20
                            )
                            color != null -> Color.parseColor(color).toDrawable().run {
                                BitmapDrawable(
                                    context.resources,
                                    toBitmap(100, 100, Bitmap.Config.ARGB_8888)
                                )
                            }
                            else -> null
                        }
                    }

                    localManager.override(imageSize.width, imageSize.height)
                        .let { builder ->
                            val placeHolder = blur.await()
                            if (placeHolder != null) {
                                builder.placeholder(placeHolder)
                            } else {
                                builder
                            }
                        }.load(data)
                        .into(target!!)

                    awaitClose {
                        clear(context, target!!)
                        blur.cancel()
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
                    loadSuccess(it.bitmap)
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

    when (state) {
        is GlideImageState.Loading -> {
            Image(
                painter = state.placeholder,
                contentDescription = contentDescription,
                modifier = modifier
                    .clip(shapes),
                contentScale = contentScale,
            )
        }

        is GlideImageState.Success -> {
            Image(
                modifier = modifier
                    .clip(shapes),
                painter = state.imageBitmap,
                contentDescription = contentDescription,
                contentScale = contentScale,
            )
        }

        else -> {

        }
    }
}
