import com.project.picpicker.Dependency.composePreview
import com.project.picpicker.Dependency.jetpackComposeUi
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = addDep(
        *jetpackComposeUi,
        *composePreview,
    ) + addDep(
        module(commonResources)
    )
)