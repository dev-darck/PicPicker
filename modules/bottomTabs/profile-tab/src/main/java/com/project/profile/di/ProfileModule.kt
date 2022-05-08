package com.project.profile.di

import com.project.navigationapi.config.Config
import com.project.profile.config.ProfileConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {
    @Binds
    @IntoSet
    abstract fun profileConfig(profileConfig: ProfileConfig): Config
}