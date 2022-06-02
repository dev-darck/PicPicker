import com.project.picpicker.Dependency.composePreview
import com.project.picpicker.Dependency.glide
import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.jetpackComposeUi
import com.project.picpicker.Dependency.okHttp
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.util
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = addDep(
        okHttp,
        *glide,
        *jetpackComposeUi,
        *hilt,
        *composePreview
    ) + addDep(
        module(util),
        module(commonResources)
    )
)