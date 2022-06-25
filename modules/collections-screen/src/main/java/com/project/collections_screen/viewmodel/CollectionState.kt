package com.project.collections_screen.viewmodel

import com.project.collections_screen.MaxCollectionPage
import com.project.collections_screen.model.CollectionInfoModel
import com.project.common_ui.paging.PagingData
import com.project.common_ui.paging.SettingsPaging
import com.project.model.PhotoModel

sealed class CollectionState {
    object Loading : CollectionState()
    data class Success(val result: PagingData<PhotoModel>, val collectionInfoModel: CollectionInfoModel) :
        CollectionState()

    object Exception : CollectionState()
}

internal fun CollectionState.updateResult(
    result: List<PhotoModel>,
    collectionInfoModel: CollectionInfoModel,
    page: Int,
): CollectionState =
    when (this) {
        is CollectionState.Success -> {
            val paging = this.result.copy()
            paging.updateData(result)
            CollectionState.Success(paging, collectionInfoModel)
        }

        else -> CollectionState.Success(
            PagingData(result, SettingsPaging(MaxCollectionPage, page, 2)),
            collectionInfoModel
        )
    }