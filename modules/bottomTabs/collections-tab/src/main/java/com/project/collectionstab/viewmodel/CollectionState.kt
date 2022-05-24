package com.project.collectionstab.viewmodel

import com.project.collectionstab.MaxCollectionPage
import com.project.common_ui.paging.PagingData
import com.project.common_ui.paging.SettingsPaging
import com.project.model.CollectionModel

sealed class CollectionState {
    object Loading : CollectionState()
    data class Success(val result: PagingData<CollectionModel>) : CollectionState()
    object Exception : CollectionState()
}

internal fun CollectionState.updateResult(
    result: List<CollectionModel>,
    page: Int,
): CollectionState =
    when (this) {
        is CollectionState.Success -> {
            val paging = this.result.copy()
            paging.updateData(result)
            CollectionState.Success(paging)
        }
        else -> CollectionState.Success(PagingData(result, SettingsPaging(MaxCollectionPage, page, 2)))
    }