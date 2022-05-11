package com.project.image_loader.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {

    @Provides
    @Singleton
    fun imageLoader(@ApplicationContext context: Context): ImageLoader =
        ImageLoader.Builder(context)
            .crossfade(true)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.3)
                    .build()
            }
            .diskCache {
                val fileDir = if (context.externalCacheDir != null
                    && context.externalCacheDir!!.exists()
                ) {
                    context.externalCacheDir
                } else {
                    context.cacheDir
                }?.resolve("image_cache")
                fileDir?.let {
                    DiskCache.Builder()
                        .directory(it)
                        .maxSizePercent(0.3)
                        .build()
                }
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build()
}