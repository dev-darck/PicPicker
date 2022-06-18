import com.project.picpicker.Dependency.googleService
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        googleService
    ),
    enabledCompose = false
)