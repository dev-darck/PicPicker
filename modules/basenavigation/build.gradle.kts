import com.project.picpicker.Dependency.hiltDeps
import com.project.picpicker.Dependency.navigationDeps
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.plugins.config.module
import com.project.picpicker.TabModule.collectionTab
import com.project.picpicker.TabModule.downloadTab
import com.project.picpicker.TabModule.homeTab
import com.project.picpicker.TabModule.profileTab
import com.project.picpicker.Modules.navigationImpl
import com.project.picpicker.Screen.photoDetail
import com.project.picpicker.Modules.bottomNavigation
import com.project.picpicker.Modules.navigationApi
import com.project.picpicker.Modules.util
import com.project.picpicker.Screen.webViewScreen
import com.project.picpicker.dependency.helper.apiModule
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus

module(
    appDependency = deps(
        navigationDeps,
        hiltDeps,
    ) + deps(
        apiModule(navigationApi),
        apiModule(homeTab),
        apiModule(downloadTab),
        apiModule(navigationImpl),
        apiModule(bottomNavigation),
        apiModule(collectionTab),
        apiModule(profileTab),
        apiModule(webViewScreen),
        apiModule(photoDetail),
        module(util)
    )
)