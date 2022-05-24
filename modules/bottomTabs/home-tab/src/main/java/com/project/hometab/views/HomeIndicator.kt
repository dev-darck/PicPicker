package com.project.hometab.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.project.scrollable_tab_row.TabPosition
import com.project.scrollable_tab_row.tabIndicatorOffset

@Composable
internal fun HomeIndicator(tabPosition: TabPosition) {
    TabRowDefaults.Indicator(
        modifier = Modifier
            .tabIndicatorOffset(tabPosition)
            .padding(horizontal = ViewsConfig.indicatorHorizontalPaddings.dp)
            .clip(MaterialTheme.shapes.small),
        color = MaterialTheme.colors.onSurface
    )
}