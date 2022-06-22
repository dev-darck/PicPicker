import com.project.picpicker.*
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