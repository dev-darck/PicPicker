import com.project.picpicker.Dependency.hiltDeps
import com.project.picpicker.Dependency.jetpackComposeActivity
import com.project.picpicker.Dependency.jetpackComposeUiDeps
import com.project.picpicker.Dependency.navigationDeps
import com.project.picpicker.Modules.baseNavigation
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.commonTheme
import com.project.picpicker.Modules.imageLoader
import com.project.picpicker.Modules.toolBar
import com.project.picpicker.Modules.unsplashApi
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
        module(imageLoader),
        module(toolBar),
        module(unsplashApi),
        module(commonTheme),
        module(commonResources),
        apiModule(baseNavigation)
    ),
    plugins = addAppPlug(
        hiltPlugin
    )
)