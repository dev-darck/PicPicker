package com.project.photodetail.di

import com.project.navigationapi.config.Config
import com.project.photodetail.photodetailconfig.PhotoDetailConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoDetailModule {
    @Binds
    @IntoSet
    abstract fun profileConfig(photoDetailConfig: PhotoDetailConfig): Config
}