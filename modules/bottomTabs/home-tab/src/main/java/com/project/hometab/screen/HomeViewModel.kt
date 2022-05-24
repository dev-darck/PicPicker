package com.project.hometab.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.model.PhotoModel
import com.project.toolbar.ToolbarStateManager
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.api.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toolbarStateManager: ToolbarStateManager,
    private val unsplashRepository: UnsplashRepository,
) : ViewModel() {

    private val _newPhotosFlow = MutableStateFlow<List<PhotoModel>>(emptyList())
    val newPhotosFlow = _newPhotosFlow.asStateFlow()

    init {
        collectionFirstPage()
    }

    private fun collectionFirstPage() {
        Timber.d("collectionFirstPage Called")
        viewModelScope.launch(Dispatchers.IO){
            val result = unsplashRepository.new(1)
            if (result is ResultWrapper.Success) {
                _newPhotosFlow.update {
                    result.value
                }
            }
        }
    }

    val state = toolbarStateManager.currentState
}