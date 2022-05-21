package com.project.collectionstab.viewmodel

import com.project.model.CollectionModel

sealed class CollectionState {
    object Loading : CollectionState()
    data class Success(val result: List<CollectionModel>) : CollectionState()
    object Exception : CollectionState()
}

internal fun CollectionState.updateResult(result: List<CollectionModel>): CollectionState =
    when (this) {
        is CollectionState.Success -> {
            val oldValue = this.copy().result.toMutableList()
            CollectionState.Success(oldValue.plus(result))
        }
        else -> CollectionState.Success(result)
    }