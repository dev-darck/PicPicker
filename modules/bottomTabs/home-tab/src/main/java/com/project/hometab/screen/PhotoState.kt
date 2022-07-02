package com.project.hometab.screen

import com.project.common_ui.paging.PagingData
import com.project.common_ui.paging.SettingsPaging
import com.project.model.PhotoModel

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val result: PagingData<PhotoModel>) : HomeState()
    object Exception : HomeState()
}

internal fun HomeState.updateResult(
    result: List<PhotoModel>,
    page: Int,
): HomeState =
    when (this) {
        is HomeState.Success -> {
            val paging = this.result.copy()
            paging.updateData(result).distinct()
            HomeState.Success(paging)
        }
        else -> HomeState.Success(PagingData(result, SettingsPaging(30, page, 2)))
    }