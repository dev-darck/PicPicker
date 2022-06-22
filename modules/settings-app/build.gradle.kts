import com.project.picpicker.*
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
        module(Modules.navigationApi),
        module(Modules.commonResources),
        module(Modules.commonUi),
        module(Modules.imageLoader),
        module(Modules.util),
        module(Modules.commonTheme)
    )
)