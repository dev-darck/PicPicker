package com.project.common_ui.paging

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

sealed class PagingState {
    object Loading : PagingState()
    object Success : PagingState()
    object Error : PagingState()
}

data class SettingsPaging(
    val maxPage: Int,
    var currentPage: Int = 1,
    val countForNextPage: Int,
    var updateState: (PagingState) -> Unit = { },
) {
    var lastPosition = 0
    fun isNextPage(): Boolean {
        return currentPage != maxPage
    }

    fun updateCurrentPage() {
        currentPage += 1
    }

    fun errorState() {
        backPage()
        updateState(PagingState.Error)
    }

    private fun backPage() {
        currentPage -= 1
    }
}

data class PagingData<T : Any>(
    private var item: List<T> = listOf(),
    val settingsPaging: SettingsPaging,
) {
    fun updateData(items: List<T>): PagingData<T> {
        val newDate = item.toMutableList()
        newDate.addAll(items)
        item = newDate
        return this
    }

    fun distinct() {
        runCatching { item.distinct() }
    }

    fun isNotEmpty(): Boolean = item.isNotEmpty()

    fun getItems(): List<T> = item
}

open class Paging<T : Any>(
    pagingData: PagingData<T>,
) {
    private val scrollState = MutableStateFlow(0)
    private val items = ArrayList(pagingData.getItems())
    private val settingsPaging: SettingsPaging = pagingData.settingsPaging
    private val state = MutableStateFlow<PagingState>(PagingState.Success)
    var statePaging = state.asStateFlow()
    var itemCount = items.count()

    init {
        settingsPaging.updateState = {
            if (this.state.value != state)
                updateState(it)
        }
    }

    fun savePosition(position: Int) {
        settingsPaging.lastPosition = position
    }

    fun emitNewItem(index: Int) {
        scrollState.tryEmit(index)
    }

    fun get(index: Int): T? {
        return items.getOrNull(index)
    }

    fun updateState(state: PagingState) {
        this.state.tryEmit(state)
    }

    fun get(): List<T> {
        return items.toList()
    }

    suspend fun collectPosition(onNewPaging: (Int) -> Unit) {
        state.combine(scrollState, ::Pair)
            .distinctUntilChanged()
            .collect { (currentState, position) ->
                val last = itemCount - settingsPaging.countForNextPage
                if (position >= last
                    && settingsPaging.isNextPage()
                    && currentState != PagingState.Loading
                    && currentState != PagingState.Error
                ) {
                    state.tryEmit(PagingState.Loading)
                    settingsPaging.updateCurrentPage()
                    onNewPaging(settingsPaging.currentPage)
                }
            }
    }
}

@Composable
fun <T : Any> PagingData<T>.rememberAsNewPage(
    onNewPaging: (Int) -> Unit,
): Paging<T> {
    val items = remember(this) {
        Paging(this)
    }
    LaunchedEffect(key1 = this) {
        items.updateState(PagingState.Success)
        items.collectPosition(onNewPaging)
    }
    return items
}

fun <T : Any> LazyListScope.pagingItems(
    items: Paging<T>,
    itemContent: @Composable (LazyItemScope.(value: T?) -> Unit),
) {
    items(
        count = items.itemCount,
        key = { it },
        itemContent = { index ->
            LaunchedEffect(key1 = index) {
                items.emitNewItem(index)
            }
            itemContent(items.get(index))
        }
    )
}

fun <T : Any> LazyGridScope.pagingItems(
    items: Paging<T>,
    itemContent: @Composable ((value: T?) -> Unit),
) {
    items(
        count = items.itemCount,
    ) { index ->
        itemContent(items.get(index))
    }
}