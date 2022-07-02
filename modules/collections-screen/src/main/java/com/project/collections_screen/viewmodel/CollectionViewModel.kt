package com.project.collections_screen.viewmodel;

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.collections_screen.MaxCollectionPage
import com.project.collections_screen.model.CollectionInfoModel
import com.project.model.PhotoModel
import com.project.navigationapi.config.CollectionScreenRoute
import com.project.navigationapi.config.DELIMITER
import com.project.navigationapi.config.DELIMITER_NAVIGATION
import com.project.navigationapi.config.Route
import com.project.navigationapi.navigation.Navigation
import com.project.unsplash_api.ResultWrapper
import com.project.unsplash_api.api.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val unsplashRepository: UnsplashRepository,
    navigation: Navigation,
) : ViewModel(), Navigation by navigation {

    private var screenDensity = 0F
    private var screenWidthDp = 0.dp
    private var spanCount = 2
    private val columnWidth
        get() = screenWidthDp / spanCount - 20.dp

    private val countPhoto = savedStateHandle.get<String>(CollectionScreenRoute.countPhoto)
    private val curator =
        savedStateHandle.get<String>(CollectionScreenRoute.curatorName)?.replace(DELIMITER, DELIMITER_NAVIGATION)
    private val collectionId = savedStateHandle.get<String>(Route.id).orEmpty()

    private val collectionInfo = CollectionInfoModel(countPhoto.orEmpty(), curator.orEmpty())

    private val photosFlow = MutableStateFlow<CollectionState>(CollectionState.Loading)
    val photos = photosFlow.asStateFlow()

    fun setNeedSize(screenDensity: Float, screenWidthDp: Dp) {
        this.screenDensity = screenDensity
        this.screenWidthDp = screenWidthDp
    }

    fun photosByCollectionFirst() {
        val state = photosFlow.value
        if (state is CollectionState.Success && state.result.isNotEmpty()) return
        photosByCollection(1)
    }

    fun photosByCollection(page: Int) {
        viewModelScope.launch {
            when (val result = unsplashRepository.collectionsById(collectionId, page, MaxCollectionPage)) {
                is ResultWrapper.Success -> {
                    photosFlow.update {
                        val homeModel = measureResult(result.value)
                        it.updateResult(homeModel, collectionInfo, page)
                    }
                }

                else -> {
                    val state = photosFlow.value
                    if (state is CollectionState.Success && state.result.isNotEmpty()) {
                        state.result.settingsPaging.errorState()
                    } else {
                        photosFlow.emit(CollectionState.Exception)
                    }
                }
            }
        }
    }

    fun retryLoading() {
        photosFlow.tryEmit(CollectionState.Loading)
        photosByCollectionFirst()
    }

    private fun measureResult(result: List<PhotoModel>): List<PhotoModel> =
        result.map { photoModel ->
            photoModel.aspectRatio = photoModel.width / photoModel.height
            photoModel.measureHeight =
                ((columnWidth.value / (photoModel.width / screenDensity)) * photoModel.height + 20 * screenDensity).toInt()
            photoModel
        }
}
