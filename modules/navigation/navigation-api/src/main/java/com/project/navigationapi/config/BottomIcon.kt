package com.project.navigationapi.config

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.project.navigationapi.navigation.Navigation

data class BottomIcon(
    val navigation: Navigation? = null
) {
    @DrawableRes
    var icon: Int? = null
    @StringRes
    var contentDescription: Int? = null
    val click: () -> Unit = {
        navigation
    }
}