package com.project.collectionstab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.navigationapi.navigation.Navigation
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.api.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    navigation: Navigation,
    private val unsplashRepository: UnsplashRepository,
) : ViewModel(), Navigation by navigation {

    private val collectionFlow = MutableStateFlow<CollectionState>(CollectionState.Loading)
    val collection = collectionFlow.asStateFlow()

    fun collectionFirstPage() {
        val state = collectionFlow.value
        if (state is CollectionState.Success && state.result.isNotEmpty()) return
        collections(1)
    }

    fun collections(page: Int) {
        viewModelScope.launch {
            when (val result = unsplashRepository.collections(page.toString())) {
                is ResultWrapper.Success -> {
                    collectionFlow.update {
                        it.updateResult(result.value)
                    }
                }
                else -> {

                }
            }
        }
    }
}