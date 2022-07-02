package com.project.searchsreen.di

import com.project.navigationapi.config.Config
import com.project.searchsreen.searchconfig.SearchConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {

    @Binds
    @IntoSet
    abstract fun searchConfig(config: SearchConfig): Config
}