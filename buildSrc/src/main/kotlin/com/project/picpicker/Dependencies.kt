package com.project.picpicker

import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.androidTest
import com.project.picpicker.dependency.helper.impl

val jetpackCompose = addDep(
    "androidx.activity:activity-compose:1.3.1".impl,
    "androidx.compose.ui:ui:1.1.1".impl,
    "androidx.compose.ui:ui-tooling:1.1.1".impl,
    "androidx.compose.foundation:foundation:1.1.1".impl,
    "androidx.compose.material:material:1.1.1".impl,
    "androidx.compose.material:material-icons-core:1.1.1".impl,
    "androidx.compose.material:material-icons-extended:1.1.1".impl,
    "androidx.compose.ui:ui-test-junit4:1.1.1".androidTest
)

