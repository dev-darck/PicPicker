import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.navigation
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.addLibPlug
import com.project.picpicker.hiltPlugin
import com.project.picpicker.plugins.config.module

module(
    appDependency = addDep(
        *navigation,
        *hilt,
    ),
    plugins = addLibPlug(
        hiltPlugin
    )
)