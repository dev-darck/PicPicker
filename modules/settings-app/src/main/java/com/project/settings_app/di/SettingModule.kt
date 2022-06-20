package com.project.settings_app.di

import com.project.navigationapi.config.Config
import com.project.settings_app.settingconfig.SettingConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingModule {
    @Binds
    @IntoSet
    abstract fun settingConfig(photoDetailConfig: SettingConfig): Config
}