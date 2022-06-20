import com.project.picpicker.Dependency.composePreviewDeps
import com.project.picpicker.Dependency.hiltDeps
import com.project.picpicker.Dependency.hiltNavigation
import com.project.picpicker.Dependency.jetpackComposeUiDeps
import com.project.picpicker.Dependency.systemuiController
import com.project.picpicker.Modules
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
        systemuiController,
        module(Modules.navigationApi),
        module(Modules.commonResources),
        module(Modules.commonUi),
        module(Modules.imageLoader),
        module(Modules.util),
        module(Modules.commonTheme)
    )
)