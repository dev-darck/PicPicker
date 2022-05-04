import com.project.picpicker.Dependency
import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.jetpackComposeActivity
import com.project.picpicker.Dependency.jetpackComposeUi
import com.project.picpicker.Modules.bottomNavigation
import com.project.picpicker.Modules.commonTheme
import com.project.picpicker.Modules.navigation
import com.project.picpicker.TabModule.collectionTab
import com.project.picpicker.TabModule.downloadTab
import com.project.picpicker.TabModule.homeTab
import com.project.picpicker.TabModule.profileTab
import com.project.picpicker.dependency.helper.addAppPlug
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.hiltPlugin
import com.project.picpicker.plugins.config.application

application(
    appDependency = addDep(
        *Dependency.navigation,
        jetpackComposeActivity,
        *jetpackComposeUi,
        *hilt,
    ) + addDep(
        module(navigation),
        module(commonTheme),
        module(bottomNavigation),
        module(homeTab),
        module(collectionTab),
        module(downloadTab),
        module(profileTab),
    ),
    plugins = addAppPlug(
        hiltPlugin
    )
)