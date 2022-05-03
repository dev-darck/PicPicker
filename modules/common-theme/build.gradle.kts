import com.project.picpicker.Dependency.jetpackComposeUi
import com.project.picpicker.Dependency.systemuiController
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.plugins.config.module

module(
    appDependency = addDep(
        *jetpackComposeUi,
        systemuiController
    )
)