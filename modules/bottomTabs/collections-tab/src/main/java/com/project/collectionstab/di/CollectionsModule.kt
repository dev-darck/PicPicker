package com.project.collectionstab.di

import com.project.collectionstab.config.CollectionsConfig
import com.project.navigationapi.config.Config
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class CollectionsModule {

    @Binds
    @IntoSet
    abstract fun collectionConfig(collectionsConfig: CollectionsConfig): Config
}