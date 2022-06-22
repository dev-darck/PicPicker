import com.project.picpicker.dependency.helper.addLibPlug
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.hiltDeps
import com.project.picpicker.hiltPlugin
import com.project.picpicker.navigationDeps
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        navigationDeps,
        hiltDeps,
    ),
    plugins = addLibPlug(
        hiltPlugin
    )
)