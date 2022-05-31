package com.project.image_loader

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

private const val memoryCacheSizeBytes = (1024 * 1024 * 20).toLong()
private const val diskCacheSizeBytes = (1024 * 1024 * 100).toLong()
private const val bitmapPoolSizeBytes = (1024 * 1024 * 30).toLong()
private const val logLevel = Log.ERROR
private const val homeGlideName = "homeGlide"

@GlideModule(glideName = homeGlideName)
class GlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes))
        builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context, diskCacheSizeBytes))
        builder.setBitmapPool(LruBitmapPool(bitmapPoolSizeBytes))
        builder.setLogLevel(logLevel)
        builder.setDefaultRequestOptions(requestOptions())
        super.applyOptions(context, builder)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(
            OkHttpClient.Builder().apply {
                connectTimeout(15, TimeUnit.SECONDS)
                readTimeout(15, TimeUnit.SECONDS)
            }.build()
        ))
        super.registerComponents(context, glide, registry)
    }

    private fun requestOptions(): RequestOptions {
        return RequestOptions()
            .encodeFormat(Bitmap.CompressFormat.PNG)
            .encodeQuality(90)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }
}