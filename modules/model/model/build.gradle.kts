import com.project.picpicker.Dependency.gson
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.plugins.config.module

module(
    enabledCompose = false,
    appDependency = deps(
        gson
    )
)