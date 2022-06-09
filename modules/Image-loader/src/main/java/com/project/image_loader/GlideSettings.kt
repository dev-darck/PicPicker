package com.project.image_loader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

public val LocalGlideRequestOptions: ProvidableCompositionLocal<RequestOptions?> =
    staticCompositionLocalOf { null }

public val LocalGlideRequestBuilder: ProvidableCompositionLocal<RequestBuilder<Drawable>?> =
    staticCompositionLocalOf { null }

public val LocalGlideRequestManager: ProvidableCompositionLocal<RequestManager?> =
    staticCompositionLocalOf { null }

internal object LocalGlideProvider {

    @Composable
    fun getGlideRequestOptions(): RequestOptions {
        return LocalGlideRequestOptions.current ?: RequestOptions()
    }

    @Composable
    fun getGlideRequestBuilder(): RequestBuilder<Drawable> {
        return LocalGlideRequestBuilder.current
            ?: getGlideRequestManager()
                .asDrawable()
    }

    @Composable
    fun getGlideRequestManager(): RequestManager {
        return LocalGlideRequestManager.current
            ?: Glide.with(LocalContext.current.applicationContext)
    }

    fun clear(context: Context, target: Target<Drawable>) {
        Glide.with(context).clear(target)
    }
}

sealed class GlideImageState {
    object None : GlideImageState()
    data class Loading(val placeholder: ImageBitmap) : GlideImageState()
    data class Success(val imageBitmap: ImageBitmap) : GlideImageState()
    data class Failure(val errorDrawable: Drawable?) : GlideImageState()
}


private suspend fun executeImageLoading(
    executeImageRequest: suspend () -> Flow<GlideImageState>,
) = flow {
    // execute imager loading
    emitAll(executeImageRequest())
}.catch {
    // emit a failure loading state
    emit(GlideImageState.Failure(null))
}.distinctUntilChanged().flowOn(Dispatchers.IO)


@Composable
fun <T : Any> ImageLoad(
    recomposeKey: T?,
    executeImageRequest: suspend () -> Flow<GlideImageState>,
    content: @Composable (imageState: GlideImageState) -> Unit
) {
    var state by remember(recomposeKey) { mutableStateOf<GlideImageState>(GlideImageState.None) }
    LaunchedEffect(recomposeKey) {
        executeImageLoading(
            executeImageRequest,
        ).collect {
            state = it
        }
    }
    content(state)
}