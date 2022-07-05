import com.project.picpicker.Model.commonModel
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.commonTheme
import com.project.picpicker.Modules.imageLoader
import com.project.picpicker.composePreviewDeps
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.jetpackComposeUiDeps
import com.project.picpicker.palette
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        jetpackComposeUiDeps,
        composePreviewDeps,
    ) + deps(
        palette,
        module(commonResources),
        module(imageLoader),
        module(commonModel),
        module(commonTheme)
    )
)