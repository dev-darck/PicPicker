package com.project.common_ui.extansions

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Colors.textField: Color
    @Composable get() = if (isLight) Color.White else Color.Black

val Colors.textFieldOnSurface: Color
    @Composable get() = if (isLight) Color.Black else Color.White