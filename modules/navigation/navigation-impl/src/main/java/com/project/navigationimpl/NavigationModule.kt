package com.project.navigationimpl

import com.project.navigationapi.navigation.Navigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NavigationModule {

    @Binds
    abstract fun navigator(navigator: NavigatorImpl): Navigation
}