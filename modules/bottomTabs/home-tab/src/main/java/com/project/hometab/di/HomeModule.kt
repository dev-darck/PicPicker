package com.project.hometab.di

import com.project.hometab.config.HomeConfig
import com.project.navigationapi.config.Config
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    @IntoSet
    abstract fun homeConfig(homeConfig: HomeConfig): Config
}