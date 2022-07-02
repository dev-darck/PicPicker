package com.project.common_ui.grid

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.common_ui.paging.Paging

enum class ScrollState {
    SCROLL_STATE_IDLE,
    SCROLL_STATE_DRAGGING,
}

@Composable
fun rememberScrollState(state: (ScrollState) -> Unit = {}): OnScrollListener {
    var y = 0
    return object : OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            y = dy
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (SCROLL_STATE_IDLE == newState) {
                if (y <= 0) {
                    state(ScrollState.SCROLL_STATE_IDLE)
                }
            }
            if (SCROLL_STATE_DRAGGING == newState) {
                if (y > 0) {
                    state(ScrollState.SCROLL_STATE_DRAGGING)
                }
            }
        }
    }
}

/**
 * This is not a very good solution. However, there is no implementation yet LazyStaggeredGrid on Compose
 * this is probably one of the best solutions
 */
@Composable
fun <T : Any> StaggeredGrid(
    modifier: Modifier = Modifier,
    spanCount: Int = 2,
    data: Paging<T>,
    isScrollEnabled: Boolean = false,
    scrollState: OnScrollListener? = null,
    scrollToPosition: Int = 0,
    content: @Composable (T) -> Unit,
    measureHeight: (T) -> Int = { MATCH_PARENT },
) {
    var removeListener by remember { mutableStateOf(false) }
    val manager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL).apply {
        gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
    }

    AndroidView(modifier = modifier, factory = {
        val adapter = StaggeredAdapter(content, measureHeight)
        RecyclerView(it).apply {
            setHasFixedSize(true)
            clipToPadding = false
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            layoutManager = manager
            this.adapter = adapter
            adapter.setItem(data)
            suppressLayout(isScrollEnabled)
            scrollToPosition(scrollToPosition)
            animation = null
            overScrollMode = OVER_SCROLL_NEVER
            addOnScrollListener(
                object : OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        data.savePosition(manager.findFirstCompletelyVisibleItemPositions(IntArray(spanCount)).last())
                    }
                }
            )
            scrollState?.let { state -> addOnScrollListener(state) }
        }
    }, update = {
        val adapter: StaggeredAdapter<T>? = it.adapter as? StaggeredAdapter<T>
        if (data != adapter?.items) {
            val oldData = adapter?.items ?: return@AndroidView
            val diffUtilCallback: DiffUtilCallback<*> =
                DiffUtilCallback(oldData, data)
            val result = DiffUtil.calculateDiff(diffUtilCallback)
            adapter.setItem(data)
            result.dispatchUpdatesTo(adapter)
            manager.invalidateSpanAssignments()
        }
        if (removeListener) {
            scrollState?.let { state -> it.removeOnScrollListener(state) }
        }
    })

    DisposableEffect(key1 = removeListener) {
        onDispose {
            removeListener = true
        }
    }
}

class StaggeredAdapter<T : Any>(
    private val content: @Composable (T) -> Unit,
    val measureHeight: (T) -> Int = { MATCH_PARENT },
) : Adapter<ViewHolder<T>>() {

    var items: Paging<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val param = ComposeView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
        return ViewHolder(param)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder<T>, position: Int) {
        items?.emitNewItem(position)
        val item = items?.get(position) ?: return
        viewHolder.itemView.apply {
            layoutParams.height = measureHeight(item)
        }
        viewHolder.bind(item, content)
    }

    override fun getItemCount(): Int = items?.itemCount ?: 0

    fun setItem(data: Paging<T>) {
        items = data
    }
}

class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: T, content: @Composable (T) -> Unit) {
        (itemView as ComposeView).setContent {
            content(item)
        }
    }
}

class DiffUtilCallback<T : Any>(
    val old: Paging<T>,
    val new: Paging<T>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.itemCount
    }

    override fun getNewListSize(): Int {
        return new.itemCount
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct: T? = old.get().getOrNull(oldItemPosition)
        val newProduct: T? = new.get().getOrNull(newItemPosition)
        return oldProduct == newProduct
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProduct: T? = old.get().getOrNull(oldItemPosition)
        val newProduct: T? = new.get().getOrNull(newItemPosition)
        return oldProduct.hashCode() == newProduct.hashCode() && oldProduct == newProduct
    }
}