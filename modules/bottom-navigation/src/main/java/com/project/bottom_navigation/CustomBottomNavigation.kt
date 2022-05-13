package com.project.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val BottomNavigationHeight = 56.dp

@Composable
fun CustomBottomNavigation(
    modifier: Modifier = Modifier,
    elevation: Dp = 15.dp,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .shadow(
                elevation + LocalAbsoluteElevation.current,
                RectangleShape,
                false,
                MaterialTheme.colors.secondaryVariant
            )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(BottomNavigationHeight)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}