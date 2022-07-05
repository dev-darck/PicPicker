package com.project.searchsreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.project.navigationapi.config.Route
import com.project.navigationapi.config.SearchScreenRoute
import com.project.navigationapi.navigation.Navigation
import com.project.searchsreen.screen.TitleSelection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    navigation: Navigation,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), Navigation by navigation {

    private var currentQuery: String = ""
    private var selection: TitleSelection = TitleSelection.PROFILE

    val query: String = SearchScreenRoute.queryNotDefault(savedStateHandle.get<String>(Route.query).orEmpty())

    fun search(query: String, titleSelection: TitleSelection) {
        currentQuery = query
        selection = titleSelection
    }
}