package com.project.searchsreen.viewmodel

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.model.PhotoModel
import com.project.navigationapi.config.Route
import com.project.navigationapi.config.SearchScreenRoute
import com.project.navigationapi.navigation.Navigation
import com.project.searchsreen.screen.TitleSelection
import com.project.searchsreen.screen.TitleSelection.*
import com.project.searchsreen.viewmodel.SearchState.*
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.api.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    navigation: Navigation,
    savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository,
) : ViewModel(), Navigation by navigation {

    private var page: Int = 1
    private var screenDensity = 0F
    private var screenWidthDp = 0.dp
    private var spanCount = 2
    private val columnWidth
        get() = screenWidthDp / spanCount - 20.dp
    private val _state = MutableStateFlow<SearchState>(NotFindContent(""))
    private val _stateSelection = MutableStateFlow(PROFILE)
    private val _stateQuery =
        MutableStateFlow(SearchScreenRoute.queryNotDefault(savedStateHandle.get<String>(Route.query).orEmpty()))

    val query: StateFlow<String> = _stateQuery.asStateFlow()
    val stateSelection: StateFlow<TitleSelection> = _stateSelection.asStateFlow()
    val state: StateFlow<SearchState> = _state.asStateFlow()

    fun setNeedSize(screenDensity: Float, screenWidthDp: Dp) {
        this.screenDensity = screenDensity
        this.screenWidthDp = screenWidthDp
    }

    fun updateTitleSelection(titleSelection: TitleSelection) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.value.isNotEmpty()) {
                _state.emit(Loading)
            }
            _stateSelection.emit(titleSelection)
            loadContent(_stateQuery.value, _stateSelection.value, page)
        }
    }

    fun retry() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(Loading)
            loadContent(_stateQuery.value, _stateSelection.value, page)
        }
    }

    fun search(query: String? = null, titleSelection: TitleSelection? = null, page: Int = 1) {
        this.page = page
        viewModelScope.launch(Dispatchers.IO) {
            delay(350)
            if ((query == null || titleSelection == null) && page != 1) {
                loadContent(_stateQuery.value, _stateSelection.value, page, false)
            } else {
                _stateQuery.emit(query.orEmpty())
                _stateSelection.emit(titleSelection ?: PROFILE)
                loadContent(_stateQuery.value, _stateSelection.value, page, true)
            }
        }
    }

    private suspend fun loadContent(
        query: String,
        titleSelection: TitleSelection,
        page: Int,
        isSearch: Boolean = true
    ) {
        if (query.isEmpty()) return
        val searchResult = when (titleSelection) {
            PHOTOS -> unsplashRepository.searchPhoto(query, page, 30)
            PROFILE -> unsplashRepository.searchUser(query, page, 30)
            COLLECTIONS -> unsplashRepository.searchCollection(query, page, 30)
        }
        if (searchResult is ResultWrapper.NetworkError || searchResult is ResultWrapper.GenericError) {
            _state.update { Exception }
            return
        }
        val result = searchResult as ResultWrapper.Success
        if (result.value.isEmpty() && state.value !is Success) {
            _state.update { NotFindContent("By \"$query\" information not found") }
            return
        }
        _state.update {
            val stateUpdate = it.updateSuccess(result.value, titleSelection, isSearch, query, page)
            if (stateUpdate !is Success) return@update stateUpdate
            if (stateUpdate.selections == PHOTOS) {
                val photos = stateUpdate.photos?.getItems() ?: emptyList()
                stateUpdate.photos?.updateData(measureResult(photos))
                stateUpdate
            } else {
                stateUpdate
            }
        }
    }

    private fun measureResult(result: List<PhotoModel>): List<PhotoModel> =
        result.map { photoModel ->
            photoModel.aspectRatio = photoModel.width / photoModel.height
            photoModel.measureHeight =
                ((columnWidth.value / (photoModel.width / screenDensity)) * photoModel.height + 20 * screenDensity).toInt()
            photoModel
        }
}