import com.project.picpicker.Model.commonModel
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.hiltDeps
import com.project.picpicker.network
import com.project.picpicker.plugins.config.module

module(
    enabledCompose = false,
    appDependency = deps(
        network,
        hiltDeps
    ) + deps(
        module(commonModel)
    )
)