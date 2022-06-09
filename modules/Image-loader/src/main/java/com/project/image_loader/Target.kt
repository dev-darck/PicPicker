package com.project.image_loader

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.asImageBitmap
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.trySendBlocking

internal class FlowCustomTarget(
    private val producerScope: ProducerScope<GlideImageState>
) : CustomTarget<Drawable>() {

    override fun onLoadStarted(placeholder: Drawable?) {
        super.onLoadStarted(placeholder)
        if (placeholder != null) {
            producerScope.trySendBlocking(GlideImageState.Loading((placeholder as BitmapDrawable).bitmap.asImageBitmap()))
        }
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        producerScope.trySendBlocking(GlideImageState.Success((resource as BitmapDrawable).bitmap.asImageBitmap()))
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        if (errorDrawable != null) {
            producerScope.trySendBlocking(GlideImageState.Failure(errorDrawable))
        }
        producerScope.channel.close()
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        producerScope.channel.close()
    }
}