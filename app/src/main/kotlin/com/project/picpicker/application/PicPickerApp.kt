package com.project.picpicker.application

import android.app.Application
import com.project.image_loader.LocalGlideProvider
import com.project.picpicker.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PicPickerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            DebugOptions.initLeakCanary()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        LocalGlideProvider.glideOnLowMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        LocalGlideProvider.glideOnTrimMemory(this, level)
    }
}