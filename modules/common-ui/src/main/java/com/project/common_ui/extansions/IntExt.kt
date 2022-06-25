package com.project.common_ui.extansions

val Int?.orDefault: String get() = (this ?: 0).toString()