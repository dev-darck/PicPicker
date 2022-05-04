package com.project.hometab.di

import com.project.bottom_navigation.BottomNavigationUi
import com.project.hometab.config.HomeConfig
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
    abstract fun homeConfig(homeConfig: HomeConfig): BottomNavigationUi
}