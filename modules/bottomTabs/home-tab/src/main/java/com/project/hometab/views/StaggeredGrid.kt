package com.project.hometab.views

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.math.abs

/**
 * The composable of staggered grid
 *
 * @param modifier the modifier
 * @param contentPadding the padding that will surround the content
 * @param cells how the content will be visible
 * @param state the state of the first column in the [LazyStaggeredGrid]
 * @param content the content, must be of [StaggeredGridScope]
 *
 * @author Younes Lagmah
 * @since 2022.01.24
 * @sample com.nesyou.staggeredgrid.StaggeredGridExample()
 */
@Composable
fun LazyStaggeredGrid(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    cells: StaggeredCells,
    state: LazyListState = rememberLazyListState(),
    content1: StaggeredGridScope.() -> Unit,
    content2: StaggeredGridScope.() -> Unit,
    differencesDp: Float
) {
    val scope1 = StaggeredGridScopeImpl()
    scope1.apply(content1)
    val scope2 = StaggeredGridScopeImpl()
    scope2.apply(content2)
    BoxWithConstraints(
        modifier = modifier
    ) {
        StaggeredGrid(
            scope1 = scope1,
            scope2 = scope2,
            padding = contentPadding,
            columnsNumber = if (cells is StaggeredCells.Fixed) cells.count else maxOf(
                (maxWidth / (cells as StaggeredCells.Adaptive).minSize).toInt(),
                1
            ),
            state = state,
            differencesDp = differencesDp
        )
    }
}


@Composable
internal fun StaggeredGrid(
    scope1: StaggeredGridScopeImpl,
    scope2: StaggeredGridScopeImpl,
    padding: PaddingValues,
    columnsNumber: Int,
    state: LazyListState,
    differencesDp: Float
) {
    val states = mutableListOf(state).apply {
        repeat(columnsNumber - 1) { this.add(element = rememberLazyListState()) }
    }
    val layoutDirection = LocalLayoutDirection.current
    val coroutineScope = rememberCoroutineScope()

    val scroll = rememberScrollableState { delta ->
        coroutineScope.launch {
            states.forEach {
                it.scrollBy(-delta)
                Timber.d("size: ${it.layoutInfo.viewportSize}")

            }
        }
        delta
    }

    Row(
        modifier = Modifier.scrollable(
            scroll,
            Orientation.Vertical,
            flingBehavior = ScrollableDefaults.flingBehavior()
        )
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1F),
            state = states[0],
            contentPadding = PaddingValues(
                start = padding.calculateLeftPadding(layoutDirection),
                end = 0.dp,
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            userScrollEnabled = false
        ) {
            for (i in scope1.content.indices) {
                item {
                    scope1.content[i]()
                }
            }

            if (differencesDp < 0) {
                item {
                    Timber.d("box 1 size: $differencesDp")
                    Box(modifier = Modifier
                        .size(abs(differencesDp).dp)
                        .background(Color.Green))
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1F),
            state = states[1],
            contentPadding = PaddingValues(
                start = 0.dp,
                end = padding.calculateRightPadding(layoutDirection),
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ),
            userScrollEnabled = false
        ) {
            for (i in scope2.content.indices) {
                item {
                    scope2.content[i]()
                }

            }
            if (differencesDp > 0) {
                item {
                    Timber.d("box 2 size: $differencesDp")
                    Box(modifier = Modifier
                        .size(differencesDp.dp)
                        .background(Color.Green))
                }
            }
        }
    }

}


sealed class StaggeredCells {
    /**
     * Combines cells with a fixed number of columns.
     */
    class Fixed(val count: Int) : StaggeredCells()

    /**
     * Combine cells with an adaptive number of columns depends on each screen with the given [minSize]
     */
    class Adaptive(val minSize: Dp) : StaggeredCells()
}


class StaggeredGridScopeImpl : StaggeredGridScope {
    private val _data = mutableListOf<@Composable () -> Unit>()

    val content get() = _data.toList()

    override fun item(content: @Composable () -> Unit) {
        _data.add(content)
    }

    override fun items(count: Int, itemContent: @Composable (index: Int) -> Unit) {
        repeat(count) {
            _data.add {
                itemContent(it)
            }
        }
    }

    override fun <T> items(items: Array<T>, itemContent: @Composable (item: T) -> Unit) {
        items.forEach {
            _data.add {
                itemContent(it)
            }
        }
    }


    override fun <T> items(items: List<T>, itemContent: @Composable (item: T) -> Unit) {
        items.forEach {
            _data.add {
                itemContent(it)
            }
        }
    }
}

interface StaggeredGridScope {
    /**
     * Add a single items
     *
     * @param content the content
     */
    fun item(content: @Composable () -> Unit)


    /**
     * Add an item that will be repeated [count] times
     *
     * @param count count of times
     * @param itemContent items content
     */
    fun items(count: Int, itemContent: @Composable (index: Int) -> Unit)


    /**
     * Add an array of items
     *
     * @param items items array
     * @param itemContent items content
     */
    fun <T> items(items: Array<T>, itemContent: @Composable (item: T) -> Unit)


    /**
     * Add an list of items
     *
     * @param items items list
     * @param itemContent items content
     */
    fun <T> items(items: List<T>, itemContent: @Composable (item: T) -> Unit)
}