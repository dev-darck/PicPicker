package com.project.bottomsheet_image_piker.di

import android.content.ContentResolver
import android.content.Context
import com.project.bottomsheet_image_piker.bottmsheetconfig.ImagePickerBottomSheetConfig
import com.project.navigationapi.config.Config
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class ImagePickerModule {
    @Binds
    @IntoSet
    abstract fun imagePickerBottomSheetConfig(imagePickerBottomSheetConfig: ImagePickerBottomSheetConfig): Config
}

@Module
@InstallIn(ViewModelComponent::class)
object ImageModule {
    @Provides
    @ViewModelScoped
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }
}