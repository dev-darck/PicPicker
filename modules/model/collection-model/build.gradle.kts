import com.project.picpicker.Dependency.gson
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.plugins.config.module

module(
    enabledCompose = false,
    appDependency = addDep(
        gson
    )
)