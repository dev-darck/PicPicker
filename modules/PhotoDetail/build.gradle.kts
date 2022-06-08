import com.project.picpicker.Dependency
import com.project.picpicker.Dependency.composePreviewDeps
import com.project.picpicker.Dependency.hiltDeps
import com.project.picpicker.Dependency.jetpackComposeUiDeps
import com.project.picpicker.Model
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
        Dependency.hiltNavigation,
        module(Modules.navigationApi),
        module(Modules.commonResources),
        module(Model.commonModel),
    )
)