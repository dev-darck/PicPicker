
import com.project.picpicker.Dependency.composePreviewDeps
import com.project.picpicker.Dependency.jetpackComposeUiDeps
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        jetpackComposeUiDeps,
        composePreviewDeps,
    )
)