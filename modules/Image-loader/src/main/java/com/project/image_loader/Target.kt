package com.project.image_loader

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.trySendBlocking
import timber.log.Timber

private const val TAG = "LOAD_IMAGE"

internal class FlowCustomTarget(
    private val producerScope: ProducerScope<GlideImageState>
) : CustomTarget<Drawable>() {

    override fun onLoadStarted(placeholder: Drawable?) {
        super.onLoadStarted(placeholder)
        if (placeholder != null) {
            Timber.tag(TAG).i("load place holder")
            val image = (placeholder as BitmapDrawable).bitmap.asImageBitmap()
            producerScope.trySendBlocking(GlideImageState.Loading(BitmapPainter(image)))
        }
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        Timber.tag(TAG).i("success load")
        val image = (resource as BitmapDrawable).bitmap.asImageBitmap()
        producerScope.trySendBlocking(GlideImageState.Success(BitmapPainter(image), resource.bitmap))
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        Timber.tag(TAG).i("error load")
        if (errorDrawable != null) {
            val image = (errorDrawable as BitmapDrawable).bitmap.asImageBitmap()
            producerScope.trySendBlocking(GlideImageState.Failure(BitmapPainter(image)))
        }
        producerScope.channel.close()
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        producerScope.channel.close()
    }
}