import com.project.picpicker.Dependency.gson
import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.okHttpLogger
import com.project.picpicker.Dependency.retrofit
import com.project.picpicker.Model.commonModel
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    enabledCompose = false,
    appDependency = addDep(
        okHttpLogger,
        retrofit,
        gson,
        *hilt
    ) + addDep(
        module(commonModel)
    )
)