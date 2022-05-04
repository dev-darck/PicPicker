package com.project.download.di

import com.project.bottom_navigation.BottomNavigationUi
import com.project.download.config.DownloadConfig
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
    abstract fun downloadConfig(downloadConfig: DownloadConfig): BottomNavigationUi
}