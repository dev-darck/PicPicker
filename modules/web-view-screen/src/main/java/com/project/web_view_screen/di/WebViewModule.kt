package com.project.web_view_screen.di

import com.project.navigationapi.config.Config
import com.project.web_view_screen.webviewconfig.WebViewConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class WebViewModule {

    @Binds
    @IntoSet
    abstract fun webViewConfig(webViewConfig: WebViewConfig): Config
}