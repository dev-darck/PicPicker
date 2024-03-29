import com.project.picpicker.*
import com.project.picpicker.Model.commonModel
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.commonTheme
import com.project.picpicker.Modules.commonUi
import com.project.picpicker.Modules.imageLoader
import com.project.picpicker.Modules.navigationApi
import com.project.picpicker.Modules.unsplashApi
import com.project.picpicker.Modules.util
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        jetpackComposeUiDeps,
        hiltDeps,
        composePreviewDeps,
    ) + deps(
        hiltNavigation,
        systemUiController,
        module(navigationApi),
        module(commonResources),
        module(commonUi),
        module(commonModel),
        module(unsplashApi),
        module(imageLoader),
        module(util),
        module(commonTheme)
    )
)