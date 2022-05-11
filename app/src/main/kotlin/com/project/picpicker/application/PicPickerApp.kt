package com.project.picpicker.application

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.project.picpicker.BuildConfig
import dagger.hilt.android.HiltAndroidApp
//import leakcanary.LeakCanary
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class PicPickerApp: Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
//            configureLeakCanary()
        }
    }

    override fun newImageLoader(): ImageLoader = imageLoader

    //    private fun configureLeakCanary() {
//        LeakCanary.config = LeakCanary.config.copy(dumpHeap = true)
//        LeakCanary.showLeakDisplayActivityLauncherIcon(true)
//    }
}