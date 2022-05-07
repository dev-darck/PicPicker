package com.project.picpicker.application

import android.app.Application
import com.project.picpicker.BuildConfig
import dagger.hilt.android.HiltAndroidApp
//import leakcanary.LeakCanary
import timber.log.Timber

@HiltAndroidApp
class PicPickerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
//            configureLeakCanary()
        }
    }

//    private fun configureLeakCanary() {
//        LeakCanary.config = LeakCanary.config.copy(dumpHeap = true)
//        LeakCanary.showLeakDisplayActivityLauncherIcon(true)
//    }
}