package com.project.web_view_screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.project.navigationapi.config.WebViewRoute
import com.project.navigationapi.navigation.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    navigation: Navigation,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), Navigation by navigation {
    val url by mutableStateOf(
        savedStateHandle.get<String>(WebViewRoute.schemeUrl)?.run {
            PREFIX + this
        } ?: "" 
    )
    val type by mutableStateOf(savedStateHandle.get<String>(WebViewRoute.schemeType) ?: "")

    private companion object {
        const val PREFIX = "https://"
    }
}