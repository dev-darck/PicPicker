package com.project.navigationapi.config

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class BottomIcon {
    @DrawableRes
    var icon: Int? = null
    @StringRes
    var contentDescription: Int? = null
    var click: () -> Unit = { }
}