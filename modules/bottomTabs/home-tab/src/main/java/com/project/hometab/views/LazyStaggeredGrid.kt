package com.project.hometab.views

//@Composable
//fun LazyStaggeredGrid(
//    columnCount: Int,
//    contentPadding: PaddingValues = PaddingValues(0.dp),
//    content: @Composable LazyStaggeredGridScope.() -> Unit,
//) {
//    val states: Array<LazyListState> = (0 until columnCount)
//        .map { rememberLazyListState() }
//        .toTypedArray()
//    val scope = rememberCoroutineScope { Dispatchers.Main.immediate }
//    val scroll = rememberScrollableState { delta ->
//        scope.launch {
//            states.forEach {
//                Timber.d("viewportSize.height: ${-delta}")
//                it.scrollBy(-delta)
//            }
//        }
//        delta
//    }
//    val gridScope = LazyStaggeredGridScope(columnCount)
//    content(gridScope)
//
//    Box(
//        modifier = Modifier
//            .scrollable(
//                scroll,
//                Orientation.Vertical,
//                flingBehavior = ScrollableDefaults.flingBehavior()
//            )
//    ) {
//        Row {
//            for (index in 0 until columnCount) {
//                LazyColumn(
//                    userScrollEnabled = false,
//                    contentPadding = contentPadding,
//                    state = states[index],
//                    modifier = Modifier
//                        .weight(1f)
//                        .onSizeChanged {
//                            Timber.d("height: ${it.height}")
//                        }
//                ) {
//                    for ((key, itemContent) in gridScope.items[index]) {
//                        item(key = key) {
//                            itemContent()
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//class LazyStaggeredGridScope(
//    private val columnCount: Int,
//) {
//
//    var currentIndex = 0
//    val items: Array<MutableList<Pair<Any?, @Composable () -> Unit>>> =
//        Array(columnCount) { mutableListOf() }
//
//    fun item(key: Any? = null, content: @Composable () -> Unit) {
//        items[currentIndex % columnCount] += key to content
//        currentIndex += 1
//    }
//}