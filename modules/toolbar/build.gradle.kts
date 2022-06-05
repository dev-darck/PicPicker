import com.project.picpicker.Dependency.composePreview
import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.jetpackComposeUi
import com.project.picpicker.Dependency.navigation
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.commonUi
import com.project.picpicker.Modules.navigationApi
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = addDep(
        *navigation,
        *jetpackComposeUi,
        *composePreview,
        *hilt,
    ) + addDep(
        module(navigationApi),
        module(commonResources),
        module(commonUi),
    )
)