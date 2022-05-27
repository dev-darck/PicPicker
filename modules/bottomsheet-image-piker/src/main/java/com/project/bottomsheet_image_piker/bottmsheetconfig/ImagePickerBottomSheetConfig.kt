package com.project.bottomsheet_image_piker.bottmsheetconfig

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.project.bottomsheet_image_piker.ImagePikerBottomSheet
import com.project.bottomsheet_image_piker.ImagePikerViewModel
import com.project.navigationapi.config.BottomSheetConfig
import com.project.navigationapi.config.ImagePickerBottomSheet
import com.project.navigationapi.config.Route
import javax.inject.Inject

class ImagePickerBottomSheetConfig @Inject constructor() : BottomSheetConfig {
    override val route: Route = ImagePickerBottomSheet
    override val openScreen: (AnimatedVisibilityScope.(NavBackStackEntry) -> Unit)? = null
    override val openBottomSheet: @Composable ColumnScope.(NavBackStackEntry) -> Unit = {
        val viewModel = hiltViewModel<ImagePikerViewModel>(it)
        ImagePikerBottomSheet(viewModel)
    }
}