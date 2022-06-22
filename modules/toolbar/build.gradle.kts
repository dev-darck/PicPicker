import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.commonUi
import com.project.picpicker.Modules.navigationApi
import com.project.picpicker.composePreviewDeps
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.hiltDeps
import com.project.picpicker.jetpackComposeUiDeps
import com.project.picpicker.navigationDeps
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        navigationDeps,
        jetpackComposeUiDeps,
        composePreviewDeps,
        hiltDeps,
    ) + deps(
        module(navigationApi),
        module(commonResources),
        module(commonUi),
    )
)