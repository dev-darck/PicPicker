import com.project.picpicker.Dependency.composePreviewDeps
import com.project.picpicker.Dependency.hiltDeps
import com.project.picpicker.Dependency.jetpackComposeUiDeps
import com.project.picpicker.Dependency.navigationDeps
import com.project.picpicker.Modules.commonUi
import com.project.picpicker.Modules.navigationApi
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.addLibPlug
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.hiltPlugin
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        navigationDeps,
        hiltDeps,
        jetpackComposeUiDeps,
        composePreviewDeps,
    ) + deps(
        module(navigationApi),
        module(commonUi),
    ),
    plugins = addLibPlug(
        hiltPlugin
    )
)