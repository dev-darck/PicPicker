package com.project.hometab.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.project.hometab.views.ViewsConfig.tabRowHorizontalPaddings
import com.project.scrollable_tab_row.PicPickerScrollableTabRow

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun HomeScrollableTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()

    PicPickerScrollableTabRow(
        modifier = Modifier,
        selectedTabIndex = selectedTabIndex,
        edgePadding = tabRowHorizontalPaddings.dp,
        tab = {
            tabs.forEachIndexed { index, title ->
                HomeTab(
                    tabIndex = index,
                    isSelected = index == selectedTabIndex,
                    title = title,
                    scope = scope,
                    pagerState = pagerState
                )
            }
        },
        divider = {
            HomeDivider()
        },
        indicator = { tabPositions ->
            HomeIndicator(tabPositions[selectedTabIndex])
        }
    )
}