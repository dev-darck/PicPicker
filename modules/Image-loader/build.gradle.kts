import com.project.picpicker.Dependency.composePreviewDeps
import com.project.picpicker.Dependency.glideDeps
import com.project.picpicker.Dependency.hiltDeps
import com.project.picpicker.Dependency.jetpackComposeUiDeps
import com.project.picpicker.Dependency.okHttp
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.util
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        glideDeps,
        jetpackComposeUiDeps,
        hiltDeps,
        composePreviewDeps
    ) + deps(
        okHttp,
        module(util),
        module(commonResources)
    )
)