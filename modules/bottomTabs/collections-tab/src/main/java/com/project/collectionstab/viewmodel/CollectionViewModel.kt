package com.project.collectionstab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collection_model.CollectionModel
import com.project.navigationapi.navigation.Navigation
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.api.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    navigation: Navigation,
    private val unsplashRepository: UnsplashRepository,
) : ViewModel(), Navigation by navigation {

    val collection = MutableStateFlow<CollectionState>(CollectionState.Loading)

    fun collections() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = unsplashRepository.collections("1", CollectionModel::class.java)
            when (result) {
                is ResultWrapper.Success -> {
                    val old = collection.value
                    val new = CollectionState.Success(result.value)
                    collection.compareAndSet(old, new)
                }
                else -> {

                }
            }
        }
    }
}