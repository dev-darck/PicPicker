import com.project.picpicker.Dependency.gson
import com.project.picpicker.Dependency.hiltDeps
import com.project.picpicker.Dependency.okHttpLogger
import com.project.picpicker.Dependency.retrofit
import com.project.picpicker.Model.commonModel
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    enabledCompose = false,
    appDependency = deps(
        hiltDeps
    ) + deps(
        okHttpLogger,
        retrofit,
        gson,
        module(commonModel)
    )
)