package com.project.download.di

import com.project.download.config.DownloadConfig
import com.project.navigationapi.config.Config
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class DownloadModule {
    @Binds
    @IntoSet
    abstract fun downloadConfig(downloadConfig: DownloadConfig): Config
}