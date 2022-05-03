import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.jetpackComposeActivity
import com.project.picpicker.Modules.commonTheme
import com.project.picpicker.Modules.navigation
import com.project.picpicker.dependency.helper.addAppPlug
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.hiltPlugin
import com.project.picpicker.plugins.config.application

application(
    appDependency = addDep(
        jetpackComposeActivity,
        *hilt,
    ) + addDep(
        module(navigation),
        module(commonTheme),
    ),
    plugins = addAppPlug(
        hiltPlugin
    )
)