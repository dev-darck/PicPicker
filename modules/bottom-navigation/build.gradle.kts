import com.project.picpicker.Dependency
import com.project.picpicker.Dependency.composePreview
import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.jetpackComposeUi
import com.project.picpicker.Modules
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.addLibPlug
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.hiltPlugin
import com.project.picpicker.plugins.config.module

module(
    appDependency = addDep(
        *Dependency.navigation,
        *hilt,
        *jetpackComposeUi,
        *composePreview,
    ) + addDep(
        module(Modules.navigation)
    ),
    plugins = addLibPlug(
        hiltPlugin
    )
)