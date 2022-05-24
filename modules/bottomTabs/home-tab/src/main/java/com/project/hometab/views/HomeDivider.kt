package com.project.hometab.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun HomeDivider() {
    TabRowDefaults.Divider(
        modifier = Modifier
            .padding(start = ViewsConfig.dividerHorizontalPaddings.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colors.onSecondary
    )
}