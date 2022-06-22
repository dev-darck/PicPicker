
import com.project.picpicker.composePreviewDeps
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.jetpackComposeUiDeps
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        jetpackComposeUiDeps,
        composePreviewDeps,
    )
)