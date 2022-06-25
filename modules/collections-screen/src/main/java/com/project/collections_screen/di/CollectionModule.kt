package com.project.collections_screen.di

import com.project.collections_screen.screenconfig.CollectionConfig
import com.project.navigationapi.config.Config
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class CollectionModule {
    @Binds
    @IntoSet
    abstract fun collectionConfig(config: CollectionConfig): Config
}