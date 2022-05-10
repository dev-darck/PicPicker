import com.project.picpicker.Dependency.gson
import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.retrofit
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.plugins.config.module

module(
    enabledCompose = false,
    appDependency = addDep(
        retrofit,
        gson,
        *hilt
    )
)