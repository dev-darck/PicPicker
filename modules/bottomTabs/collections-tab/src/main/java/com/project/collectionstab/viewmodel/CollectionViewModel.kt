package com.project.collectionstab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collection_model.CollectionModel
import com.project.navigationapi.navigation.Navigation
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

    val collectionResult = MutableStateFlow<CollectionState>(CollectionState.Loading)

    fun collections() {
        viewModelScope.launch(Dispatchers.IO) {
            val collection = unsplashRepository.collections("1", CollectionModel::class.java)
            collectionResult.emit(CollectionState.Success(collection))
        }
    }
}
