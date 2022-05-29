import com.project.picpicker.Dependency.coil
import com.project.picpicker.Dependency.composePreview
import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.jetpackComposeUi
import com.project.picpicker.Modules.util
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = addDep(
        coil,
        *jetpackComposeUi,
        *hilt,
        *composePreview
    ) + addDep(
        module(util)
    )
)