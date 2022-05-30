package com.project.scrollable_tab_row

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Based on [androidx.compose.material.ScrollableTabRow].
 * Differences:
 * 1. Divider don't depends on tabs and fill full layout width.
 * 2. Indicator placed in the center of divider along the Y axis.
 */
@Composable
fun PicPickerScrollableTabRow(
    modifier: Modifier,
    selectedTabIndex: Int,
    edgePadding: Dp,
    tab: @Composable () -> Unit,
    divider: @Composable () -> Unit = { TabRowDefaults.Divider() },
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = { tabPositions ->
        TabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
        )
    }
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val scrollableTabData = remember(scrollState, coroutineScope) {
        ScrollableTabData(
            scrollState = scrollState,
            coroutineScope = coroutineScope
        )
    }

    // I don't know how to get screen width here in another way
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    SubcomposeLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.CenterStart)
            .horizontalScroll(scrollState)
            // Works fine without everything below, but stole from original class just in case
            .selectableGroup()
            .clipToBounds(),
    ) { constraints ->
        val padding = edgePadding.roundToPx()

        val tabsPlaceable = subcompose(TabSlots.Tabs, tab).map { measurable ->
            measurable.measure(constraints)
        }

        // Divider must fill entire width of the screen, so layout width mustn't be less than
        // the screen width
        val layoutWidth = maxOf(screenWidth.roundToPx(), tabsPlaceable.sumWidth + (padding * 2))

        val dividerPlaceable = subcompose(TabSlots.Divider, divider).map { measurable ->
            measurable.measure(constraints.copy(minWidth = layoutWidth))
        }

        val tabPositions: MutableList<TabPosition> = mutableListOf()
        var left = padding
        tabsPlaceable.forEach { placeable ->
            tabPositions.add(TabPosition(left.toDp(), placeable.width.toDp()))
            left += placeable.width
        }

        val indicatorPlaceable = subcompose(TabSlots.Indicator) {
            indicator(tabPositions)
        }.map { measurable ->
            measurable.measure(constraints)
        }

        val tabHeight = tabsPlaceable.maxHeight
        val dividerHeight = dividerPlaceable.maxHeight
        val indicatorHeight = indicatorPlaceable.maxHeight

        // Indicator may goes out of the tab, so layout height calculate by tab height plus
        // out-of-bounds part
        val layoutHeight = maxOf(
            tabHeight,
            tabHeight - (dividerHeight / 2) + (indicatorHeight / 2)
        )

        layout(layoutWidth, layoutHeight) {
            tabsPlaceable.forEachIndexed { index, placeable ->
                val tabPosition = tabPositions[index]
                placeable.placeRelative(tabPosition.left.roundToPx(), 0)
            }

            dividerPlaceable.forEach { placeable ->
                placeable.placeRelative(x = 0, y = tabHeight - dividerHeight)
            }

            indicatorPlaceable.forEach { placeable ->
                placeable.placeRelative(x = 0, y = layoutHeight - indicatorHeight)
            }

            scrollableTabData.onLaidOut(
                density = this@SubcomposeLayout,
                edgeOffset = padding,
                tabPositions = tabPositions,
                selectedTab = selectedTabIndex
            )
        }
    }
}

val List<Placeable>.maxHeight
    get() = this.maxOf(Placeable::height)

val List<Placeable>.sumWidth
    get() = this.sumOf(Placeable::width)

/** full information here: [androidx.compose.material.ScrollableTabData] */
private class ScrollableTabData(
    private val scrollState: ScrollState,
    private val coroutineScope: CoroutineScope
) {
    private var selectedTab: Int? = null

    fun onLaidOut(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<TabPosition>,
        selectedTab: Int
    ) {
        if (this.selectedTab != selectedTab) {
            this.selectedTab = selectedTab
            tabPositions.getOrNull(selectedTab)?.let {
                val calculatedOffset = it.calculateTabOffset(density, edgeOffset, tabPositions)
                if (scrollState.value != calculatedOffset) {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(
                            calculatedOffset,
                            animationSpec = ScrollableTabRowScrollSpec
                        )
                    }
                }
            }
        }
    }

    private fun TabPosition.calculateTabOffset(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<TabPosition>
    ): Int = with(density) {
        val totalTabRowWidth = tabPositions.last().right.roundToPx() + edgeOffset
        val visibleWidth = totalTabRowWidth - scrollState.maxValue
        val tabOffset = left.roundToPx()
        val scrollerCenter = visibleWidth / 2
        val tabWidth = width.roundToPx()
        val centeredTabOffset = tabOffset - (scrollerCenter - tabWidth / 2)
        val availableSpace = (totalTabRowWidth - visibleWidth).coerceAtLeast(0)
        return centeredTabOffset.coerceIn(0, availableSpace)
    }
}

/** full information here: [androidx.compose.material.TabPosition] */
@Immutable
class TabPosition internal constructor(val left: Dp, val width: Dp) {
    val right: Dp get() = left + width

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TabPosition) return false

        if (left != other.left) return false
        if (width != other.width) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + width.hashCode()
        return result
    }

    override fun toString(): String {
        return "TabPosition(left=$left, right=$right, width=$width)"
    }
}

/** full information here: [androidx.compose.material.ScrollableTabRowScrollSpec] */
private val ScrollableTabRowScrollSpec: AnimationSpec<Float> = tween(
    durationMillis = 250,
    easing = FastOutSlowInEasing
)

/** full information here: [androidx.compose.material.TabSlots] */
private enum class TabSlots {
    Tabs,
    Divider,
    Indicator
}
