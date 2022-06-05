import com.project.picpicker.BuildConfig
import leakcanary.LeakCanary

object DebugOptions {
    fun initLeakCanary() {
        if (BuildConfig.ENABLED_LEAK_CANARY) {
            LeakCanary.config = LeakCanary.config.copy(dumpHeap = true)
            LeakCanary.showLeakDisplayActivityLauncherIcon(true)
        }
    }
}