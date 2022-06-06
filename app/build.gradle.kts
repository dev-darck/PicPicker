import com.project.picpicker.Dependency.hiltDeps
import com.project.picpicker.Dependency.jetpackComposeActivity
import com.project.picpicker.Dependency.jetpackComposeUiDeps
import com.project.picpicker.Dependency.navigationDeps
import com.project.picpicker.Modules.bottomNavigation
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.commonTheme
import com.project.picpicker.Modules.imageLoader
import com.project.picpicker.Modules.navigationApi
import com.project.picpicker.Modules.navigationImpl
import com.project.picpicker.Modules.toolBar
import com.project.picpicker.Modules.unsplashApi
import com.project.picpicker.Screen.webViewScreen
import com.project.picpicker.TabModule.collectionTab
import com.project.picpicker.TabModule.downloadTab
import com.project.picpicker.TabModule.homeTab
import com.project.picpicker.TabModule.profileTab
import com.project.picpicker.dependency.helper.*
import com.project.picpicker.hiltPlugin
import com.project.picpicker.plugins.config.application

application(
    appDependency = deps(
        jetpackComposeUiDeps,
        navigationDeps,
        hiltDeps,
    ) + deps(
        jetpackComposeActivity,
        module(navigationApi),
        module(webViewScreen),
        module(toolBar),
        module(homeTab),
        module(downloadTab),
        module(unsplashApi),
        module(commonTheme),
        module(navigationImpl),
        module(bottomNavigation),
        module(collectionTab),
        module(profileTab),
        module(imageLoader),
        module(commonResources),
    ),
    plugins = addAppPlug(
        hiltPlugin
    )
)