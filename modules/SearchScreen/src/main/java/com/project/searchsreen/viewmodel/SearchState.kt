package com.project.searchsreen.viewmodel

import com.project.common_ui.paging.PagingData
import com.project.common_ui.paging.PagingState
import com.project.common_ui.paging.SettingsPaging
import com.project.model.*
import com.project.searchsreen.screen.TitleSelection

sealed class SearchState {
    data class Success(
        val isShowInfoNewResult: Boolean = false,
        val queryPositive: String,
        val users: PagingData<User>? = null,
        val collections: PagingData<CollectionModel>? = null,
        val photos: PagingData<PhotoModel>? = null,
        val selections: TitleSelection,
    ) : SearchState()

    data class NotFindContent(val query: String) : SearchState()
    object Exception : SearchState()
    object Loading : SearchState()
}

fun SearchState.updateSuccess(
    searchModel: SearchModel,
    titleSelection: TitleSelection,
    isSearch: Boolean = true,
    query: String,
    page: Int
): SearchState {
    return if (this is SearchState.Success) {
        val settingsPaging = SettingsPaging(30, page, 2)
        when (titleSelection) {
            TitleSelection.PHOTOS -> {
                val photos = searchModel as SearchPhoto
                val result = photos.result ?: return SearchState.NotFindContent("Photos not found")
                var oldPhoto = this.photos?.copy() ?: PagingData(emptyList<PhotoModel>(), settingsPaging)
                if (isSearch) {
                    oldPhoto = PagingData(result, settingsPaging)
                } else {
                    oldPhoto.updateData(result).distinct()
                }
                if (result.isEmpty()) {
                    oldPhoto.settingsPaging.updateState(PagingState.Success)
                }
                SearchState.Success(
                    photos = oldPhoto,
                    selections = titleSelection,
                    isShowInfoNewResult = result.isEmpty(),
                    queryPositive = if (result.isEmpty()) queryPositive else query
                )
            }

            TitleSelection.PROFILE -> {
                val profiles = searchModel as SearchUser
                val result = profiles.result ?: return SearchState.NotFindContent("Profiles not found")
                var oldUser = this.users?.copy() ?: PagingData(emptyList<User>(), settingsPaging)
                if (isSearch) {
                    oldUser = PagingData(result, settingsPaging)
                } else {
                    oldUser.updateData(result).distinct()
                }
                if (result.isEmpty()) {
                    oldUser.settingsPaging.updateState(PagingState.Success)
                }
                SearchState.Success(
                    users = oldUser,
                    selections = titleSelection,
                    isShowInfoNewResult = result.isEmpty(),
                    queryPositive = if (result.isEmpty()) queryPositive else query
                )
            }

            TitleSelection.COLLECTIONS -> {
                val collections = searchModel as SearchCollection
                val result = collections.result ?: return SearchState.NotFindContent("Collections not found")
                var oldCollections =
                    this.collections?.copy() ?: PagingData(emptyList<CollectionModel>(), settingsPaging)
                if (isSearch) {
                    oldCollections = PagingData(result, settingsPaging)
                } else {
                    oldCollections.updateData(result).distinct()
                }
                if (result.isEmpty()) {
                    oldCollections.settingsPaging.updateState(PagingState.Success)
                }
                oldCollections.updateData(result).distinct()
                SearchState.Success(
                    collections = oldCollections,
                    selections = titleSelection,
                    isShowInfoNewResult = result.isEmpty(),
                    queryPositive = if (result.isEmpty()) queryPositive else query
                )
            }
        }
    } else {
        val settingsPaging = SettingsPaging(30, page, 2)
        when (titleSelection) {
            TitleSelection.PHOTOS -> {
                val photos = searchModel as SearchPhoto
                val result = photos.result ?: return SearchState.NotFindContent("Photos not found")
                val paddingData = PagingData(result, settingsPaging)
                SearchState.Success(photos = paddingData, selections = titleSelection, queryPositive = query)
            }

            TitleSelection.PROFILE -> {
                val profiles = searchModel as SearchUser
                val result = profiles.result ?: return SearchState.NotFindContent("Profiles not found")
                val paddingData = PagingData(result, settingsPaging)
                SearchState.Success(users = paddingData, selections = titleSelection, queryPositive = query)
            }

            TitleSelection.COLLECTIONS -> {
                val collections = searchModel as SearchCollection
                val result = collections.result ?: return SearchState.NotFindContent("Collections not found")
                val paddingData = PagingData(result, settingsPaging)
                SearchState.Success(collections = paddingData, selections = titleSelection, queryPositive = query)
            }
        }
    }
}
