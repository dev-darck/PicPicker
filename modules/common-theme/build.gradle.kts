import com.project.picpicker.Dependency.jetpackComposeUi
import com.project.picpicker.Dependency.systemuiController
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = addDep(
        *jetpackComposeUi,
        systemuiController
    ) + addDep(
        module(commonResources)
    )
)