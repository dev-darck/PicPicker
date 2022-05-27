package com.project.navigationapi.config

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

interface BottomSheetConfig: Config {
    val openBottomSheet: @Composable ColumnScope.(NavBackStackEntry) -> Unit
}