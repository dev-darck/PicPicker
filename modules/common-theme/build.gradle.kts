import com.project.picpicker.Dependency.jetpackComposeUiDeps
import com.project.picpicker.Dependency.systemuiController
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        jetpackComposeUiDeps,
    ) + deps(
        systemuiController,
        module(commonResources)
    )
)