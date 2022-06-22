import com.project.picpicker.*
import com.project.picpicker.Model.commonModel
import com.project.picpicker.Modules.bottomNavigation
import com.project.picpicker.Modules.commonResources
import com.project.picpicker.Modules.commonUi
import com.project.picpicker.Modules.imageLoader
import com.project.picpicker.Modules.navigationApi
import com.project.picpicker.Modules.scrollableTabRow
import com.project.picpicker.Modules.toolBar
import com.project.picpicker.Modules.unsplashApi
import com.project.picpicker.dependency.helper.deps
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    appDependency = deps(
        jetpackComposeUiDeps,
        hiltDeps,
        composePreviewDeps,
        pagerDeps,
    ) + deps(
        hiltNavigation,
        module(bottomNavigation),
        module(navigationApi),
        module(commonResources),
        module(scrollableTabRow),
        module(toolBar),
        module(unsplashApi),
        module(commonModel),
        module(imageLoader),
        module(commonUi),
    )
)