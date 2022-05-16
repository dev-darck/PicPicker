package com.project.collectionstab.viewmodel

import com.project.collection_model.CollectionModel

sealed class CollectionState {
    object Loading : CollectionState()
    data class Success(val result: List<CollectionModel>) : CollectionState()
}

