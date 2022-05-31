import com.project.picpicker.Dependency.glide
import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.jetpackComposeActivity
import com.project.picpicker.Dependency.jetpackComposeUi
import com.project.picpicker.Dependency.navigation
import com.project.picpicker.Modules.bottomNavigation
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.commonTheme
import com.project.picpicker.Modules.imageLoader
import com.project.picpicker.Modules.navigationApi
import com.project.picpicker.Modules.navigationImpl
import com.project.picpicker.Modules.toolBar
import com.project.picpicker.Modules.unsplashApi
import com.project.picpicker.TabModule.collectionTab
import com.project.picpicker.TabModule.downloadTab
import com.project.picpicker.TabModule.homeTab
import com.project.picpicker.TabModule.profileTab
import com.project.picpicker.dependency.helper.addAppPlug
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.hiltPlugin
import com.project.picpicker.plugins.config.application

application(
    appDependency = addDep(
        *glide,
        *navigation,
        jetpackComposeActivity,
        *jetpackComposeUi,
        *hilt,
    ) + addDep(
        module(navigationApi),
        module(navigationImpl),
        module(commonTheme),
        module(bottomNavigation),
        module(homeTab),
        module(collectionTab),
        module(downloadTab),
        module(profileTab),
        module(unsplashApi),
        module(imageLoader),
        module(toolBar),
        module(commonResources),
    ),
    plugins = addAppPlug(
        hiltPlugin
    )
)