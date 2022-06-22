import com.project.picpicker.Modules.commonResources
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.jetpackComposeUiDeps
import com.project.picpicker.plugins.config.module
import com.project.picpicker.systemUiController

module(
    appDependency = deps(
        jetpackComposeUiDeps,
    ) + deps(
        systemUiController,
        module(commonResources)
    )
)