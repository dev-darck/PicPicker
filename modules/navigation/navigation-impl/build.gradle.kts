import com.project.picpicker.*
import com.project.picpicker.Modules.navigationApi
import com.project.picpicker.dependency.helper.addLibPlug
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        navigationDeps,
        hiltDeps,
        jetpackComposeUiDeps,
        composePreviewDeps,
    ) + deps(
        module(navigationApi)
    ),
    plugins = addLibPlug(
        hiltPlugin
    )
)