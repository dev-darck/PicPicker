package com.project.toolbar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

enum class ToolbarState {
    List,
    Grid,
}

@Singleton
class ToolbarStateManager @Inject constructor() {
    private var _currentState: MutableState<ToolbarState> = mutableStateOf(ToolbarState.List)
    val currentState: State<ToolbarState> = _currentState

    fun setState(state: ToolbarState) {
        Timber.d("setState: ${state.toString()}")
        _currentState.value = state
    }

}