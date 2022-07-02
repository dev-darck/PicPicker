package com.project.photodetail.photodetailconfig

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.project.navigationapi.config.Config
import com.project.navigationapi.config.PhotoDetailRoute
import com.project.navigationapi.config.Route
import com.project.photodetail.screen.PhotoDetail
import com.project.photodetail.viewmodel.PhotoDetailViewModel
import javax.inject.Inject

class PhotoDetailConfig @Inject constructor() : Config {
    override val route: Route = PhotoDetailRoute
    override val openScreen: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit = {
        val viewMode = hiltViewModel<PhotoDetailViewModel>(it)
        PhotoDetail(viewMode)
    }
}