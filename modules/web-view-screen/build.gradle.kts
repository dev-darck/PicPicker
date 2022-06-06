import com.project.picpicker.Dependency.composePreviewDeps
import com.project.picpicker.Dependency.hiltDeps
import com.project.picpicker.Dependency.hiltNavigation
import com.project.picpicker.Dependency.jetpackComposeUiDeps
import com.project.picpicker.Model.commonModel
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.navigationApi
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
        module(navigationApi),
        module(commonResources),
        module(commonModel),
    )
)