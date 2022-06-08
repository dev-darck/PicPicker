package com.project.basenavigation.di

import com.project.basenavigation.manager.NavigationManager
import com.project.basenavigation.manager.NavigationManagerLogic
import com.project.navigationapi.config.Config
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseNavigationModule() {

    @Singleton
    @Provides
    fun provideManagerNavigation(
        screens: Set<@JvmSuppressWildcards Config>
    ): NavigationManager = NavigationManagerLogic(screens.asSequence())
}