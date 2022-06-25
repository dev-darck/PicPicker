package com.project.photodetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.navigationapi.config.Route
import com.project.navigationapi.navigation.Navigation
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.api.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository,
    navigation: Navigation,
) : ViewModel(), Navigation by navigation {

    private val photoID = savedStateHandle.get<String>(Route.id).orEmpty()

    private val _photo = MutableStateFlow<PhotoState>(PhotoState.Loading)
    val photo = _photo.asStateFlow()

    fun photo() {
        if (photoID.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                when (val result = unsplashRepository.photo(photoID)) {
                    is ResultWrapper.Success -> {
                        _photo.update {
                            it.updateResult(result.value)
                        }
                    }

                    else -> {
                        _photo.tryEmit(PhotoState.Exception)
                    }
                }
            }
        }
    }

    fun retryLoading() {
        _photo.tryEmit(PhotoState.Loading)
        photo()
    }
}